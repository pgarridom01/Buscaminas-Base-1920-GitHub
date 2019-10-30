import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import java.util.Collections;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GuardarPuntuacion {

	VentanaPrincipal ventana;
	String nFichero = "puntuaciones.obj";

	public void guardarPuntuacion(VentanaPrincipal ventana) {
		this.ventana = ventana;
		escribirFichero();
		ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
		leerFichero(jugadores);
		Collections.sort(jugadores);
		String nombre;
		if (jugadores.get(0) != null) {
			nombre = jugadores.get(0).getNombre();
		} else {
			nombre = "";
		}
		ventana.panelImagen.removeAll();
		int puntuacion = jugadores.get(0).getPuntuacion();
		JTextField texto = new JTextField("Puntuacion max: " + puntuacion + " Usu: " + nombre);
		ventana.panelImagen.add(texto);
	}

	public void escribirFichero() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		File fichero = new File(nFichero);
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

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void leerFichero(ArrayList<Jugador> jugadores) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		File fichero = new File(nFichero);
		try {
			fis = new FileInputStream(fichero);
			ois = new ObjectInputStream(fis);
			Jugador aux = (Jugador) ois.readObject();
			while (aux != null) {
				jugadores.add(aux);
				aux = (Jugador) ois.readObject();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Jugador obtenerDatos() {
		LocalDate fecha = LocalDate.now();
		LocalTime hora = LocalTime.now();
		int puntuacion = Integer.parseInt(ventana.pantallaPuntuacion.getText());

		String nombre = JOptionPane.showInputDialog("Escriba su nombre para guardar la puntuacion");
		if (nombre == null) {
			nombre = "";
		}
		Jugador jugador = new Jugador(nombre, puntuacion, fecha, hora);

		return jugador;
	}

}
