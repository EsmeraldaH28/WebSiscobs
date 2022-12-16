//Declaramos nuestras variables globlales
var tabla = $("table#tablaUsuarios"),
        frmUsuario = $("#frmUsuarios"),
        mdlUsuario = $("#modal-lg"),
        txtNombreUsuario = $("#nombreUsuario"),
        txtClave = $("#clave"),
        cboPersonas = $("#cboPersonas"),
        chkEstado = $("#chkEstadoUsuario"),
        txtIdUsu = $("#idUsu");
$(document).ready(function () {
    let li_grupo_registros = $('#li_grupo_registros');//id de nuestra etiqueta </li>
    li_grupo_registros.attr('class', 'nav-item has-treeview menu-open');//Hacemos que el menu se despliegue.
    let a_registros = $('#a_registros');
    a_registros.attr('class', 'nav-link active');
    let a = $('#li_usuarios').find('a');//id de nuestra etiqueta </li>, 
    a.attr('class', 'nav-link active');//Aplicamos la clase 'active' a la etiquea a
    let tituloPag = $('#tituloPag');
    tituloPag.html('Registros | Usuarios');
    $("input:checkbox").prop('checked', false);
    $('.select2').select2();
    //Desactivar Usuarios
    tabla.on("click", ".btn-danger", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        eliminarUsuarios(id);
    });
    //Leer Usuarios
    tabla.on("click", ".btn-warning", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        leerUsuarios(id);
        mdlUsuario.modal({backdrop: 'static', keyboard: false});
    });

    //Atributos de html
    var requisitos = {
        required: true
    };

    $('#frmUsuarios').validate({
        submitHandler: function () {
            var json = {
                idUsuario: txtIdUsu.val(),
                usuario: txtNombreUsuario.val(),
                clave: txtClave.val(),
                persona: {idPersona: parseInt(cboPersonas.val())},
                estado: (chkEstado.is(":checked"))
            };

            $.ajax({
                url: "../session?accion=" + (parseInt(json.idUsuario) === 0 ? 'registrar' : 'editar'),
                type: 'POST',
                dataType: 'json',
                data: {usu: JSON.stringify(json)},
                success: function (data) {
                    if (data.rpt) {
                        swal("Mensaje del Sistema", data.msj, "success");
                        listarUsuarios();
                        listarPersonasSinLogin();
                        cboPersonas.removeAttr('disabled');
                        frmUsuario[0].reset();
                        mdlUsuario.modal("hide");
                        txtIdUsu.val(0);
                        $('#btn-save').html('<i class="fas fa-save"></i> Registrar Usuario');
                        $('#titulo').html('Formulario de Registro');
                    } else {
                        swal("Error", data.msj, "error");
                    }
                }
            });
        },
        rules: {
            usuario: requisitos,
            clave: requisitos
        },
        messages: {
            usuario: {
                required: "Por favor, ingrese el nombre de usuario"
            },
            clave: {
                required: "Por favor, ingrese la clave"
            }
        },
        errorElement: 'span',
        errorPlacement: function (error, element) {
            error.addClass('invalid-feedback');
            element.closest('.form-group').append(error);
        },
        highlight: function (element, errorClass, validClass) {
            $(element).addClass('is-invalid');
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).removeClass('is-invalid');
        }
    });
    listarUsuarios();
    listarPersonasSinLogin();
});

function listarPersonasSinLogin() {
    $.ajax({
        url: "../session?accion=listarEmpleadosSinLogin",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var comboPersonas = '';
            for (var i = 0; i < data.length; i++) {
                comboPersonas += '<option value="' + data[i].idPersona + '">' + data[i].nombres + ' ' + data[i].apellidos + '</option>';
            }
            cboPersonas.html(comboPersonas);
        }
    });
}

function listarUsuarios() {
    $.ajax({
        url: "../session?accion=listar",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var tpl = "";
            for (var i = 0; i < data.length; i++) {
                tpl += "<tr class=\"text-center\">"
                        + "<td>" + data[i].idUsuario + "</td>"
                        + "<td>" + data[i].usuario + "</td>"
                        + "<td>" + data[i].clave + "</td>"
                        + "<td>" + data[i].persona.nombres + ' ' + data[i].persona.apellidos + "</td>"
                        + "<td>" + (data[i].estado ? '<h5><span class =\"badge badge-success\">Activo</span></h5>' : '<h5><span class =\"badge badge-danger\">Inactivo</span></h5>') + "</td>"
                        + "<td nowrap><button title=\"Editar\" class=\"btn btn-warning\">"
                        + "<span class=\"fas fa-edit\"></span></button> "
                        + "<button title=\"Eliminar\" class=\"btn btn-danger\">"
                        + "<span class=\"fa fa-trash\"></span></button></td>"
                        + "</tr>";
            }
            tabla.find("tbody").html(tpl);
            tabla.dataTable();
        }
    });
}

function eliminarUsuarios(idTemp) {
    swal({
        title: "Esta seguro de eliminar?",
        text: "Una vez eliminado ya no estará disponible!",
        type: "warning",
        showCancelButton: true,
        confirmButtonClass: "btn-danger",
        confirmButtonText: "Si, desactivar!",
        cancelButtonText: "No, cancelar!",
        closeOnConfirm: false,
        closeOnCancel: false
    },
            function (isConfirm) {
                if (isConfirm) {
                    $.ajax({
                        url: "../session?accion=desactivarUsuario",
                        type: 'POST',
                        dataType: 'json',
                        data: {id: idTemp},
                        success: function (data) {
                            swal("Eliminado!", data.msj, "success");
                            listarUsuarios();
                        }
                    });
                } else {
                    swal("Cancelado", "Petición cancelada!", "error");
                }
            });
}

function leerUsuarios(idTemp) {
    $.ajax({
        url: "../session?accion=leer",
        type: 'POST',
        dataType: 'json',
        data: {id: idTemp},
        success: function (data) {
            txtNombreUsuario.val(data.usuario);
            txtClave.val(data.clave);
            let item = '<option value="' + data.persona.idPersona + '" selected>' + (data.persona.nombres + ' ' + data.persona.apellidos) + '</option>';
            $('#cboPersonas').append(item);
            txtIdUsu.val(data.idUsuario);
            chkEstado.prop('checked', data.estado);
            cboPersonas.attr('disabled', '');
        }
    });
    $('#btn-save').html('<i class="fas fa-sync-alt"></i> Actualizar Usuario');
    $('#titulo').html('Formulario de Actualización');
}

function cancelarPeticion() {
    frmUsuario[0].reset();
    txtIdUsu.val(0);
    listarPersonasSinLogin();
    cboPersonas.removeAttr('disabled');
    $('#btn-save').html('<i class="fas fa-save"></i> Registrar Usuario');
    $('#titulo').html('Formulario de Registro');
    txtNombreUsuario.removeClass('is-invalid');//Remueve la clase is-invalid
    txtClave.removeClass('is-invalid');//Remueve la clase is-invalid
    $('#usuario-error').remove();//remueve la etiqueta span.
    $('#clave-error').remove();//remueve la etiqueta span.

}

