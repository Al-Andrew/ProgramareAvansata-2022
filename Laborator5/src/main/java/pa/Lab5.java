package pa;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

public class Lab5 {

    public static void main(String[] args) throws IOException {
        Lab5 lab5 = new Lab5();


        lab5.compulsory();
    }

    public void compulsory() throws IOException {
        Catalog docs = new Catalog("Documentation");
        docs.add(new Book("knuth67", "The Art of Computer Programming",
                "C:/school/bib/knuth76.pdf", "Donal E. Knuth"));
        docs.add(new Website("java17", "The Java Language Specification",
                "https://docs.oracle.com/javase/specs/jls/se17/html/index.html", new Date()));

        CatalogUtil.save(docs, "./test.json");
    }


}

