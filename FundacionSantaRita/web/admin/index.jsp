<%-- 
    Document   : index
    Created on : May 16, 2015, 1:01:38 PM
    Author     : MAICK
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Administrador</title>

    <!-- Bootstrap Core CSS -->
    <link href="recursos/css/bootstrap.min.css" rel="stylesheet">

    <!-- FORMULARIOS CSS -->
    <link rel="stylesheet" href="recursos/css/estilo-formularios.css">

    <!-- Custom CSS -->
    <link href="recursos/css/estilo.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="recursos/css/plugins/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="recursos/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    
    <link rel="shortcut icon" href="recursos/images/favicon32x32.png" type="image/png">
    <link rel="icon" href="recursos/images/favicon32x32.png" type="image/png">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html"><i class="fa fa-2x fa-leaf"></i><span class="titulo">ADMINISTRADOR FUNDACION SANTA RITA</span></a>
            </div>

            <!-- boton del usuario -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> Usuario<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <!-- <li>
                            <a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
                        </li> -->
                        <li class="divider"></li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>

            <!-- LISTA IZQUIERA QUE SE COLLAPSA CON PANTALLAS PEQUEÑAS -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li class="active">
                        <a href="index.html"><i class="fa fa-fw fa-dashboard"></i> INICIO></a>
                    </li>
                    <li>
                        <a href="javascript:;" data-toggle="collapse" data-target="#menu2"><i class="fa fa-fw fa-plus"></i> AGREGAR</a>
                        <ul id="menu2" class="collapse">
                            <li>
                                <a name="linkAgMed" onclick="traerhtmla1()" >Medicamento<i class="span1 pull-right fa fa-fw fa-arrow-right"></i></a>
                            </li>
                            <li>
                                <a name="linkAgTer" onclick="traerhtmla2()" >Terapia<i class="span1 pull-right fa fa-fw fa-arrow-right"></i></a>
                            </li>
                            <li>
                                <a name="linkAgEve" onclick="traerhtmla3()" >Evento<i class="span1 pull-right fa fa-fw fa-arrow-right"></i></a>
                            </li>
                            <li>
                                <a name="linkAgTes" onclick="traerhtmla4()">Testimonio<i class="span1 pull-right fa fa-fw fa-arrow-right"></i></a>
                            </li>
                            <li>
                                <a name="linkAgDon" onclick="traerhtmla5()">Donacion<i class="span1 pull-right fa fa-fw fa-arrow-right"></i></a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:;" data-toggle="collapse" data-target="#menu3"><i class="fa fa-fw fa-eye"></i> GESTIONAR</a>
                        <ul id="menu3" class="collapse">
                            <li>
                                <a name=""  href="#">Medicamentos<i class="span1 pull-right fa fa-fw fa-arrow-right"></i></a>
                            </li>
                            <li>
                                <a name=""  href="#">Terapias<i class="span1 pull-right fa fa-fw fa-arrow-right"></i></a>
                            </li>
                            <li>
                                <a name=""  href="#">Eventos<i class="span1 pull-right fa fa-fw fa-arrow-right"></i></a>
                            </li>
                            <li>
                                <a name=""  href="#">Testimonios<i class="span1 pull-right fa fa-fw fa-arrow-right"></i></a>
                            </li>
                            <li>
                                <a name=""  href="#">Donaciones<i class="span1 pull-right fa fa-fw fa-arrow-right"></i></a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:;" data-toggle="collapse" data-target="#menu4"><i class="fa fa-fw fa-list"></i> LISTAR</a>
                        <ul id="menu4" class="collapse">
                            <li>
                                <a name=""  href="#">Medicamentos<i class="span1 pull-right fa fa-fw fa-arrow-right"></i></a>
                            </li>
                            <li>
                                <a name=""  href="#">Terapias<i class="span1 pull-right fa fa-fw fa-arrow-right"></i></a>
                            </li>
                            <li>
                                <a name=""  href="#">Eventos<i class="span1 pull-right fa fa-fw fa-arrow-right"></i></a>
                            </li>
                            <li>
                                <a name=""  href="#">Testimonios<i class="span1 pull-right fa fa-fw fa-arrow-right"></i></a>
                            </li>
                            <li>
                                <a name=""  href="#">Donaciones<i class="span1 pull-right fa fa-fw fa-arrow-right"></i></a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>

        <div id="page-wrapper">

            <div id="ventana" class="container-fluid">

            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="recursos/js/jquery-2.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="recursos/js/bootstrap.min.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="recursos/js/plugins/morris/raphael.min.js"></script>
    <script src="recursos/js/plugins/morris/morris.min.js"></script>
    <script src="recursos/js/plugins/morris/morris-data.js"></script>
    <script src="recursos/js/script.js"></script>


</body>

</html>
