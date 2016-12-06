package mx.com.gm.jdbc;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PersonaDaoImpl implements PersonaDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {

		// No es com�n que se utilicen las 2 plantillas, sin embargo si es posible
		// La diferencia es el manejo de par�metros por indice o por nombre
		this.jdbcTemplate = new JdbcTemplate(dataSource);

		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	// Query con Parametros por nombre
	// Omitimos la PK ya que es autoincrementable
	private static final String SQL_INSERT_PERSONA = "INSERT INTO PERSONA (nombre, ape_paterno, ape_materno, email) values (:nombre, :apePaterno, :apeMaterno, :email)";

	// Query con Parametros por indice
	// private static final String SQL_INSERT_PERSONA =
	// "insert into persona (username, password, fullname, email, update_by_email) values (?, ?, ?, ?, ?)";

	// Parametros por nombre
	private static final String SQL_UPDATE_PERSONA = "UPDATE PERSONA set nombre = :nombre, ape_paterno = :apePaterno, ape_materno = :apeMaterno, email = :email WHERE id_persona = :idPersona";

	private static final String SQL_DELETE_PERSONA = "DELETE FROM PERSONA WHERE id_persona = :idPersona";

	private static final String SQL_SELECT_PERSONA = "SELECT id_persona, nombre, ape_paterno, ape_materno, email FROM PERSONA";

	// Parametros por indice
	private static final String SQL_SELECT_PERSONA_BY_ID = SQL_SELECT_PERSONA + " WHERE id_persona = ?";
	
	@Override
	public Persona findPersonaById(int idPersona) {

		Persona persona = null; 
		
		try{
			//Utilizamos la clase PersonaRowMapper
			persona =  jdbcTemplate.queryForObject(SQL_SELECT_PERSONA_BY_ID, new PersonaRowMapper(), idPersona);
		} catch(EmptyResultDataAccessException e){
			persona = null;
		}
		return persona;

		// Esta es otra forma sin utilizar la clase PersonaRowMapper
		// BeanPropertyRowMapper<Persona> personaRowMapper = BeanPropertyRowMapper.newInstance(Persona.class);
		// return jdbcTemplate.queryForObject(SQL_SELECT_PERSONA_BY_ID, personaRowMapper, idPersona);

	}

	@Override
	public List<Persona> findAllPersonas() {
		String sql = "SELECT * FROM PERSONA";
		RowMapper<Persona> personaRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(Persona.class);
		return this.jdbcTemplate.query(sql, personaRowMapper);
	}

	@Override
	public String getEmailByPersonaId(Persona persona) {
		String sql = "SELECT email FROM PERSONA WHERE id_persona = :idPersona";
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(persona);

		// Unicamente retorna un valor el metodo queryForInt
		return this.namedParameterJdbcTemplate.queryForObject(sql,
				namedParameters, String.class);
	}

	@Override
	public Persona getPersonaByEmail(Persona persona) {
		String sql = "SELECT * FROM PERSONA WHERE email = :email";
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(persona);

		//Si no se tiene el objeto RowMapper, se puede utilizar la siguiente linea para crear este objeto
		//RowMapper<Persona> personaRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(Persona.class);
		return this.namedParameterJdbcTemplate.queryForObject(sql,namedParameters, new PersonaRowMapper());
	}
	
	public int contadorPersonasPorNombre(Persona persona) {

		String sql = "SELECT count(*) FROM PERSONA WHERE nombre = :nombre";

		// Permite evitar crear un MAP de parametros y utilizar directamente el objeto persona
		// los atributos que coincidan con el nombre de los parametros por nombre del query
		// seran utilizados y proporcionados como atributos al query
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(persona);

		// Unicamente retorna un valor el metodo queryForInt
		return this.namedParameterJdbcTemplate.queryForInt(sql, namedParameters);
	}

	@Override
	public int contadorPersonas() {
		String sql = "SELECT count(*) FROM PERSONA";

		return this.jdbcTemplate.queryForInt(sql);

		// Esta es otra opcion si no tuvieramos jdbcTemplate
		// return this.namedParameterJdbcTemplate.getJdbcOperations().queryForInt(sql);

	}

	@Override
	public void insertPersona(Persona persona) {
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(persona);
		this.namedParameterJdbcTemplate.update(SQL_INSERT_PERSONA, parameterSource);
	}

	@Override
	public void updatePersona(Persona persona) {
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(persona);
		this.namedParameterJdbcTemplate.update(SQL_UPDATE_PERSONA, parameterSource);
	}

	@Override
	public void deletePersona(Persona persona) {
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(persona);
		this.namedParameterJdbcTemplate.update(SQL_DELETE_PERSONA, parameterSource);
	}

	

	// Metodo para ejecutar sentencias DLL
	/*
	 * public void doExecute() { //Si tuvieramos la plantilla JdbcTemplate
	 * this.jdbcTemplate
	 * .execute("create table miTabla (id integer, nombre varchar(100))"); }
	 */
	
	/*
	@Override
	public void insertPersonasBatch(List<Persona> personas) {
		String sql = SQL_INSERT_PERSONA;
		List<SqlParameterSource> parameters = new ArrayList<SqlParameterSource>();
		for (Persona persona : personas) {
			parameters.add(new BeanPropertySqlParameterSource(persona));
		}
		this.namedParameterJdbcTemplate.batchUpdate(sql,parameters.toArray(new SqlParameterSource[0]));
	}

	@Override
	public void updatePersonasBatch(List<Persona> personas) {
		String sql = SQL_UPDATE_PERSONA;
		List<SqlParameterSource> parameters = new ArrayList<SqlParameterSource>();
		for (Persona persona : personas) {
			parameters.add(new BeanPropertySqlParameterSource(persona));
		}
		this.namedParameterJdbcTemplate.batchUpdate(sql,parameters.toArray(new SqlParameterSource[0]));
	}
	
	*/

}
