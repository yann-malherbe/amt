$(document).ready(function () {
    
   $.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){	
        var temp = {};
        temp.organizations = data;
        draw_organization_list(temp);
    });
    
    var sensors = {
        sensors:[
            {id:"1", name:"sensors#2334", description:"Sensor of temperature"},
            {id:"2", name:"sensors#1125", description:"Photometric"}]
    };
    draw_sensor_list(sensors);
    
    var facts = {
        facts:[            
                {date: "10.12.2014", min:"1", average:"12", max:"14"},
                {date: "10.12.2014", min:"1", average:"12", max:"14"}]
    };
    draw_facts_table(facts);
    
    var observations = {
        observations:[
                {date: "10.12.2014 12:00:00", data:"1"},
                {date: "10.12.2014 12:00:10", data:"123"}]
    };
    draw_observations_table(observations);
    
});

function draw_organization_list(data) {
    var source = $("#organization-template").html(); 
    var template = Handlebars.compile(source);     
    var result = template(data);    
    $("#organization-list").append(result);
}

function draw_sensor_list(data) {
    var source = $("#sensor-template").html(); 
    var template = Handlebars.compile(source); 
    var result = template(data);
    $("#sensors-list").empty();
    $("#sensors-list").append(result);
}

function draw_facts_table(data) {
    var source = $("#fact-template").html(); 
    var template = Handlebars.compile(source); 
    var result = template(data);
    $("#facts-table").empty();
    $("#facts-table").append(result);
}

function draw_observations_table(data) {
    var source = $("#observation-template").html(); 
    var template = Handlebars.compile(source); 
    var result = template(data);
    $("#observations-table").empty();
    $("#observations-table").append(result);
}

/* Onchange Event */
function select_organization() {
    var sensors = {
        sensors:[
            {id:"1", name:"sensors#2334", description:"Sensor of temperature"},
            {id:"2", name:"sensors#1125", description:"Photometric"}]
    };
    draw_sensor_list(sensors);
}

/* Onchange Event */
function select_sensor() {    
    var facts = {
        facts:[            
                {date: "10.12.2014", min:"1", average:"12", max:"14"},
                {date: "10.12.2014", min:"1", average:"12", max:"14"}]
    };
    draw_facts_table(facts);
    
    var observations = {
        observations:[
                {date: "10.12.2014 12:00:00", data:"1"},
                {date: "10.12.2014 12:00:10", data:"123"}]
    };
    draw_observations_table(observations);
}


