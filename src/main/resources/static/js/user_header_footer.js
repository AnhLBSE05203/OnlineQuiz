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
                        $("#login-button").attr("href", "/account/logout");
                        $("#login-button").html("Logout");
                        $("#register-button").parent().css('visibility','hidden');
                    };
                    updateMobileMenu();
                },
                error: function(){
                    updateMobileMenu();
                },
            });
});
function updateMobileMenu(){
    var menu = $('ul#navigation');
    if(menu.length){
        menu.slicknav({
        prependTo: ".mobile_menu",
        closedSymbol: '+',
        openedSymbol:'-'
        });
    };
};