docker pull mysql:latest
docker run --name db -e MYSQL_ROOT_PASSWORD=123456 -d -p 3306:3306 mysql:latest

docker pull redis
docker run --name my-redis -p 6379:6379 -d redis