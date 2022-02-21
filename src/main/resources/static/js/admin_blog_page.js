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
        				data : 'status',
        			}]
		});
})