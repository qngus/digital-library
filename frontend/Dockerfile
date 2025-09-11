# Étape 1 : Build de l'application
FROM node:20-alpine AS build

WORKDIR /app

COPY package*.json ./

RUN npm install
RUN npm install -g vite vue-tsc npm-run-all

COPY . .

RUN npm run build

# Étape 2 : Serveur Nginx
FROM nginx:alpine

COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
