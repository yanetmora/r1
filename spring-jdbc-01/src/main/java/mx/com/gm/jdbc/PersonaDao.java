package mx.com.gm.jdbc;

import java.util.List;

public interface PersonaDao {

	void insertPersona(Persona persona);
	
    void updatePersona(Persona persona);
    
    void deletePersona(Persona persona);

    Persona findPersonaById(int idPersona);

    List<Persona> findAllPersonas();
    
    int contadorPersonasPorNombre(Persona persona);

	int contadorPersonas();
	
	String getEmailByPersonaId(Persona persona);
	
	Persona getPersonaByEmail(Persona persona);
}

