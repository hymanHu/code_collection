#! /bin/sh

function downerr() {
	echo "download error!"
	exit 1
}

download_dir=/opt/downloads
base_url=$1
version=$2
job=$3

web_tar_path=$download_dir/web.tar.gz
web_out_path=/opt/local/jetty/webapps

wget "$base_url"job/$job/lastSuccessfulBuild/com.livejournal.service.relation\$dashboard_console/artifact/com.livejournal.service.relation/dashboard_console/${version}-SNAPSHOT/dashboard_console-${version}-SNAPSHOT-bin.tar.gz -O $web_tar_path  || downerr

rm /opt/local/jetty/webapps/* -rf

tar -zxvf $web_tar_path -C $web_out_path

echo "Finished"


