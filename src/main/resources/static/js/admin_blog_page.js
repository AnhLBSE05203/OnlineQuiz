$(document).ready(function() {
		$('#BlogDatatable').DataTable({
		pageLength : 5,
        			ajax : {
        				url : '/admin/blog/all',
        				dataSrc : ''
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