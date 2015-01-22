var Client = require('node-rest-client').Client;
var client = new Client();
var async = require('async');

var port = 8080;
var hostname = 'localhost';
var pathGetSensors = '/project1/api/sensors';
var pathPostValues = '/project1/api/observations';

var fakeTimestamp = new Date().getTime();

// This map keeps track of the transactions posted by the client, 
// even if they result in an error (for instance if two parallel requests try to create a new account).
// In this case, the client is informed that the transaction has failed and it would be his responsibility
// to retry.
var submittedStats = {}

// This map keeps track of the transactions posted by the client, but only if the server has confirmed
// their processing with a successful status code. 
// In this case, the client can assume that the transaction has been successfully processed.
var processedStats = {};

var requests = [];
var localFact = [][];
var localDayliFact = [][][];

function logTransaction(stats, transaction) {
	var accountStats = stats[transaction.accountId] || {
		accountId: transaction.accountId,
		numberOfTransactions: 0,
		balance: 0
	};
	accountStats.numberOfTransactions += 1;
	accountStats.balance += transaction.amount;
	stats[transaction.accountId] = accountStats;
}

function sensorsGETRequest(callback) {

	var requestData = {
		headers:{
			"Content-Type": "application/json"
		},
		data: {}
	};
	
	logTransaction(submittedStats, requestData.data);
	
	client.get("http://"+hostname+":"+port+pathGetSensors, function(data, response) {
		var error = null;
		var result = {
			requestData: requestData,
			data: data, 
			response: response
		};
		for(var nbObs = 0; nbObs < 1000; nbObs++)
		{
			fakeTimestamp += 10;
			for(var i = 0; i < data.length; i++)
			{
				requests.push(sendObservationsPOSTRequestFunction(data[i].id));
			}
		}
		callback(error, result);
	});

}

function sendObservationsPOSTRequestFunction(sensorId) {
		//'{"sensor": {"id":'+ids[i].id+'}, "date":'+new Date().getTime()+', "value":'+Math.floor((Math.random() * 100) + 1)+'}'
	return function(callback) {
		var requestData = {
			headers:{
				"Content-Type": "application/json"
			},
			data: {
				'sensor':{'id':sensorId},
				'date':fakeTimestamp,
				'value':0
			}
		};
		requestData.data.value = Math.floor((Math.random() * 100) + 1);
		logTransaction(submittedStats, requestData.data);
		
		if(localFact.indexOf(sensorId) == 0)
		{
			localFact[sensorId][nbObs] = 0;
			localFact[sensorId][min] = 0;
			localFact[sensorId][max] = 0;
			localFact[sensorId][avg] = 0;
		}
		
		localFact[sensorId][nbObs] += 1;
		localFact[sensorId][min] = (localFact[sensorId][min] < requestData.data.value) ? localFact[sensorId][min] : requestData.data.value;
		localFact[sensorId][max] = (localFact[sensorId][max] > requestData.data.value) ? localFact[sensorId][max] : requestData.data.value;
		localFact[sensorId][avg] = (localFact[sensorId][avg]*localFact[sensorId][nbObs]+requestData.data.value)/localFact[sensorId][nbObs];
		
		client.post("http://"+hostname+":"+port+pathPostValues, requestData, function(data, response) {
			var error = null;
			var result = {
				requestData: requestData,
				data: data, 
				response: response
			};
			callback(error, result);
		});
	}
}

function postSensorsRequestsInParallel(callback) {
	console.log("\n\n==========================================");
	console.log("POST sensors requests in parallel");
	console.log("------------------------------------------");
	var numberOfUnsuccessfulResponses = 0;
	async.parallel(requests, function(err, results) {
		for (var i=0; i<results.length; i++) {
			if (results[i].response.statusCode < 200 || results[i].response.statusCode >= 300) {
				console.log("Result " + i + ": " + results[i].response.statusCode);
				numberOfUnsuccessfulResponses++;
			} else {
				logTransaction(processedStats, results[i].requestData.data);
			}
		}
		callback(null, results.length + " transaction POSTs have been sent. " + numberOfUnsuccessfulResponses + " have failed.");
	});
}

/*
 * Fetch server-side state (list of accounts). The list of accounts is passed to the callback function.
 */
function checkValues(callback) {
	console.log("\n\n==========================================");
	console.log("Comparing client-side and server-side stats");
	console.log("------------------------------------------");
	var requestData = {
		headers:{
			"Accept": "application/json"
		}
	};
	client.get("http://localhost:8080/ConcurrentUpdateDemo/api/accounts", requestData, function(data, response) {
		var numberOfErrors = 0;
		var clientSideAccounts = Object.keys(submittedStats).length;
		var serverSideAccounts = data.length;
		console.log("Number of accounts on the client side: " + clientSideAccounts);
		console.log("Number of accounts on the server side: " + serverSideAccounts);
		if (clientSideAccounts !== serverSideAccounts) {
			numberOfErrors++;
		}
		
		for (var i=0; i<data.length; i++) {
			var accountId = data[i].id;
			var serverSideValue = data[i].balance;
			var clientSideValue = processedStats[accountId].balance;
			if (clientSideValue !== serverSideValue) {
				numberOfErrors++;
				console.log("Account " + accountId + " --> Server/Client balance: " + serverSideValue + "/" + clientSideValue + "  X");
			} else {
				//console.log("Account " + accountId + " --> Server/Client balance: " + serverSideValue + "/" + clientSideValue);				
			}
			
		}
		
		callback(null, "The client side and server side values have been compared. Number of corrupted accounts: " + numberOfErrors);
	});
}

async.series([
	resetServerState,
	postTransactionRequestsInParallel,
	checkValues
], function(err, results) {
	console.log("\n\n==========================================");
	console.log("Summary");
	console.log("------------------------------------------");
	//console.log(err);
	console.log(results);
});
	