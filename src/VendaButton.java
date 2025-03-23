import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VendaButton extends JButton {
    private int produtoId;
    private ProdutosDAO produtosDAO;

    public VendaButton(int produtoId, ProdutosDAO produtosDAO) {
        this.produtoId = produtoId;
        this.produtosDAO = produtosDAO;

        // Configura o texto do botão
        setText("Vender");

        // Adiciona o ActionListener para executar a ação de venda
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                venderProduto();
            }
        });
    }

    private void venderProduto() {
        // Confirmação antes de vender o produto
        int confirmacao = JOptionPane.showConfirmDialog(
                null,
                "Tem certeza que deseja vender este produto?",
                "Confirmar Venda",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            // Chama o método de venda no ProdutosDAO
            produtosDAO.venderProduto(produtoId);

            // Atualiza a interface (se necessário)
            atualizarInterface();
        }
    }

    private void atualizarInterface() {
        // Fecha a tela atual e abre a tela de listagem de produtos novamente
        if (getTopLevelAncestor() instanceof JFrame) {
            JFrame frame = (JFrame) getTopLevelAncestor();
            frame.dispose(); // Fecha a tela atual
            new ListagemProdutosScreen().setVisible(true); // Abre a tela de listagem novamente
        }
    }
}