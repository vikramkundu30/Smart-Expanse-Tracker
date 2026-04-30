import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.time.LocalDate;

public class ReceiptScanner {

    // Same dark theme colors as HomeUI
    static final Color BG_DARK       = new Color(10, 15, 30);
    static final Color CARD_DARK     = new Color(20, 28, 52);
    static final Color FIELD_DARK    = new Color(12, 18, 38);
    static final Color BORDER_DARK   = new Color(40, 55, 90);
    static final Color ACCENT_PURPLE = new Color(88, 101, 242);
    static final Color ACCENT_GREEN  = new Color(46, 204, 113);
    static final Color TEXT_MUTED    = new Color(148, 163, 184);
    static final Color TEXT_WHITE    = Color.WHITE;

    public void scanReceipt() {
        JFileChooser ch = new JFileChooser();
        ch.setDialogTitle("Receipt Image Select Karo");
        int result = ch.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File f = ch.getSelectedFile();

            // ===== MAIN DIALOG =====
            JDialog dlg = new JDialog();
            dlg.setTitle("🧾 Receipt Scanner");
            dlg.setSize(700, 780);
            dlg.setLocationRelativeTo(null);
            dlg.setLayout(new BorderLayout(0, 0));
            dlg.getContentPane().setBackground(BG_DARK);

            // ===== TOP: File name label =====
            JPanel topBar = new JPanel(null);
            topBar.setBackground(new Color(15, 20, 40));
            topBar.setPreferredSize(new Dimension(700, 45));

            JLabel fileIcon = new JLabel("📄  " + f.getName());
            fileIcon.setFont(new Font("Arial", Font.BOLD, 14));
            fileIcon.setForeground(TEXT_WHITE);
            fileIcon.setBounds(15, 10, 600, 25);
            topBar.add(fileIcon);
            dlg.add(topBar, BorderLayout.NORTH);

            // ===== CENTER: Receipt Image =====
            ImageIcon raw = new ImageIcon(f.getAbsolutePath());
            Image scaled = raw.getImage().getScaledInstance(660, -1, Image.SCALE_SMOOTH);
            ImageIcon img = new ImageIcon(scaled);
            JLabel imgLabel = new JLabel(img);
            imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imgLabel.setBackground(new Color(8, 12, 25));
            imgLabel.setOpaque(true);

            JScrollPane sp = new JScrollPane(imgLabel);
            sp.setPreferredSize(new Dimension(700, 370));
            sp.setBorder(BorderFactory.createEmptyBorder());
            sp.getViewport().setBackground(new Color(8, 12, 25));
            dlg.add(sp, BorderLayout.CENTER);

            // ===== SOUTH: Expense Entry Form =====
            JPanel formPanel = new JPanel(null) {
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(CARD_DARK);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setColor(BORDER_DARK);
                    g2.drawLine(0, 0, getWidth(), 0); // top border line
                    g2.dispose();
                }
            };
            formPanel.setPreferredSize(new Dimension(700, 320));

            // Form title
            JLabel formTitle = new JLabel("➕  Add Expense from Receipt");
            formTitle.setFont(new Font("Arial", Font.BOLD, 16));
            formTitle.setForeground(TEXT_WHITE);
            formTitle.setBounds(20, 14, 350, 26);
            formPanel.add(formTitle);

            // ---- Amount ----
            JLabel aLbl = new JLabel("Amount (₹)");
            aLbl.setFont(new Font("Arial", Font.BOLD, 12));
            aLbl.setForeground(TEXT_MUTED);
            aLbl.setBounds(20, 55, 120, 20);
            formPanel.add(aLbl);

            JTextField amountField = darkField();
            amountField.setBounds(20, 78, 180, 38);
            formPanel.add(amountField);

            // ---- Category ----
            JLabel cLbl = new JLabel("Category");
            cLbl.setFont(new Font("Arial", Font.BOLD, 12));
            cLbl.setForeground(TEXT_MUTED);
            cLbl.setBounds(220, 55, 120, 20);
            formPanel.add(cLbl);

            String[] cats = {"Food", "Transport", "Shopping", "Bills", "Travel",
                    "Health", "Education", "Entertainment", "Other"};
            JComboBox<String> categoryBox = new JComboBox<>(cats);
            categoryBox.setBounds(220, 78, 200, 38);
            categoryBox.setBackground(FIELD_DARK);
            categoryBox.setForeground(TEXT_WHITE);
            categoryBox.setFont(new Font("Arial", Font.PLAIN, 13));
            formPanel.add(categoryBox);

