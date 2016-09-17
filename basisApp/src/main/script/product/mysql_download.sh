#! /bin/bash
function downerr() {
	echo "download error!"
	exit 1
}
wget http://downloads.mysql.com/archives/mysql-5.5/mysql-5.5.15-linux2.6-x86_64.tar.gz -O /opt/downloads/mysql-5.5.15-linux2.6-x86_64.tar.gz  || downerr
echo "Finished"
