<!-- Main Sidebar Container -->
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="#" class="brand-link">
        <img src="../dist/img/AdminLTELogo.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3"
             style="opacity: .8">
        <span class="brand-text font-weight-light">Websiscob</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar user panel (optional) -->
        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
            <div class="image">
                <img src="../dist/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">
            </div>
            <div class="info">
                <a href="#" class="d-block">¡Hola!, ${usuario.persona.nombres} </a>
            </div>
        </div>

        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                <!-- Add icons to the links using the .nav-icon class
                     with font-awesome or any other icon font library -->
                <li id="li_grupo_inicio" class="nav-item has-treeview menu-close">
                    <a id="a_pagina_prinicipal" href="#" class="nav-link">
                        <i class="nav-icon fas fa-home"></i>
                        <p>
                            Pagina Principal
                            <i class="right fas fa-angle-left"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li id="li_inicio" class="nav-item">
                            <a href="../vista/inicio.jsp" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Inicio</p>
                            </a>
                        </li>
                    </ul>
                </li>
                <li id="li_grupo_registros" class="nav-item has-treeview menu-close">
                    <a id="a_registros" href="#" class="nav-link">
                        <i class="nav-icon fas fa-clipboard"></i>
                        <p>
                            Registros
                            <i class="right fas fa-angle-left"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li id="li_equipos" class="nav-item">
                            <a href="../vista/equipo.jsp" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Equipos</p>
                            </a>
                        </li>                               
                        <li id="li_fichas" class="nav-item">
                            <a href="../vista/ficha.jsp" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Fichas Técnicas</p>
                            </a>
                        </li>
                        <li id="li_usuarios" class="nav-item">
                            <a href="../vista/usuario.jsp" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Usuarios</p>
                            </a>
                        </li>
                        <li id="li_personas" class="nav-item">
                            <a href="../vista/persona.jsp" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Personas</p>
                            </a>
                        </li>
                    </ul>
                                          
                        </li>
                    </ul>
                </li>
         
        </nav>
        <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
</aside>