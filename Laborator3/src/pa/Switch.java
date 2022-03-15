package pa;

public class Switch extends Node {

    Switch(String name) {
        super(name);
    }

    @Override
    public String toString() {
        StringBuilder builder = super.getUnEndedStringBuilder("Switch");

        builder.append('}');
        return builder.toString();
    }
}
