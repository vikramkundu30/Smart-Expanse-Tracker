import javax.swing.*;

public class GraphFrame extends JFrame {
    public GraphFrame() {
        setTitle("📊 Analytics - Bar Chart");
        setSize(950, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new GraphUI(new ExpenseDAO().getCategoryReport()));
        setVisible(true);
    }
}