$(document).ready(function () {
    
    $.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){	
        var temp = {};
        temp.organizations = data;
        draw_organization_list(temp);
        
        $.getJSON("http://localhost:8080/project1/api/organizations/"+temp.organizations[0].id , function(data,status,xhr){
            
            //Get sensors
            var temp = {};
            temp.sensors = data.sensors;
            draw_sensor_list(temp);
            
            var first = temp.sensors[0].id;
            
            //Get observations of the first organization
            $.getJSON("http://localhost:8080/project1/api/observations","bySensorId=" + first,function(data,status,xhr){
                
                //Filter : only sensors of organization
                data = $.grep(data, function(n){
                    return n.sensor.id === first;
                });
                
                //Filter : set the good date format to draw the charts.
                $.each(data, function(index){
                    var date = new Date();
                    date.setSeconds(index);
                    data[index].date = date.getYear() + "-" + date.getMonth() + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
                });
                
                //Get observations
                var temp = {};
                temp.observations = data;                
                draw_observations_table(temp);
            });
            
            $.getJSON("http://localhost:8080/project1/api/facts/summaries?order=bySensorId&id=" + first, function(data,status,xhr){
                draw_area_graph(data);
            });
        });
    }); 
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
    
    console.log(data);
    var facts = {
        facts:[            
                {date: "10.12.2014", min:"1", average:"12", max:"14"},
                {date: "10.12.2014", min:"1", average:"12", max:"14"}]
    };
    
    var source = $("#fact-template").html(); 
    var template = Handlebars.compile(source); 
    var result = template(facts);
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
    
    $.getJSON("http://localhost:8080/project1/api/organizations/"+$("#objSelect").val() , function(data,status,xhr){

        //Get sensors
        var temp = {};
        temp.sensors = data.sensors;
        draw_sensor_list(temp);

        var first = temp.sensors[0].id;

        //Get observations of the first organization
        $.getJSON("http://localhost:8080/project1/api/observations","bySensorId=" + first,function(data,status,xhr){

            //Filter : only sensors of organization
            data = $.grep(data, function(n){
                return n.sensor.id === first;
            });

            //Filter : set the good date format to draw the charts.
            $.each(data, function(index){
                var date = new Date();
                date.setSeconds(index);
                data[index].date = date.getYear() + "-" + date.getMonth() + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
            });

            //Get observations
            var temp = {};
            temp.observations = data;                
            draw_observations_table(temp);
        });
        
        $.getJSON("http://localhost:8080/project1/api/facts/summaries?order=bySensorId&id=" + first, function(data,status,xhr){
            draw_area_graph(data);
        });

    });
}

/* Onchange Event */
function select_sensor() {    
    
    var id_sensor = $("#sensorSelect").val();
    
    //Get observations of the first organization
    $.getJSON("http://localhost:8080/project1/api/observations","bySensorId=" + id_sensor,function(data,status,xhr){

        //Filter : only sensors of organization
        data = $.grep(data, function(n){
            return n.sensor.id == id_sensor;
        });

        //Filter : set the good date format to draw the charts.
        $.each(data, function(index){
            var date = new Date();
            date.setSeconds(index);
            data[index].date = date.getYear() + "-" + date.getMonth() + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        });

        //Get observations
        var temp = {};
        temp.observations = data;                
        draw_observations_table(temp);
    });
    
    $.getJSON("http://localhost:8080/project1/api/facts/summaries?order=bySensorId&id=" + firid_sensorst, function(data,status,xhr){
        draw_area_graph(data);
    });

}


