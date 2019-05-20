/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pastesitessearch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 * @author utente
 */
public class MySQLUtils {

    private static final int NOSTATUS = -1;
    private static final String DBConnectionString = "jdbc:mysql://192.168.0.29/pastes_db";
    private static final String insertPatternQuery = "insert ignore INTO patterns (pattern, short_name) values ( ? , ?)";
    private static final String insertPasteQuery = "insert ignore INTO pastes (pasted_text, status, paste_remote_id, site ) values ( ? , ? , ?, ?)";
    private static final String updatePasteQuery = "update ignore pastes set pasted_text = ?, status = ? where paste_remote_id = ? and site = ? ";
    private static final String insertRemoteIDQuery = "insert ignore INTO pastes (paste_remote_id, site ) values ( ? , ? )";
    private static final String readPatternQuery = "select pattern, short_name from patterns";
    //private final String checkPatternInPastes = "select * from pastes where paste_remote_id = ?";
    private static final String checkRemoteIDInPastes = "select * from pastes where paste_remote_id = ? and site = ?";
    private static final String checkRemoteIDWithStatusInPastes = "select * from pastes where paste_remote_id = ? and site = ? and status = ";
    private static final String checkPatternInPatterns = "select * from patterns where pattern = ?";

    // Query per aggiornare la relazione pastes, pattern
    private static final String selectPatternId_Part1 = " select id from patterns where pattern = ? ";
    private static final String selectPatternId_Part2 = " or pattern = ? ";
    private static final String selectPastesID = "select id from pastes where paste_remote_id = ? and site = ?";
    private static final String insertRelation = "insert ignore into matches (idPaste , idPattern ) values (?, ?)";
    //private final String updatePasteValuesQuery = "update paste";

//    private final String USERNAME = "utente";
//    private final String PASSWORD = "utente";
    private Connection conn = null;

    /**
     * Initialize the MySQL connection
     *
     * @param username The username
     * @param pwd The password
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public synchronized void startDBConnection(char[] username, char[] pwd) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();

        Properties connProperties = new Properties();
//        connProperties.p("user", USERNAME);
//        connProperties.put("password", PASSWORD);

        connProperties.put("user", new String(username));
        connProperties.put("password", new String(pwd));

        conn = DriverManager.getConnection(DBConnectionString, connProperties);
    }

    /**
     * Insert the pattern into the patterns table
     *
     * @param kv Map of pattern and related short name
     * @throws java.sql.SQLException
     */
    public synchronized void insertPatternWithShortNameIntoDB(Map<Pattern, String> kv) throws SQLException {

        try (PreparedStatement statement = conn.prepareStatement(insertPatternQuery)) {
            for (Pattern p : kv.keySet()) {
                statement.setString(1, p.toString());
                statement.setString(2, kv.get(p));
                statement.executeUpdate();
            }
        }
    }

    /**
     * Read the patterns from the patterns table
     *
     * @return The map of the patterns retrieved. The map is null in case of
     * exception
     * @throws SQLException
     */
    public Map<Pattern, String> readPatternFromDB() throws SQLException {
        Map<Pattern, String> kv = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet rs = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement(readPatternQuery);
                ResultSet rs = preparedStatement.executeQuery();) {

            Pattern p;
            kv = new HashMap<>();
            while (rs.next()) {
                String v = rs.getString(2);
                p = Pattern.compile(rs.getString(1), Pattern.CASE_INSENSITIVE);
                kv.put(p, v);
            }
        } catch (SQLException sqlEx) {
            throw new SQLException(sqlEx);  // Rethrows the exception
        }
//        finally {
//            if (rs != null) {
//                rs.close();
//            }
//            if (preparedStatement != null) {
//                preparedStatement.close();
//            }
//        }
        return kv;
    }

    /**
     * Insert a paste text into DB
     *
     * @param remoteID The ID of the pasted text
     * @param pasteText The test referred by the couple remoteID-pasteSite
     * @param pasteSite The site where the content referred by remoteID was
     * grabbed
     * @param status The status of the pasted text (see db status table)
     * @throws SQLException
     */
    public synchronized void insertPasteIntoDB(String remoteID, String pasteText, String pasteSite, int status) throws SQLException {
        String query = insertPasteQuery;

        if (checkRemoteIDInDBPastes(remoteID, pasteSite)) {
            query = updatePasteQuery;
        }
//insert INTO pastes (pasted_text, status, paste_remote_id, site, ) values ( ? , ? , ?, ?)";
//update pastes set pasted_text = ?, status= ? where paste_remote_id = ? and site = ?, ";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, pasteText);
            statement.setInt(2, status);
            statement.setString(3, remoteID);
            statement.setString(4, pasteSite);
            statement.executeUpdate();
        }
    }

//    public void insertRemoteIDIntoDB(String remoteID) {
//
//    }
    /**
     * Insert a set of temote ID into DB. Return the remote IDs effectively
     * inserted (not present in DB)
     *
     * @param remoteIDSet
     * @param pasteSite
     * @return
     * @throws java.sql.SQLException
     */
    public synchronized Set<String> insertRemoteIDIntoDB(Set<String> remoteIDSet, String pasteSite) throws SQLException {
        Set<String> temp = new HashSet<>();
        //PreparedStatement preparedStatement = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertRemoteIDQuery)) {

            for (String s : remoteIDSet) {
                if (checkRemoteIDInDBPastes(s, pasteSite)) {
                    continue;
                }

                preparedStatement.setString(1, s);
                preparedStatement.setString(2, pasteSite);
                preparedStatement.addBatch();

                temp.add(s);
                //preparedStatement = conn.prepareStatement(insertRemoteIDQuery);

                for (String s1 : remoteIDSet) {
                    if (checkRemoteIDInDBPastes(s1, pasteSite)) {
                        continue;
                    }

                    preparedStatement.setString(1, s1);
                    preparedStatement.setString(2, pasteSite);
                    preparedStatement.addBatch();

                    temp.add(s1);
                    int[] executeBatch;  // For future use
                    executeBatch = preparedStatement.executeBatch();
                    preparedStatement.close();
                }
            }
        }
        return temp;
    }
    // TODO: creare metodi per inserire gli ID nel DB e aggiornare i record già inseriri
    // TODO: creare metodo per verificare se gli ID esistono
    // TODO: Fare attenzione a render ei metodi thread safe

