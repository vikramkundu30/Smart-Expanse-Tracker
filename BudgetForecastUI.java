import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class BudgetForecastUI extends JFrame {

    public BudgetForecastUI() {
        Theme.apply();
        setTitle("Budget Forecast — ExpenseTrack");
        setSize(540, 440);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Theme.BG);

        JPanel header = Theme.header();
        header.setPreferredSize(new Dimension(540, 60));
        JLabel icon = new JLabel("\u25c8");
        icon.setFont(new Font("SansSerif", Font.BOLD, 18));
        icon.setForeground(Theme.AMBER);
        icon.setBounds(18, 18, 26, 26);
        header.add(icon);
        JLabel title = Theme.label("Budget Forecast", Theme.TEXT, Theme.title(20));
        title.setBounds(50, 16, 300, 28);
        header.add(title);
        add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(null);
        center.setBackground(Theme.BG);
        add(center, BorderLayout.CENTER);

        ExpenseDAO dao = new ExpenseDAO();
        double total    = dao.getTotalExpense();
        double budget   = dao.getBudget();
        double forecast = new BudgetForecast().predict(total);
        double left     = budget - total;

        Color leftColor = left >= 0 ? Theme.GREEN : Theme.RED;
        String leftText = left >= 0 ? String.format("₹%.2f", left) : "−₹" + String.format("%.2f", Math.abs(left));

        addCard(center, "Total Expenses",         String.format("₹%.2f", total),    32,  30, Theme.AMBER);
        addCard(center, "Budget Set",              String.format("₹%.2f", budget),  288, 30, Theme.TEAL);
        addCard(center, "Next Month Forecast",    String.format("₹%.2f", forecast), 32, 190, Theme.BLUE);
        addCard(center, "Budget Remaining",        leftText,                        288, 190, leftColor);

        JLabel note = Theme.label("* Forecast = Current expenses × 1.15", Theme.TEXT_MUTED, Theme.body(12));
        note.setBounds(32, 330, 476, 22);
        note.setFont(new Font("Trebuchet MS", Font.ITALIC, 12));
        center.add(note);

        setVisible(true);
    }

    void addCard(JPanel parent, String label, String value, int x, int y, Color accent) {
        JPanel card = Theme.card(accent);
        card.setBounds(x, y, 220, 140);

        JLabel lbl = Theme.label(label.toUpperCase(), Theme.TEXT_MUTED, Theme.title(10));
        lbl.setBounds(16, 18, 188, 18);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(lbl);

        JLabel val = Theme.label(value, accent, Theme.title(26));
        val.setBounds(16, 58, 188, 40);
        val.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(val);

        parent.add(card);
    }
}
