package pkg123230020;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import static java.awt.image.ImageObserver.HEIGHT;

/**
 *
 * @author Zainur Fadli
 */
public class Connector {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/movie_db";
    static final String USER = "root";
    static final String PASS = "";
    
    public Connection koneksi;
    public Statement statement;
    
    public Connector() {
        try {
            Class.forName(JDBC_DRIVER);
            koneksi = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Koneksi Berhasil");
        } catch(Exception ex) {
            System.out.println("Koneksi Gagal");
            ex.printStackTrace();
        }
    }
    
    public boolean insertData(String judul, float alur , float penokohan, float akting) throws SQLException {
        try {
            if(judul.isEmpty()) throw new Exception("Judul Kosong");
            else if(alur == 0.0f) throw new Exception("Alur Kosong");
            else if(penokohan == 0.0f) throw new Exception("Penokohan Kosong");
            else if(akting == 0.0f) throw new Exception("Akting Kosong");
            else {
                statement = koneksi.createStatement();
                String query = "INSERT INTO movie(judul, alur, penokohan, akting) values (?,?,?,?)";
                PreparedStatement prepareStatement = koneksi.prepareStatement(query);
                prepareStatement.setString(1, judul);
                prepareStatement.setFloat(2, alur);
                prepareStatement.setFloat(3, penokohan);
                prepareStatement.setFloat(4, akting);
                prepareStatement.executeUpdate();
                return true;
            }
            
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(), "Alert", HEIGHT);
        }
        
        koneksi.close();
        return false;
    }
    
    public boolean deleteData (int id) {
        try {
            String query = "DELETE from movie when id = ?";
            PreparedStatement ps = koneksi.prepareStatement(query);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateData (int id, String judul, float alur , float penokohan, float akting) {
        try {
            String query = "UPDATE movie SET judul = ?, alur = ?, penokohan = ?, akting = ? WHERE id = ?";
            PreparedStatement ps = koneksi.prepareStatement(query);
            ps.setString(1, judul);
            ps.setFloat(2, alur);
            ps.setFloat(3, penokohan);
            ps.setFloat(4, akting);
            ps.setInt(5, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
