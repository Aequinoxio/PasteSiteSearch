/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pastesitessearch;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author utente
 */
public class PasteSiteRunnable implements Runnable {

    private String pasteSiteUrl;
  //  private String pastSiteRawContent;
    SearchingPattern searchingPattern;
    int sleepTimeout;
    int queryTimeout;
    boolean haveToQuit = false;
    MySQLUtils mySQLUtils;
    private SiteParser siteParser;

    public void haveToQuit() {
        this.haveToQuit = true;
    }

    /**
     * Get the value of pasteSite
     *
     * @return the value of pasteSite
     */
    public String getPasteSite() {
        return pasteSiteUrl;
    }

//    public PasteSiteRunnable(String pasteSite, String pastSiteRawContent, SearchingPattern searchingPattern, int sleepTimeout, int queryTimeout, MySQLUtils mySQLUtils) {
//        this.pasteSiteUrl = pasteSite;
//        this.searchingPattern = searchingPattern;
//        this.sleepTimeout = sleepTimeout;
//        this.queryTimeout = queryTimeout;
//        this.mySQLUtils = mySQLUtils;
//    }

    public PasteSiteRunnable (SiteParser siteParser, int sleepTimeout, int queryTimeout, MySQLUtils mySQLUtils) {
        this.pasteSiteUrl = siteParser.pasteSiteUrl();        
        this.siteParser = siteParser;
        this.sleepTimeout = sleepTimeout;
        this.queryTimeout = queryTimeout;
        this.mySQLUtils = mySQLUtils;
    
    }
    
    @Override
    public void run() {
       // SiteParser pastebin = new PastebinParser(pasteSiteUrl,pastSiteRawContent, searchingPattern);
        System.out.println("PasteSite Thread started: "+pasteSiteUrl);
        while (!haveToQuit) {
            try {
                doAction(siteParser);
                // Main sleep between two archive grab sessions
                Thread.sleep(sleepTimeout * 1000);
                // DEBUG
                System.out.println(String.format("run done... sleeping %d sec.", sleepTimeout));
                // END DEBUG
            } catch (IOException | InterruptedException | SQLException ex) {
                Logger.getLogger(PasteSiteRunnable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("PasteSite Thread ended."+pasteSiteUrl);
    }

    protected void doAction(SiteParser siteParser) throws IOException, SQLException, InterruptedException {
        Set<String> pasteSiteArchive = new HashSet<>();

        // TODO: check duplicati e loro rimozione
        pasteSiteArchive = siteParser.getAndParsedArchivePage();
        if (pasteSiteArchive == null) {
            return;
        }

        Set<String> pasteSiteArchiveToBeRead = mySQLUtils.insertRemoteIDIntoDB(pasteSiteArchive, pasteSiteUrl);

        System.out.println("Reading: "+pasteSiteArchiveToBeRead.size());
        int counter=0;
        for (String id : pasteSiteArchiveToBeRead) {
            //siteParser.getRemoteContent(id);

            System.out.print(String.format("%02d - Retrieving remote ID: %s",counter++, id));

            // TODO: Trattare il caso di eccezione filenot found che rappresenta una pagina non più raggiungibile
            if (siteParser.parseContentPage(id)) {

                // DEBUG
                System.out.println("\tSaving data in DB");
                // fine DEBUG
                mySQLUtils.insertPasteIntoDB(id, siteParser.getLastContent(), pasteSiteUrl, 2);
                mySQLUtils.insertMatchRelation(id, pasteSiteUrl, siteParser.getMatchedPatterns());
            } else {
                System.out.println("\tNo useful information found");
            }
            
            // Aspetto un po' nel parsing tra una pagina e l'altra
            Thread.sleep(queryTimeout*1000);
        }
    }
}
