$(document).ready(function () {

    var source = $("#some-template").html(); 
    var template = Handlebars.compile(source); 
    
    var data = {
        organizations:[{name:"AMT", contact:"John"},{name:"STI", contact:"Steve"}]
    };
    
    var result = template(data);
    console.log(result);
    
    $("#organizations_table").append(result);
});