import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class Katalog {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/Katalog_Product";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);

    public static void main(String[] args) {

        try {
            // register driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            while (!conn.isClosed()) {
                showMenu();
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void showMenu() {
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("1. Insert Data");
        System.out.println("2. Show Data");
        System.out.println("3. Edit Data");
        System.out.println("4. Delete Data");
        System.out.println("0. Keluar");
        System.out.println("");
        System.out.print("PILIHAN> ");

        try {
            int pilihan = Integer.parseInt(input.readLine());

            switch (pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    insertProduct();
                    break;
                case 2:
                    showData();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                default:
                    System.out.println("Pilihan salah!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void showData() {
        String sql = "SELECT * FROM Product";

        try {
            rs = stmt.executeQuery(sql);
            
            System.out.println("+--------------------------------+");
            System.out.println("|    DATA DI KATALOG PRODUCT     |");
            System.out.println("+--------------------------------+");

            while (rs.next()) {
                int Id = rs.getInt("Id");
                String Category_Id = rs.getString("Category_Id");
                String Name = rs.getString("Name");
                String Description = rs.getString("Description");
                String Price = rs.getString("Price");
                String Status = rs.getString("Status");

                
                System.out.println(String.format("| %d | %s | %s | %s | %s | %s |", Id, Category_Id, Name, Description, Price, Status));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void insertProduct() {
        try {
            // ambil input dari user
            //tambahkan Id
            System.out.print("Id: ");
            int Id = Integer.parseInt(input.readLine());
            System.out.print("Category_Id: ");
            String Category_Id = input.readLine().trim();
            System.out.print("Name: ");
            String Name = input.readLine().trim();
            System.out.print("Description: ");
            String Description = input.readLine().trim();
            System.out.print("Price: ");
            String Price = input.readLine().trim();
            System.out.print("Status: ");
            String Status = input.readLine().trim();
 
            // query simpan
            String sql = "INSERT INTO Product (Id, Category_Id, Name, Description, Price, Status) VALUES ('%d', '%s', '%s', '%s', '%s', '%s')";
            sql = String.format(sql, Id, Category_Id, Name, Description, Price, Status);

            // simpan Product
            stmt.execute(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void updateProduct() {
        try {
            
            // ambil input dari user
            System.out.print("ID yang mau diedit: ");
            int Id = Integer.parseInt(input.readLine());
            System.out.print("Category_Id: ");
            String Category_Id = input.readLine().trim();
            System.out.print("Name: ");
            String Name = input.readLine().trim();
            System.out.print("Description: ");
            String Description = input.readLine().trim();
            System.out.print("Price: ");
            String Price = input.readLine().trim();
            System.out.print("Status: ");
            String Status = input.readLine().trim();

            // query update
            String sql = "UPDATE Product SET Category_Id='%s', Name='%s', Description='%s', Price='%s', Status='%s' WHERE Id=%d";
            sql = String.format(sql, Category_Id, Name, Description, Price, Status, Id);

            // update data Product
            stmt.execute(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteProduct() {
        try {
            
            // ambil input dari user
            System.out.print("ID yang mau dihapus: ");
            int Id = Integer.parseInt(input.readLine());
            
            // buat query hapus
            String sql = String.format("DELETE FROM Product WHERE Id=%d", Id);

            // hapus data
            stmt.execute(sql);
            
            System.out.println("Data telah terhapus...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}