#! /bin/sh

function downerr() {
	echo "download error!"
	exit 1
}

usage() {
	echo "usage: $0 host version jobname projectname jobid"
	echo "jenkins_job_download.sh http://172.17.20.100:8080 1.7.2-SNAPSHOT rs2 server 6954"
}

if [ $# != 5 ]
then 
	usage
	exit 1
fi

base_url=$1
version=$2
job=$3
project=$4
buildid=$5

case $4 in
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
		echo "usage: $0 host version jobname projectname jobid"
		exit 1
	;;
esac

download_dir=/opt/downloads/
download_filename=${artifactid}-${version}-bin.tar.gz
tar_path=${download_dir}${download_filename}

wget ${base_url}/job/${job}/${buildid}/${groupid}\$"${artifactid}"/artifact/${groupid}/${artifactid}/${version}/${download_filename} -O $tar_path  || downerr

echo "Finished"