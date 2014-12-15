$(document).ready(function () {
	
	var request = new Http.Get("http://localhost", false);

	request.start().then(function(response) {
		console.log(response);
	}).fail(function(error, errorCode) {

	});
	
	
    var data = {
        organizations:[{id: 1, name:"AMT"},{id:2, name:"STI"}]
    };
    draw_organization_list(data);
    
    var sensors = {
        sensors:[
            {name:"sensors#2334", description:"Sensor of temperature", type:"Kelvin", open:"true"},
            {name:"sensors#1125", description:"Photometric ", type:"Lux", open:"true"}]
    };
    draw_sensor_table(sensors);
   
    draw_graph();
});

function draw_organization_list(data) {
    var source = $("#organization-template").html(); 
    var template = Handlebars.compile(source); 
    var result = template(data);    
    $("#organization-list").append(result);    
}

function draw_sensor_table(data) {
    var source = $("#sensor-template").html(); 
    var template = Handlebars.compile(source); 
    var result = template(data);
    
    $("#sensors-table").empty();
    $("#sensors-table").append(result);
}


/* Onchange Event*/
function select_organization() {
    
    var data = {
        sensors:[
            {name:"sensors#2334", description:"Sensor of temperature", type:"Kelvin", open:"true"},
            {name:"sensors#1125", description:"Photometric ", type:"Lux", open:"true"}]
    };
    
    draw_sensor_table(data);
    draw_graph();
}

function draw_graph() {
    
    $("#numbers").empty();
    
    var data_graph = {
        element: 'numbers',
        data: [{
            device: 'Sensor1',
            geekbench: 500
        }, {
            device: 'Sensor2',
            geekbench: 137
        }, {
            device: 'Sensor3',
            geekbench: 275
        }, {
            device: 'Sensor4',
            geekbench: 380
        }, {
            device: 'Sensor5',
            geekbench: 655
        }, {
            device: 'Sensor6',
            geekbench: 1571
        }],
        xkey: 'device',
        ykeys: ['geekbench'],
        labels: ['Geekbench'],
        barRatio: 0.4,
        xLabelAngle: 35,
        hideHover: 'auto',
        resize: true
    }; 
    
    var graph = Morris.Bar(data_graph);
}