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

}

function deleteAccount(id) {

}