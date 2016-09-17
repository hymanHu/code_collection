#! /bin/bash

mode=$1
case "$mode" in
  'start')
    /opt/local/zookeeper-3.3.3/bin/zkServer.sh start
    ssh lj@server-raid2 "source /etc/profile && /opt/local/zookeeper-3.3.3/bin/zkServer.sh start >/dev/null 2>&1 "
    ssh lj@server-raid3 "source /etc/profile && /opt/local/zookeeper-3.3.3/bin/zkServer.sh start >/dev/null 2>&1 "
	;;
  'stop')
    /opt/local/zookeeper-3.3.3/bin/zkServer.sh stop
    ssh lj@server-raid2 "source /etc/profile && /opt/local/zookeeper-3.3.3/bin/zkServer.sh stop >/dev/null 2>&1 "
    ssh lj@server-raid3 "source /etc/profile && /opt/local/zookeeper-3.3.3/bin/zkServer.sh stop >/dev/null 2>&1 "
	;;
  *)
    basename=`basename "$0"`
    echo "Usage: $basename  {start|stop}  [ zookeeper cluster options ]"
    exit 1
    ;;
esac

