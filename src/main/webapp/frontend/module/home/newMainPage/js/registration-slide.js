/**
 * Created by Neltarion on 29.05.2016.
 */
$(document).ready(function () {

    var isShown = false;
    var isShownPassRec = false;

    $('.regbut').click(function () {
        event.preventDefault();
        if (!isShown) {
            $('#slideDown').slideToggle();
            $('html, body').animate({
                scrollTop: $("#slideDown").offset().top - 67
            }, 1000);
            isShown = true;
        } else {
            $('#slideDown').slideToggle();
            $('html, body').animate({
                scrollTop: $("#messageSignIn").offset().top - 100
            }, 1000);
            isShown = false;
        }
    });

    $('.closeClass').click(function () {
            $('#slideDown').slideToggle();
            $('html, body').animate({
                scrollTop: $("#messageSignIn").offset().top - 100
            }, 1000);
            isShown = false;
    });

    $('.passRec').click(function () {
        event.preventDefault();
        if (!isShownPassRec) {
            $('#slideForgotPass').slideToggle();
            $('html, body').animate({
                scrollTop: $("#slideForgotPass").offset().top - 50
            }, 1000);
            isShownPassRec = true;
        } else {
            $('#slideForgotPass').slideToggle();
            $('html, body').animate({
                scrollTop: $("#messageSignIn").offset().top - 100
            }, 1000);
            isShownPassRec = false;
        }
    });

    $('.closePass').click(function () {
        if (isShownPassRec) {
            $('#slideForgotPass').slideToggle();
            $('html, body').animate({
                scrollTop: $("#messageSignIn").offset().top - 100
            }, 1000);
            isShownPassRec = false;
        }
    });

});