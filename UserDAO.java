import java.sql.*;

public class UserDAO {

    public boolean register(String u, String p) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO users(username, password) VALUES(?, ?)"
            );
            ps.setString(1, u);
            ps.setString(2, PasswordUtil.hash(p));
            ps.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String u, String p) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE username=? AND password=?"
            );
            ps.setString(1, u);
            ps.setString(2, PasswordUtil.hash(p));
            ResultSet rs = ps.executeQuery();
            boolean found = rs.next();
            con.close();
            return found;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}