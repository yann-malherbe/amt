HEIG-VD project1
=============

This project design an api to manage sensors on web platofrom.

More informations : http://amt.eof.li/

Installation procedure
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

Execute the script : script.sh

If you want relaod the script, execute first : clean_install.sh

If you have errors : 
Check the mysql path. 

Example : for me it is : C:\wamp\bin\mysql\mysql5.6.17\bin

Check all domains should be stopped. 

With a terminal, go to :  C:\Program Files\glassfish-4.1\bin

Begin with this command :  ./asadmin list-domdains
You can stop the domains with this command  :
./asadmin stop-domain name_domain

Reload the scrpit.sh.

###Open the project in netbeans###
Launch Netbeans in Administrator

Open the /workspace/project1 in netbeans. If you have many errors, build the project. The project need library from maven.

Now, we need to add a server glassFish.

Go to service tab and click to Add a Server.

Give a name and click on next.

Choose the directory glassfish-4.1 a click on next.

Choose the domain AMT and click on finish.

Demontration
-------------------

You can use node js to generate data.

With node js prompt go to Scripts directory.

Execute createObjects.sh

Execute sendValue.js


Warning !!! You have to run the server glassfish before the launch the script.

You can see the result on http://localhost:8080/project1/index.html

Following the implementation 
-------------------
**Done :** 

We tested methods GET and POST on the url :

* GET /organizations 
* GET /organizations/:id 
* GET /sensors
* GET /sensors/:id
* GET /observations
* GET /observations/:id

* POST /organizations 
* POST /sensors
* POST /observations
 
You can test POST methods with the sripts /Scripts/CreateObjects and /Scripts/SendValue.

You can test GET methods with the web app on the url : http://localhost:8080/project1/index.html.

The url /users is not used but still present in api.

**Not implemented :**

/facts/summaries
/facts/numbers
