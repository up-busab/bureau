#server will be built in  ${RUNTIME}/${NAME} and server volumes will be linked to ${RUNTIME}/volumes/${NAME} 
#clean will remove running containers and server config
#sterile will clean, then remove ALL server volumes and ALL images- even unrelated ones

NAME = test

ROOT = ../../..
RUNTIME = ${ROOT}/runtime
IMAGES = ${ROOT}/config/images
COMPOSITIONS = ${ROOT}/config/compositions

SERVER = ${RUNTIME}/${NAME}
VOLUMES = ${abspath ${RUNTIME}/volumes/${NAME}}

export PATH := ${abspath ${ROOT}/runtime/bin/}:${PATH}

default: tomcat mariadb instance 

tomcat: ${IMAGES}/tomcat/Dockerfile
	docker build --tag bureau/tomcat ${IMAGES}/tomcat

mariadb: ${IMAGES}/mariadb/Dockerfile
	docker build --tag bureau/mariadb ${IMAGES}/mariadb

instance: ${SERVER} volumes_link config
 
${SERVER}:
	mkdir ${SERVER}

volumes_link: ${VOLUMES}
	ln -s ${VOLUMES} ${SERVER}/volumes  

${VOLUMES}:
	mkdir ${VOLUMES}  

config: ${COMPOSITIONS}/server-runtime.yml ${COMPOSITIONS}/sql.env
	cp ${COMPOSITIONS}/server-runtime.yml ${SERVER}
	cp ${COMPOSITIONS}/sql.env ${SERVER} 

clean:
	rm -rf ${SERVER}
	exec docker-clean

sterile: clean
	rm -r ${VOLUMES}
	exec ./docker-rmi
