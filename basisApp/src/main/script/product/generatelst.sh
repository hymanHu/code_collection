#! /bin/bash

type=$1
dst_dir=/opt/local/bin

usage() {
	echo "$0 type(queue or graph) list"
	exit 1
}

if [[ $# < 2 ]] 
then
	usage
fi

if [ "$type" != "queue" ] && [ "$type" != "graph" ]
then
	usage
fi

shift

dest="$dst_dir/${type}.lst"
>$dest
for arg in $*
do
	echo $arg >> $dest
done



