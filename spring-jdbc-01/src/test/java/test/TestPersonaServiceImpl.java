package test;

import static org.junit.Assert.*;
import java.util.List;
import mx.com.gm.jdbc.Persona;
import mx.com.gm.jdbc.PersonaDao;
import mx.com.gm.servicio.PersonaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:datasource-test.xml", "classpath:applicationContext.xml" })
public class TestPersonaServiceImpl {
	private static Log logger = LogFactory.getLog("TestPersonasServiceImpl");

	@Autowired
	private PersonaService personaService;

	@Autowired
	private PersonaDao personaDao;

	@Test
	@Transactional
	public void deberiaMostrarPersonas() {
		try {
			System.out.println();
			logger.info("Inicio del test deberiaMostrarPersonas");
			int contadorPersonas = this.desplegarPersonas(); // Segun el numero
																// de personas
																// recuperadas,
			// deberia ser el mismo de la tabla
			assertEquals(contadorPersonas, personaDao.contadorPersonas());
			logger.info("Fin del test deberiaMostrarPersonas");
			System.out.println();
		} catch (Exception e) {
			logger.error("Error Servicio", e);
		}
	}

	private int desplegarPersonas() {
		List<Persona> personas = personaService.listarPersonas();
		int contadorPersonas = 0;
		for (Persona persona : personas) {
			logger.info("Persona: " + persona);
			contadorPersonas++;
		}
		return contadorPersonas;
	}

	@Test 
	@Transactional 
	public void testOperaciones() { 
		try { 
			System.out.println(); 
			logger.info("Inicio del test testOperaciones"); 
			Persona persona1 = new Persona(); 
			//persona1.setNombre("Andrea1111111111111111111111111111111111111111111111111111"); 
			persona1.setNombre("Andrea"); 
			persona1.setApePaterno("Lara"); 
			persona1.setEmail("andrea.lara@mimail.com"); 
			personaService.agregarPersona(persona1); 
			//Deberia haber 4 personas 
			assertEquals(4, personaDao.contadorPersonas());
			//Actualizamos la persona con id=1 
			Persona persona2 = personaService.recuperarPersona(new Persona(1)); 
			persona2.setNombre("Administrador"); 
			personaService.modificarPersona(persona2); 
			this.desplegarPersonas(); 
			logger.info("Fin del test testOperaciones"); 
			System.out.println(); } catch (Exception e) {
				logger.error("Error Servicio", e);  
		}
		
	}

	@Test 
	public void testCompruebaOperaciones() { 
		try { System.out.println(); 
		logger.info("Fin del test testCompruebaOperaciones");
		//Deberia haber 3 personas 
		//El caso anterior aplic� rollBack por 
		//el uso de AbstractTransactionalJUnit4SpringContextTests
		assertEquals(3, personaDao.contadorPersonas()); 
		this.desplegarPersonas(); 
		logger.info("Fin del test testCompruebaOperaciones");
		System.out.println(); 
		} catch (Exception e) { 
			logger.error("Error Servicio", e);
			} 
		
	}
		
}
