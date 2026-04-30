import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class GraphUI extends JPanel {

    Map<String, Double> data;
    float animationProgress = 0f;
    Timer timer;
    Color[] colors = ChartColors.COLORS;

    static final Color BG_DARK     = new Color(10, 15, 30);
    static final Color CARD_DARK   = new Color(20, 28, 52);
    static final Color BORDER_DARK = new Color(40, 55, 90);
    static final Color TEXT_MUTED  = new Color(148, 163, 184);
    static final Color TEXT_WHITE  = Color.WHITE;

    public GraphUI(Map<String, Double> data) {
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
        g2.setFont(new Font("Arial", Font.BOLD, 26));
        g2.setColor(TEXT_WHITE);
        g2.drawString("Category Wise Expense", 220, 52);

        // Subtitle line
        g2.setColor(new Color(88, 101, 242));
        g2.setStroke(new BasicStroke(3f));
        g2.drawLine(220, 60, 600, 60);

        int left = 100, bottom = getHeight() - 80, right = getWidth() - 230;
        int chartHeight = bottom - 100;
        double max = data.values().stream().mapToDouble(Double::doubleValue).max().orElse(1000);
        if (max == 0) max = 1000;

        // Grid lines
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        for (int i = 0; i <= 5; i++) {
            int y = bottom - (i * chartHeight / 5);
            g2.setColor(BORDER_DARK);
            g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{4}, 0));
            g2.drawLine(left, y, right, y);
            g2.setStroke(new BasicStroke(1f));
            g2.setColor(TEXT_MUTED);
            g2.drawString("₹" + (int)(max * i / 5), 10, y + 5);
        }

        // Axes
        g2.setColor(new Color(60, 80, 130));
        g2.setStroke(new BasicStroke(2f));
        g2.drawLine(left, 80, left, bottom);
        g2.drawLine(left, bottom, right, bottom);

        // Bars
        int x = left + 40;
        int barWidth = Math.min(55, (right - left - 80) / Math.max(data.size(), 1) - 20);
        int gap = barWidth + 30;
        int i = 0;

        for (String cat : data.keySet()) {
            double value = data.get(cat);
            int barHeight = (int)(((value / max) * chartHeight) * animationProgress);
            int y = bottom - barHeight;

            // Bar shadow
            g2.setColor(new Color(colors[i % colors.length].getRed(),
                    colors[i % colors.length].getGreen(),
                    colors[i % colors.length].getBlue(), 40));
            g2.fillRoundRect(x + 4, y + 4, barWidth, barHeight, 12, 12);

            // Bar
            g2.setColor(colors[i % colors.length]);
            g2.fillRoundRect(x, y, barWidth, barHeight, 12, 12);

            // Top glow
            GradientPaint topGlow = new GradientPaint(x, y, new Color(255, 255, 255, 60),
                    x, y + barHeight/4, new Color(255, 255, 255, 0));
            g2.setPaint(topGlow);
            g2.fillRoundRect(x, y, barWidth, barHeight/4, 12, 12);

            // Value label
            g2.setFont(new Font("Arial", Font.BOLD, 11));
            g2.setColor(TEXT_WHITE);
            g2.drawString("₹" + (int)value, x - 2, y - 7);

            // Category label
            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            g2.setColor(TEXT_MUTED);
            g2.drawString(cat, x, bottom + 22);

            x += gap; i++;
        }

        // Legend box
        int boxX = right + 15, boxY = 90;
        g2.setColor(CARD_DARK);
        g2.fillRoundRect(boxX, boxY, 190, data.size() * 32 + 45, 14, 14);
        g2.setColor(BORDER_DARK);
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(boxX, boxY, 190, data.size() * 32 + 45, 14, 14);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.setColor(TEXT_WHITE);
        g2.drawString("Categories", boxX + 40, boxY + 24);

        int ly = boxY + 48; i = 0;
        for (String cat : data.keySet()) {
            g2.setColor(colors[i % colors.length]);
            g2.fillOval(boxX + 15, ly - 11, 12, 12);
            g2.setColor(TEXT_MUTED);
            g2.setFont(new Font("Arial", Font.PLAIN, 13));
            g2.drawString(cat, boxX + 36, ly);
            ly += 30; i++;
        }

        if (data.isEmpty()) {
            g2.setFont(new Font("Arial", Font.BOLD, 22));
            g2.setColor(TEXT_MUTED);
            g2.drawString("No expense data available", 200, 300);
        }
    }
}