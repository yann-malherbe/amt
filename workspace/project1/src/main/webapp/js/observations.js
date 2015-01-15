$(document).ready(function () {
    
    $.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){	
        draw_organization_list(data);
        
        $.getJSON("http://localhost:8080/project1/api/organizations/"+data[0].id , function(data,status,xhr){            
            draw_sensor_list(data);
            
            //Get observations of the first organization
            $.getJSON("http://localhost:8080/project1/api/observations","order=bySensorId&id=" + data.sensors[0].id,function(data,status,xhr){
                draw_observations_table(data);
            });
            
            $.getJSON("http://localhost:8080/project1/api/facts/summaries?order=bySensorId&id=" + data.sensors[0].id, function(data,status,xhr){
                draw_facts_table(data);
            });
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

function draw_sensor_list(data) {
    var temp = {};
    temp.sensors = data.sensors;
    var source = $("#sensor-template").html(); 
    var template = Handlebars.compile(source); 
    var result = template(temp);
    $("#sensors-list").empty();
    $("#sensors-list").append(result);
}

function draw_facts_table(data) {
    //Filter : set the good date format to draw the charts.
    $.each(data, function(index){
        var date = new Date(data[index].date);
        data[index].date = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    });
    
    var temp = {};
    temp.facts = data;           
    
    var source = $("#fact-template").html(); 
    var template = Handlebars.compile(source); 
    var result = template(temp);
    $("#facts-table").empty();
    $("#facts-table").append(result);
}

function draw_observations_table(data) {
    //Filter : set the good date format to draw the charts.
    $.each(data, function(index){
        var date = new Date(data[index].date);
        data[index].date = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    });
   
    var temp = {};
    temp.observations = data;
    var source = $("#observation-template").html(); 
    var template = Handlebars.compile(source); 
    var result = template(temp);
    $("#observations-table").empty();
    $("#observations-table").append(result);
}

/* Onchange Event */
function select_organization() {
    var id = $("#objSelect").val();
    
    $.getJSON("http://localhost:8080/project1/api/organizations/"+ id, function(data,status,xhr){
        draw_sensor_list(data);

        //Get observations of the first organization
        $.getJSON("http://localhost:8080/project1/api/observations","order=bySensorId&id=" + data.sensors[0].id,function(data,status,xhr){
            draw_observations_table(data);
        });
        
        $.getJSON("http://localhost:8080/project1/api/facts/summaries","order=bySensorId&id=" + data.sensors[0].id, function(data,status,xhr){
            draw_facts_table(data);
        });

    });
}

/* Onchange Event */
function select_sensor() {    
    var id = $("#sensorSelect").val();
    
    //Get observations of the first organization
    $.getJSON("http://localhost:8080/project1/api/observations","order=bySensorId&id=" + id,function(data,status,xhr){
        draw_observations_table(data);
    });
    
    $.getJSON("http://localhost:8080/project1/api/facts/summaries","order=bySensorId&id=" + id, function(data,status,xhr){
        draw_facts_table(data);
    });
}