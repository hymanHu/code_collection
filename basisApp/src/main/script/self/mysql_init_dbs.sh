#!/bin/bash

db_base_path=/opt/data/mysql
db_number=$1

usage() {
   echo "Usage:$0 db_number (number limit 1 to 50)"  
   exit 1
}

if [ "$UID" -ne 0 ] 
then
echo "You need use Root User" 
exit 1 
fi


if [ "$db_base_path" == "" ] || [ "$db_number" == "" ] 
then
    usage
fi

if [ $db_number -le 0 ] || [ $db_number -gt 50 ]
then
    usage
fi

if [ -d "$db_base_path" ]; then
	rm /opt/data/mysql/* -rf
    echo "Will make dir in $db_base_path"
else
	mkdir /opt/data/mysql
	chown -R lj:lj /opt/data/mysql
    echo "$db_base_path is not directory"
    exit 1
fi


function gen_conf() {
	local fpath=$1
	local port=$2
	local socket=$3
	local server_id=$4
	cat>$fpath<<END
[client] 
#password	= [your_password] 
port=$port
socket=$socket

[mysqld] 
# generic configuration options 
port=$port
socket=$socket
server-id=$server_id
back_log = 50

default-storage-engine=INNODB
max_connections = 1024
max_connect_errors = 10
table_open_cache = 2048
max_allowed_packet = 16M
binlog_cache_size = 1M
max_heap_table_size = 64M
read_buffer_size = 2M
read_rnd_buffer_size = 16M
sort_buffer_size = 8M
join_buffer_size = 8M
thread_cache_size = 8
innodb_file_per_table
# You should try [number of CPUs]*(2..4) for thread_concurrency 
thread_concurrency = 16 
query_cache_size = 64M 
# Only cache result sets that are smaller than this limit. This is to
# protect the query cache of a very large result set overwriting all
# other query results.
query_cache_limit = 2M
ft_min_word_len = 4
thread_stack = 192K
transaction_isolation = REPEATABLE-READ
tmp_table_size = 64M
log-bin=mysql-bin
# binary logging format - mixed recommended
binlog_format=mixed
slow_query_log
long_query_time = 2
key_buffer_size = 32M
bulk_insert_buffer_size = 64M
innodb_additional_mem_pool_size = 16M
innodb_buffer_pool_size = 4G
innodb_data_file_path = ibdata1:10M:autoextend
innodb_write_io_threads = 8
innodb_read_io_threads = 8
innodb_thread_concurrency = 16
innodb_flush_log_at_trx_commit = 0
innodb_log_buffer_size = 8M
innodb_log_file_size = 256M
innodb_log_files_in_group = 3
innodb_max_dirty_pages_pct = 90
innodb_lock_wait_timeout = 120
expire_logs_days = 7

[mysqldump]
quick
max_allowed_packet = 16M

[mysql]
no-auto-rehash

[myisamchk]
key_buffer_size = 512M
sort_buffer_size = 512M
read_buffer = 8M
write_buffer = 8M

[mysqlhotcopy]
interactive-timeout

[mysqld_safe]
open-files-limit = 8192
END
	chown lj:lj $fpath
}

#mkdir /opt/etc/mysql -p
chown lj:lj /opt/etc/mysql
for (( i = 1; i <= $db_number; i++)); 
do
  let "port=3305+$i"
  mkdir -p "$db_base_path/db$i"
  chown lj:lj "$db_base_path/db$i"

  su - lj -c  "/opt/local/mysql/scripts/mysql_install_db --datadir=$db_base_path/db$i  --basedir=/opt/local/mysql/"

  gen_conf /opt/etc/mysql/db$i.cnf $port /opt/var/run/mysql/db$i.sock $i
done;

