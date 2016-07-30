<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Results</title>
<script src="http://d3js.org/d3.v3.min.js"></script>
</head>
<body>
		<script >

			d3.text("upload/risultato.csv", function (datasetText) {
    			var rows = d3.csv.parseRows(datasetText);
    			var tbl = d3.select("body").append("table");


        			
    		// headers
    		tbl.append("thead").append("tr")
        	.selectAll("th")
        	.data(rows[0])
        	.enter().append("th")
        	.text(function(d) {
            	return d;
        	});

    		// data
    		tbl.append("tbody")
        		.selectAll("tr")
        		.data(rows.slice(1))
        		.enter()
        		.append("tr")
				.on("click", function(d){ //console.log(d)
                                      var a = "";
                                        for(i=0; i<d.length; i++)
                                            a += d[i] + " ";
                                        alert(a);
                                    })
        		.on("mouseover", function() {this.style.cursor="pointer";})
        		.selectAll("td")
        		.data(function(d){return d;})
        		.enter()
        			.append("td")
                    .style("border", "2px black solid")
                    .style("padding", "5px")
                    .on("mouseover", function() {d3.select(this).style("background-color", "aquamarine")})
                    .on("mouseout", function() { d3.select(this).style("background-color", "white")})
        			.text(function(d){return d;})
});

			

</script>
</body>
</html>