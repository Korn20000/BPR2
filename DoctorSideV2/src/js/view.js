function view() {
    "use strict";

    function tableMaker(patientRows) {
        var html = '<table id="patientsTable">';
        html += '<tr>';
        var count = 0;
        for (var j in patientRows[0]) {
            html += '<th onclick="view.sortTable(' + count + ')">' + j + '</th>';
            count++;
        }
        html += '</tr>';
        for (var i = 0; i < patientRows.length; i++) {
            html += '<tr>';
            for (var j in patientRows[i]) {
                html += '<td>' + patientRows[i][j] + '</td>';
            }
        }
        html += '</table>';
        return html;
    }

    function buildPatientList(patients) {
        console.log('view patients:', patients);
        if (patients === null) {
            alert("Error receiving data !");
        } else {
            document.getElementById("main_table").innerHTML = tableMaker(patients);
            sortTable(0);
        }
    }
	
	function hideMainTable() {
		document.getElementById("main_table").style.display = 'none';
	}
	
	function displayMainTable() {
		document.getElementById("main_table").style.display = 'block';
	}
	
	function hideLoading() {
		document.getElementById("loadingList").style.display = 'none';
	}
	
	function displayLoading() {
		document.getElementById("loadingList").style.display = 'block';
	}

    function sortTable(n) {
        var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
        table = document.getElementById("patientsTable");
        switching = true;
        // Set the sorting direction to ascending:
        dir = "asc";
        /* Make a loop that will continue until
        no switching has been done: */
        while (switching) {
            // Start by saying: no switching is done:
            switching = false;
            rows = table.getElementsByTagName("tr");
            /* Loop through all table rows (except the
            first, which contains table headers): */
            for (i = 1; i < (rows.length - 1); i++) {
                // Start by saying there should be no switching:
                shouldSwitch = false;
                /* Get the two elements you want to compare,
                one from current row and one from the next: */
                x = rows[i].getElementsByTagName("td")[n];
                y = rows[i + 1].getElementsByTagName("td")[n];
                /* Check if the two rows should switch place,
                based on the direction, asc or desc: */
                if (dir == "asc") {
                    if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                        // If so, mark as a switch and break the loop:
                        shouldSwitch = true;
                        break;
                    }
                } else if (dir == "desc") {
                    if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                        // If so, mark as a switch and break the loop:
                        shouldSwitch = true;
                        break;
                    }
                }
            }
            if (shouldSwitch) {
                /* If a switch has been marked, make the switch
                and mark that a switch has been done: */
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switching = true;
                // Each time a switch is done, increase this count by 1:
                switchcount++;
            } else {
                /* If no switching has been done AND the direction is "asc",
                set the direction to "desc" and run the while loop again. */
                if (switchcount == 0 && dir == "asc") {
                    dir = "desc";
                    switching = true;
                }
            }
        }
    }

    function search() {
        var input, filter, table, tr, td, i, radioForm, n;
        input = document.getElementById("searchInput");
        filter = input.value.toUpperCase();
        table = document.getElementById("patientsTable");
        tr = table.getElementsByTagName("tr");
		radioForm = document.getElementById("searchSelector").elements;
		n = 0;
		
		for (i = 0; i < radioForm.length; i++) {
			if(radioForm[i].checked) {
				n = i; break;
			}
		}

        for (i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[n];
            if (td) {
                if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    }

    return {buildPatientList, sortTable, search, hideMainTable, displayMainTable, hideLoading, displayLoading};
}