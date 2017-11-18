function adapter() {
	"use strict";

	async function patientsFromJSON() {
        $.when ($.ajax({
					url: '/src/php/process_patients.php',
					type: 'POST',
					data: 'disp_pat_json',
					dataType: 'json',
					error: function(jqXhr, textStatus, errorThrown) {
								alert(errorThrown); }
					}) ).then ( function( data ) { return JSON.parse (data); } ).catch( function( error ) { alert(error); });
    }
	
	return {patientsFromJSON};
}
	