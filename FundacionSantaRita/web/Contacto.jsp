<%-- 
    Document   : contacto
    Author     : MAICK
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
<title>Contacto</title>
<meta charset="utf-8">
<link rel="stylesheet" href="recursos/css/reset.css" type="text/css" media="all">
<link rel="stylesheet" href="recursos/css/style.css" type="text/css" media="all">
<script type="text/javascript" src="recursos/js/jquery-1.6.js" ></script>
<script type="text/javascript" src="recursos/js/cufon-yui.js"></script>
<script type="text/javascript" src="recursos/js/cufon-replace.js"></script>
<script type="text/javascript" src="recursos/js/Swis721_Cn_BT_400.font.js"></script>
<script type="text/javascript" src="recursos/js/Swis721_Cn_BT_700.font.js"></script>
<script type="text/javascript" src="recursos/js/tabs.js"></script>
<script type="text/javascript" src="recursos/js/script.js"></script>
<!--[if lt IE 9]>
  	<script type="text/javascript" src="js/html5.js"></script>
	<style type="text/css">
		.bg{ behavior: url(js/PIE.htc); }
	</style>
<![endif]-->
<!--[if lt IE 7]>
		<div style=' clear: both; text-align:center; position: relative;'>
			<a href="http://www.microsoft.com/windows/internet-explorer/default.aspx?ocid=ie6_countdown_bannercode"><img src="http://www.theie6countdown.com/images/upgrade.jpg" border="0"  alt="" /></a>
		</div>
<![endif]-->

<%@include file="recursos/html/favicon.html" %>

</head>


<body id="page5">
	<div class="body1">
	<div class="body2">
	<div class="body5">
		<div class="main">
<!-- header -->
			<header>
				<div class="wrapper">
                                    <h1><a href="index.html" id="logo">Fundaci&oacute;</a></h1>
                                
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
				<div class="wrapper">
					<section class="col1">
						<h2 class="under">Formulario de Contacto</h2>
						<form id="FormContacto" action="control" method="post">
						<div>
							<div  class="wrapper">
								<span>Nombre:</span>
								<input type="text" class="input" >
							</div>
							<div  class="wrapper">
								<span>Ciudad:</span>
								<input type="text" class="input" >
							</div>
							<div  class="wrapper">
								<span>Email:</span>
								<input type="text" class="input" >
							</div>
							<div  class="textarea_box">
								<span>Mensaje:</span>
								<textarea name="textarea" cols="1" rows="1"></textarea>
							</div>
							<a href="#" onClick="document.getElementById('formContacto').submit()">Enviar</a>
							<a href="#" onClick="document.getElementById('formContacto').reset()">Borrar</a>
						</div>
					</form>
					</section>
					<section class="col2 pad_left1">
						<h2 class="under">Contacto</h2>
						<div id="address"><span>Country:<br>
								City:<br>
								Telephone:<br>
								Email:
                                                                Direcci&oacute;n</span>
								Col&oacute;mbia<br>
								Villavicencio<br>
								+57 300 100 1000<br>
								<a href="mailto:" class="color2">fundacion@mail.com</a>
                                                                calle falsa N°123<br>
                                                </div>
                                                
                                                <h2 class="under">Informaci&oacute;n de Contacto</h2>
						<p>Revise su correo e incluso la carpeta de spam para estar al tanto de nuestra respuesta, Gracias por contactarnos</p>
					</section>
				</div>

			</article>
		</div>
	</div>
                                
	<%@include file="recursos/html/footer.html"%>

        
<script type="text/javascript"> Cufon.now(); </script>
<script>
	$(document).ready(function() {
		tabs.init();
	})
</script>
</body>
</html>