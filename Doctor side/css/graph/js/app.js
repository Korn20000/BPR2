$(document).ready(function(){
   $.ajax({
       url: "https://neuropterous-object.000webhostapp.com/Bachelor/graphData.php",
       method: "GET",
       success: function(data) {
           console.log(data);
           data = JSON.parse(data);
           var time = [];
           var level = [];
           
           for(var i in data) {
               time.push("Time " + data[i].Time);
               level.push(data[i].Level);
           }
           
           var chartdata = {
               labels: time,
               datasets : [
                   {
                       label: 'Level',
                       fill: false,
                       lineTension: 0.1,
                       backgroundColor: 'rbga(59, 89, 152, 0.75)',
                       borderColor: 'rbga(59, 89, 152, 1)',
                       pointHoverBackgroundColor: 'rbga(59, 89, 152, 1)',
                       pointHoverBorderColor: 'rbga(59, 89, 152, 1)',
                       data: level
                   }
               ]
           };
           
           var ctx = $("#mycanvas");
           
           var LineGraph = new Chart(ctx, {
               type: 'line',
               data: chartdata
           });
       },
       error: function(data) {
           console.log(data);
       }
   }); 
});