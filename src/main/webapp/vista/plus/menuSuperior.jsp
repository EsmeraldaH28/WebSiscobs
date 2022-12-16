<nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
        </li>
        <li class="nav-item d-none d-sm-inline-block">
            <a href="../index.jsp" class="nav-link">Inicio</a>
        </li>
        <li class="nav-item d-none d-sm-inline-block">
            <a href="#" class="nav-link">Contáctanos</a>
        </li>
    </ul>

    <!-- Right navbar links -->
    <ul class="navbar-nav ml-auto">
        <!-- Messages Dropdown Menu -->
        <li class="nav-item dropdown">
            <a class="nav-link btn btn-dark text-white" data-toggle="dropdown" href="#">Salir 
                <i class="fas fa-sign-out-alt"></i>
            </a>
            <div class="dropdown-menu dropdown-menu-xl dropdown-menu-right">
                <a href="#" class="dropdown-item">
                    <!-- Message Start -->
                    <div class="media">
                        <img src="../dist/img/avatar2.png" alt="User Avatar" class="img-size-50 mr-3 img-circle">
                        <div class="media-body">
                            <h3 class="dropdown-item-title">
                                ${usuario.persona.nombres} ${usuario.persona.apellidos}                                           
                            </h3>
                            <p class="text-sm">Su rol es, ${usuario.persona.rol.rol} </p>
                        </div>
                    </div>
                    <!-- Message End -->
                </a>
                <div class="dropdown-divider"></div>
                <a href="../session?accion=cerrar" class="dropdown-item dropdown-footer" style="background-color: #BD362F; color: white">Cerrar Sesión <span class="fas fa-sign-out-alt"></span></a>
            </div>
        </li>
    </ul>
</nav>