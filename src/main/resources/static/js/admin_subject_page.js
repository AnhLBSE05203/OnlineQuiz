$(document).ready(function() {
		$('#SubjectDatatable').DataTable({
		"serverSide": true,
		"scrollX": true,
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
        				title : 'Total Courses',
        				data : 'totalCourse',
        			}, {
                        title : 'Subject Info',
                        data : 'subjectInfo',
                        width: "30%",
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
                        width: "30%",
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
        				orderable: false,
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
                            + 'View/Edit</button>';
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
    var link = "/admin/subject/view/" + id;
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

function submitAddSubject(e) {
    e.preventDefault();
    var subjectName = $("#addSubjectName").val();
    var link = "/admin/subject/isDuplicated";
    $.ajax({
        url: link,
        type:"get",
        contentType: "application/json; charset=utf-8",
        data:{
            "subjectName" : subjectName
        },
        dataType: "json",
        success: function (data){
            if(data){
                alert('There is already a Subject with that name');
            } else {
                $("#subjectAddForm")[0].submit();
            }
        },
        error: function (jqXHR, exception) {

        }
    });
}
function submitEditSubject(e) {
    e.preventDefault();
    var subjectNameOriginal = $("#editSubjectNameOriginal").val();
    var subjectName = $("#editSubjectName").val();
    var link = "/admin/subject/isDuplicated";
    if(subjectNameOriginal == subjectName) {
        $("#subjectEditForm")[0].submit();

    } else{
        $.ajax({
            url: link,
            type:"get",
            contentType: "application/json; charset=utf-8",
            data:{
                "subjectName" : subjectName
            },
            dataType: "json",
            success: function (data){
                if(data){
                   alert('There is already a Subject with that name');
                } else {
                     $("#subjectEditForm")[0].submit();
                 }
            },
            error: function (jqXHR, exception) {
            }
        });
    }
}
// course section
function showCourseSection(subjectId) {
    if ($.fn.dataTable.isDataTable('#CourseDatatable')) {
            $('#CourseDatatable').DataTable().destroy();
        }
    alert("Courses are listed below!")
    $('#CourseDatatable').show();
    $('#CourseDatatable').DataTable({
    		"serverSide": true,
    		"scrollX": true,
    		pageLength : 5,
            			ajax : {
            				url : '/admin/course/coursesBySubject',
            				"type": "POST",
                            "dataType": "json",
                            "contentType": "application/json",
                            "data": function (d) {
                                d.prefilter = subjectId;
                                return JSON.stringify(d);
                            },
            			},columnDefs: [
                              {
                                  targets: 6,
                                  className: 'dt-body-right'
                              }
                        ],
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
            			}, {
                            title : 'Total No. of Enroll',
                            data : 'enrollTotal',
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
                            render: function(data,type,row,meta) {
                                return addCommas(data);
                            }
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
                                + 'View/Edit</button>';
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
function addCommas(nStr)
{
    nStr += '';
    x = nStr.split('.');
    x1 = x[0];
    x2 = x.length > 1 ? '.' + x[1] : '.00';
    x2 = x2.substring(0, 3);
    while(x2.length < 3){
     x2 += '0';
    }
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    return x1 + x2;
}