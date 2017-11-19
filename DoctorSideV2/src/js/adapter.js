function adapter() {
	"use strict";

	//var patients = [];
	
	function ajaxGetPatients() {
        return $.when( $.ajax({
					url: '/src/php/process_patients.php',
					type: 'POST',
					data: 'disp_pat_json',
					dataType: 'json'}))
				.then(null, function(error) { alert("Error sending request")});
    }
	
	//function patientsFromJSON() {
		//var patients;
		//var myJSON = $.getJSON(ajaxGetPatients(), function() {patients = myJSON.responseJSON;});
		//return patients;
		//$.when (ajaxGetPatients()).then(function(data) { return JSON.parse (data); }, function(error) { return "Error Loading list"});
		//var myAjax = ajaxGetPatients();
		
		//while(myAjax.responseJSON === null) {
			//sit and wait
		//}
		
		//console.log('adapterPATIENTS:', patients);
		
		
		//return fetchJSONResponseData().responseJSON;
	//}
	
	async function fetchJSONResponseData(ajax) {
		try {
			var myJSONReturn = await ajax;
			console.log('async myJSONReturn:', myJSONReturn);
			return myJSONReturn;
		} catch (error) {
			console.log('Error in fetchJSONResponseData:', error);
		}
		//myPatientsJSON.then(function (data) { console.log('fetchDataPromise data:', data); });
	}
		
	
	//function checkIfJSONEmpty(JSON) {
	//	if (JSON === null) {
	//		//wait 1 second
	//		await sleep(
	//	}
	//}
	
	//function sleep(ms) {
	//	return new Promise(resolve => setTimeout(resolve, ms));
	//}
	//return {patientsFromJSON};
	
	
	return {fetchJSONResponseData, ajaxGetPatients};
}
	