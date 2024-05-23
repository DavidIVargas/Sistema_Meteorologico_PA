/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
    private JLabel labelImagen;
    private JRadioButton[] radioButtons;
    private JLabel[] labelNotificaciones;
    private Persona[] personas;

    public VentanaPrincipal() {
        termometro = new Termometro();

        // Crear 5 personas por defecto
        personas = new Persona[5];
        personas[0] = new Persona("Persona 1");
        personas[1] = new Persona("Persona 2");
        personas[2] = new Persona("Persona 3");
        personas[3] = new Persona("Persona 4");
        personas[4] = new Persona("Persona 5");

        // Configurar la ventana
        setTitle("Sistema de Monitoreo Meteorológico");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Panel para personas y radio buttons
        JPanel panelPersonas = new JPanel(new GridBagLayout());
        panelPersonas.setBorder(BorderFactory.createTitledBorder("Suscripciones"));

        radioButtons = new JRadioButton[5];
        labelNotificaciones = new JLabel[5];
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        for (int i = 0; i < 5; i++) {
            JLabel label = new JLabel(personas[i].getNombre());
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.WEST;
            panelPersonas.add(label, gbc);

            radioButtons[i] = new JRadioButton("Suscribir");
            radioButtons[i].addActionListener(new RadioButtonListener(personas[i]));
            gbc.gridx = 1;
            panelPersonas.add(radioButtons[i], gbc);

            labelNotificaciones[i] = new JLabel("");
            gbc.gridx = 2;
            panelPersonas.add(labelNotificaciones[i], gbc);
        }

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

        // Crear label para la imagen del termómetro
        labelImagen = new JLabel();
        labelImagen.setHorizontalAlignment(SwingConstants.CENTER);
        actualizarImagen(0); // Inicializar con la imagen para 0 grados

        // Añadir componentes a la ventana
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(sliderTemperatura, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(panelPersonas, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(labelImagen, gbc);

        setVisible(true);
    }

    private void actualizarNotificaciones() {
        int temperatura = termometro.getTemperatura();
        String color;
        if (temperatura <= 50) {
            color = "verde";
        } else if (temperatura <= 80) {
            color = "naranja";
        } else {
            color = "rojo";
        }

        for (int i = 0; i < personas.length; i++) {
            if (radioButtons[i].isSelected()) {
                labelNotificaciones[i].setText("La temperatura es " + temperatura + "°C (" + color + ")");
            } else {
                labelNotificaciones[i].setText("");
            }
        }
    }

    private void actualizarImagen(int temperatura) {
        String imagenPath;
        if (temperatura <= 50) {
            imagenPath = "/images/Termometro_Verde.png";
        } else if (temperatura <= 80) {
            imagenPath = "/images/Termometro_Naranja.png";
        } else {
            imagenPath = "/images/Termometro_Rojo.png";
        }

        ImageIcon icon = new ImageIcon(getClass().getResource(imagenPath));
        labelImagen.setIcon(icon);
    }

    // Listener para manejar la suscripción/desuscripción de personas
    private class RadioButtonListener implements ActionListener {
        private Persona persona;

        public RadioButtonListener(Persona persona) {
            this.persona = persona;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton source = (JRadioButton) e.getSource();
            if (source.isSelected()) {
                termometro.addObserver(persona);
            } else {
                termometro.removeObserver(persona);
            }
        }
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
