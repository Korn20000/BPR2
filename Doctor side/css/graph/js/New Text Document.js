$(document).ready(function(){
	$.ajax({
		url: "https://neuropterous-object.000webhostapp.com/Bachelor/patientHistory.php",
		method: "GET",
		success: function(data) {
			console.log(data);
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
						label: 'Time',
						backgroundColor: 'rgba(200, 200, 200, 0.75)',
						borderColor: 'rgba(200, 200, 200, 0.75)',
						hoverBackgroundColor: 'rgba(200, 200, 200, 1)',
						hoverBorderColor: 'rgba(200, 200, 200, 1)',
						data: level
					}
				]
			};
			
			var ctx = $("#mycanvas");
			
			var barGraph = new Chart(ctx, {
				type: 'bar',
				data: 'chartdata'
			});
		},
		error: function(data) {
			console.log(data);
		}
	});
});