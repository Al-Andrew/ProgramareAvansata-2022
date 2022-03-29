package pa;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Lab5 {

    public static void main(String[] args) throws IOException, CommandException, TemplateException {
        Lab5 lab5 = new Lab5();

        //lab5.compulsory();
        lab5.homework();
    }

    public void compulsory() throws IOException {
       Catalog docs = new Catalog("Documentation");
       docs.add(new Book("knuth67", "The Art of Computer Programming",
               "C:/school/bib/knuth76.pdf", "Donal E. Knuth"));
       docs.add(new Website("java17", "The Java Language Specification",
               "https://docs.oracle.com/javase/specs/jls/se17/html/index.html", new Date()));

        CatalogUtil.save(docs, "./test.json");

    }

    public void homework() throws IOException, CommandException, TemplateException {
        Catalog catalog = new Catalog();

        LoadCommand.start()
                    .withCatalog(catalog)
                    .withFile("./test.json")
                    .run();

        ListCommand.start()
                .withCatalog(catalog)
                .run();

        ViewCommand.start()
                .withItem(catalog.findById("knuth67"))
                .run();

        ReportCommand.start()
                .withCatalog(catalog)
                .withFile("test.html")
                .run();

    }

}

