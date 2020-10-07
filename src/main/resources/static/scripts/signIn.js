document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?

	/* 
	Task 7 part 1
	On page load[1], define “click” event handlers[2] for the various navigation buttons defined in the
	view

	Possible problems:
		not sure what to do with 'event' parameter
		all location.assign() statements -> is .html the right URL? am I supposed to create employeeDetail.html?
		should if statement be a while statement?
		is this the right JS file to be editing for this task?
	*/

	var errorMessage = "Functionality has not yet been implemented.";

	// displays error message (from function defined in master.js) on the view when 'start transaction' button is clicked
	document.getElementById("transaction").addEventListener("click", displayError(errorMessage));
	

	// sends user to existing productListing view when 'view products' button is clicked
	document.getElementById("products").addEventListener("click", location.assign("productListing.html"));

	// when visible
	if (document.getElementById("manager").className == '') { // if (manager is not hidden)
		// navigate to new employeeDetail view when 'create employee' button is clicked
		document.getElementById("employee").addEventListener("click", location.assign("employeeDetail.html"));

		// displays error message on view when 'sales report' clicked
		document.getElementById("sales").addEventListener("click", displayError(errorMessage));

		// displays error message on view when 'cashier report' button is clicked
		document.getElementById("cashier").addEventListener("click", displayError(errorMessage));
	}


});

function validateForm() {
	// TODO: Validate the user input
	id = document.getElementById('employeeid').Value;
	pw = document.getElementById('password').Value;
	if (!id) // if id is empty
		return false;
	if (!pw) // if password is empty
		return false;
	if (Number(id) != id) // if id has something besides numbers in it
		return false;
	return true;
}
