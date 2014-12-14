var http = require("http");
var https = require("https");
//var sleep = require('sleep');

//Math.random().toString(36).substr(5, 10)

var port = 80;
var hostname				= 'home.eof.li';
var pathPostusers			= '/get/post.php';
var pathPostOrganisation	= '/get/post.php';
var pathPostSensors			= '/get/post.php';



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
	var post_data = '{"login" : "login-'+Math.random().toString(36).substr(2, 4)+'", "pass": "'+Math.random().toString(36).substr(10, 15)+'", "name" : "Name-'+Math.random().toString(36).substr(2, 4)+'", organisation: {"id":'+orgId+'}}';
			console.log(post_data);
			
	var options = {
		host: hostname,
		port: port,
		path: pathPostusers,
		method: 'POST',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
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
				path: pathPostOrganisation+'?'+orgId,
				method: 'PUT',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
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
			'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
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
	createSensors(orgId, "true");
	createSensors(orgId, "false");
	}, post_data);
};

var createSensors = function(orgId, open)
{
	for(var i = 0; i < 2; i++)
	{
		var post_data = '{"name":"sensor-'+Math.random().toString(36).substr(2, 4)+'", "description": "Sensors in room '+Math.random().toString(36).substr(1, 3)+'", "type" : "Temperature", "open" : "'+open+'", "organization" : {"id":'+orgId+'}}';
			console.log(post_data);
			
		var options = {
			host: hostname,
			port: port,
			path: pathPostSensors,
			method: 'POST',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
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
