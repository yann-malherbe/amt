$(document).ready(function () {
    
    
    $.post( 'http://localhost:8080/project1/api/organizations/', {"name" : "AMT"} ,function(data) { 
        console.log(data);
    });


 
});