$(document).ready(function () {
    
    var data = {
        organizations:[{name:"AMT", contact:"John"},{name:"STI", contact:"Steve"}]
    };
    
    draw_organization_table(data);
});

function draw_organization_table(data) {
    var source = $("#organizations-template").html(); 
    var template = Handlebars.compile(source); 
    
    var result = template(data);    
    $("#organizations_table").append(result);
}