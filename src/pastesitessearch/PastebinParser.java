/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pastesitessearch;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author utente
 */
public class PastebinParser extends SiteParserCommon {

    Set<String> pasteBinArchive = new HashSet<>();
    private String pasteSiteUrl;
    SearchingPattern searchingPattern;
    String lastContentPage;
    String remoteID;
    String archiveUrlString = "https://pastebin.com/archive";
    String rawUrlString = "https://pastebin.com/raw";

    public PastebinParser(String site, String rawUrlString, SearchingPattern searchingPattern) {
        super(site, rawUrlString, searchingPattern);
        this.searchingPattern=searchingPattern;
        this.pasteSiteUrl=site;
        this.rawUrlString=rawUrlString;
    }

//    private String getPage(String remoteUrl) throws MalformedURLException, IOException {
//        URL url = new URL(remoteUrl);
//        URLConnection conn = url.openConnection();
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        StringBuilder sb = new StringBuilder();
//        String line = null;
//        while ((line = br.readLine()) != null) {
//            sb.append(line);
//        }
//
//        return (sb.toString());
//    }

//    @Override
//    public void openConnection() throws MalformedURLException, IOException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        
////        URL url = new URL(archiveUrlString);
////        URLConnection conn = url.openConnection();
////
////        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
////        StringBuilder sb = new StringBuilder();
////        String line = null;
////        while ((line = br.readLine()) != null) {
////            sb.append(line);
////        }
////
////        String page = sb.toString();
//    }

//    @Override
//    public void closeConnection() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public Set<String> getAndParsedArchivePage() throws IOException {
        String page= getPage(archiveUrlString);
        
        Document docum;
        docum = Jsoup.parse(page);
        // TODO: irrobustire la sezione per verificare se la pagina che viene ritornata è del tipo che mi aspetto
        Element table;
        table = docum.getElementsByClass("maintable").first();
        
        // Se non ci sono tabelle ritorno null
        if (table == null) {
            return null;
        }

        // Parsing delle tabelle
        Elements rows = table.select("tr");

        for (Element row : rows) {
            Elements cols = row.select("td");
            if (cols.isEmpty()) {
                continue;
            }

            Element col = cols.get(0);
            Element href = col.select("a[href]").get(0);
            String link = href.attr("href");
            //System.out.println(link);
            pasteBinArchive.add(link);
        }

        return pasteBinArchive;
    }

//    @Override
//    public String getRemoteContent(String remoteID) throws IOException {
//        this.remoteID = remoteID;
//        String urlString = rawUrlString + remoteID;
//        lastContentPage=getPage(urlString);
//        return (lastContentPage);
//    }

    // Pattern che sono stati matchati
    private Set<String> patterns_matched;
    
//    @Override
//    public boolean parseContentPage(String remoteID) throws IOException {
//        Set<Pattern> patterns = searchingPattern.getPattern().keySet();
//        patterns_matched=new HashSet<>();
//        boolean retval = false;
//        getRemoteContent(remoteID);
//        for (Pattern p : patterns) {
//            Matcher m = p.matcher(lastContentPage);
//            while (m.find()) {
//                // TODO: Tenere conto dei match per formare un indice rappresentativo di cosa è stato trovato
//                // Ad esempio numero matches/tot matches
//                patterns_matched.add(p.toString());
//                retval = true;
//            }
//        }
//
//        return (retval);
//    }

//    @Override
//    public String getLastContent() {
//        return (lastContentPage);
//    }
//
//    @Override
//    public String getLastRemoteIdParsed() {
//        return (remoteID);
//    }
//
//    @Override
//    public Set<String> getMatchedPatterns() {
//        return patterns_matched;
//    }


}
