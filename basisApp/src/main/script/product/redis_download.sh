#! /bin/bash
function downerr() {
	echo "download error!"
	exit 1
}
wget http://redis.googlecode.com/files/redis-2.4.2.tar.gz -O /opt/downloads/redis-2.4.2.tar.gz || downerr
echo "Finished"
