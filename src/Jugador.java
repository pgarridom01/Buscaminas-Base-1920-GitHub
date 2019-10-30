import java.time.LocalDate;
import java.time.LocalTime;
/**
 * 
 * @author Pablo Garrido
 *
 */
public class Jugador implements Comparable<Jugador>{

	String nombre;
	int puntuacion;
	LocalDate fecha;
	LocalTime hora;

	public Jugador() {
		// TODO Auto-generated constructor stub
	}

	public Jugador(String nombre, int puntuacion, LocalDate fecha, LocalTime tiempo) {
		this.nombre = nombre;
		this.puntuacion = puntuacion;
		this.fecha = fecha;
		this.hora = tiempo;
	}

	@Override
	public String toString() {
		return "Jugador - Nombre: " + nombre + " - Puntuacion: " + puntuacion + " - Fecha: " + fecha + " - Hora: "
				+ hora;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	@Override
	public int compareTo(Jugador o) {
		
		if (o.getPuntuacion() > puntuacion) {
			return 1;
		}else if (o.getPuntuacion() < puntuacion) {
			return -1;
		}else {
			return 0;
		}
	}

}
