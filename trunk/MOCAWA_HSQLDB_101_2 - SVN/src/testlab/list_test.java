/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package testlab;


import java.sql.*;

/**
 *
 * @author suatatan
 * HSQLDB ile saf baglanti kurma testi
 */
public class list_test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     
        
        try {
            System.out.println("1");
            
            Class.forName("org.hsqldb.jdbcDriver");
            System.out.println("2");
            Connection conn= DriverManager.getConnection("jdbc:hsqldb:file:dbmocawa/db");
            System.out.println("3");
            Statement query = (Statement) conn.createStatement();
            System.out.println("4");
            ResultSet SonucVeriler = query.executeQuery("SELECT * FROM TEKLIFLER");
            System.out.println("5");
            while(SonucVeriler.next())
            {
                System.out.println(SonucVeriler.getString("TEKLIFNO"));
            }
            
            conn.close();
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
