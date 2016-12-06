package mx.com.gm.servicio;

import java.util.List;
import mx.com.gm.jdbc.Persona;

public interface PersonaService {
	public List<Persona> listarPersonas();

	public Persona recuperarPersona(Persona persona);

	public void agregarPersona(Persona persona);

	public void modificarPersona(Persona persona);

	public void eliminarPersona(Persona persona);
}