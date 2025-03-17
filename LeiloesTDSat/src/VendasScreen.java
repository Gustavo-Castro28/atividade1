import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VendasScreen extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;

    public VendasScreen() {
        setTitle("Produtos Vendidos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas esta tela
        setLocationRelativeTo(null);

        try {
            // Obtém a lista de produtos vendidos
            ProdutosDAO dao = new ProdutosDAO();
            ArrayList<ProdutosDTO> vendidos = dao.listarProdutosVendidos();
            System.out.println("Produtos vendidos carregados: " + vendidos.size()); // Log de depuração

            // Cria os dados para a tabela
            String[] columnNames = {"ID", "Nome", "Valor", "Status"};
            Object[][] data = new Object[vendidos.size()][4];

            for (int i = 0; i < vendidos.size(); i++) {
                ProdutosDTO produto = vendidos.get(i);
                data[i][0] = produto.getId();
                data[i][1] = produto.getNome();
                data[i][2] = produto.getValor();
                data[i][3] = produto.getStatus();
            }

            // Cria a tabela com os dados
            table = new JTable(data, columnNames);
            scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);

            // Adiciona um botão "Voltar"
            JButton btnVoltar = new JButton("Voltar");
            btnVoltar.addActionListener(e -> {
                this.dispose(); // Fecha a tela de vendas
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(btnVoltar);
            add(buttonPanel, BorderLayout.SOUTH);
        } catch (Exception ex) {
            System.err.println("Erro ao carregar a tela de vendas: " + ex.getMessage()); // Log de erro
            ex.printStackTrace();
        }
    }
}