$(document).ready(function () {
    $('#AccountDatatable').DataTable({
        "serverSide": true,
        pageLength: 5,
        ajax: {
            url: '/admin/account/all',
            "type": "POST",
            "dataType": "json",
            "contentType": "application/json",
            "data": function (d) {
                return JSON.stringify(d);
            }
        },
        columns: [{
            title: 'Id',
            data: 'id'
        }, {
            title: 'Name',
            data: 'fullName'
        }, {
            title: 'Gender',
            data: 'gender',
            render: function (data, type, row, meta) {
                return ' <span>' + (data === 1 ? "Female" : "Male") + '</span>'
            }
        }, {
            title: 'Email',
            data: 'email',
        }, {
            title: 'Roles',
            data: 'roleStr',
        }, {
            title: 'Phone',
            data: 'phone',
        }, {
            title: 'Action',
            data: 'status',
            render: function (data, type, row, meta) {
                let html = '<button type="button" class="btn btn-primary" onclick="showAccountEditModal(' + row['id'] + ')">'
                    + 'Detail</button>&nbsp';
                html += '<button type="button" class="btn btn-primary" onclick="deleteAccount(' + row['id'] + ')">'
                    + 'Delete</button>&nbsp';
                return html;
            }
        }]
    });
})

function showAccountAddModal() {
    $('#accountAddModal').modal("show");
}

function showAccountEditModal(id) {
    var link = "/admin/account/view/" + id;
    var account = "";
    $.ajax({
        url: link,
        type: "get",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            account = data;
            if (account != "") {
                $("#editAccountId").val(account.id);
                $("#editAccountFullname").val(account.fullName);
                $("#editAccountGender").val(account.genderStr);
                $("#editAccountEmail").val(account.email);
                $("#editAccountPhone").val(account.phone);
            }
        }
    });
    $('#accountEditModal').modal('show');
}

function deleteAccount(id) {
    window.location.replace("/admin/account/delete/" + id);
}

const passwordRegex = /^(?=.*?[0-9])(?=.*?[A-Z])(?=.*?[#?!@$%^&*\-_]).{8,}$/;
const emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const CONST_PASSWORD_NOT_MATCH = "Passwords do not match!";
const CONST_PASSWORD_INVALID = "Passwords need at least 1 special character, 1 upper case & length >= 8";
const CONST_EMAIL_INVALID = "Check Email Input";

function checkPasswordMatch(fieldConfirmPassword) {
    if (fieldConfirmPassword.value != $("#password").val()) {
        fieldConfirmPassword.setCustomValidity(CONST_PASSWORD_NOT_MATCH);
    } else {
        fieldConfirmPassword.setCustomValidity("");
    }
}
function checkPasswordValid(fieldPassword) {
    var value = fieldPassword.value;
    if (!value.match(passwordRegex)) {
        fieldPassword.setCustomValidity(CONST_PASSWORD_INVALID);
    } else {
        fieldPassword.setCustomValidity("");
    }
}
function checkEmailValid(fieldEmail) {
    var value = fieldEmail.value.toLowerCase();
    if (!value.match(emailRegex)) {
        fieldEmail.setCustomValidity(CONST_EMAIL_INVALID);
    } else {
        fieldEmail.setCustomValidity("");
    }
}