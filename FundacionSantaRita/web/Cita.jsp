<%-- 
    Document   : cita
    Created on : May 24, 2015, 12:07:24 PM
    Author     : MAICK
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Servicios</title>
<meta charset="utf-8">
<link rel="stylesheet" href="recursos/css/reset.css" type="text/css" media="all">
<link rel="stylesheet" href="recursos/css/style.css" type="text/css" media="all">
<script type="text/javascript" src="recursos/js/jquery-1.6.js" ></script>
<script type="text/javascript" src="recursos/js/cufon-yui.js"></script>
<script type="text/javascript" src="recursos/js/cufon-replace.js"></script>
<script type="text/javascript" src="recursos/js/Swis721_Cn_BT_400.font.js"></script>
<script type="text/javascript" src="recursos/js/Swis721_Cn_BT_700.font.js"></script>
<script type="text/javascript" src="recursos/js/script.js"></script>

  <!--[if lt IE 9]>
  	<script type="text/javascript" src="recursos/js/html5.js"></script>
	<style type="text/css">
		.bg{ behavior: url(js/PIE.htc); }
	</style>
  <![endif]-->
	<!--[if lt IE 7]>
		<div style=' clear: both; text-align:center; position: relative;'>
			<a href="http://www.microsoft.com/windows/internet-explorer/default.aspx?ocid=ie6_countdown_bannercode"><img src="http://www.theie6countdown.com/images/upgrade.jpg" border="0"  alt="" /></a>
		</div>
	<![endif]-->

</head>

<body id="page2">
	<div class="body1">
	<div class="body2">
	<div class="body5">
		<div class="main">
<!-- header -->
			<header>
				<div class="wrapper">
				<h1><a href="index.html" id="logo">Progress Business Company</a></h1>
				<%@include file="recursos/html/nav.html" %>
				</div>
                                <%@include file="recursos/html/login.html" %>
			</header>
                                
                        
<!-- header end-->
		</div>
	</div>
	</div>
	</div>
	<div class="body3">
		<div class="main">
<!-- content -->
			<article id="content">
				<div class="wrapper tabs">
                                    <h2 class="under">Formulario para Cita</h2>
                                    <form id="FormCita" action="control" method="post" name="resCit">
                                        <div>
                                            <div  class="wrapper">
                                                    <span>Nombre:</span>
                                                    <input type="text" class="input" >
                                            </div>
                                            <div  class="wrapper">
                                                    <span>Cédula:</span>
                                                    <input type="text" class="input" >
                                            </div>
                                            <div  class="wrapper">
                                                    <span>Ciudad:</span>
                                                    <input type="text" class="input" >
                                            </div>
                                            <div  class="wrapper">
                                                    <span>Email:</span>
                                                    <input type="email" class="input" >
                                            </div>
                                            <div  class="wrapper">
                                                    <span>Telefono:</span>
                                                    <input type="text" class="input" >
                                            </div>
                                            <!--<div  class="wrapper">
                                                    <span>Ter&aacute;pia:</span>
                                                    <input type="radio" name="opcionTerapia" value="1">Si
                                                    <input type="radio" name="opcionTerapia" value="2" selected>No
                                                    <select name="terapia">
                                                        <option value="Terapia 1">Terapia 1</option>
                                                        <option value="Terapia 2">Terapia 2</option>
                                                        <option value="Terapia 3">Terapia 3</option>
                                                        <option value="Terapia 4">Terapia 4</option>
                                                    </select>
                                            </div>-->
                                            <!--<div  class="wrapper">
                                                    <span>Con:</span>
                                                    <input type="radio" name="opcionProfesional" value="1">Si
                                                    <input type="radio" name="opcionProfesional" value="2" selected>No
                                                    <select name="terapia">
                                                        <option value="Doctor 1">Profesional 1</option>
                                                        <option value="Doctor 2">Profesional 2</option>
                                                        <option value="Doctor 3">Profesional 3</option>
                                                        <option value="Doctor 4">Profesional 4</option>
                                                    </select>
                                            </div>
                                            -->
                                            
                                            <div  class="textarea_box">
                                                    <span>A tener en cuenta en la Cita (mensaje):</span>
                                                    <textarea name="textarea" cols="1" rows="1"></textarea>
                                            </div>
                                            <a href="">Solicitar Cita</a>
                                            <a href="#">Borrar</a>
					</div>
                                    </form>                              
                                </div>  
				
			</article>
		</div>
	</div>
                                
	<%@include file="recursos/html/footer.html"%>

        
<script type="text/javascript"> Cufon.now(); </script>

</body>
</html>