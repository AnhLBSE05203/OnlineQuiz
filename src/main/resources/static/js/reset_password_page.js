const passwordRegex = /^(?=.*?[0-9])(?=.*?[A-Z])(?=.*?[#?!@$%^&*\-_]).{8,}$/;

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