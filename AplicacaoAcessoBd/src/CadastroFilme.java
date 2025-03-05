import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroFilme extends JFrame {
    private JTextField nomeFilmeField;
    private JTextField dataLancamentoField;
    private JTextField categoriaField;
    private JButton cadastrarButton;
    private JButton limparButton;

    public CadastroFilme() {
        setTitle("CENAFLIX - Cadastro de Filme");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        
        JLabel nomeLabel = new JLabel("Nome do Filme:");
        nomeFilmeField = new JTextField();

        JLabel dataLabel = new JLabel("Data de Lançamento (yyyy-MM-dd):");
        dataLancamentoField = new JTextField();

        JLabel categoriaLabel = new JLabel("Categoria:");
        categoriaField = new JTextField();

        
        cadastrarButton = new JButton("Cadastrar");
        limparButton = new JButton("Limpar");

        
        add(nomeLabel);
        add(nomeFilmeField);
        add(dataLabel);
        add(dataLancamentoField);
        add(categoriaLabel);
        add(categoriaField);
        add(cadastrarButton);
        add(limparButton);

        
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarFilme();
            }
        });

        
        limparButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });

        setVisible(true);
    }

    private void cadastrarFilme() {
        String nome = nomeFilmeField.getText();
        String dataLancamento = dataLancamentoField.getText();
        String categoria = categoriaField.getText();

       
        if (nome.isEmpty() || dataLancamento.isEmpty() || categoria.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

       
        if (!dataLancamento.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "A data deve estar no formato yyyy-MM-dd!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

       
        try (Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/CenaFlix", "root", "28052007")) {
            String sql = "INSERT INTO Filme (nome, data_lancamento, categoria) VALUES (?, ?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, dataLancamento);
            stmt.setString(3, categoria);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(this, "Filme cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar o filme.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        nomeFilmeField.setText("");
        dataLancamentoField.setText("");
        categoriaField.setText("");
    }

    public static void main(String[] args) {
        new CadastroFilme();
    }
}
