$(document).ready(function() {
    $('#AccountDatatable').DataTable({
        "serverSide": true,
        pageLength : 5,
        ajax : {
            url : '/admin/account/all',
            "type": "POST",
            "dataType": "json",
            "contentType": "application/json",
            "data": function (d) {
                return JSON.stringify(d);
            }
        },
        columns : [ {
            title : 'Id',
            data : 'id'
        }, {
            title : 'Name',
            data : 'fullName'
        }, {
            title : 'Gender',
            data : 'gender',
        }, {
            title : 'Email',
            data : 'email',
        }, {
            title : 'Phone',
            data : 'phone',
        }, {
            title : 'Action',
            data: 'status',
            render: function(data, type, row, meta) {
                let html = '<button type="button" class="btn btn-primary" onclick="showAccountEditModal(' + row['id'] + ')">'
                    + 'Edit</button>&nbsp';
                html += '<button type="button" class="btn btn-primary" onclick="deleteAccount('+ row['id'] +')">'
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
                $("#editAccountGender").val(account.gender);
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