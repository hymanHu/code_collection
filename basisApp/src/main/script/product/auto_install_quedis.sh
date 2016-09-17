#! /bin/bash
download_dir=/opt/downloads
base_url=172.17.20.100:8080
version=1.7.1-SNAPSHOT
quedis_tar_name=quedis-$version-bin.tar.gz
quedis_tar_path=$download_dir/$quedis_tar_name
quedis_out_path=/opt/local/
rm -fr quedis_tar_path
wget http://$base_url/jenkins/job/quedis/lastBuild/quedis\$quedis/artifact/quedis/quedis/$version/quedis-$version-bin.tar.gz -O $quedis_tar_path
cd $download_dir
tar xzvf $quedis_tar_name
cd quedis-$version

make PREFIX=$quedis_out_path/quedis install

echo "Finished"
