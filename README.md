# Library Project

Ce projet est une application de gestion de bibliothèque, composée de deux parties principales : le **backend** et le **frontend**. Chaque partie a son propre README avec des instructions détaillées, mais ce document vous donne une vue d'ensemble et la manière de lancer le projet dans son ensemble.

---


## Aperçu

- **Backend** : API REST construite avec Spring Boot, exposant les endpoints pour gérer les livres, auteurs et autres entités de la bibliothèque.
- **Frontend** : Application Vue.js 3 avec Vite et PrimeVue, permettant d'interagir avec l'API backend pour consulter et modifier les livres.

---

## Prérequis

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- Node.js et npm (pour le développement frontend)
- Java (pour le développement backend)

---

## Cloner le dépôt :

```bash
git clone https://github.com/qngus/digital-library.git
```

## Lancer le projet avec Docker Compose

Le projet peut être lancé entièrement via Docker Compose. Cela créera et démarrera les conteneurs pour le backend, le frontend et la base de données.

1. Depuis la racine du projet, exécuter :

```bash
docker-compose up --build
```
Accéder aux services :

Frontend : http://localhost:5173

Backend : http://localhost:8080

Pour arrêter les services :

```bash
docker-compose down
```

## Développement local
### Backend
Se rendre dans le dossier backend/ et suivre les instructions du README.md spécifique pour lancer le backend en mode développement.

```bash
cd backend
```

### Frontend
Se rendre dans le dossier frontend/ et suivre les instructions du README.md spécifique pour lancer le frontend en mode développement.

```bash
cd frontend
```
