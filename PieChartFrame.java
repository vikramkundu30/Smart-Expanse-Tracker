import javax.swing.*;

public class PieChartFrame extends JFrame {
    public PieChartFrame() {
        setTitle("🥧 Pie Chart Analytics");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new PieChartUI(new ExpenseDAO().getCategoryReport()));
        setVisible(true);
    }
}