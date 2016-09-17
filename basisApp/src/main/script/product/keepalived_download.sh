#! /bin/bash
function downerr() {
	echo "download error!"
	exit 1
}
wget http://www.keepalived.org/software/keepalived-1.2.1.tar.gz -O /opt/downloads/keepalived-1.2.1.tar.gz || downerr
echo "Finished"
