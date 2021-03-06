HEIG-VD project1
=============

This project design an api to manage sensors on web platofrom.

Visit our site web : http://amt.eof.li/

A great IMH to report the observations of sensors with plot ! 

![FrontEnd](https://github.com/yann-malherbe/amt/blob/master/documentation/front.png)

![Plots](https://github.com/yann-malherbe/amt/blob/master/documentation/Plot.jpg)

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

Choose the installation directory C:/.../glassfish-4.1 click on next.

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

We tested methods GET, PUT and POST on the url :

* GET /organizations 
* GET /organizations/:id 
* GET /organizations/:id/users 
* GET /sensors
* GET /sensors/:id
* GET /observations
* POST /organizations 
* POST /sensor
* POST /observations
* GET /facts/summaries
* GET /facts/numbers
* GET /facts/summaries/:id
* GET /facts/numbers/:id

Be carefull, some urls works only with querystrings. 

For this time, we assume that the value send are correct and we assume that the BD is not empty !
 
You can test POST methods with the sripts /Scripts/CreateObjects and /Scripts/SendValue.

You can test GET methods with the web app on the url : http://localhost:8080/project1/index.html.

Now, we also tested the problem of concurrency. We secure the problem when we update facts. It is possible to test this part
with the script amt/Scripts/generateAndTestValues.js.

We don't secure the problem when we create many facts at the same time.

Others points of evaluation :

- We revised the documentation on api-doc seed.
- We din't revised the struct of payload => {"name" : "AMT", "contact" : {"id" : 1}}. It involves too many changes, like the comments of id => use "id" : "/users/1" instead "id" : 1. We don't change this point.
