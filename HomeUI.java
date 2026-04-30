import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomeUI extends JFrame {

    JTextField amountField, descField;
    JComboBox<String> categoryBox;
    JLabel totalLabel;
    String username;

    public HomeUI(String username) {
        this.username = username;
        Theme.apply();
        setTitle("ExpenseTrack");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        getContentPane().setBackground(Theme.BG);

        // ── Sidebar ───────────────────────────────────────────────────────────
        JPanel side = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Theme.SIDEBAR);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(Theme.BORDER);
                g2.drawLine(getWidth()-1, 0, getWidth()-1, getHeight());
                g2.dispose();
            }
        };
        side.setOpaque(false);
        side.setBounds(0, 0, 240, 1080);
        add(side);

        // Logo
        JLabel logoIcon = new JLabel("\u25c8");
        logoIcon.setFont(new Font("SansSerif", Font.BOLD, 28));
        logoIcon.setForeground(Theme.AMBER);
        logoIcon.setBounds(18, 22, 36, 36);
        side.add(logoIcon);

        JLabel logoText = Theme.label("ExpenseTrack", Theme.TEXT, Theme.title(17));
        logoText.setBounds(58, 24, 165, 28);
        side.add(logoText);

        // Amber divider line below logo
        JPanel logoDivider = new JPanel();
        logoDivider.setBackground(Theme.AMBER);
        logoDivider.setBounds(18, 64, 40, 2);
        side.add(logoDivider);

        // User card
        JPanel userCard = Theme.card(null);
        userCard.setBounds(14, 80, 212, 62);
        side.add(userCard);

        JLabel avt = new JLabel(String.valueOf(username.toUpperCase().charAt(0)));
        avt.setFont(Theme.title(16));
        avt.setForeground(new Color(20,20,20));
        avt.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel avtCircle = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Theme.AMBER);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        avtCircle.setOpaque(false);
        avtCircle.add(avt, BorderLayout.CENTER);
        avtCircle.setBounds(12, 12, 36, 36);
        userCard.add(avtCircle);

        JLabel uName = Theme.label(username, Theme.TEXT, Theme.title(13));
        uName.setBounds(56, 12, 148, 20);
        userCard.add(uName);

        JLabel uRole = Theme.label("Account Owner", Theme.TEXT_MUTED, Theme.body(11));
        uRole.setBounds(56, 34, 148, 18);
        userCard.add(uRole);

        // Section label
        JLabel navLabel = Theme.label("NAVIGATION", Theme.TEXT_MUTED, Theme.title(10));
        navLabel.setBounds(18, 158, 200, 18);
        side.add(navLabel);

        // Menu buttons
        String[][] menu = {
            {"\u25a3", "Dashboard"},
            {"\u25a4", "Expenses"},
            {"\u25a2", "Reports"},
            {"\u25a1", "Monthly Summary"},
            {"\u25c6", "Budget Forecast"},
            {"\u25cb", "Split Expense"},
            {"\u25cf", "Receipt Scan"},
            {"\u25cc", "Currencies"}
        };
        JButton[] menuBtns = new JButton[menu.length];
        for (int i = 0; i < menu.length; i++) {
            menuBtns[i] = sideBtn(menu[i][0], menu[i][1]);
            menuBtns[i].setBounds(12, 182 + i * 46, 216, 40);
            side.add(menuBtns[i]);
        }

        // Logout
        JPanel logoDivider2 = new JPanel();
        logoDivider2.setBackground(Theme.BORDER);
        logoDivider2.setBounds(14, 580, 212, 1);
        side.add(logoDivider2);

        JButton logoutBtn = sideBtn("\u25c4", "Sign Out");
        logoutBtn.setForeground(Theme.RED);
        logoutBtn.setBounds(12, 592, 216, 40);
        side.add(logoutBtn);

        // ── Top Bar ───────────────────────────────────────────────────────────
        JPanel topBar = Theme.header();
        topBar.setBounds(240, 0, 2000, 64);
        add(topBar);

        JLabel pageTitle = Theme.label("Dashboard", Theme.TEXT, Theme.title(22));
        pageTitle.setBounds(24, 16, 400, 32);
        topBar.add(pageTitle);

        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy"));
        JLabel dateLbl = Theme.label(dateStr, Theme.TEXT_MUTED, Theme.body(13));
        dateLbl.setBounds(240, 24, 400, 20);
        topBar.add(dateLbl);

        // ── Summary Cards ─────────────────────────────────────────────────────
        Color[] cAccents = {Theme.AMBER, Theme.TEAL, Theme.BLUE, Theme.RED};
        String[] cTitles = {"Total Expenses", "Budget Set", "Forecast (+15%)", "Budget Left"};
        JLabel[] cardVals = new JLabel[4];
        JPanel[] cards = new JPanel[4];
        int[] cx = {260, 570, 880, 1190};

        for (int i = 0; i < 4; i++) {
            cards[i] = Theme.card(cAccents[i]);
            cards[i].setBounds(cx[i], 80, 285, 130);
            add(cards[i]);

            JLabel ct = Theme.label(cTitles[i].toUpperCase(), Theme.TEXT_MUTED, Theme.title(10));
            ct.setBounds(18, 20, 249, 18);
            cards[i].add(ct);

            cardVals[i] = Theme.label("Loading...", Theme.TEXT, Theme.title(28));
            cardVals[i].setBounds(18, 48, 249, 44);
            cards[i].add(cardVals[i]);

            // small accent pill
            final Color pillAccent = cAccents[i];
            JPanel pill = new JPanel() {
                { setOpaque(false); }
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(pillAccent.getRed(), pillAccent.getGreen(), pillAccent.getBlue(), 40));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2.dispose();
                }
            };
            pill.setBounds(18, 100, 60, 14);
            cards[i].add(pill);
        }
        totalLabel = cardVals[0];
        refreshCards(cardVals);

        // ── Add Expense Form ──────────────────────────────────────────────────
        JPanel form = Theme.card(null);
        form.setBounds(260, 228, 1210, 155);
        add(form);

        JLabel formTitle = Theme.label("Add New Expense", Theme.TEXT, Theme.title(15));
        formTitle.setBounds(20, 14, 260, 24);
        form.add(formTitle);

        JPanel formDivider = new JPanel();
        formDivider.setBackground(Theme.AMBER);
        formDivider.setBounds(20, 40, 40, 2);
        form.add(formDivider);

        // Amount
        JLabel aLbl = Theme.label("AMOUNT (₹)", Theme.TEXT_MUTED, Theme.title(10));
        aLbl.setBounds(20, 52, 120, 18);
        form.add(aLbl);
        amountField = Theme.field();
        amountField.setBounds(20, 72, 155, 42);
        form.add(amountField);

        // Category
        JLabel cLbl = Theme.label("CATEGORY", Theme.TEXT_MUTED, Theme.title(10));
        cLbl.setBounds(190, 52, 120, 18);
        form.add(cLbl);
        String[] cats = {"Food","Transport","Shopping","Bills","Travel","Health","Education","Entertainment","Other"};
        categoryBox = new JComboBox<>(cats);
        categoryBox.setBounds(190, 72, 180, 42);
        Theme.styleCombo(categoryBox);
        form.add(categoryBox);

        // Description
        JLabel dLbl = Theme.label("DESCRIPTION", Theme.TEXT_MUTED, Theme.title(10));
        dLbl.setBounds(386, 52, 140, 18);
        form.add(dLbl);
        descField = Theme.field();
        descField.setBounds(386, 72, 290, 42);
        form.add(descField);

        // Date
        JLabel dateFldLbl = Theme.label("DATE", Theme.TEXT_MUTED, Theme.title(10));
        dateFldLbl.setBounds(692, 52, 80, 18);
        form.add(dateFldLbl);
        JTextField dateField = Theme.field();
        dateField.setText(LocalDate.now().toString());
        dateField.setBounds(692, 72, 168, 42);
        form.add(dateField);

        // Buttons
        JButton addBtn = Theme.button("+ Add Expense", Theme.AMBER);
        addBtn.setBounds(878, 72, 168, 42);
        addBtn.setFont(Theme.title(13));
        form.add(addBtn);

        JButton setBudgetBtn = Theme.button("Set Budget", Theme.TEAL);
        setBudgetBtn.setBounds(1056, 72, 140, 42);
        form.add(setBudgetBtn);

        // ── Quick Action Grid ─────────────────────────────────────────────────
        String[][] actions = {
            {"\ud83e\uddfe", "Upload Receipt"},
            {"\ud83d\udcb1", "Currencies"},
            {"\ud83d\udcca", "Analytics"},
            {"\ud83e\udd67", "Pie Chart"},
            {"\ud83d\udccb", "View Expenses"},
            {"\ud83d\udcc4", "Report"},
            {"\ud83e\udd1d", "Split Expense"},
            {"\ud83d\udcc5", "Monthly Summary"}
        };
        Color[] actionAccents = {Theme.AMBER, Theme.TEAL, Theme.BLUE,
            new Color(168,85,247), Theme.BLUE, Theme.GREEN, Theme.AMBER, Theme.TEAL};
        int[] ax = {260, 570, 880, 1190,  260, 570, 880, 1190};
        int[] ay = {400, 400, 400, 400,   510, 510, 510, 510};

        JButton[] tiles = new JButton[8];
        for (int i = 0; i < 8; i++) {
            tiles[i] = actionTile(actions[i][0], actions[i][1], actionAccents[i]);
            tiles[i].setBounds(ax[i], ay[i], 285, 88);
            add(tiles[i]);
        }

        // ── Action Listeners ──────────────────────────────────────────────────
        addBtn.addActionListener(e -> {
            try {
                Expense ex = new Expense();
                ex.amount      = Double.parseDouble(amountField.getText().trim());
                ex.category    = categoryBox.getSelectedItem().toString();
                ex.description = descField.getText().trim();
                ex.date        = dateField.getText().trim();
                if (ex.date.isEmpty()) ex.date = LocalDate.now().toString();
                new ExpenseDAO().addExpense(ex);
                JOptionPane.showMessageDialog(this, "Expense added successfully.");
                amountField.setText(""); descField.setText("");
                refreshCards(cardVals);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setBudgetBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter budget amount (₹):", "Set Budget", JOptionPane.QUESTION_MESSAGE);
            if (input != null && !input.isEmpty()) {
                try {
                    double b = Double.parseDouble(input);
                    new ExpenseDAO().setBudget(b);
                    JOptionPane.showMessageDialog(this, "Budget updated to ₹" + b);
                    refreshCards(cardVals);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount.");
                }
            }
        });

        tiles[0].addActionListener(e -> new ReceiptScanner().scanReceipt());
        tiles[1].addActionListener(e -> openCurrency());
        tiles[2].addActionListener(e -> new GraphFrame());
        tiles[3].addActionListener(e -> new PieChartFrame());
        tiles[4].addActionListener(e -> new ViewExpensesUI());
        tiles[5].addActionListener(e -> new ReportUI());
        tiles[6].addActionListener(e -> new SplitExpenseUI());
        tiles[7].addActionListener(e -> new MonthlySummaryUI());

        menuBtns[0].addActionListener(e -> new DashboardUI());
        menuBtns[1].addActionListener(e -> new ViewExpensesUI());
        menuBtns[2].addActionListener(e -> new ReportUI());
        menuBtns[3].addActionListener(e -> new MonthlySummaryUI());
        menuBtns[4].addActionListener(e -> new BudgetForecastUI());
        menuBtns[5].addActionListener(e -> new SplitExpenseUI());
        menuBtns[6].addActionListener(e -> new ReceiptScanner().scanReceipt());
        menuBtns[7].addActionListener(e -> openCurrency());

        logoutBtn.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "Sign out of ExpenseTrack?", "Sign Out", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) { dispose(); new LoginUI(); }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    void refreshCards(JLabel[] vals) {
        ExpenseDAO dao = new ExpenseDAO();
        double total   = dao.getTotalExpense();
        double budget  = dao.getBudget();
        double left    = budget - total;
        vals[0].setText("₹" + String.format("%.2f", total));
        vals[1].setText("₹" + String.format("%.2f", budget));
        vals[2].setText("₹" + String.format("%.2f", total * 1.15));
        vals[3].setText(left >= 0 ? "₹" + String.format("%.2f", left) : "−₹" + String.format("%.2f", Math.abs(left)));
    }

    void openCurrency() {
        String s = JOptionPane.showInputDialog(this, "Enter amount (INR):", "Currency Converter", JOptionPane.QUESTION_MESSAGE);
        if (s != null && !s.isEmpty()) {
            try { new CurrencyConverter().convertCurrency(Double.parseDouble(s)); }
            catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid amount."); }
        }
    }

    JButton sideBtn(String icon, String text) {
        JButton b = new JButton() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(Theme.SURFACE);
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                    // amber left accent
                    g2.setColor(Theme.AMBER);
                    g2.fillRoundRect(0, 6, 3, getHeight()-12, 3, 3);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setText(icon + "  " + text);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setForeground(Theme.TEXT_SEC);
        b.setFont(Theme.body(14));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBorder(new EmptyBorder(8, 14, 8, 8));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setForeground(Theme.TEXT); }
            public void mouseExited(java.awt.event.MouseEvent e)  { b.setForeground(Theme.TEXT_SEC); }
        });
        return b;
    }

    JButton actionTile(String emoji, String label, Color accent) {
        JButton b = new JButton() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isRollover() ? Theme.SURFACE2 : Theme.SURFACE;
                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 14, 14));
                // accent bottom bar
                g2.setColor(accent);
                g2.fillRoundRect(0, getHeight()-3, getWidth(), 3, 3, 3);
                // border
                g2.setColor(getModel().isRollover()
                    ? new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 100)
                    : Theme.BORDER);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 14, 14));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setText("<html><center>" + emoji + "<br><span style='font-size:11px'>" + label + "</span></center></html>");
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setForeground(Theme.TEXT_SEC);
        b.setFont(Theme.body(13));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setForeground(Theme.TEXT); }
            public void mouseExited(java.awt.event.MouseEvent e)  { b.setForeground(Theme.TEXT_SEC); }
        });
        return b;
    }
}
