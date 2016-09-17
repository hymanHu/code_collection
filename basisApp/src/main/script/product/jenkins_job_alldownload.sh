#! /bin/sh

usage() {
	echo "usage: $0 host version"
	echo "$0 http://172.17.20.100:8080 1.7.2-SNAPSHOT"
}

if [ $# != 2 ]
then 
	usage
	exit 1
fi

base_url=$1
version=$2
subversion=${version%%'-SNAPSHOT'*}
jobid=lastSuccessfulBuild

chmod -R +x jenkins_job_download.sh

dashboard="$base_url $version dashboard dashboard_console"
/opt/local/bin/jenkins_job_download.sh $dashboard $jobid

us_test="$base_url $version us_test us_test"
/opt/local/bin/jenkins_job_download.sh $us_test $jobid

deploy_script="$base_url $subversion script_prod deploy_script"
/opt/local/bin/jenkins_job_download.sh $deploy_script $jobid

rs_task="$base_url $version rs_task rs_task"
/opt/local/bin/jenkins_job_download.sh $rs_task $jobid

server="$base_url $version rs2 server"
/opt/local/bin/jenkins_job_download.sh $server $jobid

redgraph="$base_url $version redgraph redgraph"
/opt/local/bin/jenkins_job_download.sh $redgraph $jobid

quedis="$base_url $version quedis quedis"
/opt/local/bin/jenkins_job_download.sh $quedis $jobid

puppet_script="$base_url $subversion puppet-script puppet_script"
/opt/local/bin/jenkins_job_download.sh $puppet_script $jobid

echo "Finished"