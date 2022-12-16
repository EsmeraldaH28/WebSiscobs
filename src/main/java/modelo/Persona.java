package modelo;

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
public class Persona {
    private int idPersona;
    private String nombres;
    private String apellidos;
    private Rol rol;
}
