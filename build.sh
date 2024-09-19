#!/bin/bash
HERE=$(pwd)
PROJECT_PATH=$HERE
JSD_PATH=$HERE/tools/java_service_drydock

git submodule init
git submodule update

source $JSD_PATH/actions.sh

echo -e "\033[1;91mInitializing build system\033[0m"
init $HERE/config.yml

echo -e "\033[1;91mBuilding domain model\033[0m"
build_lib_22 $HERE/app/domain domain 

