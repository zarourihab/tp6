
package com.example;

import com.example.model.Reservation;
import com.example.model.Salle;
import com.example.model.Utilisateur;
import com.example.service.ReservationService;
import com.example.service.ReservationServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

public class ConcurrentReservationSimulator {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("optimistic-locking-demo");

    private static final ReservationService reservationService =
            new ReservationServiceImpl(emf);

    private static Long reservationId;

    public static void main(String[] args) throws InterruptedException {
        initData();
        simulateConcurrentReservationConflict();
        emf.close();
    }

    private static void initData() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Utilisateur utilisateur1 = new Utilisateur("Dupont", "Jean", "jean.dupont@example.com");
            Utilisateur utilisateur2 = new Utilisateur("Martin", "Sophie", "sophie.martin@example.com");

            Salle salle = new Salle("Salle A101", 30);
            salle.setDetails("Salle de réunion équipée d'un projecteur");

            em.persist(utilisateur1);
            em.persist(utilisateur2);
            em.persist(salle);

            Reservation reservation = new Reservation(
                    LocalDateTime.now().plusDays(1).withHour(10).withMinute(0),
                    LocalDateTime.now().plusDays(1).withHour(12).withMinute(0),
                    "Réunion d'équipe"
            );
            reservation.setUtilisateur(utilisateur1);
            reservation.setSalle(salle);

            em.persist(reservation);

            em.getTransaction().commit();

            reservationId = reservation.getId();

            System.out.println("Données initialisées avec succès !");
            System.out.println("Réservation créée : " + reservation);
            System.out.println("ID réservation = " + reservationId);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void simulateConcurrentReservationConflict() throws InterruptedException {
        Optional<Reservation> reservationOpt = reservationService.findById(reservationId);
        if (!reservationOpt.isPresent()) {
            System.out.println("Réservation non trouvée !");
            return;
        }

        System.out.println("Réservation récupérée : " + reservationOpt.get());

        CountDownLatch latch = new CountDownLatch(1);

        Thread thread1 = new Thread(() -> {
            try {
                latch.await();
                Reservation r1 = reservationService.findById(reservationId).get();
                System.out.println("Thread 1 : version = " + r1.getVersion());

                Thread.sleep(1000);

                r1.setMotif("Réunion d'équipe modifiée par Thread 1");
                try {
                    reservationService.update(r1);
                    System.out.println("Thread 1 : mise à jour OK");
                } catch (OptimisticLockException e) {
                    System.out.println("Thread 1 : conflit Optimistic Lock !");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                latch.await();
                Reservation r2 = reservationService.findById(reservationId).get();
                System.out.println("Thread 2 : version = " + r2.getVersion());

                r2.setDateDebut(r2.getDateDebut().plusHours(1));
                r2.setDateFin(r2.getDateFin().plusHours(1));

                try {
                    reservationService.update(r2);
                    System.out.println("Thread 2 : mise à jour OK");
                } catch (OptimisticLockException e) {
                    System.out.println("Thread 2 : conflit Optimistic Lock !");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        latch.countDown();

        thread1.join();
        thread2.join();

        Optional<Reservation> finalReservationOpt = reservationService.findById(reservationId);
        finalReservationOpt.ifPresent(r -> {
            System.out.println("\nÉtat final de la réservation :");
            System.out.println("ID : " + r.getId());
            System.out.println("Motif : " + r.getMotif());
            System.out.println("Date début : " + r.getDateDebut());
            System.out.println("Date fin : " + r.getDateFin());
            System.out.println("Version : " + r.getVersion());
        });
    }
}