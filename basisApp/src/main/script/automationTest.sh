#! /bin/sh

base_url=172.17.20.100:8080
download_dir=/home/lj
health_check_version=0.0.1
common_version=1.7.2

health_check_test=true
us_test=true
perl_test=true
migration_test=true
stats_test=true

# jetty test config
zkServers=172.17.20.136
rs_cluster_zookeeper=172.17.20.136
rs_cluster_node=/rs-cluster
stats_rootConfigNode=/stats
zk_servers=172.17.20.136:2181
lock_shards=1
# migration dock test config
zk_servers=172.17.20.136
zk_pathprefix=/migration-test
cluster_directory=test_cluster
mysql_server=172.17.20.136
# migration ui test config
SERVICE_HOST=172.17.20.136
SERVICE_PORT=8080
SOURCE_CLUSTER=/rs-chenhua/sql_cluster
MIGRATION_TYPE=1
ENTITY_ID=123
ENTITY_ID_BACK=124
WRITE_SERVER=172.17.20.136:3306
READ_SERVER=172.17.20.136:3306
zkServers=172.17.20.136:2181
sessionTimeout=120000
connectionTimeout=120000
# stats test config
jdbc_url=jdbc:mysql://172.17.20.136:3306/stats
# perl test config
server_name=172.17.20.136
server_port=10000
edge_types=A,B,C
# user story test config
zkServers=172.17.20.136
zookeeper_rootConfigNode=/rs-chenhua
attribute_keyRadix=32
reservedEdgeTypes=-,_,+
rs_cluster_node=/rs-cluster
#rs_host=172.17.20.112
#rs_port=10000
rs_stats_url=http://172.17.20.136:9999

function check_file() {
	if [ ! -f $1 ]
	then
		echo "$1 file is not exist!"
		exit 1
	fi
}

echo ==== `date +%Y-%d-%m\ %H:%M:%S` Start down test files: rs_health_check, us_test, migrationui, stats_server ====

wget http://$base_url/job/rs_health_check/lastSuccessfulBuild/com.livejournal.service.relation\$rs_health_check/artifact/com.livejournal.service.relation/rs_health_check/$health_check_version-SNAPSHOT/rs_health_check-$health_check_version-SNAPSHOT-bin.tar.gz -O $download_dir/rs_health_check-bin.tar.gz

wget http://$base_url/job/us_test/lastSuccessfulBuild/com.livejournal.service.relation.ustest\$us_test/artifact/com.livejournal.service.relation.ustest/us_test/$common_version-SNAPSHOT/us_test-$common_version-SNAPSHOT-bin.tar.gz -O $download_dir/us_test-bin.tar.gz

wget http://$base_url/job/migration/lastSuccessfulBuild/com.livejournal.server.migration\$migrationui/artifact/com.livejournal.server.migration/migrationui/$common_version-SNAPSHOT/migrationui-$common_version-SNAPSHOT-bin.tar.gz -O $download_dir/migrationui-bin.tar.gz

wget http://$base_url/job/dashboard/lastSuccessfulBuild/com.livejournal.service.relation.stats_server\$stats_server/artifact/com.livejournal.service.relation.stats_server/stats_server/$common_version-SNAPSHOT/stats_server-$common_version-SNAPSHOT-bin.tar.gz -O $download_dir/stats_server-bin.tar.gz

echo ==== `date +%Y-%d-%m\ %H:%M:%S` Start unzip the files to /opt/downloads/automationTest ====

if [ ! -x "$download_dir/automationTest/" ]; then 
	mkdir "$download_dir/automationTest"
fi

