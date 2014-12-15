
$(document).ready(function () {
	
	//Scenario - structure : data, result, params
	//===================================
	
	var org = {"name":"_org0"};
	
	// Deferred only
	var d1 = test_post("http://localhost:8080/project1/api/organizations", org);
	
	$.when(d1).done(function(v1) {
		console.log(v1);
	});
	
	// Promise
	$.when(test_post_promise("http://localhost:8080/project1/api/organizations", org)).then(
		function( status ) {
			alert( status + ", things are going well" );
		},
		function( status ) {
			alert( status + ", you fail this time" );
		}
	);
	
	
	
	//WARNING check the name, it must be unique
	
	
	//POST /organizations 
	/*test_post("http://localhost:8080/project1/api/organizations", org).then(
		function(response) {
			org = console.log(response);
		}
	);
	
	test_get(org.id, function(data,status,xhr){
		//var temp = {};
		//temp.organizations = data;
		//draw_organization_table(temp);
		console.log(data);
	});*/
	
	
/*
	$.when(d1).done(function(r1){
		console.log(r1);
		console.log(org);
	});
	
	d1.resolve(test_post("http://localhost:8080/project1/api/organizations", org));
*/	
	//GET /organizations/id and GET /organizations
	//get_organizations_id(org);
	//get_organizations(org);
	
	//WARNING check the login, it must be unique
	//var user0 = {"login":"_use0","name":"_use0","pass":"_use0","organization":{"id" : org.id}};
	//var user1 = {"login":"_use1","name":"_use1","pass":"_use0","organization":{"id" : org.id}};
	//var user2 = {"login":"_use2","name":"_use2","pass":"_use0","organization":{"id" : org.id}};

	//POST /users
	//post_users(user0);
	
	//GET /users/id and GET /users
	//get_users_id(user0);
	//get_users(user0);
	
	//PUT /organizations/id => new user is the contact
	//The contact is added in the list of users
	
	//PUT the user
	
	//POST 2 users
	//post_users(user1);
	//post_users(user2);

	//GET /organizations/id
	//The 3 users are added in the list of users 
	//get_organizations_id(org);
	
	//GET /users?byOrganizationId=
	//We found 3 users
	
	//WARNING!! the name is unique
	//var sen0 = {"name": "sen0", "description": "sen0", "type" : "sen0", "open" : "true", "organization" : {"id" : org.id}}
	//var sen1 = {"name": "sen1", "description": "sen1", "type" : "sen1", "open" : "true", "organization" : {"id" : org.id}}
	//var sen2 = {"name": "sen2", "description": "sen2", "type" : "sen2", "open" : "false", "organization" : {"id" : org.id}}

	//POST /sensors (public)
	//post_sensors(sen0);
	
	//GET /sensors/id and GET /sensors
	//get_sensors_id(sen0);
	//get_sensors(sen0);
	
	//GET /organizations/id
	//The sensor is added in the list of sensors 
	//get_organizations_id(org);
	
	//POST /sensors (public)
	//POST /sensors (private)
	//post_sensors(sen1);
	//post_sensors(sen2);
	
	//GET /sensors?byOrganizationId=
	//We found 2 sensors
	
	//GET /sensors?byOrganizationId=&policy=public
	//We found 2 sensors
	
	//GET /sensors?byOrganizationId=&policy=private
	//We found 1 sensor

	//GET /sensors?byOrganizationId=&policy=all
	//We found 3 sensors
	
	//PUT /sensors/id
	//put_sensors_id(sen0);
	
	//GET /sensors/id
	//get_sensors_id(sen0):
	
	//var obs0 = {"date": "2014-15-14 00:02:52", "value" : 42, "sensor" : {"id" : sen0.id}}
	//var obs0 = {"date": "2014-15-14 00:02:52", "value" : 24, "sensor" : {"id" : sen1.id}}
	
	//POST /observations
	//POST /observations
	//post_observations(obs0);
	//post_observations(obs1);
	
	//GET /observations/id /observations
	//get_observations_id(obs0);
	//get_observations_id(obs1);
	
	//get_observations(obs0);
	//get_observations(obs1);
	
	//GET /observations?bySensorId=
	//We found 2 observations
	
	//GET /facts/numbers /facts/numbers/id
	
	//GET /facts/numbers?byOrganizationId=
	//We found 2
	
	//GET /facts/numbers?byOrganizationId=&bySensorId=
	//We found 1
	
	//GET /facts/numbers?bySensorId=
	//We found 1
	
	//GET /facts/summaries /facts/summaries/id
	//GET /facts/numbers?bySensorId=
	//We found 1
		
});

function test_post(myurl, data, cberror, cbsuccess) {
	
	var d = $.Deferred();
	
	s_data = "'"+JSON.stringify(data)+"'";
	
	$.ajax({
		url:myurl,
		type:"POST",
		data:s_data,
		contentType:"application/json",
		dataType:"json",
		success: function(msg) {
			d.resolve(msg);
		},
		error: function(err) {
			//console.log(err);
			d.reject(err);
		}
	});
	
	return d;
}

function test_post_promise(myurl, data, cberror, cbsuccess) {
	
	var d = $.Deferred();
	
	s_data = "'"+JSON.stringify(data)+"'";
	
	$.ajax({
		url:myurl,
		type:"POST",
		data:s_data,
		contentType:"application/json",
		dataType:"json",
		success: function(msg) {
			d.resolve(msg);
		},
		error: function(err) {
			//console.log(err);
			d.reject(err);
		}
	});
	
	return d.promise();
}

function test_get(id, cb) {
	$.getJSON("http://localhost:8080/project1/api/organizations/" + id, cb);
}

/*
function post_organizations() {
		$.ajax({
		url:"http://localhost:8080/project1/api/organizations",
		type:"POST",
		data:data,
		contentType:"application/json",
		dataType:"json",
		success: function(msg) {
		},
		error: function(err) {
		
		}
	});
}
 
function put_organizations_id() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function delete_organizations_id() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function post_users() {
	$.("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}
 
function put_users_id() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function delete_users_id() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function put_sensors_id() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function delete_sensors_id() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function post_observations() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function delete_factssummaries_id() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function delete_factsnumbers_id() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function get_organizations() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}
 
function get_organizations_id() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function get_sensors() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function get_sensors_id() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function get_observations() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function get_observations_id() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function get_factssummaries() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function get_factssummaries_id() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function get_factsnumbers() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}

function get_factsnumbers_id() {
	$.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
		var temp = {};
		temp.organizations = data;
		draw_organization_table(temp);
	});
}
*/
