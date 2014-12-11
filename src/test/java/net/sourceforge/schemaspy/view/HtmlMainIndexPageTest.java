package net.sourceforge.schemaspy.view;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import net.sourceforge.schemaspy.model.Database;
import net.sourceforge.schemaspy.model.Table;
import net.sourceforge.schemaspy.util.LineWriter;

import org.junit.Test;
import org.mockito.Mockito;

import freemarker.template.TemplateException;

public class HtmlMainIndexPageTest {

	@Test
	public void testOutput() throws SQLException, IOException, TemplateException {
		// given
		HtmlMainIndexPage instance = HtmlMainIndexPage.getInstance();
		Table table = Mockito.mock(Table.class);
		Mockito.when(table.getName()).thenReturn("table");
		Mockito.when(table.getComments()).thenReturn("comments");
		//new Table(db, "catalog", "schema", "test", "comments");
		LineWriter html = new LineWriter(System.out, "ISO-8859-1");
		// when
		instance.writeLineItem(table , true, html);
		
		
		instance.writeLineItemFM(table , true, html);
		
		html.flush();
		//then
		
	}

}
