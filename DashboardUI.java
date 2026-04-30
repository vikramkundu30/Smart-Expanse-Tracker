import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class DashboardUI extends JFrame {

    JLabel total, budget, forecast;

    public DashboardUI() {
        Theme.apply();
        setTitle("Dashboard — ExpenseTrack");
        setSize(1100, 560);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Theme.BG);

        // Header
        JPanel header = Theme.header();
        header.setBounds(0, 0, 1100, 70);
        add(header);

        JLabel icon = new JLabel("\u25c8");
        icon.setFont(new Font("SansSerif", Font.BOLD, 24));
        icon.setForeground(Theme.AMBER);
        icon.setBounds(22, 20, 32, 32);
        header.add(icon);

        JLabel head = Theme.label("Financial Dashboard", Theme.TEXT, Theme.title(26));
        head.setBounds(60, 16, 400, 40);
        header.add(head);

        // Cards
        total    = bigCard("Total Expenses",   200, 110, Theme.AMBER);
        budget   = bigCard("Budget Set",        510, 110, Theme.TEAL);
        forecast = bigCard("Forecast (+15%)",   820, 110, Theme.BLUE);

        // Buttons
        JButton refresh = Theme.button("\u21bb  Refresh", Theme.SURFACE2);
        refresh.setBounds(250, 400, 160, 46);
        add(refresh);

        JButton graph = Theme.button("\u25a4  Bar Chart", Theme.BLUE);
        graph.setBounds(440, 400, 160, 46);
        add(graph);

        JButton pie = Theme.button("\u2299  Pie Chart", new Color(168, 85, 247));
        pie.setBounds(630, 400, 160, 46);
        add(pie);

        loadData();
        refresh.addActionListener(e -> loadData());
        graph.addActionListener(e -> new GraphFrame());
        pie.addActionListener(e -> new PieChartFrame());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    void loadData() {
        ExpenseDAO dao = new ExpenseDAO();
        double t = dao.getTotalExpense();
        double b = dao.getBudget();
        total.setText("₹" + String.format("%.2f", t));
        budget.setText("₹" + String.format("%.2f", b));
        forecast.setText("₹" + String.format("%.2f", t * 1.15));
    }

    JLabel bigCard(String title, int x, int y, Color accent) {
        JPanel p = Theme.card(accent);
        p.setBounds(x, y, 280, 200);
        add(p);

        JLabel t = Theme.label(title.toUpperCase(), Theme.TEXT_MUTED, Theme.title(11));
        t.setBounds(20, 24, 240, 18);
        p.add(t);

        JLabel v = Theme.label("₹0", Theme.TEXT, Theme.title(34));
        v.setBounds(20, 78, 240, 50);
        p.add(v);

        // colored bar at bottom
        JPanel bar = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 50));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                g2.setColor(accent);
                g2.fillRoundRect(0, 0, getWidth()/3, getHeight(), 4, 4);
                g2.dispose();
            }
        };
        bar.setOpaque(false);
        bar.setBounds(20, 155, 240, 6);
        p.add(bar);

        return v;
    }
}
