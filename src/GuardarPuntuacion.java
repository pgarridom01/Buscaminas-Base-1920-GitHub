import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;

public class GuardarPuntuacion {

	VentanaPrincipal ventana;

	public void guardarPuntuacion(VentanaPrincipal ventana) {
		this.ventana = ventana;
		
		escribirFichero();
	}
	
	
	
	public void escribirFichero() {
		FileWriter fw = null;
		PrintWriter pw = null;
		File fichero = new File("Puntuaciones.txt");
		try {
			fw = new FileWriter(fichero, true);
			pw = new PrintWriter(fw);
			String cadena = obtenerDatos();
			pw.println(cadena);
			pw.close();
			fw.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public String obtenerDatos() {
		LocalDateTime fechaHora = LocalDateTime.now();
		String cadena;
		String puntuacion = ventana.pantallaPuntuacion.getText();

		String nombre = JOptionPane.showInputDialog("Escriba su nombre para guardar la puntuacion");
		if (!(nombre==null)) {
			cadena = nombre + ": Puntuacion: " + puntuacion + " Fecha: " + fechaHora.getDayOfMonth() + "/"
					+ fechaHora.getMonth() + "/" + fechaHora.getYear() + " Hora: " + fechaHora.getHour() + ":"
					+ fechaHora.getMinute() + ":" + fechaHora.getSecond();
		}else {
			cadena="El jugador no quiso guardar su puntuacion";
		}
		
		return cadena;
	}

}
