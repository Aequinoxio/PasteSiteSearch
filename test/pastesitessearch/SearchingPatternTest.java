/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pastesitessearch;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author utente
 */
public class SearchingPatternTest {

    private final String testString = "10/16/2017 11:10:01 AM: DEBUG - \n" +
"\n" +
"10/16/2017 11:10:01 AM: DEBUG - Program started! Hooray! \\o/\n" +
"10/16/2017 11:10:01 AM: DEBUG - Reading settings...\n" +
"10/16/2017 11:10:01 AM: DEBUG - Checking if the PSO2 Tweaker is running\n" +
"10/16/2017 11:10:01 AM: DEBUG - Loaded pso2_bin directory from settings\n" +
"10/16/2017 11:10:01 AM: DEBUG - Reading remote config...\n" +
"10/16/2017 11:10:03 AM: DEBUG - Checking for patch info...\n" +
"10/16/2017 11:10:04 AM: DEBUG - Loading settings...\n" +
"10/16/2017 11:10:04 AM: DEBUG - ----------------------------------------\n" +
"10/16/2017 11:10:04 AM: DEBUG - Program opening, running diagnostics...\n" +
"10/16/2017 11:10:04 AM: DEBUG - Current OS/Version: Microsoft Windows 10 Pro (6.2.9200.0) <English (United States)> [64-bit]\n" +
"10/16/2017 11:10:04 AM: DEBUG - Directory program is running in: D:\\Games\n" +
"10/16/2017 11:10:04 AM: DEBUG - Selected PSO2 Directory: D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\n" +
"10/16/2017 11:10:04 AM: DEBUG - .NET Framework Version: 4.6.2 or later\n" +
"10/16/2017 11:10:04 AM: DEBUG - Enabled Plugins: PSO2BlockRename.dll, PSO2DamageDump.dll, PSO2ItemTranslator.dll, PSO2RAISERSystem.dll, PSO2TitleTranslator.dll, TelepipeProxy.dll\n" +
"10/16/2017 11:10:04 AM: DEBUG - Disabled Plugins: None\n" +
"10/16/2017 11:10:04 AM: DEBUG - ProxyJSONURL is: http://alam.srb2.org/PSO2/public_proxy/config-alt.json\n" +
"10/16/2017 11:10:04 AM: DEBUG - System has been on for: 27.03 hours\n" +
"10/16/2017 11:10:04 AM: DEBUG - ----------------------------------------\n" +
"\n" +
"10/16/2017 11:10:04 AM: DEBUG - User location according to IP: Philippines\n" +
"10/16/2017 11:10:04 AM: Program opened successfully! Version 4.0.5.3 [OK!]\n" +
"10/16/2017 11:10:04 AM: Checking for PSO2 Updates...\n" +
"10/16/2017 11:10:08 AM: Checking for a new English patch...\n" +
"10/16/2017 11:10:09 AM: You have the latest English patch! [OK!]\n" +
"10/16/2017 11:10:09 AM: Checking for plugin updates...\n" +
"10/16/2017 11:10:10 AM: DEBUG - PSO2BlockRename.dll should be: 2E6CA6EA277199C8BA73967E7F6FED06. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\plugins\\PSO2BlockRename.dll) is: 2E6CA6EA277199C8BA73967E7F6FED06\n" +
"10/16/2017 11:10:10 AM: DEBUG - PSO2CrashDebug.zip should be: A2DCC20944A923C19AD3876E52BC2EBC. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\PSO2CrashDebug.zip) is: A2DCC20944A923C19AD3876E52BC2EBC\n" +
"10/16/2017 11:10:10 AM: DEBUG - PSO2DamageDump.dll should NOT be updated/checked. The local file is: D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\plugins\\PSO2DamageDump.dll.\n" +
"10/16/2017 11:10:10 AM: DEBUG - PSO2ItemTranslator.dll should be: AA60594D4575D511EFCC5E7358A354A1. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\plugins\\PSO2ItemTranslator.dll) is: AA60594D4575D511EFCC5E7358A354A1\n" +
"10/16/2017 11:10:10 AM: DEBUG - PSO2RAISERSystem.dll should be: 75A6EC1E063F7B386C6AD2A4897B54F5. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\plugins\\PSO2RAISERSystem.dll) is: 75A6EC1E063F7B386C6AD2A4897B54F5\n" +
"10/16/2017 11:10:10 AM: DEBUG - PSO2TitleTranslator.dll should be: 9BD5503E4E1A2E93B2B17872E0CBE839. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\plugins\\PSO2TitleTranslator.dll) is: 9BD5503E4E1A2E93B2B17872E0CBE839\n" +
"10/16/2017 11:10:10 AM: DEBUG - ReShade3.dll should NOT be updated/checked. The local file is: nowhere.\n" +
"10/16/2017 11:10:10 AM: DEBUG - TelepipeProxy.dll should be: 42BD403BA3658F1CD4A7C2CA5FE68077. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\plugins\\TelepipeProxy.dll) is: 42BD403BA3658F1CD4A7C2CA5FE68077\n" +
"10/16/2017 11:10:10 AM: DEBUG - pso2h.dll should be: 43DD4E43F5055A435C9D69241A9550E5. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\pso2h.dll) is: 43DD4E43F5055A435C9D69241A9550E5\n" +
"10/16/2017 11:10:10 AM: DEBUG - translation_blocks.bin should be: CE6F45B3E1B543989A6B72F31133CAAD. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\translation_blocks.bin) is: CE6F45B3E1B543989A6B72F31133CAAD\n" +
"10/16/2017 11:10:10 AM: DEBUG - translation_items.bin should be: 2CCE0586B562E665025BB964DD6B9156. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\translation_items.bin) is: 2CCE0586B562E665025BB964DD6B9156\n" +
"10/16/2017 11:10:10 AM: DEBUG - translation_titles.bin should be: D6F7993455C755B6AD426FBF78FDB58C. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\translation_titles.bin) is: D6F7993455C755B6AD426FBF78FDB58C\n" +
"10/16/2017 11:10:10 AM: (0 plugin files updated)\n" +
"10/16/2017 11:10:10 AM: All done - System ready!\n" +
"10/16/2017 11:12:00 AM: DEBUG - Check if PSO2 is running\n" +
"10/16/2017 11:12:00 AM: Launching Phantasy Star Online 2...\n" +
"10/16/2017 11:12:00 AM: DEBUG - DEBUG - ProxyJSONURL is: http://alam.srb2.org/PSO2/public_proxy/config-alt.json\n" +
"10/16/2017 11:12:00 AM: DEBUG - Checking for extra GN Fields...\n" +
"10/16/2017 11:12:00 AM: DEBUG - Spinning GN Drives...\n" +
"10/16/2017 11:12:00 AM: DEBUG - Start PSO2!\n" +
"10/16/2017 11:35:25 AM: DEBUG - \n" +
"\n" +
"10/16/2017 11:35:25 AM: DEBUG - Program started! Hooray! \\o/\n" +
"10/16/2017 11:35:25 AM: DEBUG - Reading settings...\n" +
"10/16/2017 11:35:25 AM: DEBUG - Checking if the PSO2 Tweaker is running\n" +
"10/16/2017 11:35:25 AM: DEBUG - Loaded pso2_bin directory from settings\n" +
"10/16/2017 11:35:25 AM: DEBUG - Reading remote config...\n" +
"10/16/2017 11:35:26 AM: DEBUG - Checking for patch info...\n" +
"10/16/2017 11:35:27 AM: DEBUG - Loading settings...\n" +
"10/16/2017 11:35:27 AM: DEBUG - ----------------------------------------\n" +
"10/16/2017 11:35:27 AM: DEBUG - Program opening, running diagnostics...\n" +
"10/16/2017 11:35:27 AM: DEBUG - Current OS/Version: Microsoft Windows 10 Pro (6.2.9200.0) <English (United States)> [64-bit]\n" +
"10/16/2017 11:35:27 AM: DEBUG - Directory program is running in: D:\\Games\n" +
"10/16/2017 11:35:27 AM: DEBUG - Selected PSO2 Directory: D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\n" +
"10/16/2017 11:35:27 AM: DEBUG - .NET Framework Version: 4.6.2 or later\n" +
"10/16/2017 11:35:27 AM: DEBUG - Enabled Plugins: PSO2BlockRename.dll, PSO2DamageDump.dll, PSO2ItemTranslator.dll, PSO2RAISERSystem.dll, PSO2TitleTranslator.dll, TelepipeProxy.dll\n" +
"10/16/2017 11:35:27 AM: DEBUG - Disabled Plugins: None\n" +
"10/16/2017 11:35:27 AM: DEBUG - ProxyJSONURL is: http://alam.srb2.org/PSO2/public_proxy/config-alt.json\n" +
"10/16/2017 11:35:27 AM: DEBUG - System has been on for: 27.46 hours\n" +
"10/16/2017 11:35:27 AM: DEBUG - ----------------------------------------\n" +
"\n" +
"10/16/2017 11:35:50 AM: DEBUG - \n" +
"\n" +
"10/16/2017 11:35:50 AM: DEBUG - Program started! Hooray! \\o/\n" +
"10/16/2017 11:35:50 AM: DEBUG - Reading settings...\n" +
"10/16/2017 11:35:50 AM: DEBUG - Checking if the PSO2 Tweaker is running\n" +
"10/16/2017 11:35:50 AM: DEBUG - Loaded pso2_bin directory from settings\n" +
"10/16/2017 11:35:50 AM: DEBUG - Reading remote config...\n" +
"10/16/2017 11:35:51 AM: DEBUG - Checking for patch info...\n" +
"10/16/2017 11:35:52 AM: DEBUG - Loading settings...\n" +
"10/16/2017 11:35:52 AM: DEBUG - ----------------------------------------\n" +
"10/16/2017 11:35:52 AM: DEBUG - Program opening, running diagnostics...\n" +
"10/16/2017 11:35:52 AM: DEBUG - Current OS/Version: Microsoft Windows 10 Pro (6.2.9200.0) <English (United States)> [64-bit]\n" +
"10/16/2017 11:35:52 AM: DEBUG - Directory program is running in: D:\\Games\n" +
"10/16/2017 11:35:52 AM: DEBUG - Selected PSO2 Directory: D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\n" +
"10/16/2017 11:35:52 AM: DEBUG - .NET Framework Version: 4.6.2 or later\n" +
"10/16/2017 11:35:52 AM: DEBUG - Enabled Plugins: PSO2BlockRename.dll, PSO2DamageDump.dll, PSO2ItemTranslator.dll, PSO2RAISERSystem.dll, PSO2TitleTranslator.dll, TelepipeProxy.dll\n" +
"10/16/2017 11:35:52 AM: DEBUG - Disabled Plugins: None\n" +
"10/16/2017 11:35:52 AM: DEBUG - ProxyJSONURL is: http://alam.srb2.org/PSO2/public_proxy/config-alt.json\n" +
"10/16/2017 11:35:52 AM: DEBUG - System has been on for: 27.46 hours\n" +
"10/16/2017 11:35:52 AM: DEBUG - ----------------------------------------\n" +
"\n" +
"10/16/2017 11:35:52 AM: DEBUG - User location according to IP: Philippines\n" +
"10/16/2017 11:35:52 AM: Program opened successfully! Version 4.0.5.3 [OK!]\n" +
"10/16/2017 11:35:52 AM: Checking for PSO2 Updates...\n" +
"10/16/2017 11:36:01 AM: Checking for a new English patch...\n" +
"10/16/2017 11:36:03 AM: You have the latest English patch! [OK!]\n" +
"10/16/2017 11:36:03 AM: Checking for plugin updates...\n" +
"10/16/2017 11:36:04 AM: DEBUG - PSO2BlockRename.dll should be: 2E6CA6EA277199C8BA73967E7F6FED06. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\plugins\\PSO2BlockRename.dll) is: 2E6CA6EA277199C8BA73967E7F6FED06\n" +
"10/16/2017 11:36:04 AM: DEBUG - PSO2CrashDebug.zip should be: A2DCC20944A923C19AD3876E52BC2EBC. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\PSO2CrashDebug.zip) is: A2DCC20944A923C19AD3876E52BC2EBC\n" +
"10/16/2017 11:36:04 AM: DEBUG - PSO2DamageDump.dll should NOT be updated/checked. The local file is: D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\plugins\\PSO2DamageDump.dll.\n" +
"10/16/2017 11:36:04 AM: DEBUG - PSO2ItemTranslator.dll should be: AA60594D4575D511EFCC5E7358A354A1. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\plugins\\PSO2ItemTranslator.dll) is: AA60594D4575D511EFCC5E7358A354A1\n" +
"10/16/2017 11:36:04 AM: DEBUG - PSO2RAISERSystem.dll should be: 75A6EC1E063F7B386C6AD2A4897B54F5. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\plugins\\PSO2RAISERSystem.dll) is: 75A6EC1E063F7B386C6AD2A4897B54F5\n" +
"10/16/2017 11:36:04 AM: DEBUG - PSO2TitleTranslator.dll should be: 9BD5503E4E1A2E93B2B17872E0CBE839. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\plugins\\PSO2TitleTranslator.dll) is: 9BD5503E4E1A2E93B2B17872E0CBE839\n" +
"10/16/2017 11:36:04 AM: DEBUG - ReShade3.dll should NOT be updated/checked. The local file is: nowhere.\n" +
"10/16/2017 11:36:04 AM: DEBUG - TelepipeProxy.dll should be: 42BD403BA3658F1CD4A7C2CA5FE68077. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\plugins\\TelepipeProxy.dll) is: 42BD403BA3658F1CD4A7C2CA5FE68077\n" +
"10/16/2017 11:36:04 AM: DEBUG - pso2h.dll should be: 43DD4E43F5055A435C9D69241A9550E5. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\pso2h.dll) is: 43DD4E43F5055A435C9D69241A9550E5\n" +
"10/16/2017 11:36:04 AM: DEBUG - translation_blocks.bin should be: CE6F45B3E1B543989A6B72F31133CAAD. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\translation_blocks.bin) is: CE6F45B3E1B543989A6B72F31133CAAD\n" +
"10/16/2017 11:36:04 AM: DEBUG - translation_items.bin should be: 2CCE0586B562E665025BB964DD6B9156. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\translation_items.bin) is: 2CCE0586B562E665025BB964DD6B9156\n" +
"10/16/2017 11:36:04 AM: DEBUG - translation_titles.bin should be: D6F7993455C755B6AD426FBF78FDB58C. The local file (D:\\Games\\PHANTASYSTARONLINE2\\pso2_bin\\translation_titles.bin) is: D6F7993455C755B6AD426FBF78FDB58C\n" +
"10/16/2017 11:36:04 AM: (0 plugin files updated)\n" +
"10/16/2017 11:36:04 AM: All done - System ready!\n" +
"10/16/2017 11:36:09 AM: DEBUG - Couldn't find this setting - Height3d!\n" +
"10/16/2017 11:36:09 AM: DEBUG - Couldn't find this setting - Width3d!\n" +
"10/16/2017 11:36:09 AM: DEBUG - Couldn't find this setting - DisableDirectX9Ex!\n" +
"10/16/2017 11:36:09 AM: DEBUG - Couldn't find this setting - DrawLevel!\n" +
"10/16/2017 11:36:09 AM: DEBUG - Input string was not in a correct format. Stack Trace:    at System.Number.StringToNumber(String str, NumberStyles options, NumberBuffer";

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
    public void testPattern() {
        System.out.println("testPattern");        
        SearchingPattern instance = SearchingPattern.getInstance();
        Map<Pattern, String> result = instance.getPattern();
        Set<Pattern> patterns = result.keySet();
        for (Pattern p : patterns){
            Matcher m = p.matcher(testString);
            if (m.find()){
                System.out.println(String.format("Matches: %s", p.toString()));
            }
        }             
    }
}
