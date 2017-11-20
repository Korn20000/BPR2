function modelmanager(model, adapter) {
	"use scrict";
	
	function loadPatients() {
	//console.log('patientsJSON:', adapter.fetchJSONResponseData());
	try {
		adapter.fetchJSONResponseData(adapter.ajaxGetPatients()).then(function(data) { 
													console.log('loadPatients data:', data);
													model.setPatients(JSON.parse(data));},
													function(error) { console.log('Error in loadPatients:', error);
																		model.setPatients("Error Loading Patients !");});
	} catch (error) {
		console.log('Error in loadPatients:', error);
		}
	}
	
	function getPatientsArray() {
		return model.getPatients();
	}
	
	function arePatientsUpdated() {
		return model.getPatientsUpdated();
	}
	
	function patientsUpdateFinish() {
		return model.setPatientsUpdated(false);
	}
	
	return {loadPatients, getPatientsArray, arePatientsUpdated, patientsUpdateFinish};
}