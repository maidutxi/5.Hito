package src;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class ConexionBD {
    // Datos de conexión a la base de datos MariaDB
    private static final String IP_SERVIDOR = "localhost";
    private static final String DB_NOMBRE = "hito4";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "zubiri";
    private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    private static final String DB_URL = "jdbc:mariadb://" + IP_SERVIDOR + ":3306/" + DB_NOMBRE;
    private Connection conn;




    // Constructor
    public ConexionBD() throws SQLException {
        conectarBaseDatos();
    }




    // Método para conectar a la base de datos
    private void conectarBaseDatos() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USUARIO, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Si no se encuentra el driver, lanzamos una excepción SQLException
            throw new SQLException("Error al cargar el controlador JDBC", e);
        } catch (SQLException e) {
            // Si hay un error al conectarse a la base de datos, lanzamos una excepción SQLException
            throw new SQLException("Error al conectar a la base de datos", e);
        }
    }




    // Método para obtener la conexión
    public Connection getConnection() {
        return conn;
    }




    // Método para cerrar la conexión a la base de datos
    public void cerrarConexion() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {


            e.printStackTrace();
        }
    }
}
