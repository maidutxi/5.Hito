package src.src;




import javax.swing.*;
import java.util.Map;




public class Main {
    public static void main(String[] args) {
        src.VisualizadorFotos visualizador = new src.VisualizadorFotos();
        visualizador.setVisible(true); // Mostrar la ventana








        // Obtener el mapa de visitas
        Map<Integer, Integer> visitsMap = visualizador.createVisitsMap();




        // Imprimir el mapa de visitas
        for (Map.Entry<Integer, Integer> entry : visitsMap.entrySet()) {
            System.out.println("Fotógrafo ID: " + entry.getKey() + ", Visitas: " + entry.getValue());
        }




        // Código para marcar fotógrafos como premiados
        String input = JOptionPane.showInputDialog("Ingrese el mínimo de visitas para ser premiado:");
        int minimumVisits = Integer.parseInt(input);
        visualizador.markPhotographersAsAwarded(minimumVisits);




        visualizador.cerrarConexion(); // Cerrar la conexión cuando sea necesario
    }
}
