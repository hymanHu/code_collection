#! /bin/bash

all=("openfiles" "vmsetting")

function check_openfiles() {
	local maxf=$(ulimit -n)
	if [[ $maxf != 999999 ]]
	then
		echo "please set the max open files, current value is $maxf"
		exit 1
	else
		echo "max open files:	$maxf"
	fi
}

function check_vmsetting() {
	local val=$(cat /proc/sys/vm/overcommit_memory)
	if [[ val == 1 ]]
	then
		echo "vm.overcommit_memory=1"
	else
		echo "vm.overcommit_memory=0, please set vm.overcommit_memory=1 in file /etc/sysctl.conf"
		exit 0
	fi
}

params=$*

if [[ $# == 0 ]]
then
	params=(${all[*]})
fi

if [[ $# == 1 ]] && [ "$1" == "*" ]
then
	params=$all
fi

for p in ${params[*]}
do
	check_$p
done

