$(document).ready(function () {
	
    $.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){	
        draw_organization_list(data);
        
        var id = data[0].id;
        
        $.getJSON("http://localhost:8080/project1/api/organizations/"+id , function(data,status,xhr){
            draw_sensor_table(data);
        });
 
        $.getJSON("http://localhost:8080/project1/api/facts/numbers?order=byOrganizationId&id="+id , function(data,status,xhr){
            draw_graph(data);
        });
    }); 
});

function draw_organization_list(data) {
    var temp = {};
    temp.organizations = data;
    var source = $("#organization-template").html(); 
    var template = Handlebars.compile(source); 
    var result = template(temp);    
    $("#organization-list").append(result);    
}

function draw_sensor_table(data) {
    var temp = {};
    temp.sensors = data.sensors;
    
    var source = $("#sensor-template").html(); 
    var template = Handlebars.compile(source); 
    var result = template(temp);
    $("#sensors-table").empty();
    $("#sensors-table").append(result);
}


/* Onchange Event*/
function select_organization() {
    
    $.getJSON("http://localhost:8080/project1/api/organizations/" + $("#objSelect").val(), function(data,status,xhr){	
        draw_sensor_table(data);
    });
    
    $.getJSON("http://localhost:8080/project1/api/facts/numbers?order=byOrganizationId&id="+$("#objSelect").val() , function(data,status,xhr){
        draw_graph(data);
    });

}

function draw_graph(data) {
    
    //Process
    var numbers = [];

    $.each(data, function(index){
        numbers.push({"name":data[index].sensor.name, "count":data[index].count});
    });

    //Graph
    $("#numbers").empty();
    
    var data_graph = {
        element: 'numbers',
        xkey: 'name',
        ykeys: ['count'],
        labels: ['Counts'],
        barRatio: 0.4,
        xLabelAngle: 35,
        hideHover: 'auto',
        resize: true
    }; 
    
    data_graph.data = numbers;
    
    Morris.Bar(data_graph);
}