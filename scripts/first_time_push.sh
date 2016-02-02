#!/bin/bash 
set -e

abort()
{
    echo >&2 '
    ***************
    *** ABORTED ***
    ***************
    '
    echo "An error occurred. Exiting..." >&2
    exit 1
}

summary()
{
  echo_msg "Current Apps & Services in CF_SPACE"
  cf apps
  cf services
}

echo_msg()
{
  echo ""
  echo "************** ${1} **************"
}

build()
{
  echo_msg "Building application"
  ./gradlew build 
}

cf_app_delete()
{
  EXISTS=`cf apps | grep ${1} | wc -l | xargs`
  if [ $EXISTS -ne 0 ]
  then
    echo "Deleting app"
    cf delete -f -r ${1}
  fi
}

cf_service_delete()
{
  #Were we supplied an App name?
  if [ ! -z "${2}" ]
  then
    EXISTS=`cf services | grep ${1} | grep ${2} | wc -l | xargs`
    if [ $EXISTS -ne 0 ]
    then
      cf unbind-service ${1} ${2}
    fi
  fi

  #Delete the Service Instance
  EXISTS=`cf services | grep ${1} | wc -l | xargs`
  if [ $EXISTS -ne 0 ]
  then
    cf delete-service -f ${1}
  fi
}

check_cli_installed()
{
  #Is the CF CLI installed?
  echo_msg "Targeting the following CF Environment, org and space"
  cf target
  if [ $? -ne 0 ]
  then
    echo_msg "!!!!!! ERROR: You either don't have the CF CLI installed or you are not connected to an Org or Space !!!!!!"
    exit $?
  fi
}

clean_cf()
{
  echo_msg "Removing previous deployment (if necessary!)"
  APPS=`cf apps | grep $APPNAME | cut -d" " -f1`
  for app in ${APPS[@]}
  do
    cf delete -f $app
  done
  cf_service_delete $DBSERVICE $APPNAME
  echo_msg "Removing Orphaned Routes"
  cf delete-orphaned-routes -f
}

main()
{
  APPNAME=cities-service
  DBSERVICE=MyDB
  check_cli_installed
  build
  clean_cf
  cf create-service p-mysql 100mb-dev MyDB
  echo "About to push application, you can monitor this using the command: cf logs $APPNAME"
  cf push -b java_buildpack_offline 
}

SECONDS=0
trap 'abort' 0
main
trap : 0
echo_msg "Deployment Complete in $SECONDS seconds."
