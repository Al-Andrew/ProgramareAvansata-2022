package pa;

public class Lab3 {
    public static void main(String[] args) {
        Lab3 lab3 = new Lab3();

        lab3.compulsory();
        lab3.homework();
    }

    public void compulsory() {
        Network myNetwork = new Network();

        myNetwork.addAllNodes(new Node[]{
                new Computer("Computer A", "192.168.1.101", 1024),
                new Computer("Computer B", "192.168.1.102", 512),
                new Router("Router A", "192.168.1.1"),
                new Router("Router B", "192.168.0.1"),
                new Switch("Switch A"),
                new Switch("Switch B")
        });

        System.out.println(myNetwork);
    }

    public void homework() {
        Network myNetwork = new Network();

        myNetwork.addAllNodes(new Node[]{
                new Computer("Computer A", "192.168.1.101", 1024),
                new Computer("Computer B", "192.168.1.102", 512),
                new Router("Router A", "192.168.1.1"),
                new Router("Router B", "192.168.0.1"),
                new Switch("Switch A"),
                new Switch("Switch B")
        });
        // v1 (Computer A) v2 (Router A) v3 (Switch A) v4 (Switch B) v5 (Router B) v6 (Computer B)
        myNetwork.addLinkByName("Computer A", "Router A", 10); // v1--v2 	10
        myNetwork.addLinkByName("Computer A", "Switch A", 50); // v1--v3 	50
        myNetwork.addLinkByName("Router A", "Switch A", 20); // v2--v3 	20
        myNetwork.addLinkByName("Router A", "Switch B", 20); // v2--v4 	20
        myNetwork.addLinkByName("Router A", "Router B", 20); // v2--v5 	20
        myNetwork.addLinkByName("Switch A", "Switch B", 10); // v3--v4 	10
        myNetwork.addLinkByName("Switch B", "Router B", 30); // v4--v5 	30
        myNetwork.addLinkByName("Switch B", "Computer B", 10); // v4--v6 	10
        myNetwork.addLinkByName("Router B", "Computer B", 20); // v5--v6 	20

        System.out.println(myNetwork);

        // To test out other features for this assignment
        System.out.println(System.lineSeparator());
        System.out.println("Storage of Computer A in different StorageUnits:");
        Storage compA = (Storage) myNetwork.getNodeByName("Computer A");
        System.out.println(compA.getStorageIn(StorageUnit.GIGABYTES));
        System.out.println(compA.getStorageIn(StorageUnit.MEGABYTES));
        System.out.println(compA.getStorageIn(StorageUnit.KILOBYTES));
        System.out.println(compA.getStorageIn(StorageUnit.BYTES));

        System.out.println(System.lineSeparator());
        System.out.println("All identifiable nodes in the network:");
        myNetwork.displayAllIdentifiable();

        System.out.println(System.lineSeparator());
        System.out.println("Routes:");
        System.out.println(myNetwork.getAllRoutes());

    }
}
