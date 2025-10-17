# Stage 1: Build Angular app with Node 20
FROM node:20-alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

# Build Angular app for production
RUN npm run build

# Debug: List contents of dist folder to verify build output
RUN ls -l /app/dist

# Stage 2: Serve with Nginx
FROM nginx:stable-alpine

# Copy the build output
COPY --from=build /app/dist/ecommerce-frontend /usr/share/nginx/html

# Copy the custom Nginx configuration file
COPY default.conf /etc/nginx/conf.d/

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
