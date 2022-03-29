package pa;

import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.nio.file.Path;

public abstract class Command {

    Catalog catalog;
    CatalogItem item;
    String file;

    protected Command() {
        this.catalog = null;
        this.item = null;
        this.file = null;
    }

    static public Command start() throws CommandException {
        throw new CommandException("Cannot start an abstract command");
    }

    public Command withCatalog(Catalog catalog) throws CommandException {
        throw new CommandException("This command does not take a catalog as parameter");

    }

    public Command withItem(CatalogItem item) throws CommandException {
        throw new CommandException("This command does not take an item as parameter");

    }

    public Command withFile(String file) throws CommandException {
        throw new CommandException("This command does not take a file as parameter");

    }

    public abstract void run() throws IOException, TemplateException;
}
