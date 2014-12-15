#!/bin/bash
MY_PATH=$(pwd)
DB_NAME=AMTDatabase
DB_TECHNICAL_USER=AMTUser
DB_TECHNICAL_USER_PASSWORD=dUke!1400$
DOMAIN_NAME=domainAMT
MYSQL_PATH=C:/wamp/bin/mysql/mysql5.6.12/bin
JDBC_CONNECTION_POOL_NAME=AMTDatabase_pool
JDBC_JNDI_NAME=jdbc/AMTDatabase

cd $MYSQL_PATH
./mysql -u root -p <<QUERY_INPUT
-- Drop and create database
DROP DATABASE IF EXISTS $DB_NAME;
CREATE DATABASE $DB_NAME;

CREATE USER '$DB_TECHNICAL_USER'@'localhost' IDENTIFIED BY '$DB_TECHNICAL_USER_PASSWORD';
CREATE USER '$DB_TECHNICAL_USER'@'%' IDENTIFIED BY '$DB_TECHNICAL_USER_PASSWORD';

GRANT ALL PRIVILEGES ON $DB_NAME.* TO '$DB_TECHNICAL_USER'@'localhost';
GRANT ALL PRIVILEGES ON $DB_NAME.* TO '$DB_TECHNICAL_USER'@'%';

QUERY_INPUT

cd 'C:/Program Files/glassfish-4.1/bin'
./asadmin stop-domain $DOMAIN_NAME
./asadmin delete-domain $DOMAIN_NAME
./asadmin create-domain --nopassword=true $DOMAIN_NAME

cd $MY_PATH
cp 'mysql-connector-java-5.1.33.jar' "C:/Program Files/glassfish-4.1/glassfish/domains/$DOMAIN_NAME/lib"

cd 'C:/Program Files/glassfish-4.1/bin'
./asadmin start-domain $DOMAIN_NAME

./asadmin create-jdbc-connection-pool \
 --restype=javax.sql.XADataSource \
 --datasourceclassname=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource \
 --property User=$DB_TECHNICAL_USER:Password=$DB_TECHNICAL_USER_PASSWORD:serverName=localhost:portNumber=3306:databaseName=$DB_NAME $JDBC_CONNECTION_POOL_NAME

./asadmin create-jdbc-resource --connectionpoolid $JDBC_CONNECTION_POOL_NAME $JDBC_JNDI_NAME

./asadmin ping-connection-pool $JDBC_CONNECTION_POOL_NAME