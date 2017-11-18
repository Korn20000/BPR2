function view() {
	"use strict";
	
	function buildPatientList(patients) {
		document.getElementById("main_text").innerHTML = "";
		
		if(patients.length === 0 || patients === null)
		{
			document.getElementById("main_table").innerHTML = "<p class=\"innerAlarmsInList\">No data came in JSON</p>";
		} else {
			var out = "something went wrong";
			for (var i = 0; i < patients.length; i++) { out += patients[i]; }
			document.getElementById("main_table").innerHTML = out;
		}
	}
	
	return {buildPatientList};
}