#! /bin/bash

conf_dir=/opt/etc/mysql/
shard_num=$1

usage() {
   echo "Usage:$0 shard_number(require number, limit 1 to 1000)" 
   exit 1
}

if [ "$shard_num" == "" ]
then
    usage
fi

if [ $shard_num -le 0 ] || [ $shard_num -gt 1000 ]
then
    usage
fi

for f in `ls $conf_dir/*.cnf`;
do

create_fw=""
for (( i = 1; i <= $shard_num; i++));
do
   create_fw="$create_fw;CREATE TABLE IF NOT EXISTS forward_${i}_edges (source_id int(10) unsigned NOT NULL,destination_id int(10) unsigned NOT NULL,edge_type char(1) NOT NULL,state tinyint(4) NOT NULL,created_at int(10) unsigned NOT NULL,updated_at bigint(20) unsigned NOT NULL,valid_time int(10) unsigned NOT NULL,expired_at int(10) unsigned NOT NULL,PRIMARY KEY (source_id,destination_id,edge_type), KEY index_state (state)) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE latin1_bin;"
done

create_bw=""
for (( i = 1; i <= $shard_num; i++));
do
   create_bw="$create_bw;CREATE TABLE IF NOT EXISTS  backward_${i}_edges (source_id int(10) unsigned NOT NULL,destination_id int(10) unsigned NOT NULL,edge_type char(1) NOT NULL,state tinyint(4) NOT NULL,created_at int(10) unsigned NOT NULL,updated_at bigint(20) unsigned NOT NULL,valid_time int(10) unsigned NOT NULL,expired_at int(10) unsigned NOT NULL,PRIMARY KEY (source_id,destination_id,edge_type) , KEY index_state (state)) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE latin1_bin;"
done

create_fw_attr=""
for (( i = 1; i <= $shard_num; i++));
do
   create_fw_attr="$create_fw_attr;CREATE TABLE IF NOT EXISTS forward_${i}_edge_attrs (source_id int(10) unsigned NOT NULL,destination_id int(10) unsigned NOT NULL,edge_type char(1) NOT NULL,state tinyint(4) NOT NULL,updated_at bigint(20) unsigned NOT NULL,attr_type char(1) NOT NULL,attr_value varchar(32) NOT NULL,attr_key varchar(32) NOT NULL,   PRIMARY KEY (source_id,destination_id,edge_type,attr_type,attr_value,attr_key) , KEY index_state (state)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;"
done

create_bw_attr=""
for (( i = 1; i <= $shard_num; i++));
do
   create_bw_attr="$create_bw_attr;CREATE TABLE IF NOT EXISTS backward_${i}_edge_attrs (source_id int(10) unsigned NOT NULL,destination_id int(10) unsigned NOT NULL,edge_type char(1) NOT NULL,state tinyint(4) NOT NULL,updated_at bigint(20) unsigned NOT NULL,attr_type char(1) NOT NULL,attr_value varchar(32) NOT NULL,attr_key varchar(32) NOT NULL,   PRIMARY KEY (source_id,destination_id,edge_type,attr_type,attr_value,attr_key) , KEY index_state (state)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;"
done


mysql --defaults-file=$f --user=root << END

    create database  IF NOT EXISTS relationdb_edges;
	
   use relationdb_edges;

    $create_fw
    
    $create_bw

    $create_fw_attr

    $create_bw_attr
    
END
done;

echo "create schema finish!"

