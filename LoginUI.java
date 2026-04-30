import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;

public class LoginUI extends JFrame {

    JTextField     userField;
    JPasswordField passField;

    public LoginUI() {
        Theme.apply();
        setTitle("Expense Tracker");
        setSize(480, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Theme.BG);

        // ── Background canvas ─────────────────────────────────────────────────
        JPanel canvas = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 8));
                for (int x = 0; x < getWidth(); x += 28)
                    for (int y = 0; y < getHeight(); y += 28)
                        g2.fillOval(x, y, 2, 2);
                g2.setPaint(new RadialGradientPaint(0, 0, 300,
                    new float[]{0f, 1f},
                    new Color[]{new Color(251, 191, 36, 35), new Color(0, 0, 0, 0)}));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        canvas.setBackground(Theme.BG);
        setContentPane(canvas);

        JLabel iconLbl = new JLabel("\u25c8");
        iconLbl.setFont(new Font("SansSerif", Font.BOLD, 42));
        iconLbl.setForeground(Theme.AMBER);
        iconLbl.setBounds(199, 42, 60, 52);
        iconLbl.setHorizontalAlignment(SwingConstants.CENTER);
        canvas.add(iconLbl);

        JLabel appName = Theme.label("ExpenseTrack", Theme.TEXT, Theme.title(26));
        appName.setBounds(90, 98, 300, 36);
        appName.setHorizontalAlignment(SwingConstants.CENTER);
        canvas.add(appName);

        JLabel tagline = Theme.label("Clarity in every rupee", Theme.TEXT_MUTED, Theme.body(13));
        tagline.setBounds(100, 135, 280, 22);
        tagline.setHorizontalAlignment(SwingConstants.CENTER);
        canvas.add(tagline);

        JPanel card = Theme.card(Theme.AMBER);
        card.setBounds(40, 176, 400, 345);
        canvas.add(card);

        JLabel welcome = Theme.label("Welcome back", Theme.TEXT, Theme.title(20));
        welcome.setBounds(28, 22, 250, 30);
        card.add(welcome);

        JLabel sub = Theme.label("Sign in to your account", Theme.TEXT_MUTED, Theme.body(13));
        sub.setBounds(28, 55, 240, 22);
        card.add(sub);

        JLabel uLbl = Theme.label("USERNAME", Theme.TEXT_MUTED, Theme.title(11));
        uLbl.setBounds(28, 92, 200, 18);
        card.add(uLbl);

        userField = Theme.field();
        userField.setBounds(28, 112, 344, 42);
        card.add(userField);

        JLabel pLbl = Theme.label("PASSWORD", Theme.TEXT_MUTED, Theme.title(11));
        pLbl.setBounds(28, 168, 200, 18);
        card.add(pLbl);

        passField = Theme.passField();
        passField.setBounds(28, 188, 344, 42);
        card.add(passField);

        JButton loginBtn = Theme.button("Sign In", Theme.AMBER);
        loginBtn.setBounds(28, 252, 162, 48);
        loginBtn.setFont(Theme.title(14));
        card.add(loginBtn);

        JButton regBtn = Theme.button("Create Account", Theme.SURFACE2);
        regBtn.setBounds(202, 252, 170, 48);
        regBtn.setFont(Theme.title(13));
        regBtn.setForeground(Theme.TEXT_SEC);
        card.add(regBtn);

        loginBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            if (user.isEmpty() || pass.isEmpty()) { showErr("Enter username and password."); return; }
            if (new UserDAO().login(user, pass)) { dispose(); new HomeUI(user); }
            else showErr("Invalid username or password.");
        });

        regBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            if (user.isEmpty() || pass.isEmpty()) { showErr("Enter username and password."); return; }
            if (new UserDAO().register(user, pass))
                JOptionPane.showMessageDialog(this, "Account created! You can now sign in.");
            else showErr("Username already taken.");
        });

        passField.addActionListener(e -> loginBtn.doClick());
        setVisible(true);
    }

    void showErr(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
