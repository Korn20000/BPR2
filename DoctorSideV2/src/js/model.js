function model(adapter) {
	"use strict";
	
	var patients = [];
	var patientHistory = [];
	
	function loadPatients() {
		console.log('patientsJSON:', adapter.patientsFromJSON());
		patients = adapter.patientsFromJSON();
	}
	
	function getPatients() {
		return patients;
	}
	
	function loadPatientsHistory(cpr) {
		patientHistory = adapter.patientHistory(cpr);
	}
	
	function getPatientsHistory() {
		return patientHistory;
	}
	
	return {loadPatients, getPatients, loadPatientsHistory, getPatientsHistory}
}