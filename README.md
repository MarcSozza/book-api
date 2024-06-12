# Objectif de l'exercice
Concevez et mettez en œuvre une API REST simple à l'aide du framework Spring qui gère une bibliothèque en ligne.

# Scénario métier
Vous travaillez pour une bibliothèque en ligne appelée "BookStore". Cette bibliothèque sert une variété de clients allant des lecteurs ordinaires aux chercheurs universitaires. Votre tâche est de développer une API REST à l'aide du framework Spring pour aider à gérer les ressources de la bibliothèque en ligne.

L'API doit offrir les fonctionnalités suivantes :

## Gestion des livres
- Ajout d'un nouveau livre (POST)
  - Règle métier : Un livre avec le même titre et le même auteur ne peut pas être ajouté à nouveau. Si un tel livre existe déjà, une erreur devrait être retournée.
  - Règle métier : Un livre ne peut pas être ajouté si l'année de publication est supérieure à l'année en cours. Si une telle demande est effectuée, une erreur doit être retournée.
  - Règle métier : Le genre du livre doit correspondre à une liste prédéfinie de genres. Si l'utilisateur entre un genre qui ne se trouve pas dans cette liste, une erreur est renvoyée.
  - Règle métier : Chaque auteur ne peut avoir que 50 livres publiés dans la bibliothèque. Si un nouvel ouvrage d'un auteur est ajouté et que l'auteur a déjà 50 ouvrages dans la bibliothèque, une erreur est renvoyée.
  - Règle métier : Le livre doit avoir au moins 10 pages. Si le livre a moins de 10 pages, une erreur est renvoyée.

- Récupération de détails d'un livre spécifique (GET)
- Mise à jour des détails d'un livre (PUT)
  - Règle métier : Seuls le résumé du livre peut être modifié. Les autres détails comme le titre, l'auteur, le genre ne peuvent pas être modifiés. Si une telle demande est faite, une erreur est renvoyée.
  - Règle métier : Les détails d'un livre qui est actuellement emprunté ne peuvent pas être modifiés. Si une telle demande est effectuée, une erreur doit être retournée.

- Suppression d'un livre de la bibliothèque (DELETE)
  - Règle métier : Un livre qui est actuellement emprunté ne peut pas être supprimé. Si une telle demande est effectuée, une erreur doit être retournée.
  - Règle métier : Les livres qui sont dans la bibliothèque depuis plus de 10 ans et qui n'ont pas été empruntés au cours des deux dernières années doivent être retirés de la bibliothèque.

- Récupération de tous les livres disponibles dans la bibliothèque (GET)

Chaque livre doit avoir les champs suivants :
- ID
- Titre
- Auteur
- Année de publication
- Genre
- Résumé

## Gestion des utilisateurs
- Inscription d'un nouvel utilisateur (POST)
  - Règle métier : L'email de l'utilisateur doit être unique. Si un utilisateur avec le même email existe déjà, une erreur devrait être retournée.
  - Règle métier : Le mot de passe d’un utilisateur doit avoir une longueur minimale de 8 caractères et contenir au moins un chiffre, une lettre minuscule, une lettre majuscule et un caractère spécial. Si le mot de passe ne répond pas à ces exigences, une erreur est renvoyée.
- Récupération de détails d'un utilisateur spécifique (GET)
- Mise à jour des détails d'un utilisateur (PUT)
  - Règle métier : L'email d'un utilisateur ne peut pas être modifié en un email qui est déjà utilisé par un autre utilisateur. Si une telle demande est effectuée, une erreur doit être retournée.

- Suppression d'un utilisateur (DELETE)
  - Règle métier : Un utilisateur qui a actuellement un livre emprunté ne peut pas être supprimé. Si une telle demande est effectuée, une erreur doit être retournée.

- Récupération de tous les utilisateurs inscrits à la bibliothèque (GET)

Chaque utilisateur doit avoir les champs suivants :
- ID
- Nom
- Prénom
- Email
- Mot de passe

## Gestion des emprunts
- Un utilisateur peut emprunter un livre (POST)
  - Règle métier : Un utilisateur ne peut emprunter qu'un nombre limité de livres à la fois, par exemple 5. Si un utilisateur essaie d'emprunter plus de livres, une erreur devrait être retournée.
  - Règle métier : Un utilisateur ne peut emprunter que deux livres d'un genre particulier à la fois. Si un utilisateur essaie d'emprunter un troisième livre du même genre, une erreur est renvoyée.
  - Règle métier : Un livre ne peut être emprunté que s'il est actuellement disponible. Si le livre est actuellement emprunté à un autre utilisateur, une erreur doit être retournée.
  - Règle métier : Un utilisateur ne peut pas emprunter un livre qui est déjà emprunté par lui-même. Si un tel livre est demandé, une erreur est retournée.
  - Règle métier : La durée maximale d'un emprunt est de 2 semaines. Si un utilisateur essaie d'emprunter un livre pour plus de 2 semaines, une erreur est renvoyée.

- Un utilisateur peut rendre un livre (PUT)
  - Règle métier : Un utilisateur ne peut rendre que des livres qu'il a actuellement empruntés. Si un utilisateur essaie de rendre un livre qu'il n'a pas emprunté, une erreur doit être retournée.
  - Règle métier : Si le livre est retourné endommagé (comme déterminé par un champ dans la requête), une amende est appliquée à l'utilisateur.

- Récupération de tous les livres actuellement empruntés par un utilisateur spécifique (GET)
- Récupération de tous les utilisateurs qui ont actuellement emprunté un livre spécifique (GET)
