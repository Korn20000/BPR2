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
	
	var subtable = null;

    function getPatientsFromModelManager() {
        modelmanager.loadPatients();
        if (view.getTable() !== null) {
            view.getTable().destroy();
        }
        view.hideMainTable();
        view.displayLoading();
        inner();
        async function inner() {
            if (!modelmanager.arePatientsUpdated()) {
                console.log("Sleeping");
                await sleep(1000);
                console.log("Finished sleeping > check again");
                return inner();
            } else {
                view.hideLoading();
                view.displayMainTable();
                view.buildPatientList(modelmanager.getPatientsArray());
                $('#patientsTable tbody').on('click', 'td.details-control', function() {
                    var tr = $(this).closest('tr');
                    var row = view.getTable().row(tr);
					var cpr = row.data().CPR;
					
                    if (row.child.isShown()) {
                        // This row is already open - close it
                        row.child.hide();
                        tr.removeClass('shown');
						if (subtable !== null) {
							subtable.destroy();
						}
                    } else {
                        // Open this row
						console.log("controller read cpr from my click:", row.data().CPR);
						modelmanager.loadPatientsHistory(cpr);
						inner();
						async function inner() {
							if (!modelmanager.isHistoryUpdated()) {
								console.log("SleepingForHistoryGet");
								await sleep(1000);
								console.log("Finished sleeping for history > check again");
								return inner();
							} else {
								var history = modelmanager.getPatientHistoryArray();
								row.child(tableMaker(history, cpr)).show();;
								//row.child(prepareHistoryFor(row.data().CPR)).show();
								modelmanager.historyUpdateFinish();
								makeHistoryDataTable(cpr, history);
								return;
								}
							}
                        tr.addClass('shown');
                    }
                });
                modelmanager.patientsUpdateFinish();
                return;
            }
        }
    }

//    function getPatientsHistoryFromModelManager() {
//        modelmanager.loadPatientsHistory();
//        inner();
//        async function inner() {
//            if (!modelmanager.isHistoryUpdated()) {
//                console.log("SleepingForHistoryGet");
//                await sleep(1000);
//                console.log("Finished sleeping for history > check again");
//                return inner();
//            } else {
//                modelmanager.patientsUpdateFinish();
//                return modelmanager.getPatientHistoryArray();
//            }
//        }
//    }

    function makeHistoryDataTable(CPR, history) {

        subtable = $('#' + CPR).DataTable({
            data: history,
            columns: [
				{
                    "data": "ID",
                    "title": "Measurement ID",
					"width": "20%"
                },
				{
					"data": "fk_CPR",
					"title": "CPR",
					"visible": false,
					"searchable": false
				},
                {
                    "data": "Measured_Level",
                    "title": "Measured Level",
					"width": "20%"
                },
                {
                    "data": "Date",
                    "title": "Date and Time",
					"width": "40%"
                }
            ],
            order: [
                [0, 'asc']
            ],
			scrollY: "400px",
			paging: false,
            responsive: true,
            dom: 'Bfrtip',
            stateSave: true,
            buttons: [{
                    extend: 'copyHtml5',
                    exportOptions: {
                        columns: [0, ':visible']
                    }
                },
                {
                    extend: 'excelHtml5',
                    exportOptions: {
                        columns: ':visible'
                    }
                },
                {
                    extend: 'pdfHtml5',
                    exportOptions: {
                        columns: [0, 1, 2, 5]
                    }
                },
                {
                    extend: 'print',
                    exportOptions: {
                        columns: ':visible'
                    }
                }
            ]
        });
    }
	
	function tableMaker(historyArray, CPR) {
		var html = '<table id="' + CPR + '">';
		html += '<tr>';
		var count = 0;
		for (var j in historyArray[0]) {
			html += '<th>' + j + '</th>';
			count++;
		}
		html += '</tr>';
		for (var i = 0; i < historyArray.length; i++) {
			html += '<tr>';
			for (var j in historyArray[i]) {
				html += '<td>' + historyArray[i][j] + '</td>';
			}
		}
		html += '</table>';
		return html;
		//onclick="view.sortTable(' + count + ')"
	}

    function sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    return {
        getPatientsFromModelManager
    };
}