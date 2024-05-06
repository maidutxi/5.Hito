import src.ConexionBD;
import src.Fotografia;
import src.Fotografo;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    // Método para cargar los fotógrafos desde la base de datos
    private void cargarFotografos() {
        try {
            PreparedStatement stmt = conexionBD.getConnection().prepareStatement("SELECT * FROM Fotografos");
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                int id = rs.getInt("IdFotografo");
                String nombre = rs.getString("Nombre");
                boolean premiado = rs.getBoolean("Premiado");
                comboBoxFotografos.addItem(new Fotografo(id, nombre, premiado));
            }


            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los fotógrafos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para cargar las fotos del fotógrafo seleccionado desde la fecha indicada en el JXDatePicker
    private void cargarFotos() {
        Fotografo fotografoSeleccionado = (Fotografo) comboBoxFotografos.getSelectedItem();
        Date fechaSeleccionada = datePicker.getDate();




        try {
            PreparedStatement stmt = conexionBD.getConnection().prepareStatement("SELECT * FROM Fotografias WHERE IdFotografo = ? AND Fecha >= ?");
            stmt.setInt(1, fotografoSeleccionado.getId());
            stmt.setDate(2, new java.sql.Date(fechaSeleccionada.getTime()));
            ResultSet rs = stmt.executeQuery();




            DefaultListModel<Fotografia> model = new DefaultListModel<>();
            while (rs.next()) {
                int idFoto = rs.getInt("IdFoto");
                String titulo = rs.getString("Titulo");
                String archivo = rs.getString("Archivo");
                int visitas = rs.getInt("Visitas");
                int idFotografo = rs.getInt("IdFotografo");
                model.addElement(new Fotografia(idFoto, titulo, archivo, visitas, idFotografo));
            }




            listFotos.setModel(model);




            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las fotos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para cerrar la conexión a la base de datos
    public void cerrarConexion() {
        conexionBD.cerrarConexion();
    }

    // Método para eliminar imágenes no mostradas de fotógrafos no premiados
    private void removeUnshownImages() {
        try {
            Connection connection = (Connection) conexionBD.getConnection();
            String sql = "SELECT Fotografias.IdFoto, Fotografias.Titulo FROM Fotografias " +
                    "INNER JOIN Fotografos ON Fotografias.IdFotografo = Fotografos.IdFotografo " +
                    "WHERE Fotografias.Mostrada = false AND Fotografos.Premiado = false";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();




            while (resultSet.next()) {
                int idFoto = resultSet.getInt("IdFoto");
                String titulo = resultSet.getString("Titulo");
                int opcion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar la imagen \"" + titulo + "\"?", "Eliminar Imagen", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    deleteImage(idFoto);
                }
            }




            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Método para eliminar una imagen
    private void deleteImage(int idFoto) {
        try {
            Connection connection = (Connection) conexionBD.getConnection();
            String sql = "DELETE FROM Fotografias WHERE IdFoto = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idFoto);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }






}
