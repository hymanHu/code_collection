for((i=1;i<2;i++))
do

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
END

echo ---------------------------------
done

