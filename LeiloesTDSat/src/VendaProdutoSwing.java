import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class VendaProdutoSwing extends JFrame {
    private List<Produto> produtos; // Lista de produtos
    private JComboBox<Integer> idComboBox; // ComboBox para selecionar o ID do produto
    private JButton venderButton; // Botão "Vender"

    // Dados de conexão ao banco de dados
    private static final String URL = "jdbc:mysql://localhost:3306/nome_do_banco";
    private static final String USER = "usuario";
    private static final String PASSWORD = "senha";

    public VendaProdutoSwing() {
        // Inicializa a lista de produtos
        produtos = new ArrayList<>();
        produtos.add(new Produto(1, "Produto A", 100.0, "a venda"));
        produtos.add(new Produto(2, "Produto B", 200.0, "a venda"));

        // Configurações da janela
        setTitle("Vender Produto");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Painel para organizar os componentes
        JPanel panel = new JPanel();

        // ComboBox para selecionar o ID do produto
        idComboBox = new JComboBox<>();
        for (Produto p : produtos) {
            idComboBox.addItem(p.getId()); // Adiciona os IDs dos produtos ao ComboBox
        }

        // Botão "Vender"
        venderButton = new JButton("Vender");
        venderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Passo 1: Pegar o ID selecionado
                int selectedId = (int) idComboBox.getSelectedItem();

                // Passo 2: Procurar o produto na lista
                for (Produto p : produtos) {
                    if (p.getId() == selectedId) {
                        // Passo 3: Alterar o status na lista em memória
                        p.setStatus("vendido");

                        // Passo 4: Atualizar o status no banco de dados
                        atualizarStatusNoBanco(selectedId, "vendido");

                        // Passo 5: Exibir mensagem de sucesso
                        JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
                        break;
                    }
                }
            }
        });

        // Adiciona os componentes ao painel
        panel.add(new JLabel("Selecione o ID do Produto:"));
        panel.add(idComboBox);
        panel.add(venderButton);

        // Adiciona o painel à janela
        add(panel);
    }

    // Método para atualizar o status no banco de dados
    private void atualizarStatusNoBanco(int id, String novoStatus) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Passo 1: Conectar ao banco de dados
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Passo 2: Preparar a query de atualização
            String sql = "UPDATE produtos SET status = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);

            // Passo 3: Definir os parâmetros da query
            stmt.setString(1, novoStatus);
            stmt.setInt(2, id);

            // Passo 4: Executar a query
            int rowsAffected = stmt.executeUpdate();

            // Verificar se a atualização foi bem-sucedida
            if (rowsAffected > 0) {
                System.out.println("Status atualizado no banco de dados!");
            } else {
                System.out.println("Nenhum produto foi atualizado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Passo 5: Fechar a conexão e o statement
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Executa a interface gráfica
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VendaProdutoSwing().setVisible(true);
            }
        });
    }
}

// Classe Produto
class Produto {
    private int id;
    private String nome;
    private double valor;
    private String status;

    public Produto(int id, String nome, double valor, String status) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.status = status;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}