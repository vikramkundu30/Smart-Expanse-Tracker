import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.geom.*;

public class ViewExpensesUI extends JFrame {

    public ViewExpensesUI() {
        Theme.apply();
        setTitle("All Expenses — ExpenseTrack");
        setSize(960, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Theme.BG);

        // Header
        JPanel header = Theme.header();
        header.setPreferredSize(new Dimension(960, 60));
        JLabel icon = new JLabel("\u25c8");
        icon.setFont(new Font("SansSerif", Font.BOLD, 18));
        icon.setForeground(Theme.AMBER);
        icon.setBounds(18, 18, 26, 26);
        header.add(icon);
        JLabel title = Theme.label("All Expenses", Theme.TEXT, Theme.title(20));
        title.setBounds(50, 16, 300, 28);
        header.add(title);
        add(header, BorderLayout.NORTH);

        // Table
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Amount (₹)", "Category", "Date", "Description"}, 0
        ) { public boolean isCellEditable(int r, int c) { return false; } };

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

        for (String[] row : new ExpenseDAO().getAllExpenses()) model.addRow(row);

        JScrollPane scroll = new JScrollPane(table);
        Theme.styleScroll(scroll);
        add(scroll, BorderLayout.CENTER);

        // Bottom
        JPanel bottom = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 16, 12));
        bottom.setBackground(Theme.SURFACE);
        bottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));

        JButton deleteBtn  = Theme.button("\u2715  Delete Selected", Theme.RED);
        JButton refreshBtn = Theme.button("\u21bb  Refresh", Theme.TEAL);
        deleteBtn.setPreferredSize(new Dimension(195, 42));
        refreshBtn.setPreferredSize(new Dimension(150, 42));
        bottom.add(deleteBtn);
        bottom.add(refreshBtn);
        add(bottom, BorderLayout.SOUTH);

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row to delete."); return; }
            int id = Integer.parseInt(model.getValueAt(row, 0).toString());
            if (JOptionPane.showConfirmDialog(this, "Delete expense #" + id + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                new ExpenseDAO().deleteExpense(id);
                model.removeRow(row);
            }
        });

        refreshBtn.addActionListener(e -> {
            model.setRowCount(0);
            for (String[] row : new ExpenseDAO().getAllExpenses()) model.addRow(row);
        });

        setVisible(true);
    }
}
