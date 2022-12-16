/* global moment */

//Declaramos nuestras variables globlales
var tabla = $("table#tablaEquipos"),
        frmEquipo = $("#frmEquipo"),
        mdlEquipo = $("#modal-lg"),
        txtCodigoPatrimonio = $("#codigoPatrimonio"),
        txtOrdenCompra = $("#ordenCompra"),
        txtSerieNumero = $("#serieNumero"),
        txtNombreBien = $("#nombreBien"),
        cboEstado = $("#comboEstado"),
        cboMarca = $("#comboMarca"),
        txtFechaOC = $("#fechaOC"),
        txtIdEquipo = $("#idEquipo");
$(document).ready(function () {
    let li_grupo_registros = $('#li_grupo_registros');//id de nuestra etiqueta </li>
    li_grupo_registros.attr('class', 'nav-item has-treeview menu-open');//Hacemos que el menu se despliegue.
    let a_registros = $('#a_registros');
    a_registros.attr('class', 'nav-link active');
    let a = $('#li_equipos').find('a');//id de nuestra etiqueta </li>, 
    a.attr('class', 'nav-link active');//Aplicamos la clase 'active' a la etiquea a
    let tituloPag = $('#tituloPag');
    tituloPag.html('Registros | Equipos');
    //Desactivar Equipo
    tabla.on("click", ".btn-danger", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        eliminarEquipo(id);
    });
    //Leer Equipo
    tabla.on("click", ".btn-warning", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        leerEquipo(id);
        mdlEquipo.modal({backdrop: 'static', keyboard: false});
    });

    //Atributos de html
    var requisitos = {
        required: true
    };

    $('#frmEquipo').validate({
        submitHandler: function () {
            var json = {
                idEquipo: txtIdEquipo.val(),
                codigoPatrimonio: txtCodigoPatrimonio.val(),
                ordenCompra: txtOrdenCompra.val(),
                serieNumero: txtSerieNumero.val(),
                nombreBien: txtNombreBien.val().toUpperCase(),
                marca: {idMarca: parseInt(cboMarca.val())},
                estado: {idEstado: parseInt(cboEstado.val())},
                fechaOc: txtFechaOC.val()
                //fechaOc: moment(txtFechaOC.val(), 'DD-MM-YYYY').format('YYYY-MM-DD')
            };
             const date = moment(txtFechaOC.val).format('ll');
             console.log(date);

            $.ajax({
                url: "../srvEquipos?accion=" + (parseInt(json.idEquipo) === 0 ? 'registrar' : 'editar'),
                type: 'POST',
                dataType: 'json',
                data: {equipo: JSON.stringify(json)},
                success: function (data) {
                    if (data.rpt) {
                        swal("Mensaje del Sistema", data.msj, "success");
                        listarEquipos();
                        frmEquipo[0].reset();
                        mdlEquipo.modal("hide");
                        txtIdEquipo.val(0);
                        $('#btn-save').html('<i class="fas fa-save"></i> Registrar Equipo');
                        $('#titulo').html('Formulario de Registro');
                    } else {
                        swal("Error", data.msj, "error");
                    }
                }
            });
        },
        rules: {
            codigoPatrimonio: requisitos,
            ordenCompra: requisitos,
            serieNumero: requisitos,
            nombreBien: requisitos,
            fechaOc: requisitos
        },
        messages: {
            codigoPatrimonio: {
                required: "Por favor, ingresa el codigo patrimonio"
            },
            ordenCompra: {
                required: "Por favor, ingresa la orden de compra"
            },
            serieNumero: {
                required: "Por favor, ingresa serie numero"
            },
            nombreBien: {
                required: "Por favor, ingresa nombre"
            },
            fechaOc: {
                required: "Por favor, ingresa la fecha"
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
    listarEquipos();
});

function listarEstados() {
    $.ajax({
        url: "../srvEquipos?accion=listarEstados",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var combo_cargo = '';
            for (var i = 0; i < data.length; i++) {
                combo_cargo += '<option value="' + data[i].idEstado + '">' + data[i].estado + '</option>';
            }
            cboEstado.html(combo_cargo);
        }
    });
}
listarEstados();

function listarMarcas() {
    $.ajax({
        url: "../srvEquipos?accion=listarMarcas",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var combo_cargo = '';
            for (var i = 0; i < data.length; i++) {
                combo_cargo += '<option value="' + data[i].idMarca + '">' + data[i].marca + '</option>';
            }
            cboMarca.html(combo_cargo);
        }
    });
}
listarMarcas();

function listarEquipos() {
    $.ajax({
        url: "../srvEquipos?accion=listar",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var tpl = "";
            for (var i = 0; i < data.length; i++) {
                tpl += "<tr class=\"text-center\">"
                        + "<td>" + data[i].idEquipo + "</td>"
                        + "<td>" + data[i].codigoPatrimonio + "</td>"
                        + "<td>" + data[i].ordenCompra + "</td>"
                        + "<td>" + data[i].serieNumero + "</td>"
                        + "<td>" + data[i].nombreBien + "</td>"
                        + "<td>" + data[i].marca.marca + "</td>"
                        + "<td>" + data[i].estado.estado + "</td>"
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

function eliminarEquipo(idTemp) {
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
                        url: "../srvEquipos?accion=eliminar",
                        type: 'POST',
                        dataType: 'json',
                        data: {id: idTemp},
                        success: function (data) {
                            swal("Eliminado!", data.msj, "success");
                            listarEquipos();
                        }
                    });
                } else {
                    swal("Cancelado", "Petición cancelada!", "error");
                }
            });
}

function leerEquipo(idTemp) {
    $.ajax({
        url: "../srvEquipos?accion=leer",
        type: 'POST',
        dataType: 'json',
        data: {id: idTemp},
        success: function (data) {
            txtCodigoPatrimonio.val(data.codigoPatrimonio);
            txtOrdenCompra.val(data.ordenCompra);
            txtSerieNumero.val(data.serieNumero);
            txtNombreBien.val(data.nombreBien);
            //const date = moment(data.fechaOc, 'DD-MM-YYYY').format("YYYY-MM-DD");           
            const date = moment(data.fechaOc).format('YYYY-MM-DD');
            console.log(date);
            txtFechaOC.val(date);
            cboEstado.val(data.estado.idEstado);
            cboMarca.val(data.marca.idMarca);
            txtIdEquipo.val(data.idEquipo);
        }
    });
    $('#btn-save').html('<i class="fas fa-sync-alt"></i> Actualizar Equipo');
    $('#titulo').html('Formulario de Actualización');
}

function cancelarPeticion() {
    frmEquipo[0].reset();
    txtIdEquipo.val(0);
    $('#btn-save').html('<i class="fas fa-save"></i> Registrar Equipo');
    $('#titulo').html('Formulario de Registro');
    txtCodigoPatrimonio.removeClass('is-invalid');//Remueve la clase is-invalid
    $('#codigoPatrimonio-error').remove();//remueve la etiqueta span.

}



