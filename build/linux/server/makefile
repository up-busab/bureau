#server will be built in  ${RUNTIME}/${NAME} and server volumes will be linked to ${RUNTIME}/volumes/${NAME} 
#clean will remove running containers and server config
#sterile will clean, then remove ALL server volumes and ALL images- even unrelated ones

NAME = test

ROOT = ${abspath ../../..}
RUNTIME = ${ROOT}/runtime
IMAGES = ${ROOT}/config/images
COMPOSITIONS = ${ROOT}/config/compositions
SOURCE = ${ROOT}/source/server

SERVER = ${RUNTIME}/${NAME}
VOLUMES = ${RUNTIME}/volumes/${NAME}

export PATH := ${abspath ${ROOT}/runtime/bin/}:${PATH}

default: tomcat mariadb instance 

tomcat: ${IMAGES}/tomcat/Dockerfile server_webapps
	docker build --tag bureau/tomcat ${IMAGES}/tomcat

server_webapps: ${VOLUMES}/tomcat/webapps
	cp -rf 	${SOURCE}/webapps/* ${VOLUMES}/tomcat/webapps  

${VOLUMES}/tomcat/webapps: ${VOLUMES}/tomcat
	mkdir ${VOLUMES}/tomcat/webapps

${VOLUMES}/tomcat: ${VOLUMES}
	mkdir ${VOLUMES}/tomcat 

${VOLUMES}:
	mkdir ${VOLUMES}

mariadb: ${IMAGES}/mariadb/Dockerfile
	docker build --tag bureau/mariadb ${IMAGES}/mariadb

instance: ${SERVER} volumes_link config
 
${SERVER}:
	mkdir ${SERVER}

volumes_link: ${VOLUMES}   
	ln -s ${VOLUMES} ${SERVER}/volumes  

config: ${COMPOSITIONS}/server-runtime.yml ${COMPOSITIONS}/mariadb ${COMPOSITIONS}/tomcat 
	cp ${COMPOSITIONS}/server-runtime.yml ${SERVER}
	cp -r ${COMPOSITIONS}/mariadb ${SERVER} 
	cp -r ${COMPOSITIONS}/tomcat ${SERVER} 

clean:
	rm -rf ${SERVER}
	exec docker-clean

sterile: clean
	exec ./docker-rmi
	rm -r ${VOLUMES}
