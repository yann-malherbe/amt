HEIG-VD project1
=============

Install
-------------
The platform is designed with Java EE.
We work with Netbeans IDE 8.0.2 and java jdk1.8.0_25.
We assume that you have netbeans and java.

###Install Wamp Server###

We work with wamp to manage the Mysql database. 

Apache : 2.4.9 MySQL : 5.6.17 PHP : 5.5.12 PHPMyAdmin : 4.1.14 SqlBuddy : 1.3.3 XDebug : 2.2.5

Download : http://www.wampserver.com/

###Create database and start domain###
Open a terminal as Admin and go to in the directory /install

The goal of this part is executed the script : script.sh

BUT !!!! you should edit the script to fix errors.

Line 6 : 
If you use the same wampserver version, it should be all right. But if you do not or if use an other support you have to locate your mysql path.

Example : for me it is : C:\wamp\bin\mysql\mysql5.6.17\bin

Line 16 :
Line 17 :
If you have the error : Connexion could not be allocated because : Accès refusé pour l'utilisateur : AMTUser@localhost
you have to comment this lines.

**For the first use you have to comment this lines.**

If you have others errors : you can check you domain.

With an another cygwin terminal, go to :  C:\Program Files\glassfish-4.1\bin

The command asadmin can list, stop your domains.

Begin with this command : 
./asadmin list-domains

All domains should be stopped !

If not you can stop the domains with this command  :
./asadmin stop-domain name_domain

Reload your scrpit.

###Open the project in netbeans###
Launch Netbeans in Administrator

Open the /workspace/project1 in netbeans. If you have many errors, build the project. The project need library from maven.

Now, we need to add a server glassFish.

Go to service tab and click to Add a Server.

Give a name.

Choose the directory glassfish-4.1
