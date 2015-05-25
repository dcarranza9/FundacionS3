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
    if ($('.login').is(':visible')) {
        $('.login').hide('slow');
    }
    else{
        $('.login').show('slow');
    }
    
}

$(function()
{
    var $dropdowns = $('li.dropdown');
    $dropdowns.on('mouseover', function(){
        
            var $this = $(this);

            if ($this.prop('hoverTimeout'))
            {
                $this.prop('hoverTimeout', clearTimeout($this.prop('hoverTimeout')));
            }

            $this.prop('hoverIntent', setTimeout(function()
            {
                $this.addClass('hover');
            }, 250));
        })
        .on('mouseleave', function()
        {
            var $this = $(this);

            if ($this.prop('hoverIntent'))
            {
                $this.prop('hoverIntent', clearTimeout($this.prop('hoverIntent')));
            }

            $this.prop('hoverTimeout', setTimeout(function()
            {
                $this.removeClass('hover');
            }, 250));
        });
    
});