#!/bin/bash
echo "Please make sure MySQL is running before your operation."
echo "Please input current dbserver ip: "
read master_host
a=`mysql --defaults-file=/opt/etc/mysql/db1.cnf --user=root << END
show master status;
END`
master_log_file=`echo $a |awk '{print $5}'`
master_log_pos=`echo $a |awk '{print $6}'`
echo "Please input partnership dbserver ip: "
read dbserver2
echo "Please input MySQL username in partnership dbserver: "
read dbuser2
echo "Please input MySQL password in partnership dbserver: "
mysql -h $dbserver2 -u $dbuser2 -p << END
stop slave;
change master to master_host='$master_host',master_user='rsync',master_password='sync1',master_log_file='$master_log_file',master_log_pos=$master_log_pos;
start slave;
END

