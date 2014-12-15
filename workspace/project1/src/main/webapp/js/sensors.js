$(document).ready(function () {
	
    $.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){	
        var temp = {};
        temp.organizations = data;
        draw_organization_list(temp);
        
        $.getJSON("http://localhost:8080/project1/api/organizations/"+temp.organizations[0].id , function(data,status,xhr){
            var temp = {};
            temp.sensors = data.sensors;
            draw_sensor_table(temp);
            draw_graph();         
        });
    }); 
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
    
    $.getJSON("http://localhost:8080/project1/api/organizations/" + $("#objSelect").val(), function(data,status,xhr){	
        var temp = {};
        temp.sensors = data.sensors;
        draw_sensor_table(temp);
        draw_graph();
    });     

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