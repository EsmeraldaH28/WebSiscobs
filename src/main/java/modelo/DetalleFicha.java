package modelo;

import lombok.Data;

/**
 *
 * @author Esmeralda Hernandez
 */
@Data
public class DetalleFicha {
    private int idDetalleFicha;
    private Ficha ficha;
    private Equipo equipo;
}
