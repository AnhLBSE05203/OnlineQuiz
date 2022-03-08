//todo
function showCourseEditModal(id){
var link = "/admin/course/" + id;
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