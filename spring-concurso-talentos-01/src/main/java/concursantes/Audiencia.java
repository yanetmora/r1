package concursantes;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Audiencia {
	
	@Pointcut("execution(* concursantes.Concursante.ejecutar(..))")
	public void ejecutarShow() {
	}

	@Around("ejecutarShow()")
	public void monitorearShow(ProceedingJoinPoint joinpoint) {
		try {
			System.out.println("El show est� por comenzar, favor de tomar asiento...");
			System.out.println("Favor de apagar celulares...");
			// Anotamos la hora de inicio
			long horaInicio = System.currentTimeMillis();
			// Se llama al m�todo de negocio (m�todo objetivo)
			joinpoint.proceed();
			Thread.sleep(1000); // 1 segundo
			// Este delay en milisegundos es opcional y se puede poner en los
			// m�todos
			// de negocio para simular la duraci�n del m�todo
			long horaTermino = System.currentTimeMillis();
			System.out.println("El show ha termiado, aplausos");
			System.out.println("El show tuvo una duraci�n:" + (horaTermino - horaInicio));
		} catch (Throwable t) {
			System.out.println("El show fue terrible, se devolver�n las entradas");
		}
	}
}