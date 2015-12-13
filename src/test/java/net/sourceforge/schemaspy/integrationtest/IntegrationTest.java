package net.sourceforge.schemaspy.integrationtest;

import net.sourceforge.schemaspy.Config;
import net.sourceforge.schemaspy.SchemaAnalyzer;
import net.sourceforge.schemaspy.model.Database;
import org.h2.tools.DeleteDbFiles;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;

import static org.junit.Assert.assertNotNull;

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
    public void test() throws IOException, SQLException {
        // given
        SchemaAnalyzer analyzer = new SchemaAnalyzer();
        String[] argv = new String[12];
        argv[0] = "-t";
        argv[1] = "h2";
        argv[2] = "-host";
        argv[3] = "localhost";
        argv[4] = "-db";
        argv[5] = "./src/test/resources/testdb/testdb";
        argv[6] = "-u";
        argv[7] = "";
        argv[8] = "-p";
        argv[9] = "";
        argv[10] = "-o";
        argv[11] = "./src/test/resources/testdb/output";

                //java -jar schemaSpy_5.0.0.jar -t orathin -host sitaki.rz.puzzle.ch -port 1521 -db PITC01 -u <USER> -p <pw> -s <SCHEMA> -o ./output/ -dp driver.jar

        // when
        Database result = analyzer.analyze(new Config(argv));

        // then
        assertNotNull(result);

    }
}
