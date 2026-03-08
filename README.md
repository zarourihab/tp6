Étape 8 : Implémentation d'une stratégie de gestion des conflits
1. Objectif

L’objectif de ce TP est de comprendre le fonctionnement du verrouillage optimiste (Optimistic Locking) avec JPA et Hibernate afin d’éviter les conflits lorsque plusieurs utilisateurs modifient les mêmes données en même temps.

2. Technologies utilisées

Dans ce projet nous avons utilisé :

Java

Maven

JPA (Java Persistence API)

Hibernate

Base de données H2

SLF4J pour les logs

3. Structure du projet

Le projet Maven contient :

Entités

Utilisateur

Salle

Reservation

Service

ReservationService

ReservationServiceImpl

Classe principale

ConcurrentReservationSimulator

4. Principe du verrouillage optimiste

Le verrouillage optimiste permet de détecter les conflits de modification lorsque plusieurs transactions modifient la même entité.

Dans l’entité Reservation, on utilise l’annotation :

@Version
private Long version;

Hibernate incrémente automatiquement ce numéro de version à chaque modification.

Si deux transactions modifient la même réservation, Hibernate détecte le conflit et génère une OptimisticLockException.

5. Simulation du conflit

Pour tester le verrouillage optimiste :

Création des données :

utilisateurs

salle

réservation

Création de deux threads qui modifient la même réservation.

Le premier thread met à jour la réservation avec succès.

Le deuxième thread tente de modifier une ancienne version et reçoit une OptimisticLockException.
<img width="960" height="509" alt="image" src="https://github.com/user-attachments/assets/bae49db6-b518-44b5-b524-1745e4aa2cb9" />
<img width="960" height="540" alt="image" src="https://github.com/user-attachments/assets/b325f502-43b4-4a5d-8b04-38b60989353b" />
<img width="1920" height="1017" alt="Capture d&#39;écran 2026-03-04 234020" src="https://github.com/user-attachments/assets/755e3287-8d17-4f6e-8135-2d6bd2928ecf" />
<img width="960" height="509" alt="image" src="https://github.com/user-attachments/assets/70bc3de2-4831-41d7-a9b7-69dfad7f47b3" />
<img width="960" height="512" alt="image" src="https://github.com/user-attachments/assets/a1ce9bdc-3feb-464c-815a-ae2aac6c11e4" />
<img width="960" height="509" alt="image" src="https://github.com/user-attachments/assets/4a152dbe-d7ee-4b7a-9aaf-d95b0d1736de" />
resultats
Lors de l’exécution, un seul thread peut mettre à jour la réservation.
L’autre thread détecte un conflit de concurrence, ce qui prouve que le verrouillage optimiste fonctionne correctement.
<img width="960" height="503" alt="image" src="https://github.com/user-attachments/assets/f67f0c7b-eeaa-4663-aedb-6f4f24f7965f" />
<img width="960" height="511" alt="image" src="https://github.com/user-attachments/assets/92b6ea72-1504-4142-aaee-ad6a149d59a3" />
<img width="960" height="512" alt="image" src="https://github.com/user-attachments/assets/c168519f-f8e8-4043-8bab-d6b56f68a117" />
<img width="960" height="540" alt="image" src="https://github.com/user-attachments/assets/5008cfbc-e2d8-4083-81c7-f55f041b501c" />
<img width="960" height="511" alt="image" src="https://github.com/user-attachments/assets/a4924327-83ee-4bce-a131-f66d5e6c9d2d" />
<img width="960" height="503" alt="image" src="https://github.com/user-attachments/assets/5554dcb3-0bba-4c8f-af77-4f6044c90b2b" />
<img width="960" height="514" alt="image" src="https://github.com/user-attachments/assets/89ebd597-7a9c-4c1f-9716-cc7466faff14" />


