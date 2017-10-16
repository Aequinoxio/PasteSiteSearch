/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pastesitessearch;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author utente
 */
public class PasteSitesSearch {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MySQLUtils mySQLUtils = new MySQLUtils();
        SearchingPattern sp = SearchingPattern.getInstance();
        try {
            mySQLUtils.startDBConnection();
            //mySQLUtils.insertPatternWithShortName(sp.getPattern());
            sp.setPattern(mySQLUtils.readPatternFromDB());
            //System.out.println(sp.getPattern().size());
            PasteSiteRunnable pasteSiteRunnable = new PasteSiteRunnable("https://www.pastebin.com", sp, 30, 5, mySQLUtils);
            Thread t = new Thread(pasteSiteRunnable);
            t.start();
            //pasteSiteThread.run();
            System.out.println(String.format("Time: %s", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())));
            Thread.currentThread().sleep(1 * 60 * 1000); // Aspetto un po' e poi mi fermo
            
            System.out.println(String.format("Time: %s", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())));
            pasteSiteRunnable.haveToQuit();
            
            Thread.currentThread().sleep(5 * 60 * 1000); // Aspetto 4 minuti e poi mi fermo definitivamente per far completare i thread
            System.out.println(String.format("Time: %s", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())));
            
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(PasteSitesSearch.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(PasteSitesSearch.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                mySQLUtils.closeDBConnection();
            } catch (SQLException ex) {
                Logger.getLogger(PasteSitesSearch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
