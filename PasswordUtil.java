import java.security.MessageDigest;

public class PasswordUtil {

    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] arr = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : arr) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
}