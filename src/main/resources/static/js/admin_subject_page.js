$(document).ready(function() {
		$('#SubjectDatatable').DataTable({
		"serverSide": true,
		pageLength : 5,
        			ajax : {
        				url : '/admin/subject/getSubjectsByPage',
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
        				data : 'name'
        			}, {
        				title : 'ImgSrc',
        				data : 'imgSrc',
        			}, {
        				title : 'Total Courses',
        				data : 'totalCourse',
        			}, {
                        title : 'Subject Info',
                        data : 'subjectInfo',
                        render: function(data, type, row, meta) {
                                    var html = "";
                                    if(data != "" && data != null){
                                        data = data.length > 100 ? (data.substring(0, 100) + '...') : data;
                                        html += '<p>'+ data +'</p>';
                                    }
                                    return html;
                                }
                    }, {
                        title : 'Learn After',
                        data : 'learnAfter',
                        render: function(data, type, row, meta) {
                                    var html = "";
                                    if(data != "" && data != null){
                                        data = data.length > 100 ? (data.substring(0, 100) + '...') : data;
                                        html += '<p>'+ data +'</p>';
                                    }
                                    return html;
                                }
                    }, {
        				title : 'Status',
        				data : 'statusStr',
        			}, {
        				title : 'Img',
        				data : 'imgSrc',
        				render: function(data, type, row, meta) {
        				    var html = "";
        				    if(data != "" && data != null){
        				        html += '<img src="' + data +'" class="subject-img" />';
         				    }
        					return html;
        				}
        			}, {
        			    title : 'Action',
                        data : 'status',
                        render: function(data, type, row, meta) {
                            var html = '<button type="button" class="btn btn-primary subject-action-button" onclick="showSubjectEditModal('+ row['id'] + ')">'
                            + 'Edit</button>';
                            if (data == 0) {
                                html += '<button type="button" class="btn btn-primary subject-action-button" onclick="recoverSubject('+ row['id'] +')">'
                                + 'Recover</button>';
                            } else {
                                html += '<button type="button" class="btn btn-primary subject-action-button" onclick="deleteSubject('+ row['id'] +')">'
                                + 'Delete</button>';
                            }
                            return html;
                        }
        			} ]
		});
});
function recoverSubject(id){
    window.location.replace("/admin/subject/recover/" + id);
}
function deleteSubject(id){
    window.location.replace("/admin/subject/delete/" + id);
}
function showSubjectEditModal(id) {
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
                            $("#editSubjectNameOriginal").val(subject.name);
                            $("#editSubjectTotalCourse").val(subject.totalCourse);
                            $("#editSubjectImg").attr("src", subject.imgSrc);
                            $("#editSubjectStatus").val(subject.status).change();
                            $("#editSubjectInfo").text(subject.subjectInfo);
                            $("#editSubjectLearnAfter").text(subject.learnAfter);
                        }
                    }
                });
    $('#subjectEditModal').modal('show');
};
$('#subjectEditModal').on('hidden.bs.modal', function () {
  alert('close');
});
$('#subjectEditModal').on('shown.bs.modal', function (e) {
  // do something...
});

function showSubjectAddModal(id) {
    $('#subjectAddModal').modal('show');
};
$('#subjectAddModal').on('hidden.bs.modal', function () {
   alert('close');
 });
$('#subjectAddModal').on('shown.bs.modal', function (e) {
   // do something...
 });

function submitAddSubject() {
    var subjectName = $("#addSubjectName").val();
    var link = "/admin/subject/getByName";
    var subject = "";
    $.ajax({
        url: link,
        type:"get",
        contentType: "application/json; charset=utf-8",
        data:{
            name : subjectName
        },
        dataType: "json",
        success: function (data){
        subject = data;
            if(subject != ""){
                alert('There is already a Subject with that name');
            }
        },
        error: function (jqXHR, exception) {
                $("#subjectAddForm").submit();
        }
    });
}
function submitEditSubject() {
    var subjectNameOriginal = $("#editSubjectNameOriginal").val();
    var subjectName = $("#editSubjectName").val();
    var link = "/admin/subject/getByName";
    var subject = "";
    $.ajax({
        url: link,
        type:"get",
        contentType: "application/json; charset=utf-8",
        data:{
            name : subjectName
        },
        dataType: "json",
        success: function (data){
        subject = data;
            if(subject != ""){
                if(subject.name != subjectNameOriginal){
                    alert('There is already a Subject with that name');
                } else {
                    $("#subjectEditForm").submit();
                }
            }
        },
        error: function (jqXHR, exception) {
                $("#subjectEditForm").submit();
        }
    });
}