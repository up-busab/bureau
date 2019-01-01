#yes, its a makefile, get over it

ROOT = ../../../
SOURCE = ${ROOT}/source/applications
TMP = ./tmp
BUREAU = ${TMP}/bureau 

DEPLOY = ${ROOT}/runtime/volumes

default: package

install: package
	cp -f bureau.war ${DEPLOY}

uninstall: 
	rm -rf ${DEPLOY}/bureau*

package: bureau.war

bureau.war: source
	cd ${BUREAU} && mvn package 

source: ${TMP} tmp_source

${TMP}:
	mkdir ${TMP}

tmp_source:
	cp -rRf ${SOURCE}/* ${TMP}

clean:
	rm -rf ${BUREAU}/bureau.war

cleaner: clean
	cd ${BUREAU} && mvn clean
	rm -rf ${BUREAU}/client/dist
	rm -rf ${BUREAU}/server/target

sterile: 
	rm -rf ${TMP}
