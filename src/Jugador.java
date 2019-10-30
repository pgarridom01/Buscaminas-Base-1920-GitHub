import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 
 * @author Pablo Garrido
 *
 */
public class Jugador implements Comparable<Jugador>, Serializable {

	private static final long serialVersionUID = 1L;
	String nombre;
	int puntuacionFinal;
	LocalDate fechaFinJuego;
	LocalTime horaFinJuego;

	public Jugador() {
		// TODO Auto-generated constructor stub
	}

	public Jugador(String nombre, int puntuacion, LocalDate fecha, LocalTime tiempo) {
		this.nombre = nombre;
		this.puntuacionFinal = puntuacion;
		this.fechaFinJuego = fecha;
		this.horaFinJuego = tiempo;
	}

	@Override
	public String toString() {
		return "Jugador - Nombre: " + nombre + " - Puntuacion: " + puntuacionFinal + " - Fecha: " + fechaFinJuego
				+ " - Hora: " + horaFinJuego;
	}

	/**
	 * 
	 * @return fecha del juego
	 */
	public LocalDate getFecha() {
		return fechaFinJuego;
	}

	/**
	 * 
	 * @param fecha del juego
	 */
	public void setFecha(LocalDate fecha) {
		this.fechaFinJuego = fecha;
	}

	/**
	 * 
	 * @return hora final del juego
	 */
	public LocalTime getHora() {
		return horaFinJuego;
	}

	/**
	 * 
	 * @param hora final del juego
	 */
	public void setHora(LocalTime hora) {
		this.horaFinJuego = hora;
	}

	/**
	 * 
	 * @return nombre del jugador
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * 
	 * @param nombre del jugador
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * 
	 * @return puntuacion final del juego
	 */
	public int getPuntuacion() {
		return puntuacionFinal;
	}

	/**
	 * 
	 * @param puntuacion final del juego
	 */
	public void setPuntuacion(int puntuacion) {
		this.puntuacionFinal = puntuacion;
	}

	/**
	 * Metodo para ordenar los jugadores de mayor a menos puntuacion
	 */
	@Override
	public int compareTo(Jugador o) {

		if (o.getPuntuacion() > puntuacionFinal) {
			return 1;
		} else if (o.getPuntuacion() < puntuacionFinal) {
			return -1;
		} else {
			return 0;
		}
	}

}
