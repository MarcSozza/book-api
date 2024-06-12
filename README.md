# Objectif de l'exercice
Concevez et mettez en œuvre une API REST simple à l'aide du framework Spring qui gère une bibliothèque en ligne.

## Scénario métier
Vous travaillez pour une bibliothèque en ligne appelée "BookStore". Cette bibliothèque sert une variété de clients allant des lecteurs ordinaires aux chercheurs universitaires. Votre tâche est de développer une API REST à l'aide du framework Spring pour aider à gérer les ressources de la bibliothèque en ligne.

## L'API doit offrir les fonctionnalités suivantes :

### Gestion des livres
- Ajout d'un nouveau livre (POST)
- Récupération de détails d'un livre spécifique (GET)
- Mise à jour des détails d'un livre (PUT)
- Suppression d'un livre de la bibliothèque (DELETE)
- Récupération de tous les livres disponibles dans la bibliothèque (GET)

Chaque livre doit avoir les champs suivants :
- ID
- Titre
- Auteur
- Année de publication
- Genre
- Résumé

### Gestion des utilisateurs
- Inscription d'un nouvel utilisateur (POST)
- Récupération de détails d'un utilisateur spécifique (GET)
- Mise à jour des détails d'un utilisateur (PUT)
- Suppression d'un utilisateur (DELETE)
- Récupération de tous les utilisateurs inscrits à la bibliothèque (GET)

Chaque utilisateur doit avoir les champs suivants :
- ID
- Nom
- Prénom
- Email
- Mot de passe

### Gestion des emprunts
- Un utilisateur peut emprunter un livre (POST)
- Un utilisateur peut rendre un livre (PUT)
- Récupération de tous les livres actuellement empruntés par un utilisateur spécifique (GET)
- Récupération de tous les utilisateurs qui ont actuellement emprunté un livre spécifique (GET)

Vous devez utiliser les bonnes pratiques pour développer cette API, comme l'utilisation d'entités, de services, de contrôleurs, etc. Vous devez également gérer les erreurs de manière appropriée pour informer l'utilisateur lorsqu'une action invalide est effectuée.
Une fois que vous avez terminé de développer l'API, vous pouvez utiliser un outil comme Postman pour tester chaque point de terminaison et vérifier si votre API fonctionne comme prévu. Bonne chance !