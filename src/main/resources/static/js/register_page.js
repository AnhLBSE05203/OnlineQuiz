const passwordRegex = /^(?=.*?[0-9])(?=.*?[A-Z])(?=.*?[#?!@$%^&*\-_]).{8,}$/;
const emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

function checkPasswordMatch(fieldConfirmPassword) {
    if (fieldConfirmPassword.value != $("#password").val()) {
        fieldConfirmPassword.setCustomValidity("Passwords do not match!");
    } else {
        fieldConfirmPassword.setCustomValidity("");
    }
}
function checkPasswordValid(fieldPassword) {
    var value = fieldPassword.value;
    if (!value.match(passwordRegex)) {
        fieldPassword.setCustomValidity("Passwords need at least 1 special character, 1 upper case & length >= 8");
    } else {
        fieldPassword.setCustomValidity("");
    }
}
function checkEmailValid(fieldEmail) {
    var value = fieldEmail.value.toLowerCase();
    if (!value.match(emailRegex)) {
        fieldEmail.setCustomValidity("Check Email Input");
    } else {
        fieldEmail.setCustomValidity("");
    }
}