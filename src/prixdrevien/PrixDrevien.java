
package prixdrevien;

import com.formdev.flatlaf.FlatLightLaf;
import java.sql.SQLException;
import prixdrevien.db.maindb;
import prixdrevien.db.components.Folder;
import prixdrevien.main.pages.Home;
import java.sql.ResultSet;

public class PrixDrevien {

   
    
    public static void main(String[] args) {
        FlatLightLaf.setup();
        new Home().setVisible(true);
        
    }
}
