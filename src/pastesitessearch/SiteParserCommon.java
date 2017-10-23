/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pastesitessearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author utente
 */
public abstract class SiteParserCommon implements SiteParser {

    protected final String pasteSiteUrl;
    SearchingPattern searchingPattern;
    String lastContentPage;
    String remoteID;
    protected final String rawUrlString;
    // Pattern che sono stati matchati
    private Set<String> patterns_matched;

    public SiteParserCommon(String site, String rawContentUrl, SearchingPattern searchingPattern) {
        this.searchingPattern = searchingPattern;
        this.pasteSiteUrl = site;
        this.rawUrlString = rawContentUrl;
    }

    protected String getPage(String remoteUrl) throws MalformedURLException, IOException {
        URL url = new URL(remoteUrl);
        URLConnection conn = url.openConnection();

        InputStream remoteData=null;
        try {
            remoteData = conn.getInputStream();
        } catch (Exception ioe) {
            if (conn instanceof HttpURLConnection) {
                HttpURLConnection httpConn = (HttpURLConnection) conn;
                int statusCode = httpConn.getResponseCode();
                if (statusCode != 200) {
                    remoteData= httpConn.getErrorStream();
                }
            }
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(remoteData));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        return (sb.toString());
    }

    public abstract Set<String> getAndParsedArchivePage() throws IOException;

    @Override
    public String getRemoteContent(String remoteID) throws IOException {
        this.remoteID = remoteID;
        String urlString = rawUrlString + remoteID;
        lastContentPage = getPage(urlString);
        return (lastContentPage);
    }

    @Override
    public boolean parseContentPage(String remoteID) throws IOException {
        Set<Pattern> patterns = searchingPattern.getPattern().keySet();
        patterns_matched = new HashSet<>();
        boolean retval = false;
        getRemoteContent(remoteID);
        for (Pattern p : patterns) {
            Matcher m = p.matcher(lastContentPage);
            while (m.find()) {
                // TODO: Tenere conto dei match per formare un indice rappresentativo di cosa è stato trovato
                // Ad esempio numero matches/tot matches
                patterns_matched.add(p.toString());
                retval = true;
            }
        }

        return (retval);
    }

    public String getLastContent() {
        return (lastContentPage);
    }

    public String getLastRemoteIdParsed() {
        return (remoteID);
    }

    public Set<String> getMatchedPatterns() {
        return patterns_matched;
    }
    
    @Override
    public String pasteSiteUrl() {
        return pasteSiteUrl;
    }

    @Override
    public String pastSiteRawContentUrl() {
        return rawUrlString;
    }
}
