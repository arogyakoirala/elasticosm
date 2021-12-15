docker-compose down
docker system prune --volumes -f
rm -rf es-data
rm -rf status
docker-compose build
docker-compose up