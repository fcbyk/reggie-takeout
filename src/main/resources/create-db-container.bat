@echo off

@REM 下载镜像
docker pull mysql:8

@REM 创建并运行运行容器
docker run -id -p 3306:3306 --name db_reggie -e MYSQL_ROOT_PASSWORD=123456 mysql:8

echo accomplish

pause