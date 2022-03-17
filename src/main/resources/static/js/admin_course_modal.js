function showCourseEditModal(id){
var link = "/admin/course/view/" + id;
    var course = "";
    $.ajax({
                    url: link,
                    type:"get",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (data){
                    course = data;
                        if(course != ""){
                            $("#editCourseId").val(course.id);
                            $("#editCourseName").val(course.name);
                            $("#editCourseNameOriginal").val(course.name);
                            $("#editCourseLessonTotal").val(course.lessonTotal);
                            $("#editCourseStatus").val(course.status).change();
                            $("#editCoursePrice").val(course.price);
                            $("#editCourseSubject").val(course.subjectId).change();
                            $("#editCourseOriginalSubjectId").val(course.subjectId);
                            $("#editCourseDescription").text(course.description);
                        }
                    }
                });
    $('#courseEditModal').modal('show');
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

function submitAddCourse(e) {
    e.preventDefault();
    var courseName = $("#addCourseName").val();
    var subjectId = $("#addCourseSubject").val();
    var link = "/admin/course/isDuplicated";
    $.ajax({
        url: link,
        type:"get",
        contentType: "application/json; charset=utf-8",
        data:{
            "courseName" : courseName,
            "subjectId" : subjectId,
        },
        dataType: "json",
        success: function (data){
            if(data === "true"){
                alert('There is already a Course with that name for chosen Subject');
            }else {
                $("#courseAddForm")[0].submit();
            }
        },
        error: function (jqXHR, exception) {
        }
    });
}

function submitEditCourse(e) {
    e.preventDefault();
    var courseName = $("#editCourseName").val();
    var courseNameOriginal = $("#editCourseNameOriginal").val();
    var subjectId = $("#editCourseSubject").val();
    var link = "/admin/course/isDuplicated";

    $.ajax({
        url: link,
        type:"get",
        contentType: "application/json; charset=utf-8",
        data:{
            "courseName" : courseName,
            "subjectId" : subjectId,
        },
        dataType: "json",
        success: function (data){
            if(data){
                if(courseName == courseNameOriginal) {
                        $("#courseEditForm")[0].submit();
                } else {
                alert('There is already a Course with that name for chosen Subject');
                }
            }else{
                $("#courseEditForm")[0].submit();
            }
        },
        error: function (jqXHR, exception) {
        }
    });
}