//Declaramos nuestras variables globlales
var tabla = $("table#tablaPersonas"),
        frmPersonas = $("#frmPersonas"),
        mdlPersonas = $("#modal-lg"),
        txtNombrePersona = $("#nombrePersona"),
        txtApellidoPersona = $("#apellidoPersona"),
        cboRol = $("#cboRol"),
        txtIdPersona = $("#idPersona");
$(document).ready(function () {
    let li_grupo_registros = $('#li_grupo_registros');//id de nuestra etiqueta </li>
    li_grupo_registros.attr('class', 'nav-item has-treeview menu-open');//Hacemos que el menu se despliegue.
    let a_registros = $('#a_registros');
    a_registros.attr('class', 'nav-link active');
    let a = $('#li_personas').find('a');//id de nuestra etiqueta </li>, 
    a.attr('class', 'nav-link active');//Aplicamos la clase 'active' a la etiquea a
    let tituloPag = $('#tituloPag');
    tituloPag.html('Registros | Personas');
    $('.select2').select2();
    //Desactivar Usuarios
    tabla.on("click", ".btn-danger", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        eliminarPersonas(id);
    });
    //Leer Usuarios
    tabla.on("click", ".btn-warning", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        leerPersonas(id);
        mdlPersonas.modal({backdrop: 'static', keyboard: false});
    });

    //Atributos de html
    var requisitos = {
        required: true
    };

    $('#frmPersonas').validate({
        submitHandler: function () {
            var json = {
                idPersona: txtIdPersona.val(),
                nombres: txtNombrePersona.val(),
                apellidos: txtApellidoPersona.val(),
                rol: {idRol: parseInt(cboRol.val())}
            };

            $.ajax({
                url: "../srvPersona?accion=" + (parseInt(json.idPersona) === 0 ? 'registrar' : 'editar'),
                type: 'POST',
                dataType: 'json',
                data: {persona: JSON.stringify(json)},
                success: function (data) {
                    if (data.rpt) {
                        swal("Mensaje del Sistema", data.msj, "success");
                        listarPersonas();
                        frmPersonas[0].reset();
                        mdlPersonas.modal("hide");
                        txtIdPersona.val(0);
                        $('#btn-save').html('<i class="fas fa-save"></i> Registrar Persona');
                        $('#titulo').html('Formulario de Registro');
                    } else {
                        swal("Error", data.msj, "error");
                    }
                }
            });
        },
        rules: {
            nombres: requisitos,
            apellidos: requisitos,
            rol: requisitos
        },
        messages: {
            nombres: {
                required: "Por favor, ingrese sus nombres"
            },
            apellidos: {
                required: "Por favor, ingrese sus apellidos"
            },
            rol: {
                required: "Por favor, ingrese seleccione el rol"
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
    listarPersonas();
    listarRoles();
});

function listarRoles() {
    $.ajax({
        url: "../srvPersona?accion=listarRoles",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var comboRoles = '';
            for (var i = 0; i < data.length; i++) {
                comboRoles += '<option value="' + data[i].idRol + '">' + data[i].rol + '</option>';
            }
            cboRol.html(comboRoles);
        }
    });
}

function listarPersonas() {
    $.ajax({
        url: "../srvPersona?accion=listar",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var tpl = "";
            for (var i = 0; i < data.length; i++) {
                tpl += "<tr class=\"text-center\">"
                        + "<td>" + data[i].idPersona + "</td>"
                        + "<td>" + data[i].nombres + "</td>"
                        + "<td>" + data[i].apellidos + "</td>"
                        + "<td>" + data[i].rol.rol + "</td>"
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

function eliminarPersonas(idTemp) {
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
                        url: "../srvPersona?accion=eliminar",
                        type: 'POST',
                        dataType: 'json',
                        data: {id: idTemp},
                        success: function (data) {
                            swal("Eliminado!", data.msj, "success");
                            listarPersonas();
                        }
                    });
                } else {
                    swal("Cancelado", "Petición cancelada!", "error");
                }
            });
}

function leerPersonas(idTemp) {
    $.ajax({
        url: "../srvPersona?accion=leer",
        type: 'POST',
        dataType: 'json',
        data: {id: idTemp},
        success: function (data) {
            txtNombrePersona.val(data.nombres);
            txtApellidoPersona.val(data.apellidos);
            cboRol.val(data.rol.idRol);
            txtIdPersona.val(data.idPersona);
        }
    });
    $('#btn-save').html('<i class="fas fa-sync-alt"></i> Actualizar Persona');
    $('#titulo').html('Formulario de Actualización');
}

function cancelarPeticion() {
    frmPersonas[0].reset();
    txtIdPersona.val(0);
    $('#btn-save').html('<i class="fas fa-save"></i> Registrar Persona');
    $('#titulo').html('Formulario de Registro');
    txtNombrePersona.removeClass('is-invalid');//Remueve la clase is-invalid
    txtApellidoPersona.removeClass('is-invalid');//Remueve la clase is-invalid
    cboRol.removeClass('is-invalid');//Remueve la clase is-invalid
    $('#nombres-error').remove();//remueve la etiqueta span.
    $('#apellidos-error').remove();//remueve la etiqueta span.
    $('#rol-error').remove();//remueve la etiqueta span.

}