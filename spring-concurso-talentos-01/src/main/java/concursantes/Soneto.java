package concursantes;

import org.springframework.stereotype.Component;

@Component("poema")
public class Soneto implements Poema {
	public void recitar() {
		String sonetoPasionSorJuana = "Este, que ves, enga�o colorido, \n" + "que del arte ostentando los primores,\n"
				+ "con falsos silogismos de colores\n" + "es cauteloso enga�o del sentido; \n"
				+ "�ste, en quien la lisonja ha pretendido\n" + "excusar de los a�os los horrores, \n"
				+ "y venciendo del tiempo los rigores \n" + "triunfar de la vejez y del olvido, \n"
				+ "es un vano artificio del cuidado, \n" + "es una flor al viento delicada, \n"
				+ "es un resguardo in�til para el hado, \n" + "es una necia diligencia errada, \n"
				+ "es un af�n caduco y, bien mirado, \n" + "es cad�ver, es polvo, es sombra, es nada. \n";
		System.out.println("\nSoneto:" + sonetoPasionSorJuana);
	}
}