import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ListagemProdutosScreen extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;
    private ProdutosDAO produtosDAO;

    public ListagemProdutosScreen() {
        setTitle("Listagem de Produtos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        produtosDAO = new ProdutosDAO();

        // Obtém a lista de produtos
        ArrayList<ProdutosDTO> produtos = produtosDAO.listarProdutos();

        // Cria os dados para a tabela
        String[] columnNames = {"ID", "Nome", "Valor", "Status", "Ação"};
        Object[][] data = new Object[produtos.size()][5];

        for (int i = 0; i < produtos.size(); i++) {
            ProdutosDTO produto = produtos.get(i);
            data[i][0] = produto.getId();
            data[i][1] = produto.getNome();
            data[i][2] = produto.getValor();
            data[i][3] = produto.getStatus();

            // Adiciona o botão de venda na coluna "Ação"
            JButton vendaButton = new VendaButton(produto.getId(), produtosDAO);
            data[i][4] = vendaButton;
        }

        // Cria a tabela com os dados
        table = new JTable(data, columnNames);
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Adiciona um painel para os botões
        JPanel buttonPanel = new JPanel();
        JButton consultarVendasButton = new JButton("Consultar Vendas");

        // Adiciona um ActionListener ao botão
        consultarVendasButton.addActionListener(e -> {
            System.out.println("Botão 'Consultar Vendas' clicado."); // Log de depuração
            try {
                // Abre a tela de vendas
                VendasScreen vendasScreen = new VendasScreen();
                vendasScreen.setVisible(true);
                System.out.println("Tela de vendas aberta com sucesso."); // Log de depuração
            } catch (Exception ex) {
                System.err.println("Erro ao abrir a tela de vendas: " + ex.getMessage()); // Log de erro
                ex.printStackTrace();
            }
        });

        // Adiciona o botão ao painel
        buttonPanel.add(consultarVendasButton);

        // Adiciona o painel ao JFrame
        add(buttonPanel, BorderLayout.SOUTH);

        // Verifica se o botão está visível
        System.out.println("Botão 'Consultar Vendas' adicionado ao painel."); // Log de depuração
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ListagemProdutosScreen().setVisible(true);
        });
    }
}