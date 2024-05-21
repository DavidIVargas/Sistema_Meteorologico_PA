/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelo.Observer;
import modelo.Persona;
import modelo.Termometro;

/**
 *
 * @author davidvargas
 */
public class VentanaPrincipal extends JFrame {
    private Termometro termometro;
    private JSlider sliderTemperatura;
    private JTextArea textAreaNotificaciones;
    private JLabel labelImagen;

    public VentanaPrincipal() {
        termometro = new Termometro();

        // Crear 5 personas por defecto
        Persona p1 = new Persona("Persona 1");
        Persona p2 = new Persona("Persona 2");
        Persona p3 = new Persona("Persona 3");
        Persona p4 = new Persona("Persona 4");
        Persona p5 = new Persona("Persona 5");

        // Suscribir personas al sistema meteorológico
        termometro.addObserver(p1);
        termometro.addObserver(p2);
        termometro.addObserver(p3);
        termometro.addObserver(p4);
        termometro.addObserver(p5);

        // Configurar la ventana
        setTitle("Sistema de Monitoreo Meteorológico");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear slider de temperatura
        sliderTemperatura = new JSlider(0, 100, 0);
        sliderTemperatura.setMajorTickSpacing(10);
        sliderTemperatura.setPaintTicks(true);
        sliderTemperatura.setPaintLabels(true);

        sliderTemperatura.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int temperatura = sliderTemperatura.getValue();
                termometro.setTemperatura(temperatura);
                actualizarNotificaciones();
                actualizarImagen(temperatura);
            }
        });

        // Crear área de notificaciones
        textAreaNotificaciones = new JTextArea();
        textAreaNotificaciones.setEditable(false);

        // Crear label para la imagen del termómetro
        labelImagen = new JLabel();
        labelImagen.setHorizontalAlignment(SwingConstants.CENTER);
        actualizarImagen(0); // Inicializar con la imagen para 0 grados

        // Añadir componentes a la ventana
        add(sliderTemperatura, BorderLayout.NORTH);
        add(new JScrollPane(textAreaNotificaciones), BorderLayout.CENTER);
        add(labelImagen, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void actualizarNotificaciones() {
        textAreaNotificaciones.setText(""); // Borrar notificaciones previas
        int temperatura = termometro.getTemperatura();
        String color;
        if (temperatura <= 50) {
            color = "verde";
        } else if (temperatura <= 80) {
            color = "naranja";
        } else {
            color = "rojo";
        }
        for (Observer observer : termometro.getObservers()) {
            textAreaNotificaciones.append("Notificación: La temperatura es " + temperatura + "°C (" + color + ")\n");
        }
    }

    private void actualizarImagen(int temperatura) {
        String imagenPath;
        if (temperatura <= 50) {
            imagenPath = "/images/Termometro_Verde.png";
        } else if (temperatura <= 80) {
            imagenPath = "/images/Termometro_Amarillo.png";
        } else {
            imagenPath = "/images/Termometro_Rojo.png";
        }

        ImageIcon icon = new ImageIcon(getClass().getResource(imagenPath));
        labelImagen.setIcon(icon);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaPrincipal();
            }
        });
    }
}
