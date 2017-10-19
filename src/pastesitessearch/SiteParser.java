/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pastesitessearch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

/**
 *
 * @author utente
 */
public interface SiteParser {

//    public void openConnection() throws MalformedURLException, IOException, InstantiationException ;
//    
//    public void closeConnection();
    
    /**
     * The implementation should get a set of remote paste site's archive IDs
     *
     * @return the java.util.Set<java.lang.String>
     */
    public Set<String> getAndParsedArchivePage() throws MalformedURLException, IOException;

    /**
     * The implementation should return a String with the content of the page
     * referred to remoteID
     *
     * @param remoteID
     *
     * @return The page's contents
     */
    public String getRemoteContent(String remoteID) throws MalformedURLException, IOException;

    /**
     * Parse the page referred by ID
     * @param remoteID the value of emoteID
     * @return the boolean
     */
    public boolean parseContentPage(String remoteID) throws MalformedURLException, IOException;

    /**
     * Last page parsed
     * @return A string with the text of the last page retrieved from remote 
     * site and parsed 
     */
    public String getLastContent();

    /**
     * ID of the last page parsed
     * @return A string with ID of the last page retrieved from remote site and
     * parsed
     */
    public String getLastRemoteIdParsed();
    
    /**
     * Ritorna l'insieme dei pattern matchati
     * @return 
     */
    public Set<String> getMatchedPatterns();

}
