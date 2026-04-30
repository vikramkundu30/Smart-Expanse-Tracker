import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Apply dark L&F globally
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Global dark overrides for JOptionPane, JComboBox, etc.
        Color bg    = new Color(20, 28, 52);
        Color fg    = Color.WHITE;
        Color acc   = new Color(88, 101, 242);

        UIManager.put("Panel.background",              bg);
        UIManager.put("OptionPane.background",         bg);
        UIManager.put("OptionPane.messageForeground",  fg);
        UIManager.put("Button.background",             acc);
        UIManager.put("Button.foreground",             fg);
        UIManager.put("Button.opaque",                 true);
        UIManager.put("ComboBox.background",           new Color(12, 18, 38));
        UIManager.put("ComboBox.foreground",           fg);
        UIManager.put("ComboBox.selectionBackground",  acc);
        UIManager.put("ComboBox.selectionForeground",  fg);
        UIManager.put("TextField.background",          new Color(12, 18, 38));
        UIManager.put("TextField.foreground",          fg);
        UIManager.put("TextField.caretForeground",     fg);
        UIManager.put("PasswordField.background",      new Color(12, 18, 38));
        UIManager.put("PasswordField.foreground",      fg);
        UIManager.put("ScrollPane.background",         bg);
        UIManager.put("Viewport.background",           bg);
        UIManager.put("List.background",               bg);
        UIManager.put("List.foreground",               fg);
        UIManager.put("List.selectionBackground",      acc);
        UIManager.put("List.selectionForeground",      fg);

        SwingUtilities.invokeLater(() -> new LoginUI());
    }
}