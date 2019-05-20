/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pastesitessearch;

import java.io.Console;
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
    
    enum ConsoleType{
        username, password;
    }

    /**
     * Read the password in consolle
     * @param t
     * @return The password or null in case of error
     */
    private char[] readPassword(ConsoleType t) {
        Console console = System.console();
        if (console == null) {
            System.out.println("Errore nell'acquisire la console");
            return null;
        }

        //console.printf("Password:");
        char pwd[];
        if (ConsoleType.password==t)
            pwd = console.readPassword("Password: ");     
        else 
            pwd= console.readLine("Username: ").toCharArray();     
       
        return pwd;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MySQLUtils mySQLUtils = new MySQLUtils();
        SearchingPattern sp = SearchingPattern.getInstance();

        PasteSitesSearch pasteSitesSearch = new PasteSitesSearch();
        char[] user = pasteSitesSearch.readPassword(ConsoleType.username);
        char[] pwd = pasteSitesSearch.readPassword(ConsoleType.password);

        if (user==null || pwd==null){
            System.out.println("Errore: Username o password nulli");
            // TODO: ELIMINARE -- DEBUG
            user="utente".toCharArray();
            pwd="utente".toCharArray();
            ///////////
        }
//        System.out.println("user:"+new String(user));
//        System.out.println("pwd:"+new String(pwd));
//exit(0);
        try {
            mySQLUtils.startDBConnection(user, pwd);

            // azzero le variabili critiche
            for (int i = 0; i < user.length; i++) {
                user[i] = '0';
            }

            for (int i = 0; i < pwd.length; i++) {
                pwd[i] = '0';
            }

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

            System.out.println(String.format("All threads started @: %s%n\tNow sleeping", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())));
            Thread.sleep(1 * 60 * 1000); // Aspetto un po' e poi mi fermo

            System.out.println(String.format("Yawn, waked up @: %s%n\tSend quit command", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())));
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
