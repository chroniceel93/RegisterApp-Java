let hideEmployeeSavedAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
	// TODO: Things that need doing when the view is loaded

	/*
	Task 10
	• On page load[1], define a “click” event handler[2] for the “Save” button defined in the view
		◦ Functionality should validate the input before proceeding
			▪ First name should not be blank
			▪ Last name should not be blank
			▪ Password should not be blank and should equal the value of the Confirm Password input
			▪ Employee type should be “Cashier”, “Shift Manager”, or “General Manager”
		◦ If validation fails
			▪ Display an appropriate error
			▪ Focus and select the offending element
			▪ Interrupt/stop the save functionality
		◦ Perform an HTTP request, via AJAX[3], to the server to save the employee details
			▪ Perform a POST request[4,5] if the employee is new
			▪ Perform a PATCH request[4,5] if the employee exists in the database
		◦ After saving the employee and if the employee ID input element is not visible then display
		the employee ID input element
			▪ Input should remain disabled[6]

	Possible problems:
		not sure how to display errorMessage. In employeeDetail.html there is a header for an error message that will display
		if not empty but not sure how to send that to page

	*/
	document.getElementById('save').addEventListener('click', function() {
		var firstName = document.getElementById('firstName').value;
		var lastName = document.getElementById('lastName').value;
		var pw = document.getElementById('password').value;
		var confirmPw = document.getElementById('confirmPassword').value;
		var employeeType = document.getElementById('employeeType').value;

		if (firstName == '')
			invalidResponse('First name is blank');
		if (lastName == '')
			invalidResponse('Last name is blank');
		if (pw == '')
			invalidResponse('Password is blank');
		if (pw != confirmPw)
			invalidResponse('Passwords don\'t match');
		if (employeeType != 'Cashier' && 
				employeeType != 'Shift Manager' && 
				employeeType != 'General Manager')
			invalidResponse('Employee type is invalid');
		
		


	});

});

// displays appropriate error message with the offending element and interrupt/stops the save functionality
function invalidResponse(message) {
	var errorMessage = message;
}

// Save
function saveActionClick(event) {
	// TODO: Actually save the employee via an AJAX call
	displayEmployeeSavedAlertModal();
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
//End getters and setters
