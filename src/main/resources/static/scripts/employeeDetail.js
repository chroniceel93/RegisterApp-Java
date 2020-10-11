let hideEmployeeSavedAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
	// TODO: Things that need doing when the view is loaded

	// Task 10
	// • On page load[1], define a “click” event handler[2] for the “Save” button defined in the view

	document.getElementById('save').addEventListener('click', saveActionClick);

});

// Save
function saveActionClick(event) {
	if (!validateSave()) // if all fields not validated, 
		return; // abort function

	/*
	◦ Perform an HTTP request, via AJAX[3], to the server to save the employee details
		▪ Perform a POST request[4,5] if the employee is new
		▪ Perform a PATCH request[4,5] if the employee exists in the database
	◦ After saving the employee and if the employee ID input element is not visible then display
	the employee ID input element
		▪ Input should remain disabled[6]
	*/
	
	const saveElement = event.target; // target of the click, which is the save button
	saveElement.disabled = true;  // to ensure save button cannot be re-clicked

	const employeeId = getEmployeeIdElement();
	const employeeIdIsDefined = (employeeId.value.trim() != ''); // true if ID is not empty, false otherwise
	const saveActionUrl = ('/api/employee/' + (employeeIdIsDefined ? employeeId : '')); // if employeeId is defined, add it to url
	const saveEmployeeRequest = {
		id: employeeId.value,
		managerId: getEmployeeManagerIdElement().value,
		firstName: getFirstNameElement().value,
		lastName: getLastNameElement().value,
		password: getPasswordElement().value,
		classification: getEmployeeTypeElement().value
	};

	if (employeeIdIsDefined) { // if employee exists in database
		ajaxPatch(saveActionUrl, saveEmployeeRequest, (callback) => { // perform Ajax PATCH request (from master.js)
			if (isSuccessResponse(callback)) // if request is successful
				completeSave(callback); // complete save
		});
	} else { // if employee is new (doesn't exist in database)
		ajaxPost(saveActionUrl, saveEmployeeRequest, (callback) => { // perform Ajax POST request (from master.js)
			if (isSuccessResponse(callback)) { // if request is successful
				completeSave(callback);
			}
		});
	}

}


function completeSave(callback) {
	if (callback.data == null) // abort function if there is no callback data
		return;
	
	if ((callback.data.redirectUrl != null) && (callback.data.redirectUrl !== '')) {// if redirectURL is valid
		window.location.replace(callback.data.redirectUrl); // send user to redirectURL
		return; // skip rest of function
	}

	displayEmployeeSavedAlertModal(); // honestly no idea what this does

	const employeeEmployeeId = getEmployeeEmployeeIdElement();
	const employeeEmployeeIdRow = employeeEmployeeId.closest('tr'); // closest row element ancestor to employeeEmployeeId
	if (employeeEmployeeIdRow.classList.contains('hidden')) { 
		setEmployeeId(callback.data.id);
		employeeEmployeeId.value = callback.data.employeeId;
		employeeEmployeeIdRow.classList.remove('hidden');
	}
}

/*
◦ Functionality should validate the input before proceeding
	▪ First name should not be blank
	▪ Last name should not be blank
	▪ Password should not be blank and should equal the value of the Confirm Password input
	▪ Employee type should be “Cashier”, “Shift Manager”, or “General Manager”
◦ If validation fails
	▪ Display an appropriate error
	▪ Focus and select the offending element
	▪ Interrupt/stop the save functionality
*/

// returns true if all fields are validated, false otherwise
function validateSave() {
	const firstName = getFirstNameElement(); // gets entire element of firstName (including other attributes)
	// if (firstName is blank)
	if (firstName.value.trim() == '') { // trim() ensures spaces are removed so user can't enter '  '
		displayError('First name is blank'); // from master.js
		firstName.focus(); // makes it clear to user where error is from
		firstName.select(); // auto-selects field to be fixed
		return false;
	}

	const lastName = getLastNameElement(); // gets entire element of lastName (including other attributes)
	// if (lastName is blank)
	if (lastName.value.trim() == '') { // trim() ensures spaces are removed so user can't enter '  '
		displayError('Last name is blank'); // from master.js
		lastName.focus(); // makes it clear to user where error is from
		lastName.select(); // auto-selects field to be fixed
		return false;
	}

	const password = getPasswordElement(); // gets entire element of password (including other attributes)
	// if (password is blank)
	if (password.value.trim() == '') { // trim() ensures spaces are removed so user can't enter '  '
		displayError('Password is blank'); // from master.js
		password.focus(); // makes it clear to user where error is from
		password.select(); // auto-selects field to be fixed
		return false;
	}

	const confirmPassword = document.getElementById('confirmPassword');
	// if (password does not equal confirmPassword)
	if (password.value != confirmPassword.value) {
		displayError('Passwords do not match');
		confirmPassword.focus();
		confirmPassword.select();
		return false;
	}

	const employeeType = getEmployeeTypeElement();
	// if (employeeType is not one of the accepted values)
	if (employeeType.value != 'Cashier' && 
			employeeType.value != 'Shift Manager' && 
			employeeType.value != 'General Manager') {
		displayError('Type of employee is not accepted');
		employeeType.focus();
		employeeType.select();
		return false;
	}

	// (if all validation has not failed)
	return true;

}

function displayEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	const savedAlertModalElement = getSavedAlertModalElement();
	savedAlertModalElement.style.display = "none";
	savedAlertModalElement.style.display = "block";

	hideEmployeeSavedAlertTimer = setTimeout(hideEmployeeSavedAlertModal, 1200);
}

function hideEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	getSavedAlertModalElement().style.display = "none";
}
// End save

//Getters and setters
function getSavedAlertModalElement() {
	return document.getElementById("employeeSavedAlertModal");
}

function getFirstNameElement() {
	return document.getElementById('firstName');
}

function getLastNameElement() {
	return document.getElementById('lastName');
}

function getPasswordElement() {
	return document.getElementById('password');
}

function getEmployeeTypeElement() {
	return document.getElementById('employeeType');
}


function getEmployeeIdElement() {
	return document.getElementById('employeeId');
}

function setEmployeeId(employeeId) {
	document.getElementById('employeeId').value = employeeId;
}

function getEmployeeManagerIdElement() {
	return document.getElementById('employeeManagerId');
}

function getEmployeeEmployeeIdElement() {
	return document.getElementById('employeeEmployeeId');
}

//End getters and setters
