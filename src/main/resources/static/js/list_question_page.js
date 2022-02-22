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
                var html = '<button type="button" class="btn btn-primary" onclick="showQuestionEdit('+ row['id'] + ')">'
                    + 'Edit</button>&nbsp';

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
function showQuestionEdit(id){
    alert("edit question id =  " + id);
}
function showQuestionAdd(){
    alert("Add question");
}