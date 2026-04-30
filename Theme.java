import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Centralized design system for the Expense Tracker.
 * Aesthetic: Deep charcoal + warm amber/gold + clean whites
 */
public class Theme {

    // ── Core Palette ─────────────────────────────────────────────────────────
    public static final Color BG          = new Color(14, 14, 18);    // near-black canvas
    public static final Color SURFACE     = new Color(22, 22, 28);    // card bg
    public static final Color SURFACE2    = new Color(30, 30, 38);    // elevated surface
    public static final Color BORDER      = new Color(48, 48, 60);    // subtle border
    public static final Color FIELD_BG    = new Color(18, 18, 24);    // input bg

    // ── Accents ───────────────────────────────────────────────────────────────
    public static final Color AMBER       = new Color(251, 191,  36); // primary accent
    public static final Color AMBER_DARK  = new Color(180, 130,  20);
    public static final Color TEAL        = new Color( 20, 184, 166); // secondary
    public static final Color RED         = new Color(239,  68,  68); // danger
    public static final Color GREEN       = new Color( 34, 197,  94); // success
    public static final Color BLUE        = new Color( 59, 130, 246); // info

    // ── Text ─────────────────────────────────────────────────────────────────
    public static final Color TEXT        = new Color(248, 248, 252); // primary text
    public static final Color TEXT_SEC    = new Color(160, 160, 180); // secondary
    public static final Color TEXT_MUTED  = new Color(100, 100, 120); // muted

    // ── Sidebar ───────────────────────────────────────────────────────────────
    public static final Color SIDEBAR     = new Color(10, 10, 14);

    // ── Typography ────────────────────────────────────────────────────────────
    public static Font font(int style, int size) {
        // Use Trebuchet MS as a distinctive, readable font available on all platforms
        Font f = new Font("Trebuchet MS", style, size);
        if (f.getFamily().equals("Dialog")) f = new Font("SansSerif", style, size);
        return f;
    }
    public static Font title(int size)   { return font(Font.BOLD,  size); }
    public static Font body(int size)    { return font(Font.PLAIN, size); }
    public static Font mono(int size)    { return new Font("Consolas", Font.PLAIN, size); }

