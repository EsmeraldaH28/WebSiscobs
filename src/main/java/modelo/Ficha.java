package modelo;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Esmeralda Hernandez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ficha {
    private int idFicha;
    private String numFicha;
    private Persona persona;
    private Usuario usuario;
    private Date fechaCreacion;
    private boolean estado;
    private List<DetalleFicha> detalleFicha;
}
