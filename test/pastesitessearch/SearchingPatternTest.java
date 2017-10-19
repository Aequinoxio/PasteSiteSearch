/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pastesitessearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author utente
 */
public class SearchingPatternTest {

    private final String testString = "http://pastebin.com/raw/ttm69u3Z";

    public SearchingPatternTest() {
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
     * Test of getInstance method, of class SearchingPattern.
     */
    @Test
    @Ignore
    public void testGetInstance() {
        System.out.println("getInstance");
        SearchingPattern expResult = null;
        SearchingPattern result = SearchingPattern.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of put method, of class SearchingPattern.
     */
    @Test
    @Ignore
    public void testPut() {
        System.out.println("put");
        Pattern k = null;
        String v = "";
        SearchingPattern instance = null;
        String expResult = "";
        String result = instance.put(k, v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPattern method, of class SearchingPattern.
     */
    @Test
    @Ignore
    public void testGetPattern() {
        System.out.println("getPattern");
        SearchingPattern instance = null;
        Map<Pattern, String> expResult = null;
        Map<Pattern, String> result = instance.getPattern();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPattern method, of class SearchingPattern.
     */
    @Test
    @Ignore
    public void testSetPattern() {
        System.out.println("setPattern");
        Map<Pattern, String> pattern = null;
        SearchingPattern instance = null;
        instance.setPattern(pattern);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testPattern() throws IOException {
        System.out.println("testPattern");
        String testPage = getPage("http://pastebin.com/raw/ttm69u3Z");
        SearchingPattern instance = SearchingPattern.getInstance();
        Map<Pattern, String> result = instance.getPattern();
        Set<Pattern> patterns = result.keySet();
        for (Pattern p : patterns) {

            Matcher m = p.matcher(testPage);
            int counter = 0;
            while (m.find()) {
                counter++;
            }
            if (counter > 0) {
                System.out.println(String.format("Matches: %s - count %d", p.toString(), counter));
            }
        }
    }

    private String getPage(String remoteUrl) throws MalformedURLException, IOException {
        URL url = new URL(remoteUrl);
        URLConnection conn = url.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        return (sb.toString());
    }

}
