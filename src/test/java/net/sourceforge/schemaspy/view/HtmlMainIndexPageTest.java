package net.sourceforge.schemaspy.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.schemaspy.model.Table;
import net.sourceforge.schemaspy.util.LineWriter;

import org.junit.Test;
import org.mockito.Mockito;

import freemarker.template.TemplateException;

public class HtmlMainIndexPageTest {

	
	@Test
	public void testOutputSingleTables() throws SQLException, IOException, TemplateException {
		// given
		HtmlMainIndexPage instance = HtmlMainIndexPage.getInstance();
		Table table = Mockito.mock(Table.class);
		Mockito.when(table.getName()).thenReturn("table");
		Mockito.when(table.getComments()).thenReturn("comments");
		
		Table table2 = Mockito.mock(Table.class);
		Mockito.when(table2.getName()).thenReturn("TAART");
		Mockito.when(table2.getComments()).thenReturn("comments2");

		LineWriter html = new LineWriter(System.out, "ISO-8859-1");
		List<Table> tables = new ArrayList<Table>();
		tables.add(table);
		tables.add(table2);
		// when
		instance.writeTables(tables  , true, html, 2, 0);
		
		html.flush();
		//then
		
	}

}
