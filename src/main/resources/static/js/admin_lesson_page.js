$(document).ready(function() {
    $('#LessonDatatable').DataTable({
        "serverSide": true,
        pageLength : 5,
        ajax : {
            url : '/admin/lesson/getLessonByPage',
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
            title : 'Status',
            data : 'statusStr',
        }, {
            title : 'Img',
            data : 'imgSrc',
            render: function(data, type, row, meta) {
                var html = "";
                if(data != "" && data != null){
                    html += '<img src="' + data +'" class="lesson-img" />';
                }
                return html;
            }
        }, {
            title : 'Action',
            data : 'status',
            render: function(data, type, row, meta) {
                var html = '<button type="button" class="btn btn-primary" onclick="showLessonEditModal('+ row['id'] + ')">'
                    + 'Edit</button>&nbsp';
                if (data == 0) {
                    html += '<button type="button" class="btn btn-primary" onclick="recoverLesson('+ row['id'] +')">'
                        + 'Recover</button>&nbsp';
                } else {
                    html += '<button type="button" class="btn btn-primary" onclick="deleteLesson('+ row['id'] +')">'
                        + 'Delete</button>&nbsp';
                }
                return html;
            }
        } ]
    });
});
function recoverLesson(id){
    window.location.replace("/admin/lesson/recover/" + id);
}
function deleteLesson(id){
    window.location.replace("/admin/lesson/delete/" + id);
}//234
function showLessonEditModal(id) {
    var link = "/admin/lesson/" + id;
    var subject = "";
    $.ajax({
        url: link,
        type:"get",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data){
            subject = data;
            if(subject != ""){
                $("#editLessonId").val(subject.id);
                $("#editLessonName").val(subject.name);
                $("#editLessonTotalCourse").val(subject.totalCourse);
                $("#editLessonImg").attr("src", subject.imgSrc);
                $("#editLessonStatus").val(subject.status).change();

            }
        }
    });
    $('#lessonEditModal').modal('show');
};
$('#lessonEditModal').on('hidden.bs.modal', function () {
    alert('close');
});
$('#lessonEditModal').on('shown.bs.modal', function (e) {
    // do something...
});

function showLessonAddModal(id) {
    $('#lessonAddModal').modal('show');
};
$('#lessonAddModal').on('hidden.bs.modal', function () {
    alert('close');
});
$('#lessonAddModal').on('shown.bs.modal', function (e) {
    // do something...
});