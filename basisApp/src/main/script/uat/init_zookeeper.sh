#! /bin/bash

cat >/opt/local/zookeeper-3.3.3/conf/zoo.cfg<<END
# The number of milliseconds of each tick
tickTime=2000
# The number of ticks that the initial 
# synchronization phase can take
initLimit=10
# The number of ticks that can pass between 
# sending a request and getting an acknowledgement
syncLimit=5
# the directory where the snapshot is stored.
dataDir=/opt/data/zookeeper
# the port at which the clients will connect
clientPort=2181
server.1=server-raid1:2888:3888
server.2=server-raid2:2888:3888
server.3=server-raid3:2888:3888
END

/opt/local/zookeeper-3.3.3/bin/zkCli.sh << END
create /AUTHENTICATION 667083D27188509B9800C762E237D5BC
END

