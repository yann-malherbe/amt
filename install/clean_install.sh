#!/bin/bash
MY_PATH=$(pwd)
DB_TECHNICAL_USER=AMTUser
MYSQL_PATH=C:/wamp/bin/mysql/mysql5.6.17/bin
cd $MYSQL_PATH
./mysql -u root -p <<QUERY_INPUT
-- Drop and create database
DROP USER '$DB_TECHNICAL_USER'@'localhost';
DROP USER '$DB_TECHNICAL_USER'@'%';

QUERY_INPUT