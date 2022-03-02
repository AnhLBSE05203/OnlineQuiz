$(document).ready(function () {

})

function detailSubjectModal(id){
    var link = "/subject/detail/" + id;
    console.log(id);
    var subject = "";
    $.ajax({
        url: link,
        type: "get",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            subject = data;
            if (subject != null) {
                console.log("success");
                console.log(subject.subjectInfo);
                $("#subjectInfo").text(subject.subjectInfo);
                $("#subjectLearn").text(subject.learnAfter);
            }else{
                console.log("null")
            }
        }
    });
    $('#myModal').modal('show');
}

// var link = "/subject/detail/" + id;
// console.log(id);
// var subject = "";
// $.ajax({
//     url:link,
//     type:"get",
//     contentType:"application/json; charset=utf-8",
//     dataType:"json",
//     success:function (data){
//         subject = data;
//         if(subject != null){
//             $("#subjectInfo").val(subject.subjectinfo);
//         }
//     }
// });
// function showSubjectEditModal(id) {
//     var link = "/admin/subject/" + id;
//     var subject = "";
//     $.ajax({
//         url: link,
//         type:"get",
//         contentType: "application/json; charset=utf-8",
//         dataType: "json",
//         success: function (data){
//             subject = data;
//             if(subject != ""){
//                 $("#editSubjectId").val(subject.id);
//                 $("#editSubjectName").val(subject.name);
//                 $("#editSubjectTotalCourse").val(subject.totalCourse);
//                 $("#editSubjectImg").attr("src", subject.imgSrc);
//                 $("#editSubjectStatus").val(subject.status).change();
//
//             }
//         }
//     });
//     $('#subjectEditModal').modal('show');
// };