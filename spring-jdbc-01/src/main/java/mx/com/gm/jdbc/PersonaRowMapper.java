package mx.com.gm.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PersonaRowMapper implements RowMapper<Persona>{

	/**
	 * M�todo que es llamado por la plantilla de Spring
	 * este es un m�todo callback
	 */
	@Override
	public Persona mapRow(ResultSet rs, int rowNum) throws SQLException {
		//Creacion del objeto persona por cada 
		//registro encontrado en el resultSet
		Persona persona = new Persona();
		persona.setIdPersona(rs.getLong("id_persona"));
		persona.setNombre(rs.getString("nombre"));
		persona.setApePaterno(rs.getString("ape_paterno"));
		persona.setApeMaterno(rs.getString("ape_materno"));
		persona.setEmail(rs.getString("email"));
		return persona;
	}

}
