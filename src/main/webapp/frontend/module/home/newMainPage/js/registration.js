    $(document).ready(function () {
    // popup-------------------------------------------------------------------------------popup
    // $('.regbut').bind('click', function () {
    //     $('.reg').fadeIn(500); //openpopup
    //     // openValidate();
    // });
    //
    // $('.closebtn').bind('click', function () {
    //     $('.reg').fadeOut(300); //closebutton
    // });
    //
    // $('.layout').bind('click', function () {
    //     $('.reg').fadeOut(300); //layoutclose
    // });
    //popup
    // -------------------------------------------------------------------------------validation
    $('.form-control').bind('input', ValidateForm);
    // binding


    function ValidateForm() {
        var elem = $(this);
        var innerText = elem.val();
        var errorMsg = '';

        if (elem.is('#name')) {

            if (!/^([A-ZА-ЯЁ][a-zа-яё'-]+\s?)+$/.test(innerText)) {
                errorMsg = errorMsg + 'Incorrect name';
            } else if (/([^a-zA-Zа-яА-ЯёЁ'\s-])/.test(innerText)) {
                errorMsg = errorMsg + 'Incorrect name';
            } else if (!/^.{2,30}$/.test(innerText)) {
                errorMsg = errorMsg + 'Incorrect name';
            }
            ;

            $('.correct-name').text(errorMsg);
        } else if (elem.is('#email')) {

            if (!/^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/.test(innerText)) {
                errorMsg = errorMsg + 'Incorrect email';
            }

            $('.correct-email').text(errorMsg);
        } else if (elem.is('#password')) {

            if (!/^.{6,20}$/.test(innerText)) {
                errorMsg = errorMsg + 'Incorrect symbols number';
            }

            $('.correct-password').text(errorMsg);
        } else if (elem.is('#surname')) {

            if (!/^([A-ZА-ЯЁ][a-zа-яё'-]+\s?)+$/.test(innerText)) {
                errorMsg = errorMsg + 'Need a capital letter;';
            }
            ;

            if (/([^a-zA-Zа-яА-ЯёЁ'\s-])/.test(innerText)) {
                errorMsg = errorMsg + 'Only letter\'s;';
            }
            ;

            if (!/^.{2,30}$/.test(innerText)) {
                errorMsg = errorMsg + 'Incorrect letter\'s number;';
            }
            ;
            $('.correct-surname').text(errorMsg);
        }
        ;
        buttonEnable();
    };
    // oninputvalidation

    function openValidate() {
        var errorMsg = '';

        var elem = $('#name');
        var innerText = elem.val();

        if (!/^([A-ZА-ЯЁ][a-zа-яё'-]+\s?)+$/.test(innerText)) {
            errorMsg = errorMsg + 'Incorrect name';
        } else if (/([^a-zA-Zа-яА-ЯёЁ'\s-])/.test(innerText)) {
            errorMsg = errorMsg + 'Incorrect name';
        } else if (!/^.{2,30}$/.test(innerText)) {
            errorMsg = errorMsg + 'Name should have from 2 to 30 symbols';
        }
        ;

        $('.correct-name').text(errorMsg);
        errorMsg = '';

        var elem = $('#email');
        var innerText = elem.val();

        if (!/^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/.test(innerText)) {
            errorMsg = errorMsg + 'Incorrect email';
        }

        $('.correct-email').text(errorMsg);
        errorMsg = '';

        var elem = $('#password');
        var innerText = elem.val();

        if (!/^.{6,32}$/.test(innerText)) {
            errorMsg = errorMsg + 'Password should have from 6 to 32 symbols';
        }

        $('.correct-password').text(errorMsg);
        errorMsg = '';

        var elem = $('#surname');
        var innerText = elem.val();

        if (!/^([A-ZА-ЯЁ][a-zа-яё'-]+\s?)+$/.test(innerText)) {
            errorMsg = errorMsg + 'Need a capital letter;';
        }
        ;

        if (/([^a-zA-Zа-яА-ЯёЁ'\s-])/.test(innerText)) {
            errorMsg = errorMsg + 'Only letter\'s;';
        }
        ;

        if (!/^.{2,30}$/.test(innerText)) {
            errorMsg = errorMsg + 'Incorrect letter\'s number;';
        }
        ;
        $('.correct-surname').text(errorMsg);

        buttonEnable();
    };
    // start validation
    function buttonEnable() {
        if ($('.correct-name').text() == '' && $('.correct-password').text() == '' && $('.correct-email').text() == '' && $('.correct-surname').text() == '') {
            $('#user button').prop('disabled', false);
        } else {
            $('#user button').prop('disabled', true);
        }
    };
    // ------------------------------------------validation


    $("#buttonRegistration").click(function (event) {
        event.preventDefault();
        $.ajax({
            type: 'post',
            url: '/register',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                name: $('#name').val(),
                surname: $('#surname').val(),
                email: $('#email').val(),
                password: $('#password').val(),
                roles: [{name: 'ROLE_STUDENT'}]
            }),
            success: function (response) {
                if (response.length) {
                    var errors_out = "";
                    // response.errors.forEach(item, i);
                    for (var i in response) {
                        errors_out += response[i].errorMessage + "</br>"
                    }
                    $('#messageRegistration')
                        .removeClass()
                        .empty();
                    $('#messageRegistration')
                        .addClass('alert alert-danger')
                        .html(errors_out).fadeIn();
                    $('#j_password').val("");
                } else {
                    $('#messageRegistration')
                        .removeClass()
                        .empty();
                    $('#messageRegistration')
                        .removeClass()
                        .empty();
                    $('.registration')
                        .fadeOut(300);
                    $('#messageRegistration')
                        .show()
                        .addClass('alert alert-success')
                        .html('Registered successfully').fadeIn();
                    $('.registration input')
                        .val("");
                    setTimeout(function() {
                        $("#messageRegistration").fadeOut().empty();
                    }, 3000);
                }
            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });
    });

});