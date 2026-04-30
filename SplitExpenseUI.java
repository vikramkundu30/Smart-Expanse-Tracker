import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;

public class SplitExpenseUI extends JFrame {

    JTextField totalField, peopleField;
    JTextField[] nameFields;
    JPanel namePanel;
    JTextArea resultArea;

    public SplitExpenseUI() {
        Theme.apply();
        setTitle("Split Expense — ExpenseTrack");
        setSize(640, 660);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Theme.BG);

        JPanel header = Theme.header();
        header.setBounds(0, 0, 640, 60);
        add(header);
        JLabel icon = new JLabel("\u25c8");
        icon.setFont(new Font("SansSerif", Font.BOLD, 18));
        icon.setForeground(Theme.AMBER);
        icon.setBounds(18, 18, 26, 26);
        header.add(icon);
        JLabel title = Theme.label("Split Expense Calculator", Theme.TEXT, Theme.title(20));
        title.setBounds(50, 16, 400, 28);
        header.add(title);

        // Amount
        JLabel aLbl = Theme.label("TOTAL AMOUNT (₹)", Theme.TEXT_MUTED, Theme.title(11));
        aLbl.setBounds(40, 85, 200, 18);
        add(aLbl);
        totalField = Theme.field();
        totalField.setBounds(40, 106, 220, 42);
        add(totalField);

        // People
        JLabel pLbl = Theme.label("NUMBER OF PEOPLE", Theme.TEXT_MUTED, Theme.title(11));
        pLbl.setBounds(40, 162, 200, 18);
        add(pLbl);
        peopleField = Theme.field();
        peopleField.setBounds(40, 183, 120, 42);
        add(peopleField);

        JButton loadBtn = Theme.button("Load Names", Theme.TEAL);
        loadBtn.setBounds(176, 183, 130, 42);
        add(loadBtn);

        // Names panel
        namePanel = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Theme.SURFACE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.setColor(Theme.BORDER);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 12, 12));
                g2.dispose();
            }
        };
        namePanel.setOpaque(false);
        namePanel.setBounds(40, 242, 560, 200);
        add(namePanel);

        // Result
        resultArea = new JTextArea();
        resultArea.setFont(Theme.mono(14));
        resultArea.setEditable(false);
        resultArea.setBackground(Theme.SURFACE);
        resultArea.setForeground(new Color(220, 200, 140));
        resultArea.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setBounds(40, 458, 560, 130);
        Theme.styleScroll(scroll);
        add(scroll);

        JButton calcBtn = Theme.button("\u2713  Calculate Split", Theme.AMBER);
        calcBtn.setBounds(170, 605, 300, 46);
        calcBtn.setFont(Theme.title(14));
        add(calcBtn);

        loadBtn.addActionListener(e -> {
            namePanel.removeAll();
            try {
                int n = Integer.parseInt(peopleField.getText().trim());
                if (n < 2 || n > 10) throw new Exception();
                nameFields = new JTextField[n];
                for (int i = 0; i < n; i++) {
                    JLabel lbl = Theme.label("Person " + (i+1), Theme.TEXT_MUTED, Theme.body(13));
                    lbl.setBounds(20, 16 + i*38, 100, 26);
                    namePanel.add(lbl);
                    nameFields[i] = Theme.field();
                    nameFields[i].setText("Person " + (i+1));
                    nameFields[i].setBounds(130, 16 + i*38, 180, 32);
                    namePanel.add(nameFields[i]);
                }
                namePanel.revalidate(); namePanel.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter a number between 2 and 10.");
            }
        });

        calcBtn.addActionListener(e -> {
            try {
                double tot = Double.parseDouble(totalField.getText().trim());
                int n = Integer.parseInt(peopleField.getText().trim());
                if (nameFields == null || nameFields.length != n) {
                    JOptionPane.showMessageDialog(this, "Click 'Load Names' first.");
                    return;
                }
                double each = tot / n;
                StringBuilder sb = new StringBuilder();
                sb.append("  SPLIT EXPENSE RESULT\n");
                sb.append("  ═══════════════════════════════\n");
                sb.append(String.format("  Total     :  ₹%.2f%n", tot));
                sb.append(String.format("  People    :  %d%n", n));
                sb.append(String.format("  Per person:  ₹%.2f%n", each));
                sb.append("  ───────────────────────────────\n");
                for (JTextField nf : nameFields)
                    sb.append(String.format("  %-16s →  ₹%.2f%n", nf.getText().trim(), each));
                sb.append("  ═══════════════════════════════\n");
                resultArea.setText(sb.toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid total amount.");
            }
        });

        setVisible(true);
    }
}
