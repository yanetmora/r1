package test;

import servletunit.struts.MockStrutsTestCase;

public class TestPersonaAction extends MockStrutsTestCase {
	public void testListar() {
		System.out.println();
		logger.info("Iniciando test Listar con Struts");
		setRequestPathInfo("/inicio");
		addRequestParameter("metodo", "accionListar");
		actionPerform();
		verifyForward("listar");
		logger.info("Se redireccion� exitosamente");
		verifyNoActionErrors();
		logger.info("T�rmino test Listar con Struts");
		System.out.println();
	}
}