$(document).ready(function () {
    
    /*Handlebars.registerHelper('table', function (items, options) {
        out = out + "<div class=\"table-responsive\">" + "\n";
        out = out + "<table class=\"table table-hover table-striped\">" + "\n";
        out = out + "<thead>" + "\n";
        out = out + "<tr>" + "\n";
        out = out + "<th>Organization</th>" + "\n";
        out = out + "<th>Contact</th>" + "\n";
        out = out + "<tr>" + "\n";
        out = out + "<thead>" + "\n";
        out = out + "<tbody>" + "\n";
        
        for (var i = 0, l = items.length; i < l; i++) {
            out = out + "<tr>" + "\n";
            out = out + "<td>" + items[i].name + "</td>" + "\n";
            out = out + "<td>" + items[i].contact + "</td>" + "\n";
            out = out + "</tr>" + "\n";
        }

        out = out + "</tbody>" + "\n";
        out = out + "</table>" + "\n";
        out = out + "</div>" + "\n";

        return out;
    });
    */
    var src = document.getElementById("organizations_template").innerHTML;
    console.log(src);
    var tmp = Handlebars.compile(src);
    var ctx = {
        organizations:["blbla"]//{name:"AMT", contact:"John"},{name:"STI", contact:"Steve"}]
    };

    var out = tmp(ctx);
    
    console.log(out);
    
    document.getElementById("organizations_template").innerHTML = out;
});