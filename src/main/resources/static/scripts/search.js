document.addEventListener("DOMContentLoaded", function(event) {

    // TODO: search functionality and add to cart functionality
  
    // transaction button 'click' event handler reroutes to transaction page.
    document.getElementById('transaction').addEventListener('click', function() {window.location.assign('/transaction');} );

    // Search button 'click' event handler updates search query and displays/updates item results from partial search.
    document.getElementById('search').addEventListener('click', function() { 
        // updates search query and displays results
    } );

    // Add to cart 'click' event handler updates cart with a single instance of the selected item via POST request.


});