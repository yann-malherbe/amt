$(document).ready(function () {
	
    $.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){	
        var temp = {};
        temp.organizations = data;
        draw_organization_list(temp);
        
        $.getJSON("http://localhost:8080/project1/api/organizations/"+temp.organizations[0].id , function(data,status,xhr){
            var temp = {};
            temp.sensors = data.sensors;
            draw_sensor_table(temp);
        });
        
        $.getJSON("http://localhost:8080/project1/api/facts/numbers?order=byOrganizationId&id="+temp.organizations[0].id , function(data,status,xhr){
            
            var numbers = [];
            
            $.each(data, function(index){
                numbers.push({"name":data[index].sensor.name, "count":data[index].count});
            });
            console.log(numbers);
            draw_graph(numbers);
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
    });
    
    $.getJSON("http://localhost:8080/project1/api/facts/numbers?order=byOrganizationId&id="+$("#objSelect") , function(data,status,xhr){
        draw_graph(data);
    });

}

function draw_graph(data) {
    
    console.log(data);
    
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
    
    data_graph.data = data;
    
    Morris.Bar(data_graph);
}