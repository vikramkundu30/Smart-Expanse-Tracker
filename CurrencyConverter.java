import javax.swing.*;
import java.io.*;
import java.net.*;

public class CurrencyConverter {

    public void convertCurrency(double inr) {
        String[] currencies = {"USD", "EUR", "GBP", "JPY", "AUD", "CAD", "SGD", "AED", "CNY", "RUB", "CHF", "KRW", "THB", "MYR", "SAR", "INR"};

        String to = (String) JOptionPane.showInputDialog(null,
                "Convert ₹" + inr + " to which currency?",
                "💱 Live Currency Converter",
                JOptionPane.QUESTION_MESSAGE, null, currencies, currencies[0]);

        if (to == null) return;
        if (to.equals("INR")) {
            JOptionPane.showMessageDialog(null, "₹" + inr + " = ₹" + inr + " (INR)");
            return;
        }

        try {
            URL url = new URL("https://open.er-api.com/v6/latest/INR");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder data = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) data.append(line);
            br.close();

            String key = "\"" + to + "\":";
            int start = data.indexOf(key) + key.length();
            int end = data.indexOf(",", start);
            if (end == -1) end = data.indexOf("}", start);

            double rate = Double.parseDouble(data.substring(start, end).trim());
            double converted = inr * rate;

            JOptionPane.showMessageDialog(null,
                    String.format("💱 Live Rate Conversion%n%n₹%.2f INR  =  %.4f %s%n%nRate: 1 INR = %.6f %s",
                            inr, converted, to, rate, to));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Internet/API Error!\nCheck Internet connection.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}