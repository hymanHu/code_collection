for((i=16;i<30;i++))
do
echo --------------------start $i-------------
mysql --socket=/opt/var/run/mysql/db1.sock -uroot <<END
use relationdb_edges;
ALTER TABLE forward_${i}_edges DROP INDEX index_state ;
ALTER TABLE forward_${i}_edge_attrs DROP INDEX index_state ;
ALTER TABLE backward_${i}_edges DROP INDEX index_state ;
ALTER TABLE backward_${i}_edge_attrs DROP INDEX index_state ;

ALTER TABLE forward_${i}_edges DROP PRIMARY KEY;
ALTER TABLE forward_${i}_edge_attrs DROP PRIMARY KEY;
ALTER TABLE backward_${i}_edges DROP PRIMARY KEY;
ALTER TABLE backward_${i}_edge_attrs DROP PRIMARY KEY;

ALTER TABLE forward_${i}_edges ADD PRIMARY KEY (source_id,destination_id, edge_type);
ALTER TABLE forward_${i}_edge_attrs add  index idx_sdet(source_id, destination_id, edge_type, state) ;
ALTER TABLE forward_${i}_edge_attrs add  id INTEGER  primary key  NOT NULL AUTO_INCREMENT;

ALTER TABLE backward_${i}_edges ADD PRIMARY KEY (source_id,destination_id, edge_type);
ALTER TABLE backward_${i}_edge_attrs add   index idx_sdet  (source_id, destination_id, edge_type, state);
ALTER TABLE backward_${i}_edge_attrs add  id INTEGER  primary key  NOT NULL AUTO_INCREMENT;
END

echo --------------------end $i-------------
done
