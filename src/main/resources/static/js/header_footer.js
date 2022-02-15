$(document).ready(function(){
    $.ajax({
                url:"/getPrincipal",
                type:"get",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (data){
                    if(data != ""){
                        $("#login-button").attr("href", "account/logout");
                        $("#login-button").html("Logout");
                        $("#register-button").parent().hide();
                    }
                }
            });
});
