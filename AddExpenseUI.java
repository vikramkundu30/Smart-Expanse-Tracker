import javax.swing.*;

public class AddExpenseUI extends JFrame {
    public AddExpenseUI() {
        setTitle("Add Expense");
        setSize(300, 200);
        setLocationRelativeTo(null);
        JOptionPane.showMessageDialog(null,
                "To add Expense use HomeUI form",
                "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}