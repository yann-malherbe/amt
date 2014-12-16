$(document).ready(function () {
    
    $.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){
        draw_organization_list(data);
        
        $.getJSON("http://localhost:8080/project1/api/organizations/" + data[0].id, function(data,status,xhr){
            draw_sensor_list(data);
            
            //Get observations of the first organization
            $.getJSON("http://localhost:8080/project1/api/observations","order=bySensorId&id=" + data.sensors[0].id,function(data,status,xhr){            
                draw_line_graph(data);
            });

            $.getJSON("http://localhost:8080/project1/api/facts/summaries", "order=bySensorId&id=" + data.sensors[0].id, function(data,status,xhr){
                draw_area_graph(data);
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

function draw_area_graph(data) {
    
    //Filter : set the good date format to draw the charts.
    $.each(data, function(index){
        var date = new Date(data[index].date);
        data[index].date = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    });
   
    $("#allobservations").empty();
    
    var data_graph = {
        element: 'allobservations',
        xkey: 'date',
        ykeys: ['min', 'average', 'max'],
        labels: ['min', 'average', 'max'],
        pointSize: 2,
        hideHover: 'auto',
        resize: true
    };
    
    data_graph.data = data;
    
    // Area Chart
    Morris.Area(data_graph);

}

function draw_line_graph(data) {
    
    //Filter : set the good date format to draw the charts.
    $.each(data, function(index){
        var date = new Date(data[index].date);
        data[index].date = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    });
    
    $("#today").empty();
    
    var data_graph = {
        element: 'today',
        xkey: ['date'],
        ykeys: ['value'],
        labels: ['Value'],
        smooth: false,
        resize: true
    };
    
    data_graph.data = data;
    Morris.Line(data_graph);
}

/* Onchange Event */
function select_organization() {
    
    $.getJSON("http://localhost:8080/project1/api/organizations/"+$("#objSelect").val() , function(data,status,xhr){
        draw_sensor_list(data);
    
        var first = data.sensors[0].id;
            
        //Get observations of the first organization
        $.getJSON("http://localhost:8080/project1/api/observations","order=bySensorId&id=" + first,function(data,status,xhr){
            draw_line_graph(data);
        });

        $.getJSON("http://localhost:8080/project1/api/facts/summaries","order=bySensorId&id=" + first, function(data,status,xhr){
            draw_area_graph(data);
        });           
    });
}


/* Onchange Event */
function select_sensor() {
    
    var id_sensor = $("#sensorSelect").val();

    //Get observations of the first organization
    $.getJSON("http://localhost:8080/project1/api/observations","order=bySensorId&id=" + id_sensor,function(data,status,xhr){
        draw_line_graph(data);       
    });
    
    $.getJSON("http://localhost:8080/project1/api/facts/summaries","order=bySensorId&id=" + id_sensor, function(data,status,xhr){
        draw_area_graph(data);
    });
}