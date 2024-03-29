package pa;

public class Computer extends Node implements Identifiable, Storage {
    private String address;
    private final int storageCapacity;

    public Computer(String name, String address, int storageCapacity) {
        super(name);
        this.address = address;
        this.storageCapacity = storageCapacity;
    }

    @Override
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address; // TODO: validate the given address
    }

    @Override
    public int getStorageCapacity() {
        return storageCapacity;
    }

    @Override
    public String toString() {
        StringBuilder builder = super.getUnEndedStringBuilder("Computer");

        builder.append("    address: ").append(address).append(System.lineSeparator());
        builder.append("    storage: ").append(storageCapacity).append(System.lineSeparator());
        builder.append('}');
        return builder.toString();
    }
}
