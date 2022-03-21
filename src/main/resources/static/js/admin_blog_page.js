$(document).ready(function () {
    $('#BlogDatatable').DataTable({
        "serverSide": true,
        pageLength: 5,
        ajax: {
            url: '/admin/blog/all',
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
            title: 'Title',
            data: 'title'
        }, {
            title: 'Content',
            data: 'content',
        }, {
            title: 'Status',
            data: 'statusStr',
        }, {
            title: 'Action',
            data: 'status',
            render: function (data, type, row, meta) {
                let html = '<button type="button" class="btn btn-primary" onclick="showBlogEditModal(' + row['id'] + ')">'
                    + 'Edit</button>&nbsp';
                html += '<button type="button" class="btn btn-primary" onclick="deleteBlog(' + row['id'] + ')">'
                    + 'Delete</button>&nbsp';
                return html;
            }
        }]
    });
})

function showBlogAddModal() {
    $('#blogAddModal').modal('show');
}

function showBlogEditModal(id) {
                    var link = "/admin/blog/view/" + id;
                    var blog = "";
                    $.ajax({
                        url: link,
                        type: "get",
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        success: function (data) {
                            blog = data;
                            if (blog != "") {
                                $("#editBlogId").val(blog.id);
                                $("#editBlogTitle").val(blog.title);
                                ckEditor.instances.content1.setData(blog.content);
            }
        }
    });
    $('#blogEditModal').modal('show');
}

function deleteBlog(id) {
    window.location.replace("/admin/blog/delete/" + id);
}
