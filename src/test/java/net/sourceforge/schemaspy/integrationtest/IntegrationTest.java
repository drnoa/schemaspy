package net.sourceforge.schemaspy.integrationtest;

import org.h2.tools.DeleteDbFiles;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

/**
 */
public class IntegrationTest {

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        // remove old database Files
        DeleteDbFiles.execute("./src/test/resources/testdb", "testdb", true);

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:./src/test/resources/testdb/testdb");
        Statement stat = conn.createStatement();
        // create schema and import base data
        stat.execute("runscript from 'src/test/resources/inittestdb.sql'");


        stat.close();
        conn.close();
    }

    @Test
    public void test() {

    }
}
