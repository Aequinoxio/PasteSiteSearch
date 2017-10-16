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
    SearchingPattern searchingPattern;
    int sleepTimeout;
    int queryTimeout;
    boolean haveToQuit = false;
    MySQLUtils mySQLUtils;

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

    public PasteSiteRunnable(String pasteSite, SearchingPattern searchingPattern, int sleepTimeout, int queryTimeout, MySQLUtils mySQLUtils) {
        this.pasteSiteUrl = pasteSite;
        this.searchingPattern = searchingPattern;
        this.sleepTimeout = sleepTimeout;
        this.queryTimeout = queryTimeout;
        this.mySQLUtils = mySQLUtils;
    }

    @Override
    public void run() {
        SiteParser pastebin = new PastebinParser(pasteSiteUrl, searchingPattern);
        while (!haveToQuit) {
            try {
                doAction(pastebin);
                // Main sleep between two archive grab sessions
                Thread.sleep(sleepTimeout * 1000);
                // DEBUG
                System.out.println(String.format("run done... sleeping %d sec.", sleepTimeout));
                // END DEBUG
            } catch (IOException | InterruptedException | SQLException ex) {
                Logger.getLogger(PasteSiteRunnable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Esco");
    }

    protected void doAction(SiteParser siteParser) throws IOException, SQLException, InterruptedException {
        Set<String> pasteSiteArchive = new HashSet<>();

        // TODO: check duplicati e loro rimozione
        pasteSiteArchive = siteParser.getAndParsedArchivePage();
        if (pasteSiteArchive == null) {
            return;
        }

        Set<String> pasteSiteArchiveToBeRead = mySQLUtils.insertRemoteIDIntoDB(pasteSiteArchive, pasteSiteUrl);

        for (String id : pasteSiteArchiveToBeRead) {
            //siteParser.getRemoteContent(id);

            System.out.print(String.format("Retrieving remote ID: %s", id));

            // TODO: Trattare il caso di eccezione filenot found che rappresenta una pagina non più raggiungibile
            if (siteParser.parseContentPage(id)) {

                // DEBUG
                System.out.println("\tSaving data in DB");
                // fine DEBUG
                mySQLUtils.insertPasteIntoDB(id, siteParser.getLastContent(), pasteSiteUrl, 2);
                mySQLUtils.insertMatchRelation(id,pasteSiteUrl , siteParser.getPatterns_matched());
            } else {
                System.out.println("\tNo useful information found");
            }
            
            // Aspetto un po' nel parsing tra una pagina e l'altra
            Thread.sleep(queryTimeout*1000);
        }
    }

//    private Set<String> getPasteSiteArchives() throws MalformedURLException, IOException, InstantiationException {
//        Set<String> pasteBinArchive = new HashSet<>();
//
//        //String urlString = "https://pastebin.com/archive";
//        URL url = new URL(pasteSiteUrl);
//        URLConnection conn = url.openConnection();
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        StringBuilder sb = new StringBuilder();
//        String line = null;
//        while ((line = br.readLine()) != null) {
//            sb.append(line);
//        }
//
//        String page = sb.toString();
//        Document docum;
//        docum = Jsoup.parse(page);
//        // TODO: irrobustire la sezione per verificare se la pagina che viene ritornata è del tipo che mi aspetto
//        Element table;
//        table = docum.getElementsByClass("maintable").first();
//        if (table==null)
//            return null;
//        
//        Elements rows = table.select("tr");
//
//        for (Element row : rows) {
//            Elements cols = row.select("td");
//            if (cols.size() == 0) {
//                continue;
//            }
//
//            Element col = cols.get(0);
//            Element href = col.select("a[href]").get(0);
//            String link = href.attr("href");
//            //System.out.println(link);
//            pasteBinArchive.add(link);
//        }
//
//        return pasteBinArchive;
//    }
}
