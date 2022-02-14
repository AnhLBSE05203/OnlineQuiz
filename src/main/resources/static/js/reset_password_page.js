const passwordRegex = /^(?=.*?[0-9])(?=.*?[A-Z])(?=.*?[#?!@$%^&*\-_]).{8,}$/;
const CONST_PASSWORD_NOT_MATCH = "Passwords do not match!";
const CONST_PASSWORD_INVALID = "Passwords need at least 1 special character, 1 upper case & length >= 8";
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