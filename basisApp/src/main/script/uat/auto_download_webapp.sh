#! /bin/sh

download_dir=/opt/downloads
base_url=172.17.20.17:8080

web_tar_path=$download_dir/web.tar.gz
web_out_path=/opt/local/jetty/webapps
version=1.6.1

wget http://$base_url/jenkins/job/dashboard/lastSuccessfulBuild/com.livejournal.service.relation\$dashboard_console/artifact/com.livejournal.service.relation/dashboard_console/${version}-SNAPSHOT/dashboard_console-${version}-SNAPSHOT-bin.tar.gz -O $web_tar_path

rm /opt/local/jetty/webapps/* -rf

tar -zxvf $web_tar_path -C $web_out_path

echo "Finished"


