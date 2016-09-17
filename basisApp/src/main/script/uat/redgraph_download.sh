#! /bin/bash
download_dir=/opt/downloads
base_url=172.17.20.100:8080
version=1.6.2-SNAPSHOT
redgraph_tar_name=redgraph-$version-bin.tar.gz
redgraph_tar_path=$download_dir/$redgraph_tar_name
redgraph_out_path=/opt/local/

wget http://$base_url/jenkins/job/redgraph/lastBuild/redgraph\$redgraph/artifact/redgraph/redgraph/$version/redgraph-$version-bin.tar.gz -O $redgraph_tar_path
cd $download_dir
tar xzvf $redgraph_tar_name
cd redgraph-$version
make PREFIX=$redgraph_out_path/redgraph install

echo "Finished"