    // ── Helpers ───────────────────────────────────────────────────────────────
    /** Styled rounded JButton */
    public static JButton button(String text, Color bg) {
        JButton b = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c = getModel().isPressed()  ? bg.darker().darker() :
                          getModel().isRollover() ? bg.brighter()        : bg;
                g2.setColor(c);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                // subtle top highlight
                g2.setColor(new Color(255,255,255, getModel().isRollover() ? 30 : 15));
                g2.fillRoundRect(0, 0, getWidth(), getHeight()/2, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setForeground(bg.equals(AMBER) ? new Color(20,20,20) : Color.WHITE);
        b.setFont(title(13));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    /** Styled text field */
    public static JTextField field() {
        JTextField f = new JTextField();
        f.setBackground(FIELD_BG);
        f.setForeground(TEXT);
        f.setCaretColor(AMBER);
        f.setFont(body(14));
        f.setBorder(new CompoundBorder(
            new LineBorder(BORDER, 1, true),
            new EmptyBorder(6, 12, 6, 12)
        ));
        // focus highlight
        f.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                f.setBorder(new CompoundBorder(
                    new LineBorder(AMBER, 1, true),
                    new EmptyBorder(6, 12, 6, 12)));
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                f.setBorder(new CompoundBorder(
                    new LineBorder(BORDER, 1, true),
                    new EmptyBorder(6, 12, 6, 12)));
            }
        });
        return f;
    }

    /** Styled password field */
    public static JPasswordField passField() {
        JPasswordField f = new JPasswordField();
        f.setBackground(FIELD_BG);
        f.setForeground(TEXT);
        f.setCaretColor(AMBER);
        f.setFont(body(14));
        f.setBorder(new CompoundBorder(
            new LineBorder(BORDER, 1, true),
            new EmptyBorder(6, 12, 6, 12)
        ));
        f.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                f.setBorder(new CompoundBorder(
                    new LineBorder(AMBER, 1, true),
                    new EmptyBorder(6, 12, 6, 12)));
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                f.setBorder(new CompoundBorder(
                    new LineBorder(BORDER, 1, true),
                    new EmptyBorder(6, 12, 6, 12)));
            }
        });
        return f;
    }

    /** Styled label */
    public static JLabel label(String text, Color color, Font font) {
        JLabel l = new JLabel(text);
        l.setForeground(color);
        l.setFont(font);
        return l;
    }

    /** Rounded panel with surface background and optional accent top bar */
    public static JPanel card(Color accent) {
        return new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // shadow effect (draw slightly offset dark rect)
                g2.setColor(new Color(0, 0, 0, 60));
                g2.fill(new RoundRectangle2D.Float(3, 4, getWidth()-2, getHeight()-2, 16, 16));
                // card body
                g2.setColor(SURFACE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));
                // accent top bar
                if (accent != null) {
                    g2.setColor(accent);
                    g2.fillRoundRect(0, 0, getWidth(), 4, 4, 4);
                }
                // subtle border
                g2.setColor(BORDER);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 16, 16));
                g2.dispose();
            }
        };
    }

    /** Gradient header panel */
    public static JPanel header() {
        return new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(22, 22, 32), getWidth(), 0, new Color(28, 24, 40));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // bottom divider
                g2.setColor(BORDER);
                g2.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
                g2.dispose();
            }
        };
    }

    /** Configure a JComboBox to match the theme */
    public static void styleCombo(JComboBox<String> cb) {
        cb.setBackground(FIELD_BG);
        cb.setForeground(TEXT);
        cb.setFont(body(13));
        cb.setBorder(new LineBorder(BORDER, 1, true));
        cb.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object v, int i, boolean sel, boolean focus) {
                JLabel l = (JLabel) super.getListCellRendererComponent(list, v, i, sel, focus);
                l.setBackground(sel ? SURFACE2 : FIELD_BG);
                l.setForeground(sel ? AMBER : TEXT);
                l.setBorder(new EmptyBorder(4, 10, 4, 10));
                return l;
            }
        });
    }

    /** Style scroll panes */
    public static void styleScroll(JScrollPane sp) {
        sp.getViewport().setBackground(BG);
        sp.setBorder(BorderFactory.createLineBorder(BORDER));
        sp.getVerticalScrollBar().setBackground(SURFACE);
        sp.getHorizontalScrollBar().setBackground(SURFACE);
    }

    /** Style table */
    public static void styleTable(JTable table) {
        table.setFont(body(14));
        table.setRowHeight(34);
        table.setBackground(BG);
        table.setForeground(TEXT);
        table.setGridColor(new Color(36, 36, 46));
        table.setShowGrid(true);
        table.setSelectionBackground(new Color(251, 191, 36, 60));
        table.setSelectionForeground(TEXT);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.getTableHeader().setFont(title(13));
        table.getTableHeader().setBackground(SURFACE2);
        table.getTableHeader().setForeground(TEXT_SEC);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, AMBER));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
    }

    /** Apply global Swing defaults */
    public static void apply() {
        UIManager.put("OptionPane.background",         SURFACE);
        UIManager.put("Panel.background",              SURFACE);
        UIManager.put("OptionPane.messageForeground",  TEXT);
        UIManager.put("OptionPane.messageFont",        body(14));
        UIManager.put("Button.background",             SURFACE2);
        UIManager.put("Button.foreground",             TEXT);
        UIManager.put("Button.font",                   title(13));
        UIManager.put("TextField.background",          FIELD_BG);
        UIManager.put("TextField.foreground",          TEXT);
        UIManager.put("TextField.caretForeground",     AMBER);
    }
}
