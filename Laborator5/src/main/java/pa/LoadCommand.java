package pa;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.Reference;

public class LoadCommand extends Command {


    static public Command start() {
        return new LoadCommand();
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
    public void run() throws IOException {
        FileReader reader = new FileReader(file);
        ObjectMapper mapper = new ObjectMapper();
        Catalog t = mapper.readValue(reader, Catalog.class);
        catalog.setTitle(t.getTitle());
        catalog.setItems(t.getItems());
    }
}
