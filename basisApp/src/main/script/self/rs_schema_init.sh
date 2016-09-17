#! /bin/bash

conf_dir=/opt/etc/mysql/
start_shard=$1
end_shard=$2

usage() {
   echo "Usage:$0 start_shard end_shard(require number, limit 1 to 1000)" 
   exit 1
}

if [ "$start_shard" == "" ] || [ "$end_shard" == "" ]
then
    usage
fi

if [ $start_shard -le 0 ] || [ $start_shard -gt 1000 ]
then
    usage
fi

if [ $end_shard -le 0 ] || [ $end_shard -gt 1000 ]
then
    usage
fi


for f in `ls $conf_dir/*.cnf`;
do

create_fw=""
create_bw=""
create_fw_attr=""
create_bw_attr=""
for (( i = $start_shard; i <= $end_shard; i++));
do
   create_fw="$create_fw;CREATE TABLE IF NOT EXISTS forward_${i}_edges (source_id int(10) unsigned NOT NULL,destination_id int(10) unsigned NOT NULL,edge_type char(1) NOT NULL,state tinyint(4) NOT NULL,created_at int(10) unsigned NOT NULL,updated_at bigint(20) unsigned NOT NULL,valid_time int(10) unsigned NOT NULL,expired_at int(10) unsigned NOT NULL,PRIMARY KEY (source_id,destination_id,edge_type) ,  KEY index_state (state)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;"



   create_bw="$create_bw;CREATE TABLE IF NOT EXISTS  backward_${i}_edges (source_id int(10) unsigned NOT NULL,destination_id int(10) unsigned NOT NULL,edge_type char(1) NOT NULL,state tinyint(4) NOT NULL,created_at int(10) unsigned NOT NULL,updated_at bigint(20) unsigned NOT NULL,valid_time int(10) unsigned NOT NULL,expired_at int(10) unsigned NOT NULL,PRIMARY KEY (source_id,destination_id,edge_type)  ,  KEY index_state (state)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;"
   

   create_fw_attr="$create_fw_attr;CREATE TABLE IF NOT EXISTS forward_${i}_edge_attrs (source_id int(10) unsigned NOT NULL,destination_id int(10) unsigned NOT NULL,edge_type char(1) NOT NULL,state tinyint(4) NOT NULL,updated_at bigint(20) unsigned NOT NULL,attr_type char(1) NOT NULL,attr_value varchar(32) NOT NULL,attr_key varchar(32) NOT NULL,   PRIMARY KEY (source_id,destination_id,edge_type,attr_type,attr_value,attr_key)  ,  KEY index_state (state)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;"


   create_bw_attr="$create_bw_attr;CREATE TABLE IF NOT EXISTS backward_${i}_edge_attrs (source_id int(10) unsigned NOT NULL,destination_id int(10) unsigned NOT NULL,edge_type char(1) NOT NULL,state tinyint(4) NOT NULL,updated_at bigint(20) unsigned NOT NULL,attr_type char(1) NOT NULL,attr_value varchar(32) NOT NULL,attr_key varchar(32) NOT NULL,   PRIMARY KEY (source_id,destination_id,edge_type,attr_type,attr_value,attr_key)  ,  KEY index_state (state)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;"

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

