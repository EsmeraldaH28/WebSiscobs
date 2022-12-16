/* global moment */

var cboTecnicos = $("#cboTecnicos"),
        tablaBuscarEquipos = $("table#tablaBuscarEquipos"),
        tablaEquiposAgregados = $("table#tablaEquiposAgregados"),
        chkEstadoFichaI = $("#chkEstadoFichaI");
$(document).ready(function () {
    let li_grupo_registros = $('#li_grupo_registros'); //id de nuestra etiqueta </li>
    li_grupo_registros.attr('class', 'nav-item has-treeview menu-open'); //Hacemos que el menu se despliegue.
    let a_registros = $('#a_registros');
    a_registros.attr('class', 'nav-link active');
    let a = $('#li_fichas').find('a'); //id de nuestra etiqueta </li>, 
    a.attr('class', 'nav-link active'); //Aplicamos la clase 'active' a la etiquea a
    let tituloPag = $('#tituloPag');
    tituloPag.html('Registros | Fichas Internamiento');
    listarTecnicos();
    listarEquiposObsoletos();
    obtenerCorrelativo();
});
/** Esta función lista las personas cuyo rol son tecnicas */
function listarTecnicos() {
    $.ajax({
        url: "../srvFichas?accion=listarTecnicos",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var comboTecnicos = '';
            for (var i = 0; i < data.length; i++) {
                comboTecnicos += '<option value="' + data[i].idPersona + '">' + data[i].rol.rol + ' ' + data[i].nombres + ' ' + data[i].apellidos + '</option>';
            }
            cboTecnicos.html(comboTecnicos);
        }
    });
}

/**
 * Esta función retorna una lista los equipos obsoletos
 * @returns {List} retorna una lista.
 */
function listarEquiposObsoletos() {
    $.ajax({
        url: "../srvFichas?accion=listarEquiposObsoletos",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            let tpl = "";
            for (var i = 0; i < data.length; i++) {
                tpl += "<tr class=\"text-center\">"
                        + "<td>" + data[i].idEquipo + "</td>"
                        + "<td>" + data[i].nombreBien + "</td>"
                        + "<td>" + data[i].marca.marca + "</td>"
                        + "<td>" + data[i].estado.estado + "</td>"
                        + "<td><a href=\"../srvFichas?accion=agregarEquiposFicha&cod=" + data[i].idEquipo + "\" class=\"btn btn-success\"><i class=\"fa fa-plus\"></i> Agregar</a></td>"
                        + "</tr>";
            }
            tablaBuscarEquipos.find("tbody").html(tpl);
            tablaBuscarEquipos.DataTable({
                "language": {
                    "lengthMenu": "Mostrar _MENU_ registros por página",
                    "zeroRecords": "Ups! No se encontro nada",
                    "info": "Mostrando página _PAGE_ de _PAGES_",
                    "infoEmpty": "No se encontraron registros",
                    "infoFiltered": "(filtrado de _MAX_ total totales)",
                    "search": "Buscar:",
                    "paginate": {
                        "next": "Anterior",
                        "previous": "Siguiente"
                    }
                }
            });
        }
    });
}

function registrar() {
    var json = {
        idFicha: $('#idFicha').val(),
        numFicha: $('#numFicha').val(),
        persona: {idPersona: parseInt(cboTecnicos.val())},
        usuario: {idUsuario: parseInt($('#idUsuario').val())},
        fechaCreacion: $('#fechaRegistroFicha').val(),
        estado: (chkEstadoFichaI.is(":checked"))
    };
    $.ajax({
        url: "../srvFichas?accion=registrar",
        type: 'POST',
        dataType: 'json',
        data: {ficha: JSON.stringify(json)},
        success: function (data) {
            if (data.rpt) {
                swal("Mensaje del Sistema", data.msj, "success");
            } else {
                swal("Error", data.msj, "error");
            }
        }
    });
}

function obtenerCorrelativo() {
    $.ajax({
        url: "../srvFichas?accion=obtenerCorrelativo",
        type: 'POST',
        dataType: 'json',
        success: function (data) {
            if (data) {
                $('#numFicha').val(data.numFicha);
                $('#numFicha').attr('disabled', '');
                const date = moment(data.fecha).format('YYYY-MM-DD');
                console.log(date);
                $('#fechaRegistroFicha').val(date);
                $('#fechaRegistroFicha').attr('disabled', '');
            }
        }
    });
}

