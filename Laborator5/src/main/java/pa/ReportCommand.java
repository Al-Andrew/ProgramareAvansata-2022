package pa;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ReportCommand extends Command{

    static public Command start() {
        return new ReportCommand();
    }

    @Override
    public Command withCatalog(Catalog catalog) {
        this.catalog = catalog;
        return this;
    }

    @Override
    public Command withFile(String file) {
        this.file = file;
        return this;
    }

    @Override
    public void run() throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(new File("."));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);

        Map root = new HashMap();
        root.put("catalog", catalog);
        CatalogItem latest = catalog.getItems().get(0);
        root.put("items", catalog.getItems());

        /* Get the template (uses cache internally) */

        Template temp = cfg.getTemplate("template.html");

        /* Merge data-model with template */
        Writer out = new FileWriter(file);
        temp.process(root, out);
        Desktop.getDesktop().open(new File(file));
    }
}