rm -rf $download_dir/automationTest/*

tar -zxvf $download_dir/rs_health_check-bin.tar.gz -C $download_dir/automationTest

tar -zxvf $download_dir/us_test-bin.tar.gz -C $download_dir/automationTest

tar -zxvf $download_dir/migrationui-bin.tar.gz -C $download_dir/automationTest

tar -zxvf $download_dir/stats_server-bin.tar.gz -C $download_dir/automationTest

echo ==== `date +%Y-%d-%m\ %H:%M:%S` Start check configuration files exist ====

check_file $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/docker-test.properties

check_file $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties

check_file $download_dir/automationTest/stats_server-1.7.2-SNAPSHOT/config/jdbc.properties

check_file $download_dir/automationTest/rs_health_check-0.0.1-SNAPSHOT/config/health_check.properties

check_file $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/config/tests.properties

check_file $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/perl/configuration.properties

check_file /opt/local/jetty/config/configuration.properties

echo ==== `date +%Y-%d-%m\ %H:%M:%S` Start modify configuration files ====

# jetty config
cp -f /opt/local/jetty/config/configuration.properties /opt/local/jetty/config/configuration.properties.~
sed -i '/rs.cluster.zookeeper/d' /opt/local/jetty/config/configuration.properties
sed -i '/rs.cluster.node/d' /opt/local/jetty/config/configuration.properties
sed -i '/rs.host/d' /opt/local/jetty/config/configuration.properties
sed -i '/rs.port/d' /opt/local/jetty/config/configuration.properties
echo "rs.cluster.node=$rs_cluster_node" >> /opt/local/jetty/config/configuration.properties
echo "rs.cluster.zookeeper=$rs_cluster_zookeeper" >> /opt/local/jetty/config/configuration.properties

# health check test config

# user story test config
sed -i '/zkServers/d' $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/config/tests.properties
sed -i '/zookeeper.rootConfigNode/d' $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/config/tests.properties
sed -i '/attribute.keyRadix/d' $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/config/tests.properties
sed -i '/reservedEdgeTypes/d' $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/config/tests.properties
sed -i '/rs.cluster.node/d' $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/config/tests.properties
sed -i '/rs.stats.url/d' $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/config/tests.properties
sed -i '/rs.host/d' $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/config/tests.properties
sed -i '/rs.port/d' $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/config/tests.properties
echo "zkServers=$zkServers" >> $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/config/tests.properties
echo "zookeeper.rootConfigNode=$zookeeper_rootConfigNode" >> $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/config/tests.properties
echo "attribute.keyRadix=$attribute_keyRadix" >> $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/config/tests.properties
echo "reservedEdgeTypes=$reservedEdgeTypes" >> $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/config/tests.properties
echo "rs.cluster.node=$rs_cluster_node" >> $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/config/tests.properties
echo "rs.stats.url=$rs_stats_url" >> $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/config/tests.properties

# perl test config
sed -i '/server_name/d' $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/perl/configuration.properties
sed -i '/server_port/d' $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/perl/configuration.properties
sed -i '/edge_types/d' $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/perl/configuration.properties
echo "server_name=$server_name" >> $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/perl/configuration.properties
echo "server_port=$server_port" >> $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/perl/configuration.properties
echo "edge_types=$edge_types" >> $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/perl/configuration.properties

# migration test config
if [ $migration_test = "true" ]; then 
	sed -i '/zk.servers/d' /opt/local/jetty/config/configuration.properties
	sed -i '/lock.shards/d' /opt/local/jetty/config/configuration.properties
	echo "zk.servers=$zk_servers" >> /opt/local/jetty/config/configuration.properties
	echo "lock_shards=$lock_shards" >> /opt/local/jetty/config/configuration.properties

	sed -i '/zk.servers/d' $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/docker-test.properties
	sed -i '/zk.pathprefix/d' $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/docker-test.properties
	sed -i '/cluster_directory/d' $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/docker-test.properties
	sed -i '/mysql.server/d' $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/docker-test.properties
	echo "zk.servers=$zk_servers" >> $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/docker-test.properties
	echo "zk.pathprefix=$zk_pathprefix" >> $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/docker-test.properties
	echo "cluster_directory=$cluster_directory" >> $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/docker-test.properties
	echo "mysql.server=$mysql_server" >> $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/docker-test.properties
	
	sed -i '/SERVICE_HOST/d' $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	sed -i '/SERVICE_PORT/d' $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	sed -i '/SOURCE_CLUSTER/d' $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	sed -i '/MIGRATION_TYPE/d' $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	sed -i '/ENTITY_ID/d' $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	sed -i '/ENTITY_ID_BACK/d' $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	sed -i '/WRITE_SERVER/d' $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	sed -i '/READ_SERVER/d' $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	sed -i '/zkServers/d' $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	echo "SERVICE_HOST=$SERVICE_HOST" >> $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	echo "SERVICE_PORT=$SERVICE_PORT" >> $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	echo "SOURCE_CLUSTER=$SOURCE_CLUSTER" >> $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	echo "MIGRATION_TYPE=$MIGRATION_TYPE" >> $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	echo "ENTITY_ID=$ENTITY_ID" >> $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	echo "ENTITY_ID_BACK=$ENTITY_ID_BACK" >> $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	echo "WRITE_SERVER=$WRITE_SERVER" >> $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	echo "READ_SERVER=$READ_SERVER" >> $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	echo "zkServers=$zkServers" >> $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/migrationTest.properties
	
	#sed -i 's/\/opt\/var\/log\/migration\/migrationTest.log/migrationTest.log/g' $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/log4j.xml
	#sed -i 's/\/opt\/var\/log\/migration\/migrationTest_error.log/migrationTest_error.log/g' $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/log4j.xml
	#sed -i 's/\/opt\/var\/log\/migration\/docker-test.log/docker-test.log/g' $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/config/log4j.xml
fi

# stats test config
if [ $stats_test = "true" ]; then 
	sed -i '/stats.rootConfigNode/d' /opt/local/jetty/config/configuration.properties
	echo "stats.rootConfigNode=$stats_rootConfigNode" >> /opt/local/jetty/config/configuration.properties

	sed -i '/jdbc_url/d' $download_dir/automationTest/stats_server-1.7.2-SNAPSHOT//config/jdbc.properties
	echo "jdbc.url=$jdbc_url" >> $download_dir/automationTest/stats_server-1.7.2-SNAPSHOT//config/jdbc.properties
	
	#sed -i 's/\/opt\/var\/log\/stats_server\/stats_server.log/stats_server.log/g' $download_dir/automationTest/stats_server-1.7.2-SNAPSHOT/config/log4j.properties
fi

echo ==== `date +%Y-%d-%m\ %H:%M:%S` Start test ====

current_path=`pwd`

if [ ! -x "$current_path/log/" ]; then 
	mkdir "$current_path/log"
fi

chmod +x $download_dir/automationTest/rs_health_check-0.0.1-SNAPSHOT/bin/*.sh
chmod +x $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/bin/*.sh
chmod +x $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/perl/*.pl
chmod +x $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/bin/*.sh
chmod +x $download_dir/automationTest/stats_server-1.7.2-SNAPSHOT/bin/*.sh

if [ $health_check_test = "true" ]; then 
	cd $download_dir/automationTest/rs_health_check-0.0.1-SNAPSHOT/bin/
	./rs_health_check.sh
	sleep 5s
	cp -f $download_dir/automationTest/rs_health_check-0.0.1-SNAPSHOT/bin/*.txt $current_path/log/health_check_report.txt
	cp -f $download_dir/automationTest/rs_health_check-0.0.1-SNAPSHOT/bin/check.log $current_path/log/health_check_test.log
	cp -f $download_dir/automationTest/rs_health_check-0.0.1-SNAPSHOT/bin/velocity.log $current_path/log/health_check_velocity.log
fi

if [ $us_test = "true" ]; then 
	cd $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/bin/
	./test_us.sh | tee test_us.log
	sleep 2s
	./test_multi.sh | tee test_multi.log
	sleep 2s
	./test_strategy.sh | tee test_strategy.log
	sleep 2s
	./test_user_story.sh | tee test_user_story.log
	sleep 5s
	cp -f $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/bin/test_us.log $current_path/log/test_us.log
	cp -f $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/bin/test_multi.log $current_path/log/test_multi.log
	cp -f $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/bin/test_strategy.log $current_path/log/test_strategy.log
	cp -f $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/bin/test_user_story.log $current_path/log/test_user_story.log
fi

if [ $perl_test = "true" ]; then 
	#su - root -c "yum -y install perl"
	cd $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/perl/
	./ping.pl | tee perl_ping.log
	sleep 2s
	./run_cases.pl | tee perl_run_cases.log
	sleep 2s
	./run_regression.pl | tee perl_run_regression.log
	sleep 2s
	./attributes_test.pl | tee perl_attributes_test.log
	sleep 5s
	cp -f $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/perl/perl_ping.log $current_path/log/perl_ping.log
	cp -f $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/perl/perl_run_cases.log $current_path/log/perl_run_cases.log
	cp -f $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/perl/perl_run_regression.log $current_path/log/perl_run_regression.log
	cp -f $download_dir/automationTest/us_test-1.7.2-SNAPSHOT/perl/perl_attributes_test.log $current_path/log/perl_attributes_test.log
fi


if [ $migration_test = "true" ]; then 
	#cd /opt/local/jetty/bin/
	#./jetty.sh restart
	#sleep 20s

	cd $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/bin/
	./start-migration-test.sh | tee migration_ui_test.log
	sleep 2s
	./runtest.sh | tee docker_test.log
	sleep 5s
	cp -f $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/bin/migration_ui_test.log $current_path/log/migration_ui_test.log
	cp -f $download_dir/automationTest/migrationui-1.7.2-SNAPSHOT/bin/docker_test.log $current_path/log/docker_test.log
fi

if [ $stats_test = "true" ]; then 
	cd $download_dir/automationTest/stats_server-1.7.2-SNAPSHOT/bin/
	./test_stats.sh | tee stats_test.log
	sleep 5s
	cp -f $download_dir/automationTest/stats_server-1.7.2-SNAPSHOT/bin/stats_test.log $current_path/log/stats_test.log
fi

cp -f /opt/local/jetty/config/configuration.properties.~ /opt/local/jetty/config/configuration.properties
#if [ $migration_test = "true" ]; then 
	#cd /opt/local/jetty/bin/
	#./jetty.sh restart
#fi

echo "Finished"