package net.sourceforge.schemaspy.view;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.schemaspy.model.Database;
import net.sourceforge.schemaspy.model.Table;

import org.junit.Test;
import org.mockito.Mockito;

import freemarker.template.TemplateException;

import static org.junit.Assert.assertEquals;

public class HtmlMainIndexPageTest {

	
	@Test
	public void testOutputSingleTables() throws SQLException, IOException, TemplateException {
		// given
		HtmlMainIndexPage instance = HtmlMainIndexPage.getInstance();
		Table table = Mockito.mock(Table.class);
		Mockito.when(table.getName()).thenReturn("table");
		Mockito.when(table.getComments()).thenReturn("comments");
		
		Table table2 = Mockito.mock(Table.class);
		Mockito.when(table2.getName()).thenReturn("table2");
		Mockito.when(table2.getComments()).thenReturn("comments2");

		List<Table> tables = new ArrayList<Table>();
		tables.add(table);
		tables.add(table2);
		
		
		GlobalData globalData = new GlobalData();
		Database database = Mockito.mock(Database.class);
		globalData.setDatabase(database);
		
		MainIndexPageData data = new MainIndexPageData();
		data.setGlobalData(globalData);
		data.setTables(tables);
		data.setRemoteTables(tables);
		data.setShowIds(true);
		data.setNumberOfTables(tables.size() - 0);
		data.setNumberOfViews(0);
		data.setNumberTableCols(12);
		data.setNumberTableRows(2000);
		data.setNumberViewCols(0);
		
		// when
		String result = instance.writeTables(data);

		//then
		String expected = readFile("src/test/resources/templates/testtemplates/expectedresults/MainIndexResult1.html");
		assertEquals(expected, result);
	}

	private String readFile(String filename) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(filename));

		return new String(bytes, Charset.forName("UTF-8"));
	}
}
