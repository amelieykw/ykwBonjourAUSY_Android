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

6. The create all the container on this docker machine, run this command in the root of server-part project:
```
docker-compose up -d
```
Check the IP address of your docker machine by:
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
1. change the docker machine ip address in /app/build.gradle to your docker machine ip address.
```
    compileSdkVersion 25
    buildToolsVersion "25.0.2"
    defaultConfig {
        applicationId "com.ausy.yu.bonjourausy"
        minSdkVersion 15
        targetSdkVersion 25
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

        buildConfigField "int", "LIMIT", "100"
        buildConfigField "String", "BASEURL", "\"http://192.168.99.100:8080/\""
        buildConfigField "int", "CACHETIME", "432000" // 5days
    }
```
2. Launche the app on Genymotion, Android Tablet Nexus 9 to test if there's a problem.

#### Attention:
1. All the images you build are local. So you can only use app in Genymotion to interact with the server side.
If you want to use a real Android device to test, you should first upload all your local images to the docker cloud platform (first create a node of DigitalOcean, then copy the content of file docker-cloud.yml to create a stack, deploy the stack, then open the service link into the browser, you'll see the same content of webpage). The problem is that we cannot open the page if we link the Internet of AUSY because of the firewall. But if we use other like the hot spot of our own phone, we can open it without any problems.

2. You can test everything with Genymotion (version for personal use) except the fonction of SMS. 
To send the SMS, you have 2 way:
* buy the Genymotion version professional
* test it with the real Android Device + Server running on Docker Cloud 

3. About the Structure of Android app, I'll upload the framework at noon.

#### Docker Cloud Platform which I use:
https://cloud.docker.com/app/amelieykw/dashboard/onboarding/cloud-registry

Login:
amelieykw1991@gmail.com
MickyMouse1991


## Helpful reading:
1. docker command helpful for this project:
http://www.jianshu.com/p/168abd5ec825

2. differences of docker for Mac and Linux:
http://www.jianshu.com/p/22239a8a8e6e