            // ---- Date ----
            JLabel dateLbl = new JLabel("Date (YYYY-MM-DD)");
            dateLbl.setFont(new Font("Arial", Font.BOLD, 12));
            dateLbl.setForeground(TEXT_MUTED);
            dateLbl.setBounds(440, 55, 180, 20);
            formPanel.add(dateLbl);

            JTextField dateField = darkField();
            dateField.setText(LocalDate.now().toString()); // aaj ki date pre-filled
            dateField.setBounds(440, 78, 230, 38);
            formPanel.add(dateField);

            // ---- Description ----
            JLabel dLbl = new JLabel("Description");
            dLbl.setFont(new Font("Arial", Font.BOLD, 12));
            dLbl.setForeground(TEXT_MUTED);
            dLbl.setBounds(20, 132, 150, 20);
            formPanel.add(dLbl);

            JTextField descField = darkField();
            descField.setText(f.getName()); // file naam pre-filled
            descField.setBounds(20, 155, 650, 38);
            formPanel.add(descField);

            // ---- Save Button ----
            JButton saveBtn = new JButton("✅  SAVE EXPENSE") {
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    Color bg = getModel().isPressed() ? ACCENT_GREEN.darker()
                            : getModel().isRollover() ? ACCENT_GREEN.brighter()
                              : ACCENT_GREEN;
                    g2.setColor(bg);
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            saveBtn.setContentAreaFilled(false);
            saveBtn.setBorderPainted(false);
            saveBtn.setFocusPainted(false);
            saveBtn.setForeground(Color.WHITE);
            saveBtn.setFont(new Font("Arial", Font.BOLD, 15));
            saveBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            saveBtn.setBounds(20, 215, 300, 46);
            formPanel.add(saveBtn);

            // ---- Cancel Button ----
            JButton cancelBtn = new JButton("✖  CANCEL") {
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    Color bg2 = getModel().isRollover() ? new Color(80, 30, 30) : new Color(60, 20, 20);
                    g2.setColor(bg2);
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            cancelBtn.setContentAreaFilled(false);
            cancelBtn.setBorderPainted(false);
            cancelBtn.setFocusPainted(false);
            cancelBtn.setForeground(new Color(255, 100, 100));
            cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));
            cancelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            cancelBtn.setBounds(340, 215, 180, 46);
            formPanel.add(cancelBtn);

            dlg.add(formPanel, BorderLayout.SOUTH);

            // ===== BUTTON ACTIONS =====
            saveBtn.addActionListener(e -> {
                String amountText = amountField.getText().trim();
                String category   = (String) categoryBox.getSelectedItem();
                String date       = dateField.getText().trim();
                String desc       = descField.getText().trim();

                if (amountText.isEmpty()) {
                    JOptionPane.showMessageDialog(dlg,
                            "⚠️ Amount daalna zaroori hai!",
                            "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (date.isEmpty()) {
                    date = LocalDate.now().toString();
                }

                try {
                    double amount = Double.parseDouble(amountText);

                    Expense expense    = new Expense();
                    expense.amount     = amount;
                    expense.category   = category;
                    expense.date       = date;
                    expense.description = desc;

                    new ExpenseDAO().addExpense(expense); // ✅ Database mein save hoga

                    JOptionPane.showMessageDialog(dlg,
                            "✅ Expense successfully add ho gaya!\n\n" +
                                    "Amount:   ₹" + amount +
                                    "\nCategory: " + category +
                                    "\nDate:     " + date,
                            "Success", JOptionPane.INFORMATION_MESSAGE);

                    dlg.dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dlg,
                            "⚠️ Amount sirf number mein daalo! (e.g. 250 ya 99.50)",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            });

            cancelBtn.addActionListener(e -> dlg.dispose());

            dlg.setVisible(true);
        }
    }

    // ===== Helper: Dark styled text field =====
    private JTextField darkField() {
        JTextField f = new JTextField();
        f.setBackground(FIELD_DARK);
        f.setForeground(TEXT_WHITE);
        f.setCaretColor(TEXT_WHITE);
        f.setFont(new Font("Arial", Font.PLAIN, 14));
        f.setBorder(new CompoundBorder(
                new LineBorder(BORDER_DARK, 1, true),
                new EmptyBorder(5, 10, 5, 10)));
        return f;
    }
}
