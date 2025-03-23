import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conectaDAO {

    public Connection connectDB() {
        Connection conn = null;

        try {
            // URL de conexão com useSSL=false
            String url = "jdbc:mysql://127.0.0.1:3306/atividade1?useSSL=false";
            String user = "root";
            String password = "28052007";

            // Estabelece a conexão
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ConectaDAO: " + erro.getMessage());
        }
        return conn;
    }
}