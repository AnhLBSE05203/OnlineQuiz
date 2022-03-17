function showUserCourseDetailModal(id) {
    var link = "/user/getCourseUserDTO/" + id;
    var course = "";
    $.ajax({
                    url: link,
                    type:"get",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (data){
                    course = data;
                        if(course != ""){
                            $("#courseId").text(course.id);
                            $("#courseName").text(course.courseName);
                            $("#courseSubjectName").text(course.subjectName);
                            $("#courseDescription").text(course.description);
                            $("#courseImg").attr("src", course.imgSrc);
                            $("#coursePrice").text(course.price);
                            $("#courseLessonTotal").text(course.lessonTotal);
                        }
                    }
                });
    $('#courseUserModal').modal('show');
};
function registrationCourse(){
    var courseId = document.getElementById("courseId").textContent;
    window.location.replace("/user/registration?courseId=" + courseId);
}