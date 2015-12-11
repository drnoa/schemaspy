package net.sourceforge.schemaspy.view;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TemplateServiceTest {

    public static final String TESTTEMPLATES_FOOTEMPLATE_FTL = "testtemplates/footemplate.ftl";

    @Test
    public void renderTemplate_shouldRenderSuccessfully() throws IOException {
        // given
        String template = TESTTEMPLATES_FOOTEMPLATE_FTL;
        Map<String, String> data = new HashMap();
        data.put("foo", "data");

        //when
        String result = TemplateService.getInstance().renderTemplate(template, data);

        // then
        assertEquals("data", result);
    }

    @Test(expected = IOException.class)
    public void renderTemplate_shouldRenderTemplateNotFound() throws IOException {
        // given
        String template = "notfoundtemplate.ftl";
        Map<String, String> data = new HashMap();
        data.put("foo", "data");

        //when
        String result = TemplateService.getInstance().renderTemplate(template, data);

        // then
        fail();
    }

}