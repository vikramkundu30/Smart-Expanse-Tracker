import java.sql.*;
import java.util.*;

public class ExpenseDAO {

    public void addExpense(Expense e) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO expenses(amount, category, date, description) VALUES(?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, e.amount);
            ps.setString(2, e.category);
            ps.setString(3, e.date);
            ps.setString(4, e.description);
            ps.executeUpdate();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteExpense(int id) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM expenses WHERE expense_id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String[]> getAllExpenses() {
        List<String[]> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM expenses ORDER BY date DESC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] row = {
                        String.valueOf(rs.getInt("expense_id")),
                        String.valueOf(rs.getDouble("amount")),
                        rs.getString("category"),
                        rs.getString("date"),
                        rs.getString("description")
                };
                list.add(row);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public double getTotalExpense() {
        double total = 0;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT SUM(amount) FROM expenses");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) total = rs.getDouble(1);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public Map<String, Double> getCategoryReport() {
        Map<String, Double> map = new LinkedHashMap<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT category, SUM(amount) total FROM expenses GROUP BY category";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("category"), rs.getDouble("total"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map<String, Double> getMonthlyReport() {
        Map<String, Double> map = new LinkedHashMap<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT DATE_FORMAT(date, '%Y-%m') AS month, SUM(amount) total " +
                    "FROM expenses GROUP BY month ORDER BY month";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("month"), rs.getDouble("total"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public void setBudget(double amount) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement check = con.prepareStatement("SELECT * FROM budget WHERE id=1");
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                PreparedStatement ps = con.prepareStatement("UPDATE budget SET amount=? WHERE id=1");
                ps.setDouble(1, amount);
                ps.executeUpdate();
            } else {
                PreparedStatement ps = con.prepareStatement("INSERT INTO budget(id, amount) VALUES(1, ?)");
                ps.setDouble(1, amount);
                ps.executeUpdate();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getBudget() {
        double budget = 0;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT amount FROM budget WHERE id=1");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) budget = rs.getDouble(1);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return budget;
    }

    public List<String[]> filter(String category, String date) {
        List<String[]> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM expenses WHERE 1=1");
            if (category != null && !category.isEmpty()) sql.append(" AND category=?");
            if (date != null && !date.isEmpty()) sql.append(" AND date=?");
            sql.append(" ORDER BY date DESC");

            PreparedStatement ps = con.prepareStatement(sql.toString());
            int idx = 1;
            if (category != null && !category.isEmpty()) ps.setString(idx++, category);
            if (date != null && !date.isEmpty()) ps.setString(idx, date);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] row = {
                        String.valueOf(rs.getDouble("amount")),
                        rs.getString("category"),
                        rs.getString("date"),
                        rs.getString("description")
                };
                list.add(row);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}