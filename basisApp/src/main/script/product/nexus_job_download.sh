#! /bin/sh

function downerr() {
	echo "download error!"
	exit 1
}

usage() {
	echo "usage: $0 host version projectname"
	echo "$0 http://172.17.20.100:8081/nexus 1.7.2-SNAPSHOT server"
}

if [ $# != 3 ]
then 
	usage
	exit 1
fi

base_url=$1
version=$2
project=$3

versiontype=${version##*'-'}

if [ $versiontype == "SNAPSHOT" ];then
    repo=livejournal.local.01
else
	repo=livejournal.local.02
fi

case $3 in
	dashboard_console)
	groupid=com.livejournal.service.relation
	artifactid=dashboard_console
	;;
	us_test)
	groupid=com.livejournal.service.relation.ustest
	artifactid=us_test
	;;
	deploy_script)
	groupid=deploy_script
	artifactid=deploy_script
	;;
	rs_task)
	groupid=com.livejournal.service.relation.server
	artifactid=rs_task
	;;
	server)
	groupid=com.livejournal.service.relation.server
	artifactid=server
	;;
	redgraph)
	groupid=com.livejournal.framework
	artifactid=redgraph
	;;
	quedis)
	groupid=com.livejournal.framework
	artifactid=quedis
	;;
	puppet_script)
	groupid=puppet_script
	artifactid=puppet_script
	;;
	*)
		echo "usage: $0 host version projectname"
		exit 1
	;;
esac

download_dir=/opt/downloads/
download_filename=${artifactid}-${version}-bin.tar.gz
tar_path=${download_dir}${download_filename}

groupid=${groupid//\./\/}

wget ${base_url}/content/repositories/${repo}/${groupid}/${artifactid}/${version}/${download_filename} -O $tar_path  || downerr

echo "Finished"