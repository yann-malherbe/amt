$(document).ready(function () {

    var organizations = {
        organizations:[{id: 1, name:"AMT"},{id:2, name:"STI"}]
    };
    
    var users = {
        users:[{name:"John Doe", isContact:true}, {name:"Eric Stan", isContact:false}]
    };
        
    draw_organization_list(organizations);
    draw_user_table(users);
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
    console.log($("#objSelect").value);
    
    //get ....
    var users = {
        users:[{name:"John Doe", isContact:true}, {name:"Eric Stan", isContact:false}]
    };
    
    draw_organization_table(users);
}
