#! /bin/sh

download_dir=/opt/downloads
stats_socket_dir=/opt/local
base_url=$1
jobname=$3

version=$2
rs_server_tar_path=$download_dir/server-${version}-bin.tar.gz
rs_server_out_path=/opt/local/


usgae() {
	echo "$0 host version jobname"
}

if [ $# != 3 ]
then 
	usgae
	exit 1
fi

mkdir -p $rs_server_out_path || exit 1

function downerr() {
	echo "download error!"
	exit 1
}
wget http://$base_url/job/$jobname/lastBuild/com.livejournal.service.relation.server\$server/artifact/com.livejournal.service.relation.server/server/${version}/server-${version}-bin.tar.gz -O $rs_server_tar_path || downerr


echo "Finished"

