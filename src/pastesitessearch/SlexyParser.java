/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pastesitessearch;

import java.io.IOException;
import java.net.URL;
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
public class SlexyParser extends SiteParserCommon {

    Set<String> slexyArchive = new HashSet<>();
    private String pasteSiteUrl;
    SearchingPattern searchingPattern;
    String lastContentPage;
    String remoteID;
//    String archiveUrlString = ""; // Non occorre in quanto faccio il parsing direttamente della pagina dei risultati
//    String rawUrlString = "";

    public SlexyParser(String site, String rawUrlString, SearchingPattern searchingPattern) {
        super(site, rawUrlString, searchingPattern);
        this.searchingPattern = searchingPattern;
        this.pasteSiteUrl = site;
        //this.rawUrlString=rawUrlString;
    }

    /**
     * Parsa tutte le pagine degli indici a partire da quella principale.
     * Costruisce le altre pagine da recuperare a partire dalla principale
     *
     * @return
     * @throws IOException
     */
    @Override
    public Set<String> getAndParsedArchivePage() throws IOException {
        String tempUrl;

        tempUrl = (pasteSiteUrl + "/slexy/recent/").replaceAll("(?<!(http:|https:))//+", "/");
        Set<String> tmp = parsePageWorker(tempUrl);
        slexyArchive.addAll(tmp);

        // Recupero gli altri ID dalle altre pagine. Il formato è
        // https://slexy.org/slexy/recent/{2,4,6,8}0
        tempUrl = (pasteSiteUrl + "/slexy/recent/20").replace("(?<!(http:|https:))//+", "/");
        tmp = parsePageWorker(tempUrl);
        slexyArchive.addAll(tmp);

        tempUrl = (pasteSiteUrl + "/slexy/recent/40").replaceAll("(?<!(http:|https:))//+", "/");
        tmp = parsePageWorker(tempUrl);
        slexyArchive.addAll(tmp);

        tempUrl = (pasteSiteUrl + "/slexy/recent/60").replaceAll("(?<!(http:|https:))//+", "/");
        tmp = parsePageWorker(tempUrl);
        slexyArchive.addAll(tmp);

        tempUrl = (pasteSiteUrl + "/slexy/recent/80").replaceAll("(?<!(http:|https:))//+", "/");
        tmp = parsePageWorker(tempUrl);
        slexyArchive.addAll(tmp);

        return slexyArchive;
    }

    /**
     * Parsa tutte la pagina degli indici
     *
     * @param url - Url da parsare
     * @return
     * @throws IOException
     */
    private Set<String> parsePageWorker(String url) throws IOException {
        Set<String> tmp = new HashSet<String>();
        String page = getPage(url);

        Document docum;
        docum = Jsoup.parse(page);
        // TODO: irrobustire la sezione per verificare se la pagina che viene ritornata è del tipo che mi aspetto
        Element table;
        table = docum.getElementsByTag("tbody").first();

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

            Element col = cols.get(3);
            Elements hyperlinks = col.select("a[href]");
            if (hyperlinks.isEmpty()) {
                continue;
            }

            Element href = hyperlinks.get(0);
            String link = href.attr("href");
            //System.out.println(link);
            tmp.add(link);
        }

        return tmp;
    }

    @Override
    public String getRemoteContent(String remoteID) throws IOException {
        //String htmlPage = getPage(rawUrlString+remoteID);
        String htmlPage = super.getRemoteContent(remoteID);
        Document docum;
        docum = Jsoup.parse(htmlPage);
        Element testoHtml = docum.getElementsByClass("main").first();
        if (testoHtml == null) {
            return "";
        }

        Elements listaHtml = testoHtml.getElementsByTag("li");

        // Non leggo il raw ma parso l'html. Devo aggiornare lastContentPage
        super.lastContentPage=listaHtml.text();
        
        return (super.lastContentPage);
    }

//    @Override
//    public String pasteSiteUrl() {
//        return pasteSiteUrl;
//    }
//
//    @Override
//    public String pastSiteRawContentUrl() {
//        return rawUrlString;
//    }
}
