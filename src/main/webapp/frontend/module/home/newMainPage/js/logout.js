/**
 * Created by Pavel on 27.04.2016.
 */
/**
 * Created by Pavel on 25.04.2016.
 */
var logoutParam = new RegExp('[\?&]' + 'logout').exec(window.location.href);
if (logoutParam != null) {
    $('#messageSignIn').addClass('alert alert-success');
    $('#messageSignIn').html("You have been logged out").fadeIn();
    setTimeout(function() {
        $("#messageSignIn").fadeOut().empty();
    }, 3000);
    window.history.pushState({"html": document.html, "pageTitle": document.pageTitle}, "", "/");
}
