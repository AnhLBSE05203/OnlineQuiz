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
                        updateMenuAfterLogin(principal);
                    }
                }
            });
});
function updateMenuAfterLogin(principal){
    for(let i = 0; i < principal.roles.length; i++){
        var role = principal.roles[i].name;
        alert(role);
        switch(role){
            case("ROLE_ADMIN") :
                $('#accountMenu').show();
                $('#blogMenu').show();
                $('#subjectMenu').show();
                $('#lessonMenu').show();
                break;

            case("ROLE_SALES"):
                $('#blogMenu').show();
                break;
            case("ROLE_EXPERT"):
                $('#subjectMenu').show();
                $('#lessonMenu').show();
                break;

        }
    }
}