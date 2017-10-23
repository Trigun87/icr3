/**
 * 
 */
function matchpass(){  
	var firstpassword=document.f1.password.value;  
	var secondpassword=document.f1.password2.value;  
	

	if(firstpassword==secondpassword){  
		return true;  
	}  
	else{  
		alert("Le password non coincidono!");  
		return false;  
	}  
}  