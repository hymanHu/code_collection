  #su - lj -c "sed -i 's/^server-id=/server-id=$server_id/g' /opt/etc/mysql/db$i.cnf"
#!/bin/bash
echo "Please make sure MySQL is running before your operation."
source /etc/profile
echo "Please input partnership ip or host: "
read db2ip
server=`hostname`
mysql --defaults-file=/opt/etc/mysql/db1.cnf --user=root << END
	use mysql;
	 delete from user where Host= '$server';
	 delete from user where User='';
	 GRANT ALL PRIVILEGES ON *.* TO 'lj'@'%' IDENTIFIED BY 'livejournal' WITH GRANT OPTION;
	 GRANT REPLICATION SLAVE ON *.* TO 'rsync'@'%' IDENTIFIED BY 'sync1';
	 flush privileges;
	 INSTALL PLUGIN rpl_semi_sync_slave SONAME 'semisync_slave.so';
	 INSTALL PLUGIN rpl_semi_sync_master SONAME 'semisync_master.so';

END
sleep 5
mysql --defaults-file=/opt/etc/mysql/db1.cnf --user=root << END
         GRANT ALL PRIVILEGES ON *.* TO 'lj'@'%' IDENTIFIED BY 'livejournal' WITH GRANT OPTION;
	 GRANT REPLICATION SLAVE ON *.* TO 'rsync'@'%' IDENTIFIED BY 'sync1';
         flush privileges;
         INSTALL PLUGIN rpl_semi_sync_slave SONAME 'semisync_slave.so';
         INSTALL PLUGIN rpl_semi_sync_master SONAME 'semisync_master.so';
	
END


mysql.server stop
sleep 5
ssh lj@$db2ip 'source /etc/profile&&mysql.server stop'
sleep 5
ssh lj@$db2ip 'rm -rf /opt/data/mysql'
scp -r /opt/data/mysql lj@$db2ip:/opt/data/
clear
echo "Start current MySQL."
mysql.server start
echo "Start partnership MySQL."
ssh lj@$db2ip 'source /etc/profile&&mysql.server start'
