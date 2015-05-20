var expUsuario=/^[a-zA-Z0-9\.\-]+$/;
var expPassword=/^[a-zA-Z0-9\.\-]/;

$(document).ready(function(){
    

    $("#btsubmit").click(function(){
        
        var usuario = $("#usuario").val();
        var password = $("#password").val();

        if(usuario==="" || !expUsuario.test(usuario)){
            $("#usuario").css("box-shadow","0 0 1px 1px red");
            return false;
        }
        if(password==="" || !expPassword.test(password)){
            $("#password").css("box-shadow","0 0 1px 1px red");
            return false;
        }
        
    });

    $(document).mouseup(function (e)
    {
        var container = $(".login");

        if ( container.has(e.target).length === 0) // ... nor a descendant of the container
        {
            $('.login').hide();
        }
    });
    
});

function mostrarLogin()
{
    $('.login').slideToggle('slow');
}