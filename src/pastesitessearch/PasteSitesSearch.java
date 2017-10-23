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
            SiteParser pasteBinParser = new PastebinParser("https://www.pastebin.com", "https://www.pastebin.com/raw", sp);
            PasteSiteRunnable pasteSiteRunnablePastebin = new PasteSiteRunnable(pasteBinParser, 30, 5, mySQLUtils);
            Thread tPastebin = new Thread(pasteSiteRunnablePastebin);
            tPastebin.start();
            
            SiteParser slexyParser = new SlexyParser("https://slexy.org/", "https://slexy.org", sp);
            PasteSiteRunnable pasteSiteRunnableSlexy = new PasteSiteRunnable(slexyParser, 30, 5, mySQLUtils);
            Thread tSlexy = new Thread(pasteSiteRunnableSlexy);
            tSlexy.start();

            System.out.println(String.format("All threads started @: %s\n\tNow sleeping", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())));
            Thread.sleep(1 * 60 * 1000); // Aspetto un po' e poi mi fermo

            System.out.println(String.format("Yawn, waked up @: %s\n\tSend quit command", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())));
            pasteSiteRunnablePastebin.haveToQuit();
            pasteSiteRunnableSlexy.haveToQuit();

            Thread.sleep(5 * 60 * 1000); // Aspetto 5 minuti e poi mi fermo definitivamente per far completare i thread
            System.out.println(String.format("Done @: %s", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())));

        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException | InterruptedException ex) {
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
