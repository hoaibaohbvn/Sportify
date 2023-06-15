package duan.sportify.controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
	public static void main(String[] args) {
        Connection conn = null;

        try {
            // Thiết lập thông tin kết nối
            String url = "jdbc:mysql://localhost:3306/sportify";
            String username = "root";
            String password = "12345";

            // Thiết lập kết nối
            conn = DriverManager.getConnection(url, username, password);

            // Kết nối thành công
            System.out.println("Kết nối thành công vào schema");

            // Các thao tác với schema ở đây...
        } catch (SQLException e) {
            // Xử lý lỗi kết nối
            e.printStackTrace();
        } finally {
            // Đóng kết nối
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Đóng kết nối thành công");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
