package net.sourceforge.schemaspy.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.schemaspy.model.Database;
import net.sourceforge.schemaspy.model.Table;

import org.junit.Test;
import org.mockito.Mockito;

import freemarker.template.TemplateException;

public class HtmlTablePageTest {

	
	@Test
	public void testOutputSingleTable() throws SQLException, IOException, TemplateException {
		// given
		HtmlTablePage instance = HtmlTablePage.getInstance();
		Table table = Mockito.mock(Table.class);
		Mockito.when(table.getName()).thenReturn("table");
		Mockito.when(table.getComments()).thenReturn("comments");
		
		
		GlobalData globalData = new GlobalData();
		Database database = Mockito.mock(Database.class);
		globalData.setDatabase(database);
			
		TablePageData data = new TablePageData();
        data.setTable(table);
        data.setGlobalData(globalData);
        
        data.setHasImplied(true);
        data.setCheckShowComments(true);
		
		
		System.out.println(instance.writeMainTable(table, data));

		
	}

}
