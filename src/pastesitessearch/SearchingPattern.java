/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pastesitessearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Singleton with the searching patterns
 * Thanx to DumpMon
 * @author utente
 */
class SearchingPattern {

    /**
     * Pattern di ricerca con la categoria relativa
     */
    private Map<Pattern,String> pattern;

    public static SearchingPattern getInstance(){
        return SingletonHolder.INSTANCE;
    }
    
    public String put(Pattern k, String v) {
        return pattern.put(k, v);
    }

    /**
     * Set inizial patterns
     */
    private SearchingPattern() {
        pattern = new HashMap<Pattern,String>();
                
        pattern.put( Pattern.compile("gab.gal@gmail.com|procava@gmail.com|ordini.bianchi@gmail.com|coverctrl@gmail.com|gabriele.galluzzo@gmail.com|prokeprok@gmail.com|gabriele.galluzzo@ingpec.eu|gabriele_galluzzo@alice.it|tiziana.castellani@gmail.com", Pattern.CASE_INSENSITIVE),"email_specific");
        pattern.put(Pattern.compile(
                //"[A-Z]{6}[0-9LMNPQRSTUV]{2}[ABCDEHLMPRST]{1}[0-7LMNPQRST]{1}[0-9LMNPQRSTUV]{1}[A-Z]{1}[0-9LMNPQRSTUV]{3}[A-Z]{1}"
                "(?:[B-DF-HJ-NP-TV-Z](?:[AEIOU]{2}|[AEIOU]X)|[AEIOU]{2}X|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}[\\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[1256LMRS][\\dLMNP-V])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM])(?:[A-MZ][1-9MNP-V][\\dLMNP-V]{2}|[A-M][0L](?:[\\dLMNP-V][1-9MNP-V]|[1-9MNP-V][0L]))[A-Z]",
                Pattern.CASE_INSENSITIVE), "codice_fiscale");
        pattern.put( Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}", Pattern.CASE_INSENSITIVE),"codice_fiscale");
//        pattern.add(Pattern.compile(Pattern.CASE_INSENSITIVE),"\\d{3}-?\\d{2}-?\\d{4}"); // ; //	    #",ssn", : - 
        pattern.put( Pattern.compile("[^<A-F\\d/]([A-F\\d]{32})[^A-F\\d]", Pattern.CASE_INSENSITIVE),"hash32"); // ; //	    ",hash32",: - 
        pattern.put( Pattern.compile("[^<A-F\\d/]([A-F\\d]{64})[^A-F\\d]", Pattern.CASE_INSENSITIVE),"hash64"); // ; //	    ",hash64",: -             
        pattern.put( Pattern.compile("FBI\\s*Friday", Pattern.CASE_INSENSITIVE),"FFF"); //  # will need to work on this to not match CSS ; //	    ",FFF",: - 
        pattern.put( Pattern.compile("(lulzsec|antisec)", Pattern.CASE_INSENSITIVE),"lulz"); // ; //	    ",lulz",: - 
        pattern.put( Pattern.compile("enable\\s+secret", Pattern.CASE_INSENSITIVE),"cisco_hash"); // ; //	    ",cisco_hash",: - 
        pattern.put( Pattern.compile("enable\\s+password", Pattern.CASE_INSENSITIVE),"cisco_pass"); // ; //	    ",cisco_pass",: - 
        pattern.put( Pattern.compile("\\W(AIza.{35})", Pattern.CASE_INSENSITIVE),"google_api"); // ; //	    ",google_api",: - 
        pattern.put( Pattern.compile("<dionaea\\.capture>", Pattern.CASE_INSENSITIVE),"honeypot"); // ; //	    ",honeypot",: - 
        pattern.put( Pattern.compile("BEGIN PGP PRIVATE", Pattern.CASE_INSENSITIVE),"pgp_private"); // ; //	    ",pgp_private",: - 
        pattern.put( Pattern.compile("BEGIN RSA PRIVATE", Pattern.CASE_INSENSITIVE),"ssh_private"); // ; //	    ",ssh_private",: - 

//	    ",db_keywords",: - 
        pattern.put( Pattern.compile("((\\W?pass(wor)?d|hash)[\\s|:])", Pattern.CASE_INSENSITIVE),"db_keywords"); //
        pattern.put( Pattern.compile("((customers?|email|users?|members?|acc(?:oun)?ts?)([-_|/\\s]?(address|name|id[^\\\")a-zA-Z0-9_]|[-_:|/\\\\])))", Pattern.CASE_INSENSITIVE),"db_keywords");
        pattern.put( Pattern.compile("((\\btarget|\\bsite)\\s*?:?\\s*?(([a-z][\\w-]+:/{1,3})?([-\\w\\s_/]+\\.)*[\\w=/?%]+))", Pattern.CASE_INSENSITIVE),"db_keywords"); //  # very basic URL check - may be improved later
        pattern.put( Pattern.compile("(my\\s?sql[^i_\\.]|sql\\s*server)", Pattern.CASE_INSENSITIVE),"db_keywords"); //
        pattern.put( Pattern.compile("((host|target)[-_\\s]+ip:)", Pattern.CASE_INSENSITIVE),"db_keywords"); //
        pattern.put( Pattern.compile("(data[-_\\s]*base|\\Wdb)", Pattern.CASE_INSENSITIVE),"db_keywords"); //  # added the non-word char before db.. we",ll see if that helps
        pattern.put( Pattern.compile("(table\\s*?:)", Pattern.CASE_INSENSITIVE),"db_keywords"); //
        pattern.put( Pattern.compile("((available|current)\\s*(databases?|dbs?)\\W)", Pattern.CASE_INSENSITIVE),"db_keywords"); //
        pattern.put( Pattern.compile("(hacked\\s*by)", Pattern.CASE_INSENSITIVE),"db_keywords"); //		# I was hoping to not have to make a blacklist, but it looks like I don",t really have a choice

//	    ",blacklist",: - 
        pattern.put( Pattern.compile("(select\\s+.*?from|join|declare\\s+.*?\\s+as\\s+|update.*?set|insert.*?into)", Pattern.CASE_INSENSITIVE),"blacklist"); //  # SQL
        pattern.put( Pattern.compile("(define\\(.*?\\)|require_once\\(.*?\\))", Pattern.CASE_INSENSITIVE),"blacklist"); //  # PHP
        pattern.put( Pattern.compile("(function.*?\\(.*?\\))", Pattern.CASE_INSENSITIVE),"blacklist"); //
        pattern.put( Pattern.compile("(Configuration(\\.Factory|\\s*file))", Pattern.CASE_INSENSITIVE),"blacklist"); //
        pattern.put( Pattern.compile("((border|background)-color)", Pattern.CASE_INSENSITIVE),"blacklist"); //  # Basic CSS (Will need to be improved)
        pattern.put( Pattern.compile("(Traceback \\(most recent call last\\))", Pattern.CASE_INSENSITIVE),"blacklist"); //
        pattern.put( Pattern.compile("(java\\.(util|lang|io))", Pattern.CASE_INSENSITIVE),"blacklist"); //
        pattern.put( Pattern.compile("(sqlserver\\.jdbc)", Pattern.CASE_INSENSITIVE),"blacklist");

// Banlist            
        pattern.put( Pattern.compile("faf\\.fa\\.proxies", Pattern.CASE_INSENSITIVE),"banlist");
        pattern.put( Pattern.compile("Technic Launcher is starting", Pattern.CASE_INSENSITIVE),"banlist");
        pattern.put( Pattern.compile("OTL logfile created on", Pattern.CASE_INSENSITIVE),"banlist");
        pattern.put( Pattern.compile("RO Game Client crashed!", Pattern.CASE_INSENSITIVE),"banlist");
        pattern.put( Pattern.compile("Selecting PSO2 Directory", Pattern.CASE_INSENSITIVE),"banlist");
        pattern.put( Pattern.compile("TDSS Rootkit", Pattern.CASE_INSENSITIVE),"banlist");
        pattern.put( Pattern.compile("SysInfoCrashReporterKey", Pattern.CASE_INSENSITIVE),"banlist");
        pattern.put( Pattern.compile("Current OS Full name: ", Pattern.CASE_INSENSITIVE),"banlist");
        pattern.put( Pattern.compile("Multi Theft Auto: ", Pattern.CASE_INSENSITIVE),"banlist");
        pattern.put( Pattern.compile("Initializing cgroup subsys cpuset", Pattern.CASE_INSENSITIVE),"banlist");
        pattern.put( Pattern.compile("Init vk network", Pattern.CASE_INSENSITIVE),"banlist");
        pattern.put( Pattern.compile("MediaTomb UPnP Server", Pattern.CASE_INSENSITIVE),"banlist");
        pattern.put( Pattern.compile("#EXTM3U\n#EXTINF:", Pattern.CASE_INSENSITIVE),"banlist");

    }

    public Map<Pattern,String> getPattern() {
        return pattern;
    }

    public void setPattern(Map<Pattern,String> pattern) {
        this.pattern = pattern;
    }
    
    private static class SingletonHolder {
        private static final SearchingPattern INSTANCE = new SearchingPattern();
    }
}
