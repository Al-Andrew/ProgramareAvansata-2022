package pa;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class ViewCommand extends Command {

    static public Command start() {
        return new ViewCommand();
    }

    @Override
    public Command withItem(CatalogItem item) {
        this.item = item;
        return this;
    }

    @Override
    public void run() throws IOException {
        Desktop.getDesktop().open(new File(this.item.getLocation()));
    }
}
