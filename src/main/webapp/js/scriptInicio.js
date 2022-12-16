$(document).ready(function () {
    let li_grupo_inicio = $('#li_grupo_inicio');//id de nuestra etiqueta </li>
    li_grupo_inicio.attr('class', 'nav-item has-treeview menu-open');//Hacemos que el menu se despliegue.
    let a_pagina_prinicipal = $('#a_pagina_prinicipal');
    a_pagina_prinicipal.attr('class', 'nav-link active');
    let a = $('#li_inicio').find('a');//id de nuestra etiqueta </li>, 
    a.attr('class', 'nav-link active');//Aplicamos la clase 'active' a la etiquea a
    let tituloPag = $('#tituloPag');
    tituloPag.html('Registros | Inicio');
});


