document.addEventListener("DOMContentLoaded", function(event) {
    
    // TODO: implement deleteCart() and change quantity button functionality and validation

    // Cancel button 'click' event handler reroutes to main menu, deleting cart with POST request.
    document.getElementById('cancel').addEventListener('click', function() {window.location.assign('/mainMenu');} );

    // Confirm button 'click' event handler sends current cart to checkout via POST request, rerouting to main menu.
    document.getElementById('confirm').addEventListener('click', function() {window.location.assign('/mainMenu');} );

    // Search button 'click' event handler reroutes to search page.
    document.getElementById('search').addEventListener('click', function() {window.location.assign('/search');} );

    // Delete from cart button 'click' event handler deletes item from cart via POST request.

    // Change quantity button 'click' event handler updates quantity of item with what the user entered (needs validation e.g. no negative numbers).

	


});


// deletes current cart with a POST request
function deleteCart() {

}