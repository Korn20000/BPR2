function controller(view, modelmanager) {
	"use scrict";
	
	//async function clickLoadJSONTest() {
	//	try {
	//		let dummy = await model.loadPatients();
	//		console.log('model.getPatients:', dummy);
	//		view.buildPatientList(dummy);
	//	} catch (error) {
	//		console.log('Error in clickLoadJSONTest:', error);
	//	}
	//}
	
	function getPatientsFromModelManager() {
		modelmanager.loadPatients();
		if(view.getTable() !== null) { 
			view.getTable().destroy();
		}
		view.hideMainTable();
		view.displayLoading();
		inner();
		async function inner() {
			if(!modelmanager.arePatientsUpdated())
			{
				console.log("Sleeping");
				await sleep(1000);
				console.log("Finished sleeping > check again");
				return inner ();
			} else {
				view.hideLoading();
				view.displayMainTable();
				view.buildPatientList(modelmanager.getPatientsArray());
				modelmanager.patientsUpdateFinish();
				return;
			}
		}
	}
	
	function sleep(ms) {
		return new Promise(resolve => setTimeout(resolve, ms));
	}
	
	return {getPatientsFromModelManager};
}