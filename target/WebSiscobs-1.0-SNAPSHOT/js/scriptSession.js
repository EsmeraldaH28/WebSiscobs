$(document).ready(function () {
    var DOM = {
        frmLogin: $("#frmLogin"),
        txtUser: $("input#user"),
        txtClave: $("input#pass"),
        frmSolicitarCambio: $("#frmSolicitarCambioClave"),
        frmChangePassword: $("#frmChangePassword"),
        mdlSolicitarCambio: $("#modal-lg"),
        mdlChangePassword: $("#modal-changePass"),
        txtNombreUser: $("#nombreUser"),
        //Inpust del segundo modal
        clave1: $("#pass1"),
        clave2: $("#pass2"),
        alert: $("a#contenedor")
    };

    DOM.frmLogin.on("submit", function (e) {
        e.preventDefault();
        iniciarSession();
    });
    
    DOM.frmSolicitarCambio.on("submit", function (e) {
        e.preventDefault();
        buscarUsuarioPorCodUser();
    });

    DOM.frmChangePassword.on("submit", function (e) {
        e.preventDefault();
        changePassword();
    });

    function iniciarSession() {
        var obj = {
            usuario: DOM.txtUser.val(),
            clave: DOM.txtClave.val()};
        $.post("session?accion=identificar",
                {datos: JSON.stringify(obj)},
                function (data) {
                    if (data.rpt) {
                        DOM.alert[0].textContent = data.msj;
                        setTimeout(function () {
                            window.location.href = "vista/inicio.jsp";
                        }, 1500);
                    } else {
                        DOM.alert[0].textContent = data.msj;
                    }
                });
    }
    
    function buscarUsuarioPorCodUser() {
        $.ajax({
            url: "session?accion=solicitarCambioC",
            type: 'POST',
            dataType: 'json',
            data: {nombreUsuario: DOM.txtNombreUser.val()},
            success: function (data) {
                if (data.rpta) {
                    swal("Mensaje del Sistema", data.msje, "success");
                    DOM.mdlSolicitarCambio.modal('hide');
                    DOM.mdlChangePassword.modal('show');
                    $('#nombreUsu').val(data.body.usuario);//Obtemenos el NombreUsuario en un input de tipo hidden en el modal de cambiar contraseña
                    $('#nombreEmpleado').html(data.body.nombresCompletos);//Obtenemos el nombre de el empleado
                    DOM.txtNombreUser.val("");
                } else {
                    swal("Error", data.msje, "error");
                }

            }, error: function (x, y) {
                swal("Error", "No se puede hacer la petición", "error");
                console.log(x.responseText);
            }
        });
    }

    function changePassword() {
        var obj = {
            usuario: $('#nombreUsu').val(), //input de tipo hidden
            clave: DOM.clave1.val()};//txtClave1 
        if (DOM.clave1.val() === DOM.clave2.val()) {
            $.ajax({
                type: 'post',
                url: 'session?accion=cambiarPassword',
                data: (obj),
                success: function (data) {
                    if (data.rpt) {
                        swal("Mensaje del Sistema", data.msj, "success");
                        DOM.mdlChangePassword.modal('hide');
                        $('#pass1').val("");
                        $('#pass2').val("");
                    } else {
                        swal("Error", data.msj, "error");
                    }
                }, error: function (x, y) {
                    alertify.error('No se puedo conectar con el servidor');
                }
            });
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Las contraseñas nos coinciden, intente otra vez!'
            });
            $('#pass1').val("");
            $('#pass2').val("");
        }
    }
});

