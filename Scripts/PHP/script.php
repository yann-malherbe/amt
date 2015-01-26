<?php

ini_set('max_execution_time', 600);

$sensorType[] = array('name' => 'Thermo sensors', 'description' => 'Temperature sensor with C°', 'type' => 'Temperature in C°');
$sensorType[] = array('name' => 'Thermo sensors', 'description' => 'Temperature sensor with K', 'type' => 'Temperature in K');
$sensorType[] = array('name' => 'Thermo sensors', 'description' => 'Temperature sensor with F', 'type' => 'Temperature in F');
$sensorType[] = array('name' => 'Pressus sensors', 'description' => 'Pressus sensor with Bar', 'type' => 'Pressur in Bar');
$sensorType[] = array('name' => 'Wind Speed sensors', 'description' => 'Wind speed sensor with km/h', 'type' => 'Speed in km/h');
$sensorType[] = array('name' => 'Wind Speed sensors', 'description' => 'Wind speed sensor with m/s', 'type' => 'Speed in m/s');
$sensorType[] = array('name' => 'Moisture sensors', 'description' => 'Moisture sensor that give humidity in %', 'type' => 'Moisture in %');
$sensorType[] = array('name' => 'Light sensors', 'description' => 'Light sensor that give the amount of light with Lux', 'type' => 'Light in Lux');

$arrName = array('James Smith', 'Michael Smith', 'Robert Smith', 'Maria Garcia', 'David Smith', 'Maria Rodriguez', 'James Johnson', 'Robert Johnson', 'John Smith');

$fakeTimestamp = time();

$expectedSensors = 4*40;
$receivedSensors = 0;

$localFact = array();

function get($url)
{
	$response = false;
	$curl = curl_init($url);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
	$curl_response = curl_exec($curl);
	if ($curl_response === false) {
		$info = curl_getinfo($curl);
		curl_close($curl);
		$response['status'] = false;
		$response['data'] = $info;
		return $response;
		echo "\n\n!!! An error occured !!!\n\n";
	}
	curl_close($curl);
	$decoded = json_decode($curl_response, true);
	
	if($decoded === null)
	{
		echo "\n\n!!! An error occured !!!\n\n";
		$response['status'] = false;
		$response['data'] = $curl_response;
		return $response;
	}
	//echo 'response ok!';
	$response['status'] = true;
	$response['data'] = $decoded;
	

	
	return $response;
}

function post($url, $param, $method = "POST")
{
	$curl = curl_init($url);
	$data_string = json_encode($param);                                                                                   

	curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($curl, CURLOPT_CUSTOMREQUEST, $method);                                                                     
	curl_setopt($curl, CURLOPT_POSTFIELDS, $data_string);                                                                    
	curl_setopt($curl, CURLOPT_HTTPHEADER, array(                                                                          
		'Content-Type: application/json',                                                                                
		'Content-Length: ' . strlen($data_string))                                                                       
	); 
	$curl_response = curl_exec($curl);
	if ($curl_response === false) {
		$info = curl_getinfo($curl);
		curl_close($curl);
		echo "\n\n!!! An error occured !!!\n\n";
		$response['status'] = false;
		$response['data'] = $info;
		return $response;
	}
	curl_close($curl);
	$decoded = json_decode($curl_response, true);
	if($decoded === null)
	{
		echo "\n\n!!! An error occured !!!\n\n";
		$response['status'] = false;
		$response['data'] = $curl_response;
		return $response;
	}
	//echo 'response ok!';
	$response['status'] = true;
	$response['data'] = $decoded;
	
	return $response;
	
}

function createUsers($orgId, $isContact, $orgName)
{
	global $arrName;
	$name = $arrName[array_rand($arrName)];
	
	$postData = array (
	'login'=>$name,
	'pass'=>substr( md5(rand()), 0, 7),
	'name'=>$name,
	'organization'=>array('id'=>$orgId));
	
	$result = post('http://localhost:8080/project1/api/users', $postData);
	
	if($isContact && $result['status'])
	{	
		$id = $result['data'];
		$id = $id['id'];
		$postData = array(
			'name'=>$orgName,
			'contact'=>array('id'=>$id));
		post('http://localhost:8080/project1/api/organizations/'.$orgId, $postData, "PUT");
	}
}

