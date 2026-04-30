import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PieChartUI extends JPanel {

    Map<String, Double> data;
    float animationProgress = 0f;
    Timer timer;
    Color[] colors = ChartColors.COLORS;

    static final Color BG_DARK     = new Color(10, 15, 30);
    static final Color CARD_DARK   = new Color(20, 28, 52);
    static final Color BORDER_DARK = new Color(40, 55, 90);
    static final Color TEXT_MUTED  = new Color(148, 163, 184);
    static final Color TEXT_WHITE  = Color.WHITE;

    public PieChartUI(Map<String, Double> data) {
        this.data = data;
        setBackground(BG_DARK);
        timer = new Timer(15, e -> {
            animationProgress += 0.02f;
            if (animationProgress >= 1f) { animationProgress = 1f; timer.stop(); }
            repaint();
        });
        timer.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Title
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.setColor(TEXT_WHITE);
        g2.drawString("Category Expense Distribution", 260, 58);

        g2.setColor(new Color(88, 101, 242));
        g2.setStroke(new BasicStroke(3f));
        g2.drawLine(260, 66, 720, 66);

        if (data.isEmpty()) {
            g2.setFont(new Font("Arial", Font.BOLD, 22));
            g2.setColor(TEXT_MUTED);
            g2.drawString("No expense data available!", 350, 350);
            return;
        }

        double total = data.values().stream().mapToDouble(v -> v).sum();
        int size = 380;
        int x = getWidth() / 2 - size / 2 - 120;
        int y = 100;

        // Pie slices
        int start = 0; int i = 0;
        for (String cat : data.keySet()) {
            double val = data.get(cat);
            int angle = (int)(((val / total) * 360) * animationProgress);
            Color c = colors[i % colors.length];

            // Shadow
            g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 50));
            g2.fillArc(x + 6, y + 6, size, size, start, angle);

            // Slice
            g2.setColor(c);
            g2.fillArc(x, y, size, size, start, angle);

            // Slice border
            g2.setColor(new Color(BG_DARK.getRed(), BG_DARK.getGreen(), BG_DARK.getBlue(), 180));
            g2.setStroke(new BasicStroke(2f));
            g2.drawArc(x, y, size, size, start, angle);

            start += angle; i++;
        }

        // Donut hole
        g2.setColor(BG_DARK);
        g2.fillOval(x + 90, y + 90, 200, 200);

        // Center text
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(TEXT_MUTED);
        g2.drawString("Total", x + 138, y + 172);
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        g2.setColor(TEXT_WHITE);
        g2.drawString(String.format("₹%.0f", total), x + 118, y + 198);

        // Legend box
        int lx = x + size + 90, ly = y + 10;
        g2.setColor(CARD_DARK);
        g2.fillRoundRect(lx, ly, 270, data.size() * 38 + 55, 18, 18);
        g2.setColor(BORDER_DARK);
        g2.setStroke(new BasicStroke(1.2f));
        g2.drawRoundRect(lx, ly, 270, data.size() * 38 + 55, 18, 18);

        g2.setFont(new Font("Arial", Font.BOLD, 15));
        g2.setColor(TEXT_WHITE);
        g2.drawString("Category Breakdown", lx + 30, ly + 28);

        int iy = ly + 55; i = 0;
        for (String cat : data.keySet()) {
            double val = data.get(cat);
            double pct = (val / total) * 100;
            g2.setColor(colors[i % colors.length]);
            g2.fillOval(lx + 14, iy - 12, 13, 13);
            g2.setColor(TEXT_MUTED);
            g2.setFont(new Font("Arial", Font.PLAIN, 13));
            g2.drawString(String.format("%s  %.1f%%  ₹%.0f", cat, pct, val), lx + 36, iy);
            iy += 34; i++;
        }
    }
}