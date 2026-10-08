


package BrowserProject.src.main.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataBaseManager{

    private static final String url="jdbc:mysql://localhost:3306/LruDB";
    private static final String user="root";
    private static final String pass="Radhika*123";

    public static void createDatabase() throws Exception{
        Connection con=DriverManager.getConnection(url,user,pass);
        Statement st=con.createStatement();
        st.executeUpdate("CREATE DATABASE IF NOT EXISTS LruDB");
        System.out.println("Database created successfully.");
        st.close();
        con.close();
    }
}