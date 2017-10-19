/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pastesitessearch;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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
public class MySQLUtilsTest {

    public MySQLUtilsTest() {
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
     * Test of startDBConnection method, of class MySQLUtils.
     */
    @Test
    @Ignore
    public void testStartDBConnection() throws Exception {
        System.out.println("startDBConnection");
        MySQLUtils instance = new MySQLUtils();
        instance.startDBConnection();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of insertPatternWithShortNameIntoDB method, of class MySQLUtils.
     */
    @Test
    @Ignore
    public void testInsertPatternWithShortNameIntoDB() throws Exception {
        System.out.println("insertPatternWithShortNameIntoDB");
        Map<Pattern, String> kv = null;
        MySQLUtils instance = new MySQLUtils();
        instance.insertPatternWithShortNameIntoDB(kv);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readPatternFromDB method, of class MySQLUtils.
     */
    @Test
    @Ignore
    public void testReadPatternFromDB() throws Exception {
        System.out.println("readPatternFromDB");
        MySQLUtils instance = new MySQLUtils();
        Map<Pattern, String> expResult = null;
        Map<Pattern, String> result = instance.readPatternFromDB();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertPasteIntoDB method, of class MySQLUtils.
     */
    @Test
    @Ignore
    public void testInsertPasteIntoDB() throws Exception {
        System.out.println("insertPasteIntoDB");
        String remoteID = "/BFWJxZZK_TODEL";
        String pasteText = "CANCELLAMI";
        String pasteSite = "TEST_TO_DEL_ROWS_WITH_THIS_VALUE";
        int status = 1;
        MySQLUtils instance = new MySQLUtils();
        instance.startDBConnection();
        instance.insertPasteIntoDB(remoteID, pasteText, pasteSite, status);
        instance.closeDBConnection();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of insertRemoteIDIntoDB method, of class MySQLUtils.
     */
    @Test
    @Ignore
    public void testInsertRemoteIDIntoDB_Set_String() throws Exception {
        System.out.println("insertRemoteIDIntoDB");
        Set<String> remoteIDSet = new HashSet<String>();
        remoteIDSet.add("/xqKaTs8B");
        remoteIDSet.add("/e1F0LPnQ");
        remoteIDSet.add("/BFWJxZZK");
        remoteIDSet.add("/BFWJxZZK_TODEL");
        remoteIDSet.add("/BFWJxZZK_ARITODEL");
        String pasteSite = "TEST_TO_DEL_ROWS_WITH_THIS_VALUE";
        MySQLUtils instance = new MySQLUtils();
        instance.startDBConnection();
        Set<String> expResult = new HashSet<String>();

        expResult.add("/BFWJxZZK_TODEL");
        expResult.add("/BFWJxZZK_ARITODEL");

        Set<String> result = instance.insertRemoteIDIntoDB(remoteIDSet, pasteSite);

        instance.closeDBConnection();

        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of checkPatternInDBPatterns method, of class MySQLUtils.
     */
    @Test
    @Ignore
    public void testCheckPatternInDBPatterns() throws Exception {
        System.out.println("checkPatternInDBPatterns");
        String pattern = "";
        MySQLUtils instance = new MySQLUtils();
        boolean expResult = false;
        boolean result = instance.checkPatternInDBPatterns(pattern);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkRemoteIDInDBPastes method, of class MySQLUtils.
     */
    @Test
    @Ignore
    public void testCheckRemoteIDInDBPastes() throws Exception {
        System.out.println("checkRemoteIDInDBPastes");
        String remoteID = "";
        String pasteSite = "";
        MySQLUtils instance = new MySQLUtils();
        boolean expResult = false;
        boolean result = instance.checkRemoteIDInDBPastes(remoteID, pasteSite, 1);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of closeDBConnection method, of class MySQLUtils.
     */
    @Test
    @Ignore
    public void testCloseDBConnection() throws Exception {
        System.out.println("closeDBConnection");
        MySQLUtils instance = new MySQLUtils();
        instance.closeDBConnection();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    
    public void insertMatchRelation() throws Exception {
        System.out.println("insertMatchRelation");
        String remoteID = "/kG462JJv";
        String site="https://www.pastebin.com";
        Set<String> patterns = new HashSet<>();
        SearchingPattern sp = SearchingPattern.getInstance();
        
        Iterator<Pattern> itP = sp.getPattern().keySet().iterator();
        patterns.add(itP.next().toString());      
        patterns.add(itP.next().toString());
        patterns.add(itP.next().toString());
        
        MySQLUtils instance = new MySQLUtils();
        instance.startDBConnection();
        instance.insertMatchRelation(remoteID, site, patterns);
//        boolean expResult = false;
//        assertEquals(expResult, result);
    }

}
