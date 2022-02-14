const passwordRegex = /^(?=.*?[0-9])(?=.*?[A-Z])(?=.*?[#?!@$%^&*\-_]).{8,}$/;
const emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const CONST_PASSWORD_NOT_MATCH = "Passwords do not match!";
const CONST_PASSWORD_INVALID = "Passwords need at least 1 special character, 1 upper case & length >= 8";
const CONST_EMAIL_INVALID = "Check Email Input";
function checkPasswordMatch(fieldConfirmPassword) {
    if (fieldConfirmPassword.value != $("#password").val()) {
        fieldConfirmPassword.setCustomValidity(CONST_PASSWORD_NOT_MATCH);
    } else {
        fieldConfirmPassword.setCustomValidity("");
    }
}
function checkPasswordValid(fieldPassword) {
    var value = fieldPassword.value;
    if (!value.match(passwordRegex)) {
        fieldPassword.setCustomValidity(CONST_PASSWORD_INVALID);
    } else {
        fieldPassword.setCustomValidity("");
    }
}
function checkEmailValid(fieldEmail) {
    var value = fieldEmail.value.toLowerCase();
    if (!value.match(emailRegex)) {
        fieldEmail.setCustomValidity(CONST_EMAIL_INVALID);
    } else {
        fieldEmail.setCustomValidity("");
    }
}