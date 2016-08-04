$(document).ready(function () {
    // popup-------------------------------------------------------------------------------popup
    $('.recoverybtn').bind('click', function () {
        $('.recovery').fadeIn(500); //openpopup
        //openValidatePasswordRecovery();
    });

    $('.closebtn').bind('click', function () {
        $('.recovery').fadeOut(300); //closebutton
    });

    $('.layout').bind('click', function () {
        $('.recovery').fadeOut(300); //layoutclose
    });
    //popup
    // -------------------------------------------------------------------------------validation
    $('.form-control').bind('input', ValidateRecoveryForm);
    // binding

    function ValidateRecoveryForm() {
        var elem = $(this);
        var innerText = elem.val();
        var errorMsg = '';
        if (elem.is('#userMail')) {
            if (!/^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/.test(innerText)) {
                errorMsg = errorMsg + 'Incorrect email';
            }
            $('.correct-email').text(errorMsg);
        }
        ;
        buttonEnablePasswordRecovery();
    };
    // oninputvalidation

    function openValidatePasswordRecovery() {
        var errorMsg = '';

        var elem = $('#userMail');
        var innerText = elem.val();

        if (!/^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/.test(innerText)) {
            errorMsg = errorMsg + 'Incorrect email';
        }
        $('.correct-email').text(errorMsg);
        buttonEnablePasswordRecovery();
    };
    // start validation
    function buttonEnablePasswordRecovery() {
        if ($('.correct-email').text() == '') {
            $('#stupidUser button').prop('disabled', false);
        } else {
            $('#stupidUser button').prop('disabled', true);
        }
    };
    // ------------------------------------------validation

    $("#buttonRecoverPassword").click(function () {
        event.preventDefault();
        $.ajax({
            type: 'post',
            url: '/passwordRecovery',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                email: $('#userMail').val()
            }),
            success: function (response) {
                if (response.length) {
                    var errors_out = "";
                    for (var i in response) {
                        errors_out += response[i].errorMessage + "</br>"
                    }
                    $('#passwordRecoveryMessage')
                        .removeClass()
                        .empty();
                    $('#passwordRecoveryMessage')
                        .addClass('alert alert-danger')
                        .html(errors_out);
                    $('#userMail').val("");
                } else {
                    $('#passwordRecoveryMessage')
                        .removeClass()
                        .empty();
                    $('#passwordRecoveryMessage')
                        .addClass('alert alert-success')
                        .html('Request was sent successfully');
                    $('#userMail').hide();
                    $('#buttonRecoverPassword').hide();
                }
            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });
    });

    function fillError(item) {
        //toDo
    }

});