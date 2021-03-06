document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?

	/*
	Task 7 part 1
	On page load[1], define “click” event handlers[2] for the various navigation buttons defined in the
	view

	Possible problems:
		not sure what to do with 'event' parameter
	
	*/
	var errorMessage = "Functionality has not yet been implemented.";

	// displays error message (from function defined in master.js) on the view when 'start transaction' button is clicked
	document.getElementById("transaction").addEventListener("click", function() {displayError(errorMessage);} );
	
	// sends user to existing productListing view when 'view products' button is clicked
	document.getElementById("products").addEventListener("click", function() {window.location.assign("/productListing");} );

	
	// navigate to new employeeDetail view when 'create employee' button is clicked
	document.getElementById("employee").addEventListener("click", function() {window.location.assign("/employeeDetail");} );

	// displays error message on view when 'sales report' clicked
	document.getElementById("sales").addEventListener("click", function() {displayError(errorMessage);} );

	// displays error message on view when 'cashier report' button is clicked
	document.getElementById("cashier").addEventListener("click", function() {displayError(errorMessage);} );
	
	


});


// These were all copied from master.js because don't know how to import functions in js

// Display error message
function clearError() {
	const errorMessageContainerElement = getErrorMessageContainerElement();

	if ((errorMessageContainerElement == null)
		|| errorMessageContainerElement.classList.contains("hidden")) {

		return;
	}

	errorMessageContainerElement.classList.add("hidden");

	const errorMessageDisplayElement = getErrorMessageDisplayElement();

	if (errorMessageDisplayElement != null) {
		errorMessageDisplayElement.innerHTML = "";
	}
}

function displayError(errorMessage) {
	if ((errorMessage == null) || (errorMessage === "")) {
		return;
	}

	const errorMessageDisplayElement = getErrorMessageDisplayElement();
	const errorMessageContainerElement = getErrorMessageContainerElement();

	if ((errorMessageContainerElement == null)
		|| (errorMessageDisplayElement == null)) {

		return;
	}

	errorMessageDisplayElement.innerHTML = errorMessage;
	if (errorMessageContainerElement.classList.contains("hidden")) {
		errorMessageContainerElement.classList.remove("hidden");
	}
}
// End display error message

//Getters and setters
function getSignOutActionElement() {
	return document.getElementById("signOutImage");
}

function getErrorMessageContainerElement() {
	return document.getElementById("error");
}

function getErrorMessageDisplayElement() {
	return document.getElementById("errorMessage");
}
// End getters and setters
