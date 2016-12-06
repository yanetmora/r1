package prueba;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import beans.Traductor;

import org.springframework.beans.factory.BeanFactory;

public class PruebaInterpreteSpring {

	public static void main(String[] args) {
		BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xml");
		Traductor traductor1 = (Traductor) factory.getBean("traductorEspanol");
		traductor1.hablar();
		System.out.println();
		Traductor traductor2 = (Traductor) factory.getBean("traductorIngles");
		traductor2.hablar();

	}

}
