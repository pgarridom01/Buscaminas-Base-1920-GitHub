import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import java.util.Collections;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * 
 * @author pgarridom01
 *
 */
public class GuardarPuntuacion {

	VentanaPrincipal ventana;
	ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

	/**
	 * Metodo donde hago las operaciones necesarias para guardar las puntuaciones
	 * 
	 * @param ventana Ventana desde la que juego
	 * @param nivel   Nivel seleccionado en el juego
	 */
	public void guardarPuntuacion(VentanaPrincipal ventana, String nivel) {
		this.ventana = ventana;
		// Escribo en el fichero los datos de la partida
		escribirFichero(nivel);
		colocarPuntuacion(nivel, ventana);
		// Creo un fichero de texto para poder ver todos las veces que se ha jugado
		escribirFicheroDeTexto(jugadores, nivel);
	}

	public void colocarPuntuacion(String nivel, VentanaPrincipal ventana) {
		// Leo el fichero y voy guardando los jugadores en la lista
		leerFichero(jugadores, nivel);
		// Ordeno los jugadores de mayor a menos puntuacion
		Collections.sort(jugadores);
		// Obtengo el nombre de y la puntuacion del mejor jugador, que estara en la
		// primera posicion de la lista
		String nombre = null;
		if (jugadores.get(0) != null) {
			nombre = jugadores.get(0).getNombre();
		}
		int puntuacion = jugadores.get(0).getPuntuacion();
		// Quito lo que hay en el panel Imagen
		ventana.panelImagen.removeAll();
		// Me creo un JTextField con la puntuacion maxima y el nombre de quien la obtuvo
		JTextField punt = new JTextField("Punt:"+puntuacion);
		JTextField nom = new JTextField("Usuario: "+nombre);
		// Añado al panel Imagen el JTextField para mostrar la mayor puntuacion
		ventana.panelImagen.add(new JTextField("Puntuacion Maxima"));
		ventana.panelImagen.add(nom);
		ventana.panelImagen.add(punt);
		
	}

	/**
	 * Metodo donde creo ficheros de texto dependiendo del nivel, para despues poder
	 * ver los datos de cada partida
	 * 
	 * @param jugadores ArrayList de jugadores
	 * @param nivel     Nivel en el que he se ha jugado la partida
	 */
	public void escribirFicheroDeTexto(ArrayList<Jugador> jugadores, String nivel) {
		FileWriter fw = null;
		PrintWriter pw = null;
		File fichero = null;
		String nFichero = "Ficheros de texto//Puntuaciones-Nivel" + nivel + ".txt";
		// Dependiendo del nivel creo un fichero de texto para cada uno
		switch (nivel) {
		case "Facil":
			fichero = new File(nFichero);
			break;
		case "Medio":
			fichero = new File(nFichero);
			break;
		case "Dificil":
			fichero = new File(nFichero);
			break;
		}
		try {
			fw = new FileWriter(fichero);
			pw = new PrintWriter(fw);
			for (int i = 0; i < jugadores.size(); i++) {
				pw.println(jugadores.get(i).toString());
			}
			pw.close();
			fw.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * Metodo donde guardo en un fichero de objetos los jugadores
	 * 
	 * @param nivel Nivel en el que he se ha jugado la partida
	 */
	public void escribirFichero(String nivel) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		File fichero = null;
		String nFichero = "Ficheros de objetos//Puntuaciones-Nivel" + nivel + ".obj";
		// Dependiendo del nivel creo el fichero
		switch (nivel) {
		case "Facil":
			fichero = new File(nFichero);
			break;
		case "Medio":
			fichero = new File(nFichero);
			break;
		case "Dificil":
			fichero = new File(nFichero);
			break;
		}
		try {
			if (fichero.exists()) {
				fos = new FileOutputStream(fichero, true);
				oos = new MiObjectOutputStream(fos);
			} else {
				fos = new FileOutputStream(fichero);
				oos = new ObjectOutputStream(fos);
			}

			Jugador j = obtenerDatos();
			oos.writeObject(j);

			oos.close();
			fos.close();

		} catch (IOException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Metodo para leer el archivo de objetos con los jugadores que han jugado
	 * anteriormente y añadirlos a la lista
	 * 
	 * @param jugadores ArrayList de jugadores
	 * @param nivel     Nivel en el que he se ha jugado la partida
	 */
	public void leerFichero(ArrayList<Jugador> jugadores, String nivel) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		File fichero = null;
		String nFichero = "Ficheros de objetos//Puntuaciones-Nivel" + nivel + ".obj";
		// Dependiendo del nivel, leo el fichero correspondiente para cada nivel
		switch (nivel) {
		case "Facil":
			fichero = new File(nFichero);
			break;
		case "Medio":
			fichero = new File(nFichero);
			break;
		case "Dificil":
			fichero = new File(nFichero);
			break;
		}
		try {
			fis = new FileInputStream(fichero);
			ois = new ObjectInputStream(fis);
			Jugador aux = (Jugador) ois.readObject();
			while (aux != null) {
				jugadores.add(aux);
				aux = (Jugador) ois.readObject();
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO: handle exception
			System.out.println("Fin de lectura de fichero");
		}
	}

	/**
	 * Metodo para obtener los datos del jugador de cada partida
	 * 
	 * @return jugador que ha realizado la partida
	 */
	public Jugador obtenerDatos() {
		// Obtengo fecha y hora local cuando termina el juego
		LocalDate fecha = LocalDate.now();
		LocalTime hora = LocalTime.now();
		// Obtengo la puntuacion de la partida
		int puntuacion = Integer.parseInt(ventana.pantallaPuntuacion.getText());
		// Pido el nombre al jugador
		String nombre = JOptionPane.showInputDialog("Escriba su nombre para guardar la puntuacion");
		// En caso de que no inserte el nombre, guardo los datos de la partida sin
		// nombre
		if (nombre == null) {
			nombre = "Sin nombre";
		}
		// Creo el jugador con los datos obtenidos anteriormente
		Jugador jugador = new Jugador(nombre, puntuacion, fecha, hora);

		return jugador;
	}

}