function createOrganisations($orgname)
{
	$postData = array('name'=>$orgname);
	$result = post('http://localhost:8080/project1/api/organizations', $postData);
	
	if($result['status'])
	{
		$id = $result['data'];
		$id = $id['id'];
		createUsers($id, true, $orgname);
		createUsers($id, false, $orgname);
		createUsers($id, false, $orgname);
		createUsers($id, false, $orgname);
		
		for($i = 0; $i < 40; $i++)
			createSensors($id);
	}	
}
	
function createSensors($orgId, $open = true)
{
	global $sensorType;
	$sensor = $sensorType[array_rand($sensorType)];
	$postData = array(
		'name'=>$sensor['name'].'-'.rand(0,100),
		'description'=>$sensor['description'],
		'type'=>$sensor['type'],
		'open'=>$open?'true':'false',
		'organization'=>array('id'=>$orgId)
	);
	
	post('http://localhost:8080/project1/api/sensors', $postData);
}
	
function getSensors()
{	
	$result = get('http://localhost:8080/project1/api/sensors');
	if($result['status'])
	{
		sendObs($result['data']);
	}
	
}
	
function sendObs($sensors)
{
	global $receivedSensors;
	global $fakeTimestamp;
	global $localFact;
	
	$receivedSensors = count($sensors);
	
	for($nbObs = 0; $nbObs < 50; $nbObs++)
	{
		$fakeTimestamp+=600;
		reset($sensors);
		foreach($sensors as $sensor)
		{	
			$value = rand(0,100);
			$postData = array(
				'date'=>$fakeTimestamp,
				'value'=>$value,
				'sensor'=>array('id'=>$sensor['id'])
			);
			
			if(!isset($localFact[$sensor['id']]))
			{
				$localFact[$sensor['id']]['nbObs'] = 0;
				$localFact[$sensor['id']]['min'] = $value;
				$localFact[$sensor['id']]['max'] = $value;
				$localFact[$sensor['id']]['avg'] = $value;
			}
			
			$localFact[$sensor['id']]['nbObs']++;
			$localFact[$sensor['id']]['min'] = $localFact[$sensor['id']]['min'] < $value ? $localFact[$sensor['id']]['min'] : $value;
			$localFact[$sensor['id']]['max'] = $localFact[$sensor['id']]['max'] > $value ? $localFact[$sensor['id']]['max'] : $value;
			$localFact[$sensor['id']]['avg'] = ($localFact[$sensor['id']]['avg']*($localFact[$sensor['id']]['nbObs']-1)+$value)/$localFact[$sensor['id']]['nbObs'];
			
			post('http://localhost:8080/project1/api/observations', $postData);
		}
	}
}
	
echo '<pre>';


if(isset($_GET['create']))
{
	createOrganisations('AMT');
	createOrganisations('SAN');
	createOrganisations('RTA');
	createOrganisations('IOT');

	echo "Create finished \n";	
}
else
{
	getSensors();
	echo 'Expected sensors: '.$expectedSensors."\n";
	echo 'Received sensors: '.$receivedSensors."\n";
	$urlNumber = 'http://localhost:8080/project1/api/facts/numbers/?filterBy=bySensorId&filterId=24';
	$urlSummary = 'http://localhost:8080/project1/api/facts/summaries/?filterBy=bySensorId&filterId=24';
	$getFact = array();
	
	foreach($localFact as $key => $fact)
	{
		$result = get('http://localhost:8080/project1/api/facts/numbers/?filterBy=bySensorId&filterId='.$key);
		$nbObs = $result['data'][0]['count'];
		
		$result = get('http://localhost:8080/project1/api/facts/summaries/?filterBy=bySensorId&filterId='.$key);
		$min = $result['data'][0]['min'];
		$max = $result['data'][0]['max'];
		$avg = $result['data'][0]['average'];
		
		$getFact[$key]['nbObs'] = $nbObs;
		$getFact[$key]['min'] = $min;
		$getFact[$key]['max'] = $max;
		$getFact[$key]['avg'] = $avg;
	}
	
	echo "Send\n";
	print_r($localFact);
	
	echo "\nReceived\n";
	print_r($getFact);
	
	//echo "\nDifference\n";
	//print_r(@array_diff(@$localFact, @$getFact));
}
echo '</pre>';
	
	
	
	
	
	
	
	
	
	
	
	
	
	

?>
