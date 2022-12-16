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
public class Usuario {
   private int idUsuario;
   private String usuario;
   private String clave;
   private boolean estado;
   private Persona persona; 
   private String nombresCompletos;
}
