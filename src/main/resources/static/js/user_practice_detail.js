function showModal(){
    $('#practiceDetailModal').modal('show');
}
function closeModal(){
    $('#practiceDetailModal').modal('hide');
}
function start(){
    var number = document.getElementById('number').value;
    if(isNaN(number)){
        alert("Please enter a number");
    }else if(number <= 0){
        alert("Number of question must be greater than 0");
    }else if(number > 2147483647){
        alert("Number of question must be less than 2147483647");
    }
}