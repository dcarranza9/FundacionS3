$(document).ready(function(){
$("#mytable #checkall").click(function () {
        if ($("#mytable #checkall").is(':checked')) {
            $("#mytable input[type=checkbox]").each(function () {
                $(this).prop("checked", true);
            });

        } else {
            $("#mytable input[type=checkbox]").each(function () {
                $(this).prop("checked", false);
            });
        }
    });
    
    $("[data-toggle=tooltip]").tooltip();
});

function traerhtmla1()
{
	$('#ventana').hide();
	$('#ventana').load('recursos/formularios/agregarMedicamento.html');
	$('#ventana').toggle("slide");
}

function traerhtmla2(){
	$('#ventana').hide();
	$('#ventana').load('recursos/formularios/agregarTerapia.html');
	$('#ventana').toggle("slide");
}

function traerhtmla3(){
	$('#ventana').hide();
	$('#ventana').load('recursos/formularios/agregarEvento.html');
	$('#ventana').toggle("slide");
}

function traerhtmla4(){
	$('#ventana').hide();
	$('#ventana').load('recursos/formularios/agregarTestimonio.html');
	$('#ventana').toggle("slide");
}

function traerhtmla5(){
	$('#ventana').hide();
	$('#ventana').load('recursos/formularios/agregarDonacion.html');
	$('#ventana').toggle("slide");
}



/////////////////////////GESTIONAR/////////////////////////////
//////////////////////////////////////////////////////////////
function traerhtmlb1()
{
	$('#ventana').hide();
	$('#ventana').load('recursos/formularios/gestionarMedicamento.html');
	$('#ventana').toggle("slide");
}

function traerhtmlb2(){
    $('#ventana').hide();
    $('#ventana').load('recursos/formularios/gestionarTerapia.html');
    $('#ventana').toggle("slide");
}

function traerhtmlb3(){
    $('#ventana').hide();
    $('#ventana').load('recursos/formularios/gestionarEvento.html');
    $('#ventana').toggle("slide");
}

function traerhtmlb4(){
    $('#ventana').hide();
    $('#ventana').load('recursos/formularios/gestionarTestimonio.html');
    $('#ventana').toggle("slide");
}

function traerhtmlb5(){
    $('#ventana').hide();
    $('#ventana').load('recursos/formularios/gestionarDonacion.html');
    $('#ventana').toggle("slide");
}

function traerhtmlb6(){
    $('#ventana').hide();
    $('#ventana').load('recursos/formularios/gestionarTestimonio.html');
    $('#ventana').toggle("slide");
}

function traerhtmlb7(){
    $('#ventana').hide();
    $('#ventana').load('recursos/formularios/gestionarDonacion.html');
    $('#ventana').toggle("slide");
}