$(document).ready(function() {
    $('#QuestionDatatable').DataTable({
        "serverSide": true,
        pageLength : 5,
        ajax : {
            url : '/question/getQuestionsByPage',
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
            // data : 'status',
            render: function(data, type, row, meta) {
                var html = '<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#detailQuestionModal" onclick="showDetailQuestionModal('+ row['id'] + ')">'
                    + 'Detail</button>&nbsp';

                    html += '<button type="button" class="btn btn-primary" onclick="deleteQuestion('+ row['id'] +')">'
                        + 'Delete</button>&nbsp';
                return html;
            }
        } ]
    });
});
function deleteQuestion(id){
    alert("delete question id=  " + id);
}
function edit(){
    var id = document.getElementById('questionId').val;
    alert("Edit questionId = " + id);
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
};
function closeModal(){
    $('#detailQuestionModal').modal('hide');
}