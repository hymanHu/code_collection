#! /bin/bash
cfg_file=/opt/etc/mysql/db1.cnf
script_version=$(grep "^#VERSION=1$" $cfg_file)
if [[ "script_version" == "#VERSION=1" ]]
then
	echo "The config version is $script_version now."
	exit 1
fi
port="$(sed -n '/^port=/{p;q;}' $cfg_file|sed 's/^port=[ ]*\([0-9]*\)/\1/')"
socket="$(sed -n '/^socket=/{p;q;}' $cfg_file|sed 's/^socket=[ ]*\([0-9]*\)/\1/')"
server_id="$(sed -n '/^server-id=/{p;q;}' $cfg_file|sed 's/^server-id=[ ]*\([0-9]*\)/\1/')"


function gen_conf() {
	local fpath=$1
	local port=$2
	local socket=$3
	cat>$fpath<<END
#VERSION=1
[client] 
#password	= [your_password] 
port=$port
socket=$socket

[mysqld] 
# generic configuration options 
port=$port
socket=$socket
server-id=$server_id

skip-name-resolve
open_files_limit = 65535
read_rnd_buffer_size = 5M
max_allowed_packet = 24M
max_connect_errors = 50000
max_connections = 2000
thread_cache_size=64
table_cache=256

query_cache_size = 30M
query_cache_type=2

concurrent_insert = 2
key_buffer = 100M
sort_buffer_size = 8M
join_buffer_size = 8M
read_buffer_size = 8M
myisam_sort_buffer_size = 100M

default-storage-engine = INNODB
innodb_flush_method = O_DSYNC
innodb_file_per_table = 1
innodb_flush_log_at_trx_commit = 0
innodb_lock_wait_timeout = 120
innodb_additional_mem_pool_size = 100M
innodb_buffer_pool_size = 12G 
innodb_log_buffer_size= 200M
innodb_log_file_size = 500M 
innodb_log_files_in_group = 8
innodb_file_io_threads = 16
innodb_thread_concurrency = 64
innodb_io_capacity = 2000
innodb_max_dirty_pages_pct = 50
#transaction-isolation = READ-COMMITTED
innodb_data_file_path = ibdata1:1G:autoextend

sql-mode="NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION"

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

gen_conf $cfg_file $port $socket $server_id
echo "patch the mysql sql version is finish."