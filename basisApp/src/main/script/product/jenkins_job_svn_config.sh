#! /bin/bash

svn=https://svn.bulyon.com/repos/LJ11/trunk/

client_svn="$svn"client
dashboard_svn="$svn"dashboard
framework_svn="$svn"framework
quedis_svn="$svn"framework/quedis
redgraph_svn="$svn"framework/redgraph
rs_task_svn="$svn"rs_task
rs_svn="$svn"rs2
script_prod_svn="$svn"deploy_script/product
script_puppet_svn="$svn"deploy_script/puppet
us_test_svn="$svn"test/us_test

sed -e "s#\(<remote>\)\(.*\)\(</remote>\)#\1$client_svn\3#" 	/var/lib/jenkins/jobs/client/config.xml -i
sed -e "s#\(<remote>\)\(.*\)\(</remote>\)#\1$dashboard_svn\3#" 	/var/lib/jenkins/jobs/dashboard/config.xml -i
sed -e "s#\(<remote>\)\(.*\)\(</remote>\)#\1$framework_svn\3#" 	/var/lib/jenkins/jobs/framework/config.xml -i
sed -e "s#\(<remote>\)\(.*\)\(</remote>\)#\1$quedis_svn\3#" 	/var/lib/jenkins/jobs/quedis/config.xml -i
sed -e "s#\(<remote>\)\(.*\)\(</remote>\)#\1$redgraph_svn\3#" 	/var/lib/jenkins/jobs/redgraph/config.xml -i
sed -e "s#\(<remote>\)\(.*\)\(</remote>\)#\1$rs_task_svn\3#" 	/var/lib/jenkins/jobs/rs_task/config.xml -i
sed -e "s#\(<remote>\)\(.*\)\(</remote>\)#\1$rs_svn\3#" 		/var/lib/jenkins/jobs/rs2/config.xml -i
sed -e "s#\(<remote>\)\(.*\)\(</remote>\)#\1$script_prod_svn\3#" /var/lib/jenkins/jobs/script_prod/config.xml -i
sed -e "s#\(<remote>\)\(.*\)\(</remote>\)#\1$script_puppet_svn\3#" /var/lib/jenkins/jobs/script_puppet/config.xml -i
sed -e "s#\(<remote>\)\(.*\)\(</remote>\)#\1$us_test_svn\3#" 	/var/lib/jenkins/jobs/us_test/config.xml -i

echo "Finished"
