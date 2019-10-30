import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Clase que implementa el listener de los botones del Buscaminas. De alguna
 * manera tendrá que poder acceder a la ventana principal. Se puede lograr
 * pasando en el constructor la referencia a la ventana. Recuerda que desde la
 * ventana, se puede acceder a la variable de tipo ControlJuego
 * 
 * @author Pablo Garrido Marin
 **
 */
public class ActionBoton implements MouseListener {

	int i, j;
	VentanaPrincipal ventana;

	public ActionBoton() {
		// TODO
	}

	/**
	 * 
	 * @param i       posicion vertical del panel
	 * @param j       posicion horizontal del panel
	 * @param ventana ventana del juego
	 */
	public ActionBoton(int i, int j, VentanaPrincipal ventana) {
		this.i = i;
		this.j = j;
		this.ventana = ventana;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// En caso de que pulse el boton izquierdo compuebo si tiene puesta bandera, en
		// caso de que la tenga no hace nada, si no tiene bandera, abre ese boton
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (ventana.comprobarBandera(i, j)) {
				ventana.mostrarNumMinasAlrededor(i, j);
			}
		}

		// Si pulsa el boton derecho, compruebo si hay bandera, si la hay la quito y en
		// caso de que no la haya, la pongo

		if (e.getButton() == MouseEvent.BUTTON3) {
			if (!ventana.comprobarBandera(i, j)) {
				ventana.quitarBandera(i, j);

			} else {
				ventana.colocarBandera(i, j);

			}

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}