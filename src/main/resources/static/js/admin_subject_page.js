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
        				width: "10%",
        				orderable: false
        			}, {
        				title : 'Total Courses',
        				data : 'totalCourse',
        				orderable: false
        			}, {
                        title : 'Subject Info',
                        data : 'subjectInfo',
                        width: "20%",
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
                        width: "20%",
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
        				orderable: false
        			}, {
        				title : 'Img',
        				data : 'imgSrc',
        				orderable: false,
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
                        orderable: false,
                        render: function(data, type, row, meta) {
                            // edit modal button
                            var html = '<button type="button" class="btn btn-primary subject-action-button" onclick="showSubjectEditModal('+ row['id'] + ')">'
                            + 'Edit</button>';
                            // show courses datatable by subject
                                html += '<button type="button" class="btn btn-primary subject-action-button" onclick="showCourseSection('+ row['id'] + ')">'
                                                                    + 'View Courses</button>';
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
// course section
function showCourseSection(subjectId) {
    if ($.fn.dataTable.isDataTable('#CourseDatatable')) {
            $('#CourseDatatable').DataTable().destroy();
        }
    $('#courseSection').show();
    $('#CourseDatatable').DataTable({
    		"serverSide": true,
    		pageLength : 5,
            			ajax : {
            				url : '/admin/subject/courses',
            				"type": "POST",
                            "dataType": "json",
                            "contentType": "application/json",
                            "data": function (d) {
                                d.prefilter = subjectId;
                                return JSON.stringify(d);
                            },
            			},
            			columns : [ {
            				title : 'Id',
            				data : 'id'
            			}, {
            				title : 'Name',
            				data : 'name'
            			}, {
            			    title : 'SubjectId',
            			    data: 'subjectId',
            			    visible: false,
            			}, {
            				title : 'Total Lessons',
            				data : 'lessonTotal',
            				orderable: false
            			}, {
                            title : 'Description',
                            data : 'description',
                            width: "20%",
                            render: function(data, type, row, meta) {
                                        var html = "";
                                        if(data != "" && data != null){
                                            data = data.length > 100 ? (data.substring(0, 100) + '...') : data;
                                            html += '<p>'+ data +'</p>';
                                        }
                                        return html;
                                    }
                        },{
                            title : 'Price',
                            data : 'price',
                        }, {
            				title : 'Status',
            				data : 'statusStr',
            				orderable: false
            			}, {
            			    title : 'Action',
                            data : 'status',
                            orderable: false,
                            render: function(data, type, row, meta) {
                                // edit modal button
                                var html = '<button type="button" class="btn btn-primary subject-action-button" onclick="showCourseEditModal('+ row['id'] + ')">'
                                + 'Edit</button>';
                                if (data == 0) {
                                    html += '<button type="button" class="btn btn-primary subject-action-button" onclick="recoverCourse('+ row['id'] +')">'
                                    + 'Recover</button>';
                                } else {
                                    html += '<button type="button" class="btn btn-primary subject-action-button" onclick="deleteCourse('+ row['id'] +')">'
                                    + 'Delete</button>';
                                }
                                return html;
                            }
            			} ]
    		});
}
//todo
function showCourseEditModal(id){
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
}
function showCourseAddModal(){
    $('#courseAddModal').modal('show');
}
function recoverCourse(id){
    window.location.replace("/admin/course/recover/" + id);
}
function deleteCourse(id){
    window.location.replace("/admin/course/delete/" + id);
}
function submitAddCourse() {
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
function submitEditCourse() {
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