//    private static void updatePasteDB(String paste) throws SQLException, IOException {
//        if (checkExistence(paste)) {
//            return;
//        }
//
//        String page = null;
//        try {
//            page = readPage(paste);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(DumpMonRegexes.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        String query = "insert INTO pastes (paste_remote_id, site, pasted_text) values ( ? , ? , ?)";
//        PreparedStatement statement = conn.prepareStatement(query);
//
//        // query = "insert INTO pastes (paste_remote_id, site, ) values ( ? , ? )";
//        statement.setString(1, paste);
//        statement.setString(2, "https://pastebin.com");
//        if (parsePastePage(page)) {
//            statement.setString(3, page);
//        } else {
//            statement.setString(3, "");
//        }
//        statement.executeUpdate();
//    }
    /**
     * Check existence in patterns table
     *
     * @param pattern
     * @return true if pattern exists, false otherwise
     * @throws SQLException
     */
    public boolean checkPatternInDBPatterns(String pattern) throws SQLException {
        return (checkRecordExistence(checkPatternInPatterns, pattern));
    }

    /**
     * Check the existence of a paste site's remote ID in Pastes table
     *
     * @param remoteID
     * @param pasteSite
     * @return true if remoteID exists, false otherwise
     * @throws SQLException
     */
    public boolean checkRemoteIDInDBPastes(String remoteID, String pasteSite) throws SQLException {
        return (checkRecordExistence(checkRemoteIDInPastes, remoteID, pasteSite));
    }

    /**
     * Check the existence of a paste site's a remote ID with specified status
     * in Pastes table
     *
     * @param remoteID
     * @param pasteSite
     * @param status
     * @return
     * @throws SQLException
     */
    public boolean checkRemoteIDInDBPastes(String remoteID, String pasteSite, int status) throws SQLException {
        String query_temp = null;
        switch (status) {
            case 1:

                break;
            case 2:
                break;
            case 3:
                break;
            default:
                throw new SQLException("Status not known");
        }

        String query = checkRemoteIDWithStatusInPastes + String.format("%d", status);

        return (checkRecordExistence(query, remoteID, pasteSite));
    }

    /**
     * Common code
     *
     * @param query
     * @param pattern
     * @return true if pattern exists, false otherwise
     * @throws SQLException
     */
    private boolean checkRecordExistence(String query, String... parameters) throws SQLException {
        boolean retval = false;
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            int paramCount = 1;

            for (String p : parameters) {
                preparedStatement.setString(paramCount, p);
                paramCount++;
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                retval = resultSet.first();
            }
        }

        return (retval);

    }

    /**
     * Closes the DB connection
     *
     * @throws SQLException
     */
    public synchronized void closeDBConnection() throws SQLException {
        // conn.commit();  // Se autocommit è false decommentare la linea
        conn.close();
    }

    /**
     * Insert the information about the match found in the specific relation
     * table
     *
     * @param remoteID The remote id of the paste site
     * @param site The site wich the remote id refers to
     * @param patterns The patterns matchet against the remoteId contents
     * @throws SQLException
     */
    public synchronized void insertMatchRelation(String remoteID, String site, Set<String> patterns) throws SQLException {

        // Recupero i pattern dal DB
        StringBuilder sb = new StringBuilder(selectPatternId_Part1);
        // Aggiungo tante clausole in OR quanti sono i pattern
        for (int i = 0; i < patterns.size() - 1; i++) {
            sb.append(selectPatternId_Part2);
        }

        String query = sb.toString();

        // Aggiungo i parametri
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            // Aggiungo i parametri
            int counter = 1;
            for (String p : patterns) {
                preparedStatement.setString(counter, p);
                counter++;
            }

            try (ResultSet rsIDPattern = preparedStatement.executeQuery()) {
                try (// Recupero l'id associato a remoteID e site dalla tabella pastes
                        PreparedStatement preparedStatement2 = conn.prepareStatement(selectPastesID)
                        ) {
                    preparedStatement2.setString(1, remoteID);
                    preparedStatement2.setString(2, site);
                    // Dovrebbe essercene solo uno (chiave primaria composita remoteID+Site
                    ResultSet rsIDRemoteID = preparedStatement2.executeQuery();

                    // Aggiorno la tabella dei match
                    int IDPattern;
                    rsIDRemoteID.first();
                    int IDremoteID = rsIDRemoteID.getInt(1);
                    try (PreparedStatement preparedStatement3 = conn.prepareStatement(insertRelation)) {
                        while (rsIDPattern.next()) {
                            IDPattern = rsIDPattern.getInt(1);
                            preparedStatement3.setInt(1, IDremoteID);
                            preparedStatement3.setInt(2, IDPattern);
                            preparedStatement3.addBatch();
                        }
                        preparedStatement3.executeBatch();
                    }
                }
                rsIDPattern.close();
            }
        }
    }
}
