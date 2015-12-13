package net.sourceforge.schemaspy.model.xml;

import net.sourceforge.schemaspy.model.InvalidConfigurationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class SchemaMetaTest {

    @Test
    public void testSchemaContrutctor(){
        // given
        String directory = "src/test/resources";
        String meta = "test";

        // when
        SchemaMeta test = new SchemaMeta(directory, meta, null);

        // then
        assertNotNull(test);
        assertEquals(1, test.getTables().size());
        assertEquals("author", test.getTables().get(0).getName());
        assertEquals("test", test.getTables().get(0).getComments());
        assertEquals("additionalInfoLink", test.getTables().get(0).getAdditionalInfo().get(0).getKey());
        assertEquals("http://google.ch", test.getTables().get(0).getAdditionalInfo().get(0).getValue());
    }

    @Test(expected = InvalidConfigurationException.class)
    public void testSchemaContrutctor_no_directory(){
        // given
        String directory = "notavailabledirectory";
        String meta = "test";

        // when
        new SchemaMeta(directory, meta, null);

        // then
        fail();
    }
    @Test(expected = InvalidConfigurationException.class)
    public void testSchemaContrutctor_no_file(){
        // given
        String directory = "src/test/resources";
        String meta = "noFile";

        // when
        new SchemaMeta(directory, meta, null);

        // then
        fail();
    }

}