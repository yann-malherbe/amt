var http = require("http");
var https = require("https");
//var sleep = require('sleep');

//Math.random().toString(36).substr(5, 10)
//application/x-www-form-urlencoded;charset=utf-8

var port = 8080;
var hostname				= 'localhost';
var pathPostusers			= '/project1/api/users';
var pathPostOrganisation	= '/project1/api/organizations';
var pathPostSensors			= '/project1/api/sensors';


var sensorType[];

sensorType.push({name:'Thermo sensors', description: 'Temperature sensor with C°', type:'Temperature in C°'});
sensorType.push({name:'Thermo sensors', description: 'Temperature sensor with K', type:'Temperature in K'});
sensorType.push({name:'Thermo sensors', description: 'Temperature sensor with F', type:'Temperature in F'});
sensorType.push({name:'Pressus sensors', description: 'Pressus sensor with Bar', type:'Pressur in Bar'});
sensorType.push({name:'Wind Speed sensors', description: 'Wind Speed sensor with km/h', type:'Speed in km/h'});
sensorType.push({name:'Moisture sensors', description: 'Moisture sensor that give humidity in %', type:'Moisture in %'});
sensorType.push({name:'Wind Speed sensors', description: 'Wind Speed sensor with m/s', type:'Speed in m/s'});
sensorType.push({name:'Light sensors', description: 'Light sensor that give the amount of light with Lux', type:'Light in Lux'});

var arrName[];

arrName.push('James Smith', 'Michael Smith', 'Robert Smith', 'Maria Garcia', 'David Smith', 'Maria Rodriguez', 'James Johnson', 'Robert Johnson', 'John Smith');


/**
 * getJSON:  REST get request returning JSON object(s)
 * @param options: http options object
 * @param callback: callback to pass the results JSON object(s) back
 */
var sendRequest = function(options, onResult, post_data)
{
    console.log("Start");

    var prot = options.port == 443 ? https : http;
    var req = prot.request(options, function(res)
    {
        var output = '';
        console.log(options.host + ':' + res.statusCode);
        res.setEncoding('utf8');

        res.on('data', function (chunk) {
            output += chunk;
        });

        res.on('end', function() {
			console.log(output);
            var obj = JSON.parse(output);
			console.log("onResult: (" + res.statusCode + ")\n\nData:\n" + JSON.stringify(obj));
            //var obj = output;
			onResult(res.statusCode, obj);
        });
    });

    req.on('error', function(err) {
        //res.send('error: ' + err.message);
    });

	req.write(post_data);
    req.end();
};


var createUsers = function(orgId, isContact, orgName)
{
	var name = arrName[Math.floor(Math.random() * arrName.length)];
	var sensor = arrName[Math.floor(Math.random() * arrName.length)];
	var post_data = '{"login" : "'+name+'", "pass": "'+Math.random().toString(36).substr(10, 15)+'", "name" : "'+name+'", "organization" : {"id":'+orgId+'}}';
			console.log(post_data);
			
	var options = {
		host: hostname,
		port: port,
		path: pathPostusers,
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			'Content-length': post_data.length
		}
	};
	sendRequest(options, function(statusCode, result){
		var userId = result.id;
		// if isContact set as contact
		if(isContact)
		{
			var post_data = '{"name" : "'+orgName+'", "contact": {"id":'+userId+'}}';
			console.log(post_data);
			
			
			var options = {
				host: hostname,
				port: port,
				path: pathPostOrganisation+'/'+orgId,
				method: 'PUT',
				headers: {
					'Content-Type': 'application/json',
					'Content-length': post_data.length
				}
			};
				
				sendRequest(options, function(statusCode, result){}, post_data);
		}
	
	}, post_data);
};

var createOrganisations = function(orgName)
{
	var post_data = '{"name" : "'+orgName+'"}';
			console.log(post_data);
			
	var options = {
		host: hostname,
		port: port,
		path: pathPostOrganisation,
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			'Content-length': post_data.length
		}
	};
	sendRequest(options, function(statusCode, result){
	var orgId = result.id;
	
	//Users
	createUsers(orgId, true, orgName); //contact
	createUsers(orgId, false, orgName);
	createUsers(orgId, false, orgName);
	createUsers(orgId, false, orgName);
	
	createSensors(orgId, "true");
	createSensors(orgId, "true");
	createSensors(orgId, "false");
	createSensors(orgId, "false");
	}, post_data);
};

var createSensors = function(orgId, open)
{
	for(var i = 0; i < 4; i++)
	{
		var sensor = sensorType[Math.floor(Math.random() * sensorType.length)];
		
		var post_data = '{"name":"sensor-'+Math.random().toString(36).substr(2, 4)+'", "description": "Sensors in room '+Math.random().toString(36).substr(1, 3)+'", "type" : "Temperature", "open" : "'+open+'", "organization" : {"id":'+orgId+'}}';
			console.log(post_data);
			
		var options = {
			host: hostname,
			port: port,
			path: pathPostSensors,
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				'Content-length': post_data.length
			}
		};
		sendRequest(options, function(statusCode, result){}, post_data);	
	}
}

//createUsers();

createOrganisations("AMT");
createOrganisations("SAN");
createOrganisations("STI");
createOrganisations("RTA");
