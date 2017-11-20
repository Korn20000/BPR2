function model() {
	"use strict";
	
	var patients = [];
	var patientHistory = [];
	var patientsUpdated = false;
	
	function setPatients(patientsArg) {
		setPatientsUpdated(true);
		patients = patientsArg;
	}
	
	function getPatients() {
		return patients;
	}
	
	function getPatientsUpdated() {
		return patientsUpdated;
	}
	
	function setPatientsUpdated(yesNo) {
		patientsUpdated = yesNo;
	}
	
	return {setPatients, getPatients, getPatientsUpdated, setPatientsUpdated};
}