import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.Map;

public class MonthlySummaryUI extends JFrame {

    public MonthlySummaryUI() {
        Theme.apply();
        setTitle("Monthly Summary — ExpenseTrack");
        setSize(660, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Theme.BG);

        JPanel header = Theme.header();
        header.setPreferredSize(new Dimension(660, 60));
        JLabel icon = new JLabel("\u25c8");
        icon.setFont(new Font("SansSerif", Font.BOLD, 18));
        icon.setForeground(Theme.AMBER);
        icon.setBounds(18, 18, 26, 26);
        header.add(icon);
        JLabel title = Theme.label("Monthly Summary", Theme.TEXT, Theme.title(20));
        title.setBounds(50, 16, 300, 28);
        header.add(title);
        add(header, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Month", "Total Expense (₹)", "Forecast (+15%)"}, 0
        ) { public boolean isCellEditable(int r, int c) { return false; } };

        ExpenseDAO dao = new ExpenseDAO();
        Map<String, Double> monthMap = dao.getMonthlyReport();
        if (monthMap.isEmpty()) model.addRow(new String[]{"No data", "0.00", "0.00"});
        else for (String month : monthMap.keySet()) {
            double val = monthMap.get(month);
            model.addRow(new String[]{month, String.format("%.2f", val), String.format("%.2f", val * 1.15)});
        }

        JTable table = new JTable(model) {
            public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Theme.BG : Theme.SURFACE);
                    c.setForeground(Theme.TEXT);
                } else {
                    c.setBackground(new Color(251, 191, 36, 50));
                    c.setForeground(Theme.TEXT);
                }
                return c;
            }
        };
        Theme.styleTable(table);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < 3; i++) table.getColumnModel().getColumn(i).setCellRenderer(center);

        JScrollPane scroll = new JScrollPane(table);
        Theme.styleScroll(scroll);
        add(scroll, BorderLayout.CENTER);

        // Footer
        double grand = dao.getTotalExpense();
        JPanel footer = new JPanel(null);
        footer.setBackground(Theme.SURFACE);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));
        footer.setPreferredSize(new Dimension(660, 50));
        JLabel fl = Theme.label(String.format("Grand Total: ₹%.2f   |   Forecast: ₹%.2f", grand, grand * 1.15),
            Theme.TEXT, Theme.title(13));
        fl.setBounds(20, 12, 560, 26);
        footer.add(fl);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }
}
