$(document).ready(function() {
		$('#BlogDatatable').DataTable({
		"serverSide": true,
		pageLength : 5,
        			ajax : {
						url : '/admin/blog/all',
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
        				title : 'Title',
        				data : 'title'
        			}, {
        				title : 'Content',
        				data : 'content',
        			}, {
        				title : 'Status',
        				data : 'statusStr',
        			}, {
						title : 'Action',
						data: 'status',
						render: function(data, type, row, meta) {
							let html = '<button type="button" class="btn btn-primary" onclick="showBlogEditModal(' + row['id'] + ')">'
								+ 'Edit</button>&nbsp';
							html += '<button type="button" class="btn btn-primary" onclick="deleteBlog('+ row['id'] +')">'
									+ 'Delete</button>&nbsp';
							return html;
						}
					}]
		});
})

function showBlogEditModal(id) {

}

function deleteBlog(id){
	window.location.replace("/admin/subject/delete/" + id);
}