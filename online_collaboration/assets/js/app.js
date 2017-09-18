    function openNav() {
        document.getElementById("mySidenav").style.width = "250px";
        document.getElementById("main").style.marginLeft = "250px";
    }

    function closeNav() {
        document.getElementById("mySidenav").style.width = "0";
        document.getElementById("main").style.marginLeft = "0";
    }

var load = function () {


    // // Show sideNav
    // $('#userNav').sideNav('show');
    // // Hide sideNav
    // $('#userNav').sideNav('hide');
    // // Destroy sideNav
    // $('#userNav').sideNav('destroy');

    $('.button-collapse').sideNav({
        menuWidth: 240, // Default is 240
        edge: 'right', // Choose the horizontal origin
        closeOnClick: false, // Closes side-nav on <a> clicks, useful for Angular/Meteor
        draggable: true // Choose whether you can drag to open on touch screens
    }
    );

    // // Show sideNav
    // $('#menuNav').sideNav('show');
    // // Hide sideNav
    // $('#menuNav').sideNav('hide');
    // // Destroy sideNav
    // $('#menuNav').sideNav('destroy');

    $('.side-collapse').sideNav({
        menuWidth: 240, // Default is 240
        edge: 'left', // Choose the horizontal origin
        closeOnClick: false, // Closes side-nav on <a> clicks, useful for Angular/Meteor
        draggable: true // Choose whether you can drag to open on touch screens
    }
    );

    $('.dropdown-button').dropdown({
        inDuration: 300,
        outDuration: 225,
        constrain_width: false, // Does not change width of dropdown to that of the activator
        hover: true, // Activate on hover
        gutter: 0, // Spacing from edge
        belowOrigin: false, // Displays dropdown below the button
        alignment: 'left' // Displays dropdown with edge aligned to the left of button
    }
    );

    // $('.dropdown-button').dropdown('open');

    // $('.dropdown-button').dropdown('close');

    $('.datepicker').pickadate({
        selectMonths: true, // Creates a dropdown to control month
        selectYears: 15 // Creates a dropdown of 15 years to control year
    });

    $('.checkbox').change(function () {
        $(this).is(':checked') ? $(this).siblings('.checkbox').prop('disabled', true) : "";
    });

    $('.materialboxed').materialbox();

    Materialize.updateTextFields();

    $('.collapsible').collapsible();
}


$(function () {
    load();
});
