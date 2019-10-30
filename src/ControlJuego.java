import javax.swing.JOptionPane;

/**
 * Clase gestora del tablero de juego. Guarda una matriz de enteros representado
 * el tablero. Si hay una mina en una posiciÃ³n guarda el nÃºmero -1 Si no hay
 * una mina, se guarda cuÃ¡ntas minas hay alrededor. Almacena la puntuaciÃ³n de
 * la partida
 * 
 * @author Pablo Garrido Marin
 *
 */
public class ControlJuego {

	private final static int MINA = -1;
	final int MINAS_INICIALES;
	final int LADO_TABLERO;

	private int[][] tablero;
	private int puntuacion;

	String[] niveles = { "Facil", "Medio", "Dificil" };

	public ControlJuego() {
		// Creamos el tablero:
		int opc = JOptionPane.showOptionDialog(null, "Seleccione un nivel", "Nivel", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, niveles, "Facil");
		switch (opc) {
		case 0: // Opcion facil
			LADO_TABLERO = 10;
			MINAS_INICIALES = 20;
			break;
		case 1: // Opcion medio
			LADO_TABLERO = 15;
			MINAS_INICIALES = 45;
			break;
		case 2: // Opcion dificil
			LADO_TABLERO = 20;
			MINAS_INICIALES = 80;
			break;
		default: // Por defecto, si cierra el seleccionador de nivel, se inicia el juego en nivel
					// facil
			LADO_TABLERO = 10;
			MINAS_INICIALES = 20;
			break;
		}
		tablero = new int[LADO_TABLERO][LADO_TABLERO];

		// Inicializamos una nueva partida
		inicializarPartida();
	}

	/**
	 * MÃ©todo para generar un nuevo tablero de partida:
	 * 
	 * @pre: La estructura tablero debe existir.
	 * @post: Al final el tablero se habrÃ¡ inicializado con tantas minas como
	 *        marque la variable MINAS_INICIALES. El resto de posiciones que no son
	 *        minas guardan en el entero cuÃ¡ntas minas hay alrededor de la celda
	 */
	public void inicializarPartida() {

		// TODO: Repartir minas e inicializar puntaciï¿½n. Si hubiese un tablero
		// anterior, lo pongo todo a cero para inicializarlo.
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero.length; j++) {
				tablero[i][j] = 0;
			}
		}

		puntuacion = 0;
		// Creamos una variable boolean para saber si la posicion del tablero esta
		// ocupada
		boolean ocupada = false;
		for (int x = 0; x < MINAS_INICIALES; x++) {
			// Do-while mientras que las posiciones generadas aleatoriamente contengan una
			// mina, salgo del bucle, cuando genero una posicion que no tiene mina
			do {
				// Creo dos aleatorios para dar la fila y columna donde coloco la mina
				int i = (int) (Math.random() * LADO_TABLERO);
				int j = (int) (Math.random() * LADO_TABLERO);
				// Compruebo si la posicion generada aleatoriamente guarda una mina o no, en
				// caso negativo, coloco una mina
				if (tablero[i][j] == -1) {
					ocupada = true;
				} else {
					ocupada = false;
					tablero[i][j] = -1;
				}

			} while (ocupada);

		}

		// Al final del mï¿½todo hay que guardar el nï¿½mero de minas para las casillas
		// que no son mina:
		for (int i = 0; i < LADO_TABLERO; i++) {
			for (int j = 0; j < LADO_TABLERO; j++) {
				if (tablero[i][j] != MINA) {
					tablero[i][j] = calculoMinasAdjuntas(i, j);
				}
			}
		}
	}

	/**
	 * CÃ¡lculo de las minas adjuntas: Para calcular el nÃºmero de minas tenemos que
	 * tener en cuenta que no nos salimos nunca del tablero. Por lo tanto, como
	 * mucho la i y la j valdrÃ¡n LADO_TABLERO-1. Por lo tanto, como poco la i y la
	 * j valdrÃ¡n 0.
	 * 
	 * @param i: posiciÃ³n vertical de la casilla a rellenar
	 * @param j: posiciÃ³n horizontal de la casilla a rellenar
	 * @return : El nÃºmero de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int i, int j) {
		int minasAdjuntas = 0;
		for (int vertical = i - 1; vertical <= i + 1; vertical++) {
			for (int horizontal = j - 1; horizontal <= j + 1; horizontal++) {
				if (!(vertical < 0 || vertical > LADO_TABLERO - 1 || horizontal < 0 || horizontal > LADO_TABLERO - 1)) {
					if (tablero[vertical][horizontal] == MINA) {
						minasAdjuntas++;
					}
				}
			}
		}
		return minasAdjuntas;
	}

	/**
	 * MÃ©todo que nos permite
	 * 
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por
	 *      el ControlJuego. Por lo tanto siempre sumaremos puntos
	 * @param i: posiciÃ³n verticalmente de la casilla a abrir
	 * @param j: posiciÃ³n horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j) {

		if (tablero[i][j] == MINA) {
			return false;
		} else {

			puntuacion++;
			return true;
		}

	}

	/**
	 * MÃ©todo que checkea si se ha terminado el juego porque se han abierto todas
	 * las casillas.
	 * 
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son
	 *         minas.
	 **/
	public boolean esFinJuego() {
		if (puntuacion == LADO_TABLERO * LADO_TABLERO - MINAS_INICIALES) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * MÃ©todo que pinta por pantalla toda la informaciÃ³n del tablero, se utiliza
	 * para depurar
	 */
	public void depurarTablero() {
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("\nPuntuaciÃ³n: " + puntuacion);
	}

	/**
	 * MÃ©todo que se utiliza para obtener las minas que hay alrededor de una celda
	 * 
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta
	 *      calcularlo, sÃ­mplemente consultarlo
	 * @param i : posiciÃ³n vertical de la celda.
	 * @param j : posiciÃ³n horizontal de la cela.
	 * @return Un entero que representa el nÃºmero de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		return tablero[i][j];
	}

	/**
	 * MÃ©todo que devuelve la puntuaciÃ³n actual
	 * 
	 * @return Un entero con la puntuaciÃ³n actual
	 */
	public int getPuntuacion() {
		return puntuacion;
	}

}
