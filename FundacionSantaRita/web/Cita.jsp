<%-- 
    Document   : cita
    Author     : MAICK
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Cita</title>
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
<%@include file="recursos/html/favicon.html" %>
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
                                                    <input type="text" class="input" required>
                                            </div>
                                            <div  class="wrapper">
                                                    <span>Cédula:</span>
                                                    <input type="text" class="input" required>
                                            </div>
                                            <div  class="wrapper">
                                                    <span>Sexo:</span>
                                                    <input name="sexo" type="radio" value="m" selected>M
                                                    <input name="sexo" type="radio"value="f" selected>F
                                            </div>
                                            <div  class="wrapper">
                                                    <span>Ciudad:</span>
                                                    <input type="text" class="input" >
                                            </div>
                                            <div  class="wrapper">
                                                    <span>Email:</span>
                                                    <input type="email" class="input" required>
                                            </div>
                                            <div  class="wrapper">
                                                <span>Tel&eacute;fono:</span>
                                                    <input type="text" class="input" required>
                                            </div>
                                                                                        
                                            <div  class="textarea_box">
                                                    <span>A tener en cuenta en la Cita (mensaje):</span>
                                                    <textarea name="textarea" cols="1" rows="1"></textarea>
                                            </div>
                                            <a class="btn-form-cita" href="#" onClick="document.getElementById('formCita').submit()">Solicitar Cita</a>
                                            <a class="btn-form-cita"  href="#" onClick="document.getElementById('formCita').reset()">Borrar</a>
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