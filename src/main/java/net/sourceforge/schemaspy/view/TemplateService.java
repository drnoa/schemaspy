package net.sourceforge.schemaspy.view;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import net.sourceforge.schemaspy.Config;

public class TemplateService {
	private static final String TEMPLATES_PATH = "/templates";
	private static TemplateService instance;
	private static Configuration cfg;
	private final static Logger logger = Logger
			.getLogger(TemplateService.class.getName());

	/**
	 * Singleton: Don't allow instantiation
	 */
	private TemplateService() {
	}

	/**
	 * Singleton accessor
	 * 
	 * @return the singleton instance
	 */
	public static TemplateService getInstance() {
		if(instance == null){
			instance = new TemplateService();
			cfg = getFreemarkerConfig();
		}
		return instance;
	}

	private static Configuration getFreemarkerConfig() {
		// custom template folder comes first, if template is available this
		// template is used for rendering
		File customerTemplateFolder = null;
		FileTemplateLoader customerTemplateLoader = null;
		try {
			String templateDirectory = Config.getInstance()
					.getTemplateDirectory();
			if (templateDirectory != null) {
				customerTemplateFolder = new File(templateDirectory);
				if(customerTemplateFolder.exists()){
					customerTemplateLoader = new FileTemplateLoader(
							customerTemplateFolder);
				}else{
					logger.info("could not read customTemplatefolder: " + templateDirectory + ", default template folder is used.");
				}
				
			}

		} catch (IOException e) {
			logger.log(Level.WARNING, "custom Template folder not found", e);
		}

		// add also mainTemplate folder in the Jar so we use the ClassTemplateLoader
		ClassTemplateLoader ctl = new ClassTemplateLoader(TemplateService.class, TEMPLATES_PATH);

		TemplateLoader[] loaders;

		if (customerTemplateLoader != null && ctl != null) {
			loaders = new TemplateLoader[] { customerTemplateLoader,
					ctl };
		} else if (customerTemplateLoader == null && ctl != null) {
			loaders = new TemplateLoader[] { ctl };
		} else {
			logger.log(Level.WARNING, "no templateloader found");
			loaders = new TemplateLoader[] {};
		}

		MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);

		Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
		cfg.setTemplateLoader(mtl);
		cfg.setDefaultEncoding(Config.DOT_CHARSET);
		cfg.setURLEscapingCharset(Config.DOT_CHARSET);
		cfg.setLocale(Config.getInstance().getLocale());
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		return cfg;
	}
	

	/**
	 * Renders the given Freemarker template and returns the rendered Template
	 * as String
	 * 
	 * @param template
	 * @param data
	 * @return rendered Template
	 * @throws IOException
	 */
	public String renderTemplate(String template, Object data)
			throws IOException {
		Template temp = cfg.getTemplate(template);

		Writer out = new StringWriter();
		try {
			temp.process(data, out);
		} catch (TemplateException e) {
			logger.log(Level.WARNING, "TemplateException: ", e);
		}
		return out.toString();
	}

}
