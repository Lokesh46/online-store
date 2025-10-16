# Stage 1: Build Angular app
FROM node:18-alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

# Build Angular app for production
RUN npm run build -- --prod

# Stage 2: Serve with Nginx
FROM nginx:stable-alpine

COPY --from=build /app/dist/your-angular-app-name /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
