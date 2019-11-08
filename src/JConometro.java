

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class JConometro extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	JLabel contador = new JLabel();;
	JPanel panel;
	double tiempoTranscurrido;
	double tiempoOriginal;
	boolean cronometroActivo = false;
	Thread hilo = null;

	public JConometro() {
		super();
		panel = new JPanel();
		this.setBackground(Color.CYAN);
		this.setLayout(new GridBagLayout());
		panel.setBackground(Color.CYAN);
		panel.add(contador);
		contador.setFont(new Font("Arial", Font.BOLD, 30));
		contador.setHorizontalAlignment(SwingUtilities.CENTER);
		this.add(panel);

	}

	public void comenzar() {

		if (hilo == null) {
			cronometroActivo = true;
			hilo = new Thread(this);
			hilo.start();
		}

	}

	
	public void parar() {
		if (hilo != null) {
			cronometroActivo = false;
			hilo = null;
			actualizarPantalla();
		}
	}

	public void resetear() {
		if (hilo == null) {
			contador.setText("00:00");
		}else {
			parar();
			comenzar();
		}
	}

	@Override
	public void run() {
		tiempoOriginal = System.nanoTime();
		while (cronometroActivo) {
			calcularTiempoTranscurrido();
			actualizarPantalla();
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void calcularTiempoTranscurrido() {
		tiempoTranscurrido = System.nanoTime() - tiempoOriginal;

	}

	private void actualizarPantalla() {
		double t = Math.floor(tiempoTranscurrido / 1000000000);
		DecimalFormat df = new DecimalFormat("##");
		double m = Math.floor(t / 60);
		double s = Math.floor(t % 60);
		String tiempo;
		if (m < 10) {
			if (s < 10) {
				tiempo = "0" + df.format(m) + ":0" + df.format(s);
			} else {
				tiempo = "0" + df.format(m) + ":" + df.format(s);
			}
		} else {
			if (s < 10) {
				tiempo = df.format(m) + ":0" + df.format(s);
			} else {
				tiempo = df.format(m) + ":" + df.format(s);
			}
		}

		contador.setText(tiempo);
	}

}
