import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
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
 * 
 * @author Pablo Garrido Marin
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
	// Hacemos esto para que podamos cambiar después los componentes por otros
	JPanel[][] panelesJuego;
	JButton[][] botonesJuego;

	// Correspondencia de colores para las minas:
	Color correspondenciaColores[] = { Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED,
			Color.RED, Color.RED, Color.RED, Color.RED };

	JButton botonEmpezar;
	JTextField pantallaPuntuacion;

	// LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;

	// Constructor, marca el tamaño y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 100, 700, 500);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		juego = new ControlJuego();
		juego.depurarTablero();
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
		panelJuego.setLayout(new GridLayout(10, 10));
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
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
		// VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		// AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
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
		panelesJuego = new JPanel[10][10];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}

		// Botones
		botonesJuego = new JButton[10][10];
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j] = new JButton("-");
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}

		// BotónEmpezar:
		panelEmpezar.add(botonEmpezar);
		panelPuntuacion.add(pantallaPuntuacion);

	}

	/**
	 * Método que inicializa todos los lísteners que necesita inicialmente el
	 * programa
	 */
	public void inicializarListeners() {
		botonEmpezar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego.length; j++) {
				botonesJuego[i][j].addMouseListener(new ActionBoton(i, j, this));
			}
		}
	}

	/**
	 * Pinta en la pantalla el número de minas que hay alrededor de la celda Saca
	 * el botón que haya en la celda determinada y añade un JLabel centrado y no
	 * editable con el número de minas alrededor. Se pinta el color del texto
	 * según la siguiente correspondecia (consultar la variable
	 * correspondeciaColor): - 0 : negro - 1 : cyan - 2 : verde - 3 : naranja - 4 ó
	 * más : rojo
	 * 
	 * @param i: posición vertical de la celda.
	 * @param j: posición horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i, int j) {
		int numero = juego.getMinasAlrededor(i, j);
		if (juego.abrirCasilla(i, j)) {
			JLabel nMina = new JLabel();
			panelesJuego[i][j].removeAll();
			// Opciones para el jlabel (Texto, color y alineacion).
			nMina.setText(String.valueOf(juego.getMinasAlrededor(i, j)));
			nMina.setForeground(correspondenciaColores[numero]);
			nMina.setHorizontalAlignment(JLabel.CENTER);

			panelesJuego[i][j].add(nMina);
			actualizarPuntuacion();
			refrescarPantalla();
			mostrarFinJuego(false);
		} else {
			botonEmpezar.setIcon(new ImageIcon("Imagenes/emojiMuerto.png"));
			mostrarFinJuego(true);

		}
	}

	/**
	 * Muestra una ventana que indica el fin del juego
	 * 
	 * @param explosion : Un booleano que indica si es final del juego porque ha
	 *                     explotado una mina (true) o bien porque hemos desactivado
	 *                     todas (false)
	 * @post : Todos los botones se desactivan excepto el de volver a iniciar el
	 *       juego.
	 */
	public void mostrarFinJuego(boolean explosion) {
		int op = -1;
		// Si hemos perdido.
		if (explosion) {
			mostrarRestoTablero();
			op = JOptionPane.showConfirmDialog(null, "Una bomba ha explotado. �Quieres volver a jugar?", "HAS PERDIDO",
					JOptionPane.YES_NO_OPTION, 0);
		}
		// Si hemos ganado.
		if (!explosion && juego.esFinJuego()) {
			op = JOptionPane.showConfirmDialog(null, "�Quieres volver a jugar?", "HAS GANADO.",
					JOptionPane.YES_NO_OPTION, 0);
		}
		// Si es si iniciamos de nuevo el juego.
		if (op == 0) {
			juego.inicializarPartida();
			refrescarPantalla();
		}
		// Si es no cerramos el juego.
		if (op == 1) {
			ventana.dispose();
		}
	}

	/**
	 * Método que muestra la puntuación por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText(String.valueOf(juego.getPuntuacion()));
	}

	/**
	 * Método para refrescar la pantalla
	 */
	public void refrescarPantalla() {
		ventana.revalidate();
		ventana.repaint();
	}

	/**
	 * Método que devuelve el control del juego de una ventana
	 * 
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * Método para inicializar el programa
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
}
