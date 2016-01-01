package net.sourceforge.schemaspy.view;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.junit.Test;
import org.mockito.Mockito;

import freemarker.template.TemplateException;
import net.sourceforge.schemaspy.model.Database;
import net.sourceforge.schemaspy.model.Table;

public class HtmlTablePageTest {

	
	@Test
	public void testOutputSingleTable() throws SQLException, IOException, TemplateException {
		// given
		HtmlTablePage instance = HtmlTablePage.getInstance();
		Table table = Mockito.mock(Table.class);
		when(table.getName()).thenReturn("table");
		when(table.getComments()).thenReturn("comments");
		
		
		GlobalData globalData = new GlobalData();
		Database database = Mockito.mock(Database.class);
		when(database.getConnectTime()).thenReturn("time");
		globalData.setDatabase(database);
			
		TablePageData data = new TablePageData();
        data.setTable(table);
        data.setGlobalData(globalData);
        
        data.setHasImplied(true);
        data.setCheckShowComments(true);
		
		// when
		String result = instance.writeMainTable(table, data);
		
		//then
		String expected = readFile("src/test/resources/templates/testtemplates/expectedresults/TableResult1.html");
		assertEquals(expected, result);
	}

	private String readFile(String filename) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(filename));

		return new String(bytes, Charset.forName("UTF-8"));
	}

}
