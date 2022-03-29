package pa;

import java.io.IOException;

public class ListCommand extends Command {

    static public Command start() {
        return new ListCommand();
    }

    @Override
    public Command withCatalog(Catalog catalog) {
        this.catalog = catalog;
        return this;
    }

    @Override
    public void run() throws IOException {
        System.out.println("Listing items in catalog: " + catalog.getTitle());
        for(CatalogItem item : catalog.getItems()) {
            System.out.println(item.toString());
        }
    }
}
