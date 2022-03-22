package pa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CatalogUtil {

    public static void save(Catalog catalog, String to) throws IOException {
        FileWriter writer = new FileWriter(to);
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.writeValue(writer, catalog);
    }

    public static Catalog load(String from) throws IOException {
        FileReader reader = new FileReader(from);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(reader, Catalog.class);
    }

}
