function showUserCourseDetailModal(id) {
    var link = "/course/getCourseUserDTO/" + id;
    var course = "";
    $.ajax({
                    url: link,
                    type:"get",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (data){
                    course = data;
                        if(subject != ""){
                            $("#editSubjectId").val(subject.id);
                            $("#editSubjectName").val(subject.name);
                            $("#editSubjectTotalCourse").val(subject.totalCourse);
                            $("#editSubjectImg").attr("src", subject.imgSrc);
                            $("#editSubjectStatus").val(subject.status).change();

                        }
                    }
                });
    $('#subjectEditModal').modal('show');
};