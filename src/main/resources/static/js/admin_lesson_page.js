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
            title : 'Lesson Id',
            data : 'lesId'
        }, {
            title : 'Lesson Name',
            data : 'name'
        }, {
            title : 'Type',
            data : 'lessonType',
        }, {
            title : 'Subject',
            data : 'subjects',
        }, {
            title : 'Content',
            data : 'content',
        },{
            title : 'Status',
            data : 'status',
        },{
            title : 'Time',
            data : 'time',
        }]
    });
});
function recoverLesson(id){
    window.location.replace("/admin/lesson/recover/" + id);
}
function deleteLesson(id){
    window.location.replace("/admin/lesson/delete/" + id);
}
function showLessonEditModal(id) {
    var link = "/admin/lesson/" + id;
    var lesson = "";
    $.ajax({
        url: link,
        type:"get",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data){
            lesson = data;
            if(subject != ""){
                $("#editLessonId").val(lesson.id);
                $("#editLessonName").val(lesson.name);
                $("#editLessonTotalCourse").val(lesson.totalCourse);
                $("#editLessonImg").attr("src", lesson.imgSrc);
                $("#editLessonStatus").val(lesson.status).change();

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