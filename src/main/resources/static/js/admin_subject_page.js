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
        				render: function(data, type, row, meta) {
        					return '<img src="' + data +'" class="subject-img" />';
        				}
        			}, {
        			    title : 'Action',
                        data : 'status',
                        render: function(data, type, row, meta) {
                            var html = '<button type="button" class="btn btn-primary" onclick="showDetail('+ row['id'] + ')">'
                            + 'Edit</button>';
                            if (data == 0) {
                                html += '<button type="button" class="btn btn-primary">'
                                + 'Recover</button>';
                            } else {
                                html += '<button type="button" class="btn btn-primary">'
                                + 'Delete</button>';
                            }
                            return html;
                        }
        			} ]
		});
});
function showDetail(id) {
    var link = "/admin/subject/" + id;
    var subject = "";
    $.ajax({
                    url: link,
                    type:"get",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (data){
                    subject = data;
                        if(subject != ""){
                            $("#editSubjectId").val(subject.id);
                            $("#editSubjectName").val(subject.name);
                        }
                    }
                });
    $('#subjectDetailModal').modal('show');
}
$('#subjectDetailModal').on('hidden.bs.modal', function () {
  alert('close');
})
$('#myModal').on('shown.bs.modal', function (e) {
  // do something...
})