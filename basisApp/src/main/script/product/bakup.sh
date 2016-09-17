#! /bin/bash

dir=$1
suffix=$2

function usgae() {
	echo "$0 directory suffix"
	exit 0
}

if [[ $# < 2 ]]
then 
	usgae
fi

if [ ! -d $dir ]
then
	usgae
fi


bs=$(date "+%Y-%m-%d_%H:%M:%S")
for f in $(ls $dir/*.$suffix)
do
	mv $f "${f}_${bs}"
done

