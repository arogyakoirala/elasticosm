# FROM node:latest

# COPY package*.json /
# # RUN npm install
# # required to serve the react app on the live server
# COPY . /app
# WORKDIR /app



# # # noop files for non python projects and local development
# # RUN echo "#!/bin/bash" > /app/migrate.sh && chmod +x /app/migrate.sh
# # RUN echo "#!/bin/bash" > /usr/local/bin/start && chmod +x /usr/local/bin/start
# RUN npm i -g cross-env
# COPY start.sh ./start.sh
# RUN chmod +x ./start.sh
# CMD ./start.sh


# pull official base image
FROM node:13.12.0-alpine

# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# install app dependencies
COPY package.json ./
COPY package-lock.json ./
RUN npm install --silent
RUN npm install react-scripts@3.4.1 -g --silent

# add app
COPY . ./

# start app
CMD ["npm", "start"]