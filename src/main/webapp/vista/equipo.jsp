<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page session="true" %>
<%
    if (request.getSession().getAttribute("usuario") != null) {
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <%@include file="plus/head.jsp" %>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <!-- Navbar -->
            <%@include file="plus/menuSuperior.jsp" %>            
            <!-- /.navbar -->

            <!-- Main Sidebar Container -->
            <%@include  file="plus/menuLateral.jsp"%>
            <!-- /. Main Sidebar Container -->

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <div class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-6">
                                <h1 class="m-0 text-dark">Equipos</h1>
                            </div><!-- /.col -->
                            <div class="col-sm-6">
                                <ol class="breadcrumb float-sm-right">
                                    <li class="breadcrumb-item"><a href="#">Registros</a></li>
                                    <li class="breadcrumb-item active">Equipos</li>
                                </ol>
                            </div><!-- /.col -->
                        </div><!-- /.row -->
                    </div><!-- /.container-fluid -->
                </div>
                <!-- /.content-header -->

                <div class="modal fade" data-backdrop="static" data-keyboard="false" id="modal-lg">
                    <input type="hidden" id="idEquipo" value="0">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 id="titulo" class="modal-title">Formulario De Registro</h4>
                                <button onclick="cancelarPeticion();" type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <form class="form" id="frmEquipo">
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4">
                                            <div class="form-group">
                                                <label style="font-family: sans-serif">Código Patrimonio</label>
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fas fa-folder"></i></span>
                                                    </div>
                                                    <input type="text" name="codigoPatrimonio" id="codigoPatrimonio" maxlength="15" class="form-control" placeholder="740892000336">
                                                </div>
                                            </div>                                            
                                        </div>                                  
                                        <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4">
                                            <div class="form-group">
                                                <label style="font-family: sans-serif">Orden Compra</label>
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fas fa-file"></i></span>
                                                    </div>
                                                    <input type="text" name="ordenCompra" id="ordenCompra" maxlength="15" class="form-control" placeholder="466-2010">
                                                </div>
                                            </div>                                            
                                        </div>
                                        <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4">
                                            <div class="form-group">
                                                <label style="font-family: sans-serif">Serie Número</label>
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fas fa-barcode"></i></span>
                                                    </div>
                                                    <input type="text" name="serieNumero" id="serieNumero" maxlength="15" class="form-control" placeholder="DFXNJN1">
                                                </div>
                                            </div>                                            
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
                                            <div class="form-group">
                                                <label style="font-family: sans-serif">Nombre</label>
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fas fa-folder"></i></span>
                                                    </div>
                                                    <input type="text" name="nombreBien" id="nombreBien" maxlength="15" class="form-control" placeholder="SERVIDOR">
                                                </div>
                                            </div>                                            
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4">
                                            <div class="form-group">
                                                <label style="font-family: sans-serif">Estado</label>
                                                <select id="comboEstado" required="" name="comboEstado" style="width: 100%" class="select2 form-control" data-placeholder="Seleccionar">                                               
                                                    <!-- Cargar desde la base de datos -->
                                                    <option>Cargando . . .</option>
                                                </select>  
                                            </div>                                           
                                        </div>
                                        <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4">
                                            <div class="form-group">
                                                <label style="font-family: sans-serif">Marca</label>
                                                <select id="comboMarca" required="" name="comboMarca" style="width: 100%" class="select2 form-control" data-placeholder="Seleccionar">                                               
                                                    <!-- Cargar desde la base de datos -->
                                                    <option>Cargando . . .</option>
                                                </select>  
                                            </div>                                           
                                        </div>
                                        <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4">
                                            <div class="form-group">
                                                <label style="font-family: sans-serif">Fecha Registro</label>
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fas fa-calendar"></i></span>
                                                    </div>
                                                    <input type="date" name="fechaOC" id="fechaOC" class="form-control">
                                                </div>
                                            </div>                                            
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer clearfix">                               
                                    <button id="btn-save" type="submit" class="btn btn-outline-success float-right">Registrar Equipo <span class="fas fa-save"></span></button>
                                </div>
                            </form>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>

                <!-- Main content -->
                <div class="content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
                                <div class="card card-blue card-outline">
                                    <div class="card-header">
                                        <h3 class="card-title">Registrar Equipo</h3>
                                    </div>
                                    <div class="card-body">
                                        <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modal-lg"><i class="fas fa-file-signature"></i> Nuevo Registro</button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
                                <div class="card card-yellow card-outline">
                                    <div class="card-header">
                                        <h3 class="card-title">Listado De Equipos</h3>
                                    </div>
                                    <!-- /.card-header -->
                                    <div class="card-body">
                                        <table id="tablaEquipos" class="table table-responsive-lg table-bordered table-hover">
                                            <thead>
                                                <tr class="text-center">
                                                    <th>Id</th>
                                                    <th>Codigo Patrimonio</th>                                                
                                                    <th>Orden Compra</th>                                                
                                                    <th>Serie</th>
                                                    <th>Tipo</th>
                                                    <th>Marca</th>
                                                    <th>Estado</th>
                                                    <th>Acciones</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                            <tfoot>
                                                <tr class="text-center">
                                                    <th>Id</th>
                                                    <th>Codigo Patrimonio</th>                                                
                                                    <th>Orden Compra</th>                                                
                                                    <th>Serie</th>
                                                    <th>Tipo</th>
                                                    <th>Marca</th>
                                                    <th>Estado</th>
                                                    <th>Acciones</th>
                                                </tr>
                                            </tfoot>
                                        </table>
                                    </div>
                                    <!-- /.card-body -->
                                </div>
                                <!-- /.card -->
                            </div>   
                        </div>
                        <!-- /.row -->
                    </div><!-- /.container-fluid -->
                </div>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->

            <!-- Main Footer -->
            <%@include file="plus/footer.jsp" %>            
            <!-- /.Main Footer -->
        </div>
        <!-- ./wrapper -->

        <!-- REQUIRED SCRIPTS -->
        <!-- scripts -->
        <%@include file="plus/scripts.jsp" %>
        <script src="../js/scriptEquipos.js" type="text/javascript"></script>
        <!-- /.scripts -->
    </body>
</html>
<%
    } else {
        response.sendRedirect("../index.jsp");
    }
%>