$(document).ready(function () {

    $.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){	
        draw_organization_list(data);
        
        $.getJSON("http://localhost:8080/project1/api/organizations/" + temp.organizations[0].id, function(data,status,xhr){	
            draw_user_table(data);
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

function draw_user_table(data) {     
    var temp = {};
    temp.users = data.users;
    
    var source = $("#user-template").html(); 
    var template = Handlebars.compile(source);    
    var result = template(temp);
    $("#users-table").empty();
    $("#users-table").append(result);    
}

/* Onchange Event */
function select_organization() {
    
    $.getJSON("http://localhost:8080/project1/api/organizations/" + $("#objSelect").val(), function(data,status,xhr){	
        draw_user_table(data);
    }); 
}
