/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pastesitessearch;

import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author utente
 */
public class SlexyParserTest {
    
    public SlexyParserTest() {
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
     * Test of getAndParsedArchivePage method, of class SlexyParser.
     */
    @Test
    public void testGetAndParsedArchivePage() throws Exception {
        System.out.println("getAndParsedArchivePage");
        SearchingPattern searchingPattern = SearchingPattern.getInstance();
        SlexyParser slexyParser=  new SlexyParser("https://slexy.org/", "https://slexy.org", searchingPattern);
        
        Set<String> expResult = null;
        Set<String> result = slexyParser.getAndParsedArchivePage();
        String rawContent=slexyParser.getRemoteContent(result.iterator().next());
        assertNotEquals("", rawContent);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
