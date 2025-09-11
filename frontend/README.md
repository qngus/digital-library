# Book UI

Book UI est le frontend de l'application **Library**, construit avec **Vue.js 3**, **PrimeVue** et **Vite**. Cette application permet de consulter, créer et gérer des livres dans la bibliothèque.

---

## Prérequis

- Node.js (version 20.19.0 ou >= 22.12.0)
- npm
- Docker (pour le déploiement en conteneur)
- Nginx (inclus dans le conteneur Docker)

---

## Installation en développement

```bash
npm install
```
Lancer l'application en mode développement :

```bash
npm run dev
```
L'application sera accessible sur http://localhost:5173 (Vite définit ce port par défaut).

Scripts npm disponibles
| Commande              | Description |
|-----------------------|-------------|
| `npm run dev`         | Démarre le serveur de développement Vite |
| `npm run build`       | Compile l'application pour la production |
| `npm run preview`     | Prévisualise la version buildée de l'application |
| `npm run lint`        | Vérifie et corrige le code avec ESLint |
| `npm run format`      | Formate le code source avec Prettier |
| `npm run type-check`  | Vérifie les types TypeScript |
| `npm run test`        | Lance les tests unitaires avec Vitest |
| `npm run test:ui`     | Lance l'interface de tests Vitest UI |


## Build Docker
L'application peut être exécutée dans un conteneur Docker avec Nginx.

Construire l'image Docker :

```bash
docker build -t book-ui .
```

Lancer le conteneur :

```bash
docker run -p 5173:80 book-ui
```
L'application sera accessible sur http://localhost:5173.