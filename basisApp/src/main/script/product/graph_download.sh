#! /bin/bash
download_dir=/opt/downloads
base_url=$1
version=$2
jobname=$3
redgraph_tar_name=redgraph-$version-bin.tar.gz
redgraph_tar_path=$download_dir/$redgraph_tar_name
redgraph_out_path=/opt/local/

usgae() {
    echo "$0 host version jobname"
}

if [ $# != 3 ]
then
    usgae
    exit 1
fi

function downerr() {
	echo "download error!"
	exit 1
}
wget http://$base_url/job/$jobname/lastBuild/redgraph\$redgraph/artifact/redgraph/redgraph/$version/redgraph-$version-bin.tar.gz -O $redgraph_tar_path || downerr

echo "Finished"
