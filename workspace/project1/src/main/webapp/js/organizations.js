$(document).ready(function () {
    
    //Data fzrom project1
    $.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){	
        var temp = {};
        temp.organizations = data;
        draw_organization_table(temp);
    }); 
    
});

function draw_organization_table(data) {
    var source = $("#organizations-template").html(); 
    var template = Handlebars.compile(source); 
    var result = template(data);    
    $("#organizations_table").append(result);
}