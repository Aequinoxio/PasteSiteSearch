/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pastesitessearch;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author utente
 */
public class PasteSiteThreadTest {

    public PasteSiteThreadTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of haveToQuit method, of class PasteSiteThread.
     */
    @Test
    @Ignore
    public void testHaveToQuit() {
    }

    /**
     * Test of getPasteSite method, of class PasteSiteThread.
     */
    @Test
    @Ignore
    public void testGetPasteSite() {
    }

    /**
     * Test of run method, of class PasteSiteThread.
     */
    @Test
    @Ignore
    public void testRun() {
    }

    /**
     * Test of doAction method, of class PasteSiteThread.
     */
    @Test
    public void testDoAction() throws Exception {
        MySQLUtils mySQLUtils = new MySQLUtils();
        String pasteSiteUrl="https://www.pastebin.com";
        String pasteSiteRawUrl="https://www.pastebin.com/raw";
        SearchingPattern searchingPattern = SearchingPattern.getInstance();
        SiteParser siteParser = new PastebinParser(pasteSiteUrl, pasteSiteRawUrl, searchingPattern);
      //  PastebinParser siteParser = new PastebinParser(pasteSiteUrl, pasteSiteRawUrl, searchingPattern);

        try {
            mySQLUtils.startDBConnection("utente".toCharArray(),"utente".toCharArray());
            //mySQLUtils.insertPatternWithShortName(sp.getPattern());
            searchingPattern.setPattern(mySQLUtils.readPatternFromDB());
            //System.out.println(sp.getPattern().size());
           // PasteSiteRunnable pasteSiteThread = new PasteSiteRunnable("https://www.pastebin.com", pasteSiteRawUrl, searchingPattern, 30, 10, mySQLUtils);
            PasteSiteRunnable pasteSiteThread = new PasteSiteRunnable(siteParser, 30, 10, mySQLUtils);
            pasteSiteThread.doAction(siteParser);
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
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
