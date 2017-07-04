/*
 All the javascript functions are handled here
 */
$(document).ready(function () {

    /*
     Handling the User Type field for displaying admin access code, admin job description and the birth date fields
     Selected value: 1 shows the admin access code and job description fields for admin users
     Selected value:2 shows the birth date field for normal users
     */
    $("#userType").change(function () {
        var selectedValue = $(this).val();
        if (selectedValue == 'Admin') {
            $("#adminCode").css('display', 'block');
            $("#adminJobDescription").css('display', 'block');
            $("#birthDate").css('display', 'none');
        } else if (selectedValue == 'Normal') {
            $("#adminCode").css('display', 'none');
            $("#adminJobDescription").css('display', 'none')
            $("#birthDate").css('display', 'block');
        } else {
            $("#adminCode").css('display', 'none');
            $("#birthDate").css('display', 'none');
            $("#adminJobDescription").css('display', 'none');
        }
    });

    /*
     Custom validation methods
     validateUserType: validating the userType dropdown box
     validateAdminAccessCode: validating the adminAccessCode text field
     validateAdminJobResponsibility: validating the adminJobResponsibility text field
     validateDateOfBirth: validating the dateOfBirth text field
     */
    $.validator.addMethod('validateUserType', function (value, element, params) {
        if (value != 0) {
            return true
        }
    }, "User Type is required");

    $.validator.addMethod('validateAdminAccessCode', function (value, element, params) {
        var userType = $('#userType').val();
        if (userType == 'Admin' && value != "") {
            return true
        }
    }, "Admin Access Code is required");

    $.validator.addMethod('validateAdminJobResponsibility', function (value, element, params) {
        var userType = $('#userType').val();
        if (userType == 'Admin' && value != "") {
            return true
        }
    }, "Job Responsibility Description is required");

    $.validator.addMethod('validateDateOfBirth', function (value, element, params) {
        var userType = $('#userType').val();
        if (userType == 'Normal' && value != "") {
            return true
        }
    }, "Enter the date of birth");


    //Form validation rules
    $("#formNewUer").validate({
        errorElement: "span",
        rules: {
            userType: {
                validateUserType: true
            },
            adminAccessCode: {
                validateAdminAccessCode: true
            },
            adminJobResponsibility: {
                validateAdminJobResponsibility: true
            },
            dateOfBirth: {
                validateDateOfBirth: true
            }
        },
        messages: {},
        submitHandler: function (form) {
            form.submit();
        }
    });

    /* Deleting an user by user id
     *
     * */
    $("a[id^=deleteUser_]").click(function () {
        var res = confirm("Do you want to delete this user?");
        if (res) {
            var id = $(this).attr('id');
            var splitId = id.split("_");
            location.href = '/users/delete/' + splitId[1];
        }
    });

    /*
     *Datetime picker component
     *
     */
    $('#datetimepicker').datetimepicker({
        pickTime: false,
        disabledTimeIntervals: true,
        format: "yyyy-mm-dd"
    });

    /*
     *Auto suggestion component
     *
     */
    $("#query").typeahead({
        source: function(query, process) {
            return $.ajax({
                url: '/users/suggestions/'+query,
                type: 'get',
                dataType: 'json',
                success: function(json) {
                    return process(json);
                }
            });
        }
    });

});