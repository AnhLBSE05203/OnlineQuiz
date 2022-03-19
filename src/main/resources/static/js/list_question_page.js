$(document).ready(function() {
    var lessonId = document.getElementById("lessonId").value;
    $('#QuestionDatatable').DataTable({
        "serverSide": true,
        pageLength : 5,
        ajax : {
            url : '/question/getQuestionsByPage/' + lessonId,
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
            title : 'Question',
            data : 'question'
        }, {
            title : 'Answer',
            data : 'answer',
        }, {
            title : 'Explain',
            data : 'explain',
        }, {
            title : 'Action',
            orderable: false,
            // data : 'status',
            render: function(data, type, row, meta) {
                var html = '<button type="button" class="btn btn-primary" onclick="showDetailQuestionModal('+ row['id'] + ')">'
                    + 'Detail</button>&nbsp';

                    html += '<button type="button" class="btn btn-primary" onclick="deleteQuestion('+ row['id'] +')">'
                        + 'Delete</button>&nbsp';
                return html;
            }
        } ]
    });
});
function deleteQuestion(ques_id){
    var confirmDelete = confirm("Are you sure to delete this question?");
    if (confirmDelete == 1) {
        var les_id = document.getElementById('lessonId').value;
        window.location.replace('/question/delete?questionId=' + ques_id + '&lessonId='+les_id);
        alert("Delete successful!");
    }
}
function edit(){
    var ques_id = document.getElementById('questionId').value;
    // window.location = '/question/edit?questionId=' + ques_id + '&subjectId='+sub_id;
    window.location.replace('/question/edit?questionId=' + ques_id);
}
function showDetailQuestionModal(id) {
    var link = "/question/" + id;
    var question = "";
    $.ajax({
        url: link,
        type:"get",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data){
            question = data;
            if(question != ""){
                $("#questionId").val(question.id);
                $("#question").val(question.question);
                $("#answer").val(question.answer);
                $("#explain").val(question.explain);
            }
        }
    });
    $('#detailQuestionModal').modal('show');
}
function closeModal(){
    $('#detailQuestionModal').modal('hide');
}
function createQuestion(){
    var lessonId = document.getElementById("lessonId").value;
    window.location.replace('/question/create?lessonId='+lessonId);
}



