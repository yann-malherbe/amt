$(document).ready(function () {

    $.getJSON("http://localhost:8080/project1/api/organizations", function(data,status,xhr){	
        var temp = {};
        temp.organizations = data;
        draw_organization_list(temp);
        
        $.getJSON("http://localhost:8080/project1/api/organizations/" + temp.organizations[0].id, function(data,status,xhr){	
            var temp = {};
            temp.users = data.users;
            draw_user_table(temp);
        });
    });    
});

function draw_organization_list(data) { 
    var source = $("#organization-template").html(); 
    var template = Handlebars.compile(source); 
    var result = template(data);    
    
    $("#organization-list").append(result);
}

function draw_user_table(data) { 
    var source = $("#user-template").html(); 
    var template = Handlebars.compile(source);    
    var result = template(data);
    
    $("#users-table").empty();
    $("#users-table").append(result);    
}

/* Onchange Event */
function select_organization() {
    
    $.getJSON("http://localhost:8080/project1/api/organizations/" + $("#objSelect").val(), function(data,status,xhr){	
        var temp = {};
        temp.users = data.users;
        draw_user_table(temp);
    }); 
}
