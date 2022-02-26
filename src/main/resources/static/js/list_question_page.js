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
                var html = '<a href="/question/detailQuestion?questionId='+row['id']+'">'
                    + 'Detail</a>&nbsp';

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
// function showQuestion(id){
//     alert("show question id = " + id);
//     $.ajax({
//         url:"/question/showDetailQuestion?questionId=" + id,
//         type:"GET",
//         success:function (){}
//     })
// }