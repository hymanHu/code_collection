#! /bin/sh

download_dir=/opt/downloads
stats_socket_dir=/opt/local
base_url=172.17.20.17:8080

rs_server_tar_path=$download_dir/rs-server.tar.gz
rs_server_out_path=/opt/local/
version=1.6.1


mkdir -p $rs_server_out_path

rm -rf /opt/local/server-${version}-SNAPSHOT

wget http://$base_url/jenkins/job/rs2/lastBuild/com.livejournal.service.relation.server\$server/artifact/com.livejournal.service.relation.server/server/${version}-SNAPSHOT/server-${version}-SNAPSHOT-bin.tar.gz -O $rs_server_tar_path



tar -zxvf $rs_server_tar_path -C $rs_server_out_path

chmod -R +x /opt/local/server-${version}-SNAPSHOT


rm /opt/local/rs-server -f
ln -s  /opt/local/server-${version}-SNAPSHOT /opt/local/rs-server

echo "Finished"

