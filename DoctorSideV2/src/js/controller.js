function controller(view, model) {
	"use scrict";
	
	function clickLoadJSONTest() {
		model.loadPatients();
		view.buildPatientList(model.getPatients());
	}
	
	return {clickLoadJSONTest};
}