<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <link rel="icon" href="img/perfil.png">
        <title>| Login</title>
        <!-- Font Awesome Icons -->
        <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
        <link rel="stylesheet" href="dist/css/adminlte.min.css">
        <!-- Sweel Alert -->
        <link href="plugins/sweetAlert/sweetalert.css" rel="stylesheet" type="text/css"/>
        <!-- Google Font: Source Sans Pro -->
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">
   </head>
    <body class="hold-transition login-page">
        <div class="login-box">
            <div class="login-logo">
                <a href="#"> </a>
            </div>
            <!-- /.login-logo -->
            <div class="card card-primary card-outline">
                <div class="card-body login-card-body">
                    <p class="login-box-msg">Identifíquese para iniciar sesión</p>
                    <div class="text-center">
                        <img src="img/onpe_logo.png" width="200"> 
                    </div>
                    <br>       
                    <form id="frmLogin">
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" maxlength="10" required="" placeholder="Usuario" id="user" style="text-transform: uppercase;">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-user"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <input type="password" maxlength="10" class="form-control" required="" placeholder="Password" id="pass" style="text-transform: uppercase;">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-lock"></span>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-6">
                            </div>
                            <!-- /.col -->
                            <div class="col-6">
                                <button type="submit" class="btn btn-block float-right" style="background-color: #2a3132; color: white">Iniciar Sesión <span class="fas fa-sign-in-alt"></span></button>
                            </div>
                            <!-- /.col -->
                        </div>
                        <div class="social-auth-links text-center mb-3">
                            <a id="contenedor" href="#" class="btn btn-block" style="background-color: #336b87; color: white">
                                <i class="fas fa-bell mr-2"></i> Verificación de credenciales
                            </a>
                        </div>
                    </form>
                    <p class="mb-1">
                        <a href="#modal-lg" data-toggle="modal" data-backdrop="static" data-keyboard="false">Olvidé mi contraseña</a>
                    </p>
                </div>
                <!-- /.login-card-body -->
            </div>
        </div>
        <div class="modal fade" id="modal-lg">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 style="font-family: Roboto, sans-serif;" class="modal-title">Solicitud para cambio de contraseña</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="false"><i style="margin-top: 8px;" class="far fa-times-circle"></i></span>
                        </button>
                    </div>
                    <div class="card-blue card-outline">
                        <div class="card-body login-card-body">
                            <p class="login-box-msg">¿Olvidaste tu contraseña? Aquí puede recuperar fácilmente una nueva contraseña.</p>
                            <form id="frmSolicitarCambioClave">
                                <div class="input-group mb-3">
                                    <input type="text" required="" maxlength="10" id="nombreUser" class="form-control" placeholder="Introduce tu nombre de usuario">
                                    <div class="input-group-append">
                                        <div class="input-group-text">
                                            <span class="fas fa-user"></span>
                                        </div>
                                    </div>                            
                                </div>
                                <div class="row">
                                    <div class="col-12">
                                        <button id="btnChangePass" type="submit" class="btn btn-primary btn-block">Solicitar Nueva Contraseña <i class="fas fa-arrow-right"></i></button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <!-- /.login-card-body -->
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>

        <div class="modal fade" id="modal-changePass">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 style="font-family: Roboto, sans-serif;" class="modal-title">Ingrese sus nuevas credenciales</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="false"><i style="margin-top: 8px;" class="far fa-times-circle"></i></span>
                        </button>
                    </div>
                    <div class="card-orange card-outline">
                        <div class="card-body login-card-body">
                            <input type="hidden" id="nombreUsu">                    
                            <p class="login-box-msg">Hola, <b id="nombreEmpleado">Usuario</b> , estás a solo un paso de su nueva contraseña, recupere su contraseña ahora.</p>
                            <form id="frmChangePassword">
                                <div class="input-group mb-3">
                                    <input id="pass1" maxlength="10" type="password" required="" class="form-control" placeholder="Contraseña">
                                    <div class="input-group-append">
                                        <div class="input-group-text">
                                            <span class="fas fa-lock"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="input-group mb-3">
                                    <input id="pass2" maxlength="10" type="password" required="" class="form-control" placeholder="Confirma tu contraseña">
                                    <div class="input-group-append">
                                        <div class="input-group-text">
                                            <span class="fas fa-lock"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-12">
                                        <button id="btnSaveChange" type="submit" class="btn btn-success btn-block">Cambiar Contraseña <i class="fas fa-check-circle"></i></button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <!-- /.login-card-body -->
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        <script src="plugins/jquery/jquery.min.js"></script>
        <script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="dist/js/adminlte.min.js"></script>
        <script src="plugins/sweetAlert/sweetalert.js" type="text/javascript"></script>
        <script src="js/scriptSession.js"></script>
    </body>
</html>