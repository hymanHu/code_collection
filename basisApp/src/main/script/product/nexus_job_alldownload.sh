#! /bin/sh

usage() {
	echo "usage: $0 host version"
	echo "$0 http://172.17.20.100:8081/nexus 1.7.2-SNAPSHOT"
}

if [ $# != 2 ]
then 
	usage
	exit 1
fi

base_url=$1
version=$2

dashboard="$base_url $version dashboard_console"
/opt/local/bin/nexus_job_download.sh $dashboard

us_test="$base_url $version us_test"
/opt/local/bin/nexus_job_download.sh $us_test

deploy_script="$base_url $version deploy_script"
/opt/local/bin/nexus_job_download.sh $deploy_script

rs_task="$base_url $version rs_task"
/opt/local/bin/nexus_job_download.sh $rs_task

server="$base_url $version server"
/opt/local/bin/nexus_job_download.sh $server

redgraph="$base_url $version redgraph"
/opt/local/bin/nexus_job_download.sh $redgraph

quedis="$base_url $version quedis"
/opt/local/bin/nexus_job_download.sh $quedis

puppet_script="$base_url $version puppet_script"
/opt/local/bin/nexus_job_download.sh $puppet_script

echo "Finished"