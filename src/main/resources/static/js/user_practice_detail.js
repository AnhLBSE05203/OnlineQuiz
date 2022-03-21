function showModal(){
    $('#practiceDetailModal').modal('show');
}
function closeModal(){
    $('#practiceDetailModal').modal('hide');
}
function start(){
    var number = document.getElementById('numberInput').value;
    var lessonId = document.getElementById("lessonId").value;
    alert(number + " " + lessonId);
    if(isNaN(number)){
        alert("Please enter a number");
    }else if(number <= 0){
        alert("Number of question must be greater than 0");
    }else if(number > 2147483647){
        alert("Number of question must be less than 2147483647");
    }else {
        $.ajax({
            url: "/test",
            type: "get",
            data: {
                lessonId: lessonId,
                number: number
            },
            success: function () {

            }
        })
    }
}
function showNumber(){
    var lessonId = document.getElementById("lessonId").value;

    $.ajax({
        url : "/practices/count",
        type: "get",
        data: {
            lessonId : lessonId
        },
        success: function (data){
            // alert(data);
            var num = data;
            if(num != ""){
                $('#number').text(num);
            }else{
                $('#number').text(0);
            }
        }
    })
}