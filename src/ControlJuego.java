import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Clase gestora del tablero de juego. Guarda una matriz de enteros representado
 * el tablero. Si hay una mina en una posición guarda el número -1 Si no hay
 * una mina, se guarda cuántas minas hay alrededor. Almacena la puntuación de
 * la partida
 * 
 * @author Pablo Garrido Marin
 *
 */
public class ControlJuego {

	private final static int MINA = -1;
	final int MINAS_INICIALES = 20;
	final int LADO_TABLERO = 10;

	private int[][] tablero;
	private int puntuacion;

	public ControlJuego() {
		// Creamos el tablero:
		tablero = new int[LADO_TABLERO][LADO_TABLERO];

		// Inicializamos una nueva partida
		inicializarPartida();
	}

	/**
	 * Método para generar un nuevo tablero de partida:
	 * 
	 * @pre: La estructura tablero debe existir.
	 * @post: Al final el tablero se habrá inicializado con tantas minas como
	 *        marque la variable MINAS_INICIALES. El resto de posiciones que no son
	 *        minas guardan en el entero cuántas minas hay alrededor de la celda
	 */
	public void inicializarPartida() {

		// TODO: Repartir minas e inicializar puntaci�n. Si hubiese un tablero
		// anterior, lo pongo todo a cero para inicializarlo.
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

		// Al final del m�todo hay que guardar el n�mero de minas para las casillas
		// que no son mina:
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] != MINA) {
					tablero[i][j] = calculoMinasAdjuntas(i, j);
				}
			}
		}
	}

	/**
	 * Cálculo de las minas adjuntas: Para calcular el número de minas tenemos que
	 * tener en cuenta que no nos salimos nunca del tablero. Por lo tanto, como
	 * mucho la i y la j valdrán LADO_TABLERO-1. Por lo tanto, como poco la i y la
	 * j valdrán 0.
	 * 
	 * @param i: posición vertical de la casilla a rellenar
	 * @param j: posición horizontal de la casilla a rellenar
	 * @return : El número de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int i, int j) {
		int minasAdjuntas = 0;
		if (i == 0) {
			if (j == 0) {
				// mirar abajo diagonal hacia abajoderecha, derecha
				for (int k = 0; k <= 1; k++) {
					for (int k2 = 0; k2 <= 1; k2++) {
						if (tablero[k][k2] == MINA) {
							minasAdjuntas++;
						}
					}
				}
			} else if (j == 10) {
				// mirar abajo diagonal hacia abajoizquierda, izquierda
				
			} else {
				// mirar abajo, izquierda, derecha, diagonales hacia abajo izquierda y derecha
				
			}
		}
		if (i == 10) {
			if (j == 0) {
				// mirar arriba diagonal hacia arribaderecha, derecha
				
			} else if (j == 10) {
				// mirar arriba diagonal hacia arribaizquierda, izquierda
				
			} else {
				// mirar arriba, izquierda, derecha, diagonales hacia arriba izquierda y derecha
				
			}
		}

		if (i > 0 || i < 10) {
			if (j == 0) {
				// mirar arriba, diagonal derecha arriba. derecha, diagonal abajo derecha y
				// abajo
				
			} else if (j == 10) {
				// mirar arriba, diagonal izquierda arriba, izquierda, diagonal abajo izquierda,
				// abajo
				
			}
		}

		if ((i > 0 || i < 10) && (j > 0 || j < 10)) {
			// mirar todas las posiciones
			
		}
		return minasAdjuntas;
	}

	/**
	 * Método que nos permite
	 * 
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por
	 *      el ControlJuego. Por lo tanto siempre sumaremos puntos
	 * @param i: posición verticalmente de la casilla a abrir
	 * @param j: posición horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j) {
		return false;

	}

	/**
	 * Método que checkea si se ha terminado el juego porque se han abierto todas
	 * las casillas.
	 * 
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son
	 *         minas.
	 **/
	public boolean esFinJuego() {
		return false;
	}

	/**
	 * Método que pinta por pantalla toda la información del tablero, se utiliza
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
		System.out.println("\nPuntuación: " + puntuacion);
	}

	/**
	 * Método que se utiliza para obtener las minas que hay alrededor de una celda
	 * 
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta
	 *      calcularlo, símplemente consultarlo
	 * @param i : posición vertical de la celda.
	 * @param j : posición horizontal de la cela.
	 * @return Un entero que representa el número de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		return 0;
	}

	/**
	 * Método que devuelve la puntuación actual
	 * 
	 * @return Un entero con la puntuación actual
	 */
	public int getPuntuacion() {
		return 0;
	}

}
