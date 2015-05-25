<%-- 
    Document   : Servicios.jsp
    Created on : May 14, 2015, 2:31:38 PM
    Author     : MAICK
--%>

<%@page import="java.util.ArrayList, modelo.dto.TerapiaDto"%>
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
                                    
                                    <a href="control?accion=cita">RESERVAR UNA CITA!</a><br><br>
                                    
                                    <div class='tab-content'>
                                    <%
                                        if(request.getAttribute("terapias")!=null)
                                        {     
                                            ArrayList t=(ArrayList)request.getAttribute("terapias");
                                            for (int i=1;i<=t.size();i++)
                                            {
                                                TerapiaDto terapia=(TerapiaDto)t.get(i-1);
                                    %>
                                                
                                                <h5><span class='dropcap'><strong> <%=i%> </strong><span>---</span></span><%=terapia.getNombre() %></h5>
                                                <div class='wrapper pad_bot2'>
                                                    <figure class='left marg_right1'><img src='recursos/images/servicios/terapia1.jpg' alt=''></figure>
                                                    <p class='pad_bot1'><%=terapia.getDescripcion() %></p>
                                                    <a href='#' class='link1'>Leer M&aacute;s</a>
                                                </div>
                                                
                                    <%      }
                                        }
                                    %>
                                       
                                        <h5><span class='dropcap'><strong>1</strong><span>---</span></span>Terapia 1 ejemplo</h5>
                                        <div class='wrapper pad_bot2'>
                                            <figure class='left marg_right1'><img src='recursos/images/servicios/terapia1.jpg' alt=''></figure>
                                            <p class='pad_bot1'>ejemplo de vista en caso de que no se este conectado a la base de datos </p>
                                            <a href='#' class='link1'>Leer M&aacute;s</a>
                                        </div>
                                    
                                        <h5><span class='dropcap'><strong>2</strong><span>---</span></span>Terapia 2 ejemplo</h5>
                                        <div class='wrapper pad_bot2'>
                                            <figure class='left marg_right1'><img src='recursos/images/servicios/terapia1.jpg' alt=''></figure>
                                            <p class='pad_bot1'>ejemplo de vista en caso de que no se este conectado a la base de datos </p>
                                            <a href='#' class='link1'>Leer M&aacute;s</a>
                                        </div>
                                    </div>  
				</div>

			</article>
		</div>
	</div>
                                
	<%@include file="recursos/html/footer.html"%>

        
<script type="text/javascript"> Cufon.now(); </script>

</body>
</html>