var principal = "";
$(document).ready(function(){
    $.ajax({
                url:"/getPrincipal",
                type:"get",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (data){
                principal = data;
                    if(principal != ""){
                        $("#current-user").html(principal.email);
                    }
                }
            });
});