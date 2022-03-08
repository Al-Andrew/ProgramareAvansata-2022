package pa;

public class Lab3 {
    public static void main(String[] args) {
        Lab3 lab3 = new Lab3();

        lab3.compulsory();
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
}
