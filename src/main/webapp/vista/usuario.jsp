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
                                <h1 class="m-0 text-dark">Usuarios</h1>
                            </div><!-- /.col -->
                            <div class="col-sm-6">
                                <ol class="breadcrumb float-sm-right">
                                    <li class="breadcrumb-item"><a href="#">Registros</a></li>
                                    <li class="breadcrumb-item active">Usuarios</li>
                                </ol>
                            </div><!-- /.col -->
                        </div><!-- /.row -->
                    </div><!-- /.container-fluid -->
                </div>
                <!-- /.content-header -->

                <div class="modal fade" data-backdrop="static" data-keyboard="false" id="modal-lg">
                    <input type="hidden" id="idUsu" value="0">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 id="titulo" class="modal-title">Formulario De Registro</h4>
                                <button onclick="cancelarPeticion();" type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <form class="form" id="frmUsuarios">
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                            <div class="form-group">
                                                <label style="font-family: sans-serif">Usuario</label>
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fas fa-user"></i></span>
                                                    </div>
                                                    <input type="text" name="nombreUsuario" maxlength="10" id="nombreUsuario" class="form-control" placeholder="Ingresar Nombre Usuario">
                                                </div>
                                            </div>                                            
                                        </div>
                                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                            <div class="form-group">
                                                <label style="font-family: sans-serif">Clave</label>
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fas fa-lock"></i></span>
                                                    </div>
                                                    <input type="text" name="clave" maxlength="10" id="clave" class="form-control" placeholder="Ingresar Clave">
                                                </div>
                                            </div>                                            
                                        </div>
                                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6"> 
                                            <div class="form-group">
                                                <label id="etiqueta">Seleccionar Persona</label>
                                                <select id="cboPersonas" style="width: 100%" class="select2 form-control" data-placeholder="Seleccionar">                                               
                                                    <!-- Cargar desde la base de datos -->
                                                    <option>Cargando.....</option>
                                                </select>  
                                            </div>
                                        </div>
                                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                            <div class="form-group">
                                                <label style="font-family: sans-serif">Estado</label>
                                                <div class="input-group">                                             
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text">
                                                            <input type="checkbox" checked="" name="chkEstadoUsuario" id="chkEstadoUsuario">
                                                        </span>
                                                    </div>
                                                    <label type="text" class="form-control">Activo / Inactivo</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer clearfix">                               
                                    <button id="btn-save" type="submit" class="btn btn-outline-success float-right">Registrar Usuario <span class="fas fa-save"></span></button>
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
                                <div class="card card-success card-outline">
                                    <div class="card-header">
                                        <h3 class="card-title">Registrar Usuarios</h3>
                                    </div>
                                    <div class="card-body">
                                        <button type="button" class="btn btn-outline-success" data-toggle="modal" data-target="#modal-lg"><i class="fas fa-file-signature"></i> Nuevo Registro</button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
                                <div class="card card-primary card-outline">
                                    <div class="card-header">
                                        <h3 class="card-title">Listado De Usuarios</h3>
                                    </div>
                                    <!-- /.card-header -->
                                    <div class="card-body">
                                        <table id="tablaUsuarios" class="table table-responsive-lg table-bordered table-hover">
                                            <thead>
                                                <tr class="text-center">
                                                    <th>Id</th>
                                                    <th>Usuario</th>                                                
                                                    <th>Clave</th>                                                
                                                    <th>Persona</th>                                                
                                                    <th>Estado</th>                                                
                                                    <th>Acciones</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                            <tfoot>
                                                <tr class="text-center">
                                                    <th>Id</th>
                                                    <th>Usuario</th>
                                                    <th>Clave</th> 
                                                    <th>Persona</th>
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
        <script src="../js/scriptUsuarios.js" type="text/javascript"></script>
        <!-- /.scripts -->
    </body>
</html>
<%
    } else {
        response.sendRedirect("../index.jsp");
    }
%>