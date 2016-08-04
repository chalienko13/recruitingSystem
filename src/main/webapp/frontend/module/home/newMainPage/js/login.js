/**
 * Created by Pavel on 25.04.2016.
 */
$(document).ready(function () {

    $('#j_password, #password').hideShowPassword(false, true);
    
    $("#buttonSignIn").click(function (event) {
        event.preventDefault();
        $.ajax({
            type: 'post',
            url: '/security_check',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                email: $('#j_username').val(),
                password: $('#j_password').val()
            }),
            success: function (response) {
                if (response.length) {
                    var errors_out = "";
                    for (var i in response) {
                        errors_out += response[i].errorMessage + "</br>"
                    }
                    $('#messageRegistration')
                        .removeClass()
                        .empty();
                    $('#messageSignIn')
                        .addClass('alert alert-danger')
                        .html(errors_out).fadeIn();
                    $('#j_password').val("");
                } else {
                    location.reload();
                }
            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });
    });
    
});

