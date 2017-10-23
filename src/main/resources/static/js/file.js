/**
 * 
 */


function isnum(obj) {

		if (isNaN(obj.value) || parseInt(obj.value) < 0)
		{ 
			alert('Nel campo si possono immettere solo numeri!');
			obj.value = "";
			obj.focus();
		}

	}