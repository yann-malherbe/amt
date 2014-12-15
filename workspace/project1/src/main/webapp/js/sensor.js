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
            
                draw_line_graph(data);
                
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

function draw_line_graph(data) {
    
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
                draw_line_graph(data);
              
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
            console.log(n.sensor.id);
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
        draw_line_graph(data);
       
    });
    
    $.getJSON("http://localhost:8080/project1/api/facts/summaries?order=bySensorId&id=" + id_sensor, function(data,status,xhr){
        draw_area_graph(data);
    });

}


function draw_area_graph(data) {
    
    console.log(data);
    
    $("#allobservations").empty();
    
    var data_graph = {
        element: 'allobservations',
        data: [{
            period: '2010 Q1',
            iphone: 2666,
            ipad: null,
            itouch: 2647
        }, {
            period: '2010 Q2',
            iphone: 2778,
            ipad: 2294,
            itouch: 2441
        }, {
            period: '2010 Q3',
            iphone: 4912,
            ipad: 1969,
            itouch: 2501
        }, {
            period: '2010 Q4',
            iphone: 3767,
            ipad: 3597,
            itouch: 5689
        }, {
            period: '2011 Q1',
            iphone: 6810,
            ipad: 1914,
            itouch: 2293
        }],
        xkey: 'period',
        ykeys: ['iphone', 'ipad', 'itouch'],
        labels: ['iPhone', 'iPad', 'iPod Touch'],
        pointSize: 2,
        hideHover: 'auto',
        resize: true
    };
    
    // Area Chart
    var graph = Morris.Area(data_graph);

}

