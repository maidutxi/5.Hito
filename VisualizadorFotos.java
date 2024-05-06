import src.ConexionBD;
import src.Fotografia;
import src.Fotografo;

import javax.swing.*;
import java.awt.*;

public class VisualizadorFotos {
    private ConexionBD conexionBD;
    private JComboBox<Fotografo> comboBoxFotografos;
    private JLabel labelFotografo;
    private JXDatePicker datePicker;
    private JLabel labelFecha;
    private JList<Fotografia> listFotos;
    private JLabel labelImagen;




    public VisualizadorFotos() {
        setTitle("Visualizador de Fotos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel(new BorderLayout());
        JButton btnAward = new JButton("AWARD");
        JButton btnRemove = new JButton("REMOVE");




        // Ajustar tamaño de los botones
        Dimension buttonSize = new Dimension(getWidth() / 2, 60); // La mitad del ancho de la ventana
        btnAward.setPreferredSize(buttonSize);
        btnRemove.setPreferredSize(buttonSize);




        panelBotones.add(btnAward, BorderLayout.WEST); // Añadir AWARD a la izquierda
        panelBotones.add(btnRemove, BorderLayout.EAST); // Añadir REMOVE a la derecha
        add(panelBotones, BorderLayout.NORTH); // Agregar el panel de botones en la parte superior




        // Panel central para los componentes principales
        JPanel panelPrincipal = new JPanel(new GridLayout(2, 2));
        add(panelPrincipal, BorderLayout.CENTER);




        comboBoxFotografos = new JComboBox<>();
        labelFotografo = new JLabel("Photographer");
        datePicker = new JXDatePicker();
        labelFecha = new JLabel("Photos after");
        listFotos = new JList<>();
        labelImagen = new JLabel();




        comboBoxFotografos.setPreferredSize(labelFotografo.getPreferredSize());
        datePicker.setFormats("dd/MM/yyyy");
        datePicker.setLightWeightPopupEnabled(true);




        JPanel panelComboBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelDatePicker = new JPanel(new FlowLayout(FlowLayout.LEFT));




        panelComboBox.add(labelFotografo);
        panelComboBox.add(comboBoxFotografos);
        panelDatePicker.add(labelFecha);
        panelDatePicker.add(datePicker);




        panelPrincipal.add(panelComboBox);
        panelPrincipal.add(panelDatePicker);
        panelPrincipal.add(new JScrollPane(listFotos));
        panelPrincipal.add(labelImagen);

    }
