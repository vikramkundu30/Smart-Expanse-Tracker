import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Map;

public class ReportUI extends JFrame {

    public ReportUI() {
        Theme.apply();
        setTitle("Report — ExpenseTrack");
        setSize(740, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Theme.BG);

        JPanel header = Theme.header();
        header.setPreferredSize(new Dimension(740, 60));
        JLabel icon = new JLabel("\u25c8");
        icon.setFont(new Font("SansSerif", Font.BOLD, 18));
        icon.setForeground(Theme.AMBER);
        icon.setBounds(18, 18, 26, 26);
        header.add(icon);
        JLabel title = Theme.label("Expense Report", Theme.TEXT, Theme.title(20));
        title.setBounds(50, 16, 300, 28);
        header.add(title);
        add(header, BorderLayout.NORTH);

        JTextArea area = new JTextArea();
        area.setFont(Theme.mono(14));
        area.setEditable(false);
        area.setBackground(Theme.SURFACE);
        area.setForeground(new Color(220, 200, 140)); // warm amber-tinted mono text
        area.setCaretColor(Theme.AMBER);
        area.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));

        ExpenseDAO dao = new ExpenseDAO();
        Map<String, Double> catMap   = dao.getCategoryReport();
        Map<String, Double> monthMap = dao.getMonthlyReport();
        double total  = dao.getTotalExpense();
        double budget = dao.getBudget();
        double left   = budget - total;

        StringBuilder sb = new StringBuilder();
        sb.append("  EXPENSETRACK — FINANCIAL REPORT\n");
        sb.append("  ═══════════════════════════════════════\n\n");
        sb.append("  CATEGORY BREAKDOWN\n");
        sb.append("  ───────────────────────────────────────\n");
        if (catMap.isEmpty()) sb.append("  (no expenses recorded)\n");
        else for (String k : catMap.keySet())
            sb.append(String.format("  %-20s  ₹%.2f%n", k, catMap.get(k)));
        sb.append("\n  MONTHLY SUMMARY\n");
        sb.append("  ───────────────────────────────────────\n");
        if (monthMap.isEmpty()) sb.append("  (no data)\n");
        else for (String k : monthMap.keySet())
            sb.append(String.format("  %-14s  ₹%.2f%n", k, monthMap.get(k)));
        sb.append("\n  FINANCIAL OVERVIEW\n");
        sb.append("  ───────────────────────────────────────\n");
        sb.append(String.format("  Total Expenses  :  ₹%.2f%n", total));
        sb.append(String.format("  Budget          :  ₹%.2f%n", budget));
        sb.append(String.format("  Remaining       :  ₹%.2f%n", left));
        sb.append(String.format("  Forecast +15%%   :  ₹%.2f%n", total * 1.15));
        sb.append("\n  ═══════════════════════════════════════\n");

        area.setText(sb.toString());

        JScrollPane scroll = new JScrollPane(area);
        Theme.styleScroll(scroll);
        add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 12));
        bottom.setBackground(Theme.SURFACE);
        bottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));
        JButton exportBtn = Theme.button("\u2913  Export as TXT", Theme.AMBER);
        exportBtn.setPreferredSize(new Dimension(200, 42));
        bottom.add(exportBtn);
        add(bottom, BorderLayout.SOUTH);

        exportBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File("ExpenseReport.txt"));
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try (FileWriter fw = new FileWriter(fc.getSelectedFile())) {
                    fw.write(area.getText());
                    JOptionPane.showMessageDialog(this, "Report saved.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Save failed: " + ex.getMessage());
                }
            }
        });

        setVisible(true);
    }
}
