document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?
});

function validateForm() {
	// TODO: Validate the user input
	id = document.getElementById('employeeId').value;
	pw = document.getElementById('password').value;
	if (!id) // if id is empty
		return false;
	if (!pw) // if password is empty
		return false;
	if (Number(id) != id) // if id has something besides numbers in it
		return false;
	return true;
}
