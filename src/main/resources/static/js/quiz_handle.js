//getting all require element
const start_btn = document.querySelector(".start_button button");
const info_box = document.querySelector(".info_box");
const exit_btn = document.querySelector(".buttons .quit");
const continue_btn = document.querySelector(".buttons .restart");
const quiz_box = document.querySelector(".quiz_box");
const option_list = document.querySelector(".option_list");
const timeCount = quiz_box.querySelector(".timer .timer_sec");
const timeLine = quiz_box.querySelector(".time_line");

//If click start quiz button
start_btn.onclick = () => {
    info_box.classList.add("activeInfo");//show info infobox
}

//if click exit button
exit_btn.onclick = () => {
    info_box.classList.remove("activeInfo");//hide the info box
}

//if click continue button
continue_btn.onclick = () => {
    info_box.classList.remove("activeInfo");//hide the info box
    quiz_box.classList.add("activeQuiz");//show the quiz box
    // showQuestion(0);
    // queCount(1);
    // startTime(15);
    // startTimeLine(0);
}



//getting question and option from array
let que_count = 0;
let que_count_bottom = 1;
let counter;
let timeValue = 15;
let count_line;
let widthValue = 0;

const next_btn = quiz_box.querySelector(".next_btn");
const result_box = document.querySelector(".result_box");
const restart_quiz = result_box.querySelector(".buttons .restart");
const quit_quiz = result_box.querySelector(".buttons .quit");

//if click quit on result box
quit_quiz.onclick = () => {
    window.location.reload()
}

//if click replay on result box
restart_quiz.onclick = () => {
    quiz_box.classList.add("activeQuiz");//show the quiz box
    result_box.classList.remove("activeResult");//remove the result box
    que_count = 0;
    que_count_bottom = 1;
    timeValue = 15;
    widthValue = 0;
    showQuestion(que_count);
    queCount(que_count_bottom);
    clearInterval(counter);
    startTime(timeValue);
    clearInterval(count_line);
    startTimeLine(widthValue);
    next_btn.style.display = "none";
}

//if next button clicked
next_btn.onclick =() =>{
    if(que_count < question.length - 1){
        que_count++;
        que_count_bottom++;
        showQuestion(que_count);
        queCount(que_count_bottom);
        clearInterval(counter);
        startTime(timeValue);
        clearInterval(count_line);
        startTimeLine(widthValue);
        next_btn.style.display = "none";
    }else{
        console.log("question end");
        showResultBox();
    }
}
function showQuestion(index) {
    const que_text = document.querySelector(".que_text");

    let que_tag = '<span>' +question[index].num+'. '+ question[index].question + '</span>';
    let option_tag = '<div class="option">' + question[index].option[0] + '<span></span></div>'
        + '<div class="option">' + question[index].option[1] + '<span></span></div>'
        + '<div class="option">' + question[index].option[2] + '<span></span></div>'
        + '<div class="option">' + question[index].option[3] + '<span></span></div>';
    que_text.innerHTML = que_tag;
    option_list.innerHTML = option_tag;

    const option = option_list.querySelectorAll(".option");
    for (let i = 0; i < option.length; i++) {
        option[i].setAttribute("onclick","optionSelected(this)");

    }
}

let tickIcon = '<div class="icon tick"><i class="fas fa-check"></i></div>';
let crossIcon = '<div class="icon cross"><i class="fas fa-times"></i></div>';
let userScore = 0;
function optionSelected(answer){
    clearInterval(counter);
    clearInterval(count_line);
    let userAnswer = answer.textContent;
    let correctAnswer = question[que_count].answer;
    let allOption = option_list.children.length;
    if(userAnswer == correctAnswer){
        userScore+=1;
        console.log(userScore);
        answer.classList.add("correct");
        console.log("Answer is correct");
        answer.insertAdjacentHTML("beforeend",tickIcon);
    }else{
        answer.classList.add("incorrect");
        console.log("Answer is wrong");
        answer.insertAdjacentHTML("beforeend",crossIcon);
        //if user answer wrong, show answer correct
        for (let i = 0; i < allOption; i++) {
            if(option_list.children[i].textContent == correctAnswer){
                option_list.children[i].setAttribute("class","option correct");
                // option_list.children[i].classList.add("correct");
                option_list.children[i].insertAdjacentHTML("beforeend",tickIcon);
            }

        }
    }
    //if user once select will disabled all answer
    for (let i = 0; i < allOption; i++) {
        option_list.children[i].classList.add("disabled");
    }
    next_btn.style.display = "block";
}

function queCount(index){
    const bottom_question_count = document.querySelector(".total_que");
    let totalQuestionTag = '<span><p>'+index+'</p>of<p>'+question.length+'</p>Questions</span>';
    bottom_question_count.innerHTML = totalQuestionTag;
}

function startTime(time){
    counter = setInterval(timer,1000);
    function timer(){
        timeCount.textContent = time;
        time--;
        if(time < 9){
            let addZero = timeCount.textContent;
            timeCount.textContent = "0"+ addZero;
        }
        if(time < 0){
            clearInterval(counter);
            timeCount.textContent = "00";
        }
    }
}

function startTimeLine(time){
    count_line = setInterval(timer,29);
    function timer(){
        time +=1;
        timeLine.style.width = time +"px";
        if(time > 549){
            clearInterval(count_line);
        }
    }
}

function showResultBox(){
    info_box.classList.remove("activeInfo");//hide the info box
    quiz_box.classList.remove("activeQuiz");//hide quiz box
    result_box.classList.add("activeResult");//show result box
    const scoreText = result_box.querySelector(".score_text");
    if(userScore < question.length){
        let scoreTag = '<span>and sorry, You got only<p>'+userScore+'</p>out of <p>'+question.length+'</p></span>'
        scoreText.innerHTML = scoreTag;
    }else {
        let scoreTag = '<span>and congrats!, You got <p>'+userScore+'</p>out of <p>'+question.length+'</p></span>'
        scoreText.innerHTML = scoreTag;
    }
}