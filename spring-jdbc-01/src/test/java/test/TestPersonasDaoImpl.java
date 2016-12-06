package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import mx.com.gm.jdbc.Persona;
import mx.com.gm.jdbc.PersonaDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:datasource-test.xml",
		"classpath:applicationContext.xml" })
public class TestPersonasDaoImpl {

	private static Log logger = LogFactory.getLog("TestPersonasDaoImpl");

	@Autowired
	private PersonaDao personaDao;
	
	@Test
	public void deberiaMostrarPersonas() {
		try {
			System.out.println();
			logger.info("Inicio del test deberiaMostrarPersonas");

			List<Persona> personas = personaDao.findAllPersonas();

			int contadorPersonas = 0;
			for (Persona persona : personas) {
				logger.info("Persona: " + persona);
				contadorPersonas++;
			}
			
			//Segun el numero de personas recuperadas, deberia ser el mismo de la tabla
			assertEquals(contadorPersonas, personaDao.contadorPersonas());

			logger.info("Fin del test deberiaMostrarPersonas");
		} catch (Exception e) {
			logger.error("Error JBDC", e);
		}
	}

	@Test
	public void testContarPersonasPorNombre() {

		try {
			System.out.println();
			logger.info("Inicio del test Contar Personas por nombre");

			String nombre="Juan";
			Persona personaEjemplo = new Persona();
			personaEjemplo.setNombre(nombre);

			int noPersonasEncontradas = personaDao.contadorPersonasPorNombre(personaEjemplo);

			logger.info("Numero de personas encontradas por nombre '" + nombre + "': " + noPersonasEncontradas);

			assertEquals(2, noPersonasEncontradas);

			logger.info("Fin del test Contar Personas por nombre");

		} catch (Exception e) {
			logger.error("Error JBDC", e);
		}
	}
	
	@Test
	public void deberiaEncontrarPersonaPorId() {
		try {
			System.out.println();
			logger.info("Inicio del test deberiaEncontrarPersonaPorId");

			int idPersona = 1;
			
			Persona persona = personaDao.findPersonaById(idPersona);

						
			//Segun la persona recuperada, deberia ser la misma que el registro 1
			assertEquals("Admin", persona.getNombre());
			
			//Imprimimos todo el objeto
			logger.info("Persona recuperada (id="+ idPersona + "): " + persona);

			logger.info("Fin del test deberiaEncontrarPersonaPorId");
		} catch (Exception e) {
			logger.error("Error JBDC", e);
		}
	}
	
	@Test
	public void deberiaInsertarPersona() {
		try {
			System.out.println();
			logger.info("Inicio del test deberiaInsertarPersona");

			// El script de datos tiene 3 registros
			assertEquals(3, personaDao.contadorPersonas());

			Persona persona = new Persona();
			persona.setNombre("Carlos");
			persona.setApePaterno("Romero");
			persona.setApeMaterno("Esparza");
			persona.setEmail("carlos.romero@gmail.com");
					
			personaDao.insertPersona(persona);
			
			//Recuperamos a la persona recien insertada por su email
			persona = personaDao.getPersonaByEmail(persona);
			
			logger.info("Persona insertada: " + persona);

			// Deberia haber ya cuatro personas
			assertEquals(4, personaDao.contadorPersonas());

			logger.info("Fin del test deberiaInsertarPersona");
		} catch (Exception e) {
			logger.error("Error JBDC", e);
		}
	}

	@Test
	//@Ignore
	public void deberiaActualizarPersona() {
		try {
			System.out.println();
			logger.info("Inicio del test deberiaActualizarPersona");

			int idPersona = 1;
			
			Persona persona = personaDao.findPersonaById(idPersona);
			
			logger.info("Persona a modificar (id="+ idPersona + "): " + persona);

			//Actualizamos el nombre y apeMaterno
			persona.setNombre("Administrador");
			persona.setApeMaterno("Sistemas");
						
			personaDao.updatePersona(persona);
			
			//Volvemos a leer el usuario
			persona = personaDao.findPersonaById(idPersona);
			
			//Segun la persona recuperada, deberia ser la misma que el registro 1
			assertEquals("Administrador", persona.getNombre());
			
			//Imprimimos todo el objeto
			logger.info("Persona modificada (id="+ idPersona + "): " + persona);

			logger.info("Fin del test deberiaActualizarPersona");
		} catch (Exception e) {
			logger.error("Error JBDC", e);
		}
	}
	
	@Test
	//@Ignore
	public void deberiaEliminarPersona() {
		try {
			System.out.println();
			logger.info("Inicio del test deberiaEliminarPersona");

			//Buscamos eliminar la persona con id = 2
			int idPersona = 2;
			
			Persona persona = personaDao.findPersonaById(idPersona);
			
			logger.info("Persona a eliminar (id="+ idPersona + "): " + persona);

			//Eliminamos la persona recuperada
			personaDao.deletePersona(persona);
			
			persona = personaDao.findPersonaById(idPersona);
			
			//Deberia de regresar nulo al buscar la persona 2
			assertNull(persona);
			
			//Imprimimos todo el objeto
			logger.info("Nuevo listado de personas:");
			
			List<Persona> personas = personaDao.findAllPersonas();

			int contadorPersonas = 0;
			for (Persona persona2 : personas) {
				logger.info("Persona: " + persona2);
				contadorPersonas++;
			}
			
			//Segun el numero de personas recuperadas, deberia ser el mismo de la tabla
			assertEquals(contadorPersonas, personaDao.contadorPersonas());

			logger.info("Fin del test deberiaEliminarPersona");
			System.out.println();
		} catch (Exception e) {
			logger.error("Error JBDC", e);
		}
	}
	
	

}
