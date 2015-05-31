<%-- 
    Document   : index
    Author     : MAICK
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Fundaci&oacute;n</title>

<meta charset="utf-8">

<link rel="stylesheet" href="recursos/css/reset.css" type="text/css" media="all">
<link rel="stylesheet" href="recursos/css/style.css" type="text/css" media="all">

<script type="text/javascript" src="recursos/js/jquery-1.6.js" ></script>
<script type="text/javascript" src="recursos/js/cufon-yui.js"></script>
<script type="text/javascript" src="recursos/js/cufon-replace.js"></script>
<script type="text/javascript" src="recursos/js/Swis721_Cn_BT_400.font.js"></script>
<script type="text/javascript" src="recursos/js/Swis721_Cn_BT_700.font.js"></script>
<script type="text/javascript" src="recursos/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="recursos/js/tms-0.3.js"></script>
<script type="text/javascript" src="recursos/js/tms_presets.js"></script>
<script type="text/javascript" src="recursos/js/jcarousellite.js"></script>
<script type="text/javascript" src="recursos/js/slider.js"></script>
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
<body id="page1">
<div class="body1">
	<div class="body2">
		<div class="main">
<!-- header -->
			<header>
				<div class="wrapper">
				<h1><a href="index.html" id="logo"></a></h1>
                                
                                <!--Aqui se incluye el nav-->
                                <%@include file="recursos/html/nav.html" %>


				</div>

                                <%@include file="recursos/html/login.html" %>
				
				<div class="wrapper">
					<div class="slider">
					  <ul class="items">
						<li><img src="recursos/images/img1.jpg" alt=""></li>
						<li><img src="recursos/images/img2.jpg" alt=""></li>
						<li><img src="recursos/images/img3.jpg" alt=""></li>
						<li><img src="recursos/images/img4.jpg" alt=""></li>
						<li><img src="recursos/images/img5.jpg" alt=""></li>
					  </ul>
					</div>
				</div>
			</header>
<!-- header end-->
		</div>
	</div>
	</div>
	<div class="body3">
		<div class="main">
<!-- content -->
			<article id="content">

				<div class="wrapper">
					<section class="col1">
						<h2 class="under">Bienvenido Querido visitante!</h2>
						<div class="wrapper">
							<figure class="left marg_right1"><img src="recursos/images/page1_img1.jpg" alt=""></figure>
							<p class="pad_bot1">Somos una fundacion con una ideologia naturista que buscamos ayudar a las personas gracias a la ayuda de otras personas, hemos desarrollado una linea de productos y servicios para poder tratar todo tipo de males que aquejan la mente cuerpo y alma de quienes acuden a nosotros</p>
							<p>Buscamos que se cada vez mas personas se sumen a nuestra fundacion y nos permitan ayudarles con terapias y medicamentos naturistas</p>
						</div>
					</section>
					<section class="col2 pad_left1">
						<h2>Testimonios</h2>
						<div class="testimonials">
						<div id="testimonials">
						  <ul>
							<li>
								<div>
									“Recomiendo a todos las terapias de esta fundacion me han ayudado.”
								</div>
								<span><strong class="color1">Persona1,</strong> Paciente</span>
							</li>
							<li>
								<div>
									“Creo que es de las mejores decisiones que he tomado es empezar con los medicamentos naturistas.”
								</div>
								<span><strong class="color1">Persona2,</strong> Paciente</span>
							</li>
							<li>
								<div>
                                                                    “Mi animo, energias, actitud se han visto influidos de manera positiva desde que uso los medicamentos de la fundaci&oacute;n.”
								</div>
								<span><strong class="color1">Persona3,</strong> Paciente</span>
							</li>
                                                        
                                                        <li>
								<div>
									“Feliz desde que empece a tomar los medicamento efectivos de la fundacion, mi vida ha cambiado”
								</div>
								<span><strong class="color1">Persona4,</strong> Paciente</span>
							</li>
						  </ul>
						</div>
						<a href="#" class="up"></a>
						<a href="#" class="down"></a>
						</div>
					</section>
				</div>
			</article>
		</div>
	</div>
	
        <%@include file="recursos/html/footer.html"%>
        
		
<script type="text/javascript"> Cufon.now(); </script>
</body>
</html>
