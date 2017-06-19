# ykwBonjourAUSY_Android

## Server Part

#### Prerequists:
1. Docker Machine (If you use Mac)
2. Virtualbox
3. TextEditor (like Sublime)

#### STEP:
1. Download the part of server (Docker): https://github.com/amelieykw/ykwBonjourAUSY_dockerServer

2. Start Docker machine on your Mac, then open the terminal, create a docker machine and name it like "BonjourAUSY"
```
docker-machine create --drive virtualbox BonjourAUSY
```
3. To check the docker machine that you created:
```
docker-machine ls
```
you should see the docker machine you just created.

4. If its status is not running, start it by
```
docker-machine start BonjourAUSY
```
If it is running, then like your terminal with the terminal of this docker machine by
```
docker-machine env BonjourAUSY

eval $(docker-machine env BonjourAUSY)
```
5. Once your terminal becomes the terminal of the docker machine you created, you can build the images on this docker machine.
There are 4 images you should build:
* php7_fpm_base
* app
* webnginx
* mysql

```
1. /images/php7_fpm_base:  {php7_fpm_base}

* docker build -t bonjourausy_php7_fpm_base .

* docker tag bonjourausy_php7_fpm_base $DOCKER_ID_USER/bonjourausy_php7_fpm_base

* docker push $DOCKER_ID_USER/bonjourausy_php7_fpm_base

* docker images

- php: 7-fpm

- bonjourausy_php7_fpm_base:latest

- amelieykw/bonjourausy_php7_fpm_base:latest
```
```
2. /images/app:  {app}

* docker build -t bonjourausy_app .

* docker tag bonjourausy_app $DOCKER_ID_USER/bonjourausy_app

* docker push $DOCKER_ID_USER/bonjourausy_app

* docker images

- bonjourausy_app : latest

- amelieykw/bonjourausy_app : latest
```
```
3. /images/nginx:   {webnginx}

* docker build -t bonjourausy_webnginx .

* docker tag bonjourausy_webnginx $DOCKER_ID_USER/bonjourausy_webnginx

* docker push $DOCKER_ID_USER/bonjourausy_webnginx

* docker images

- nginx: latest

- bonjourausy_webnginx: latest

- amelieykw/bonjourausy_webnginx: latest
```
```
4. /images/mysql:   {mysql}

* docker build -t bonjourausy_mysql .

* docker tag bonjourausy_mysql $DOCKER_ID_USER/bonjourausy_mysql

* docker push $DOCKER_ID_USER/bonjourausy_mysql

* docker images

- mysql: latest

- bonjourausy_mysql: latest

- amelieykw/bonjourausy_mysql: latest
```

6. Check the IP address of your docker machine by:
```
docker-machine ip MACHINE_NAME
```
Then open <ip:8080> on your browser, you shall see the content of the page.

7. In the webpage, do
* create the table in order :
  * administrator
  * site
  * contact
  * rendezvous
* insert into table in order:
  * administrator
  * site
  * contact
  * rendezvous
  
8. Then if you access into the container of mysql, you'll see the initial data.
To find the name of the mysql image
```
docker images
```
To find the ID of the container of mysql image
```
docker container ls
```
Use the container ID to access into the mysql container
```
docker exec -it CONTAINER_ID /bin/bash
mysql -u root -p
admin
use bonjourausy
```

## Android Part

#### Prerequists:
1. Android Studio 3
2. Genymotion

#### STEP:
1. change the docker machine ip address in 


