import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Creacion de la ventana e implementacion del juego
 * 
 * {@link #inicializar()} {@code 
 * 		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();}
 * 
 * @author Pablo Garrido Marin
 * @version 1.7
 * @since 22/10/2019
 * @see ControlJuego
 *
 */

public class VentanaPrincipal {

	// La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	JPanel panelImagen;
	JPanel panelEmpezar;
	JPanel panelPuntuacion;
	JPanel panelJuego;

	// Todos los botones se meten en un panel independiente.
	// Hacemos esto para que podamos cambiar despu√©s los componentes por otros
	JPanel[][] panelesJuego;
	JButton[][] botonesJuego;
	GuardarPuntuacion guardar = new GuardarPuntuacion();
	// Correspondencia de colores para las minas:
	Color correspondenciaColores[] = { Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED,
			Color.RED, Color.RED, Color.RED };

	JButton botonEmpezar;
	JTextField pantallaPuntuacion;

	// LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;

	// Constructor, marca el tama√±o y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(0, 0, 700, 500);
		// Situo la ventana en el medio de la pantalla
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		juego = new ControlJuego();
	}

	// Inicializa todos los componentes del frame
	public void inicializarComponentes() {

		// Definimos el layout:
		ventana.setLayout(new GridBagLayout());

		// Inicializamos componentes
		panelImagen = new JPanel();
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout(1, 1));
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout(1, 1));
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(juego.LADO_TABLERO, juego.LADO_TABLERO));

		ImageIcon icon = new ImageIcon("Imagenes/emojiFeliz.png");
		botonEmpezar = new JButton(icon);
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);

		// Bordes y colores:
		panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));

		// Colocamos los componentes:
		// AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
		// VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		// AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelPuntuacion, settings);
		// ROJO
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 10;
		settings.gridwidth = 3;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, settings);

		// Paneles
		panelesJuego = new JPanel[juego.LADO_TABLERO][juego.LADO_TABLERO];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}

		// Botones
		botonesJuego = new JButton[juego.LADO_TABLERO][juego.LADO_TABLERO];
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j] = new JButton();
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}

		// Bot√≥nEmpezar:
		panelEmpezar.add(botonEmpezar);
		panelPuntuacion.add(pantallaPuntuacion);

	}

	/**
	 * MÈtodo que inicializa todos los listeners que necesita inicialmente el
	 * programa
	 */
	public void inicializarListeners() {
		botonEmpezar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Si pulso el emoji para empezar una nueva partida reinicio la partida
				reiniciarPartida();
				// quito los iconos de banderas que pueda haber en los botones
				for (int i = 0; i < botonesJuego.length; i++) {
					for (int j = 0; j < botonesJuego.length; j++) {
						botonesJuego[i][j].setIcon(null);
					}
				}
				// actualizo la puntuacion a 0
				actualizarPuntuacion();
				// refresco la pantalla
				refrescarPantalla();
			}
		});
		// Establezo los listener de los demas botones
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego.length; j++) {
				botonesJuego[i][j].addMouseListener(new ActionBoton(i, j, this));
			}
		}
	}

	/**
	 * Pinta en la pantalla el n˙mero de minas que hay alrededor de la celda Saca el
	 * botÛn que haya en la celda determinada y aÒade un JLabel centrado y no
	 * editable con el n˙mero de minas alrededor. Se pinta el color del texto seg˙n
	 * la siguiente correspondecia (consultar la variable correspondeciaColor): - 0
	 * : negro - 1 : cyan - 2 : verde - 3 : naranja - 4 Û m·s : rojo
	 * 
	 * @param i: posiciÛn vertical de la celda.
	 * @param j: posiciÛn horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i, int j) {
		int numero = juego.getMinasAlrededor(i, j);
		// Miro si la casilla que abro a ver si es una mina o no
		if (juego.abrirCasilla(i, j)) { // En caso de que no sea una mina
			JLabel nMina = new JLabel();
			panelesJuego[i][j].removeAll();
			// Establezo n∫ minas, color y centro el texto
			nMina.setText(String.valueOf(juego.getMinasAlrededor(i, j)));
			nMina.setForeground(correspondenciaColores[numero]);
			nMina.setHorizontalAlignment(JLabel.CENTER);
			// AÒado el JLabel al panel del juego
			panelesJuego[i][j].add(nMina);
			actualizarPuntuacion();
			refrescarPantalla();
			mostrarFinJuego(false);
		} else { // Caso en el que la casilla contiene una mina
			botonEmpezar.setIcon(new ImageIcon("Imagenes/emojiMuerto.png"));
			mostrarFinJuego(true);

		}
	}

	/**
	 * Muestra una ventana que indica el fin del juego
	 * 
	 * @param explosion : Un booleano que indica si es final del juego porque ha
	 *                  explotado una mina (true) o bien porque hemos desactivado
	 *                  todas (false)
	 * @post : Todos los botones se desactivan excepto el de volver a iniciar el
	 *       juego.
	 */
	public void mostrarFinJuego(boolean explosion) {
		// Guardo un numero cualquiera para inicializarla
		int op = 10;

		// Si hemos perdido.
		if (explosion) {
			mostrarRestoTablero();
			op = JOptionPane.showConfirmDialog(ventana,
					"Una bomba ha explotado. øQuieres volver a jugar?\nPuntuacion: " + pantallaPuntuacion.getText(),
					"HAS PERDIDO", JOptionPane.YES_NO_OPTION, 0, new ImageIcon("Imagenes/emojiMuerto.png"));
		}
		// Si hemos ganado.
		if (!explosion && juego.esFinJuego()) {
			op = JOptionPane.showConfirmDialog(ventana,
					"øQuieres volver a jugar?\nPuntuacion: " + pantallaPuntuacion.getText(), "HAS GANADO.",
					JOptionPane.YES_NO_OPTION, 0, new ImageIcon("Imagenes/emojiFeliz.png"));

		}
		// Si es si iniciamos de nuevo el juego.
		if (op == 0) {
			reiniciarPartida();
			for (int i = 0; i < botonesJuego.length; i++) {
				for (int j = 0; j < botonesJuego.length; j++) {
					botonesJuego[i][j].setIcon(null);
				}
			}
			guardar.guardarPuntuacion(this, juego.nivel);
			botonEmpezar.setIcon(new ImageIcon("Imagenes/emojiFeliz.png"));
			actualizarPuntuacion();
			refrescarPantalla();
		}
		// Si es no cerramos el juego.
		if (op == 1) {
			guardar.guardarPuntuacion(this, juego.nivel);
			ventana.dispose();
		}
		// Si pulsamos la X entendemos que tampoco quiere seguir jugando
		if (op == -1) {
			guardar.guardarPuntuacion(this, juego.nivel);
			ventana.dispose();
		}

	}

	/**
	 * MÈtodo que muestra la puntuaciÛn por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText(String.valueOf(juego.getPuntuacion()));
	}

	/**
	 * MÈtodo para refrescar la pantalla
	 */
	public void refrescarPantalla() {
		ventana.revalidate();
		ventana.repaint();
	}

	/**
	 * MÈtodo que devuelve el control del juego de una ventana
	 * 
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * MÈtodo para inicializar el programa
	 */
	public void inicializar() {
		// IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS
		// COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
	}

	/**
	 * Metodo para mostrar el resto del tablero cuando se pierde
	 */
	public void mostrarRestoTablero() {
		for (int i = 0; i < juego.LADO_TABLERO; i++) {
			for (int j = 0; j < juego.LADO_TABLERO; j++) {
				if (!juego.abrirCasilla(i, j)) {
					JLabel imagenMina = new JLabel();
					imagenMina.setIcon(new ImageIcon("Imagenes/mina.png"));
					imagenMina.setHorizontalAlignment(JLabel.CENTER);
					panelesJuego[i][j].removeAll();
					panelesJuego[i][j].add(imagenMina);
				} else {
					JLabel nMina = new JLabel();
					panelesJuego[i][j].removeAll();
					nMina.setText(String.valueOf(juego.getMinasAlrededor(i, j)));
					nMina.setForeground(correspondenciaColores[juego.getMinasAlrededor(i, j)]);
					nMina.setHorizontalAlignment(JLabel.CENTER);

					panelesJuego[i][j].add(nMina);
				}
			}
		}
		refrescarPantalla();
	}

	/**
	 * Metodo para borrar lo que habia y empezar el juego de nuevo
	 */
	public void reiniciarPartida() {
		juego.inicializarPartida();
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego.length; j++) {
				panelesJuego[i][j].removeAll();
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}

	}

	/**
	 * Metodo para colocar la bandera en el boton[i][j]
	 * 
	 * @param i Posicion vertical de la celda
	 * @param j Posicion horizontal de la celda
	 */
	public void colocarBandera(int i, int j) {
		botonesJuego[i][j].setIcon(new ImageIcon("Imagenes/bandera.png"));
	}

	/**
	 * Metodo para quitar la bandera del boton[i][j]
	 * 
	 * @param i Posicion vertical de la celda
	 * @param j Posicion horizontal de la celda
	 */
	public void quitarBandera(int i, int j) {
		botonesJuego[i][j].setIcon(null);
	}

	/**
	 * Metodo para comprobar si hay bandera en el boton[i][j]
	 * 
	 * @param i Posicion vertical de la celda
	 * @param j Posicion horizontal de la celda
	 */
	public boolean comprobarBandera(int i, int j) {
		return botonesJuego[i][j].getIcon() == null;
	}

}
