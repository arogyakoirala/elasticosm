#!/bin/bash
# echo "API_BASE_URL=$API_BASE_URL" >> .env

# npm run start



# Try
# ENV PATH=/node_modules/.bin:$PATH
# ENV PORT=3000
# ENV HOST=0.0.0.0
# ENV BROWSER='none'
npm i 
npm run build
export NODE_OPTIONS=--openssl-legacy-provider

npm i -g serve
serve -s build -l 3000
# CMD ["serve", "-s", "build", "-l", "3000"]
