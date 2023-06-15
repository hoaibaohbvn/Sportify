package duan.sportify.controller;
import java.sql.*;
public class MySQLExample {
	public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/sportify";
        String username = "root";
        String password = "123456";

        try {
            // Tạo kết nối
            Connection connection = DriverManager.getConnection(url, username, password);

            // Tạo câu lệnh SQL
            String sql = "SELECT * FROM eventweb";

            // Tạo đối tượng PreparedStatement
            PreparedStatement statement = connection.prepareStatement(sql);

            // Thực thi câu lệnh SQL và nhận kết quả
            ResultSet resultSet = statement.executeQuery();

            // Xử lý kết quả
            while (resultSet.next()) {
                String name = resultSet.getString("nameevent");
                String email = resultSet.getString("datestart");
                System.out.println("Name: " + name + ", s: " + email);
            }

            // Đóng kết nối và giải phóng tài nguyên
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
