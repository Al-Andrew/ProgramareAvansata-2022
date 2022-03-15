package pa;

public class Router extends Node implements Identifiable {
    private String address;

    Router(String name, String address) {
        super(name);
        this.address = address;
    }

    @Override
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address; // TODO: validate the given address
    }

    @Override
    public String toString() {
        StringBuilder builder = super.getUnEndedStringBuilder("Router");

        builder.append("    address: ").append(address).append(System.lineSeparator());
        builder.append('}');
        return builder.toString();
    }
}

