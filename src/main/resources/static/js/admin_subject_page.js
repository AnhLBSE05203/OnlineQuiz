$(document).ready(function() {
		$('#SubjectDatatable').DataTable({
		pageLength : 5,
        			ajax : {
        				url : '/admin/getSubjects',
        				dataSrc : ''
        			},
        			columns : [ {
        				title : 'Id',
        				data : 'id'
        			}, {
        				title : 'Name',
        				data : 'name'
        			}, {
        				title : 'ImgSrc',
        				data : 'imgSrc',
        			}, {
        				title : 'Total Courses',
        				data : 'totalCourse',
        			}, {
        				title : 'Status',
        				data : 'statusStr',
        			}, {
        				title : 'Img',
        				data : 'imgSrc',
        				render: function(data) {
        					return '<img src="' + data +'" class="subject-img" />';
        				}
        			}, {
        			    title : 'Action',
                        data : 'status',
                        render: function(data) {
                            var html = '<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editModal">'
                            + 'Edit</button>';
                            if (data == 0) {
                                html += '<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editModal">'
                                + 'Recover</button>';
                            } else {
                                html += '<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editModal">'
                                + 'Delete</button>';
                            }
                            return html;
                        }
        			} ]
		});
});