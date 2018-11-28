public class NodeJoin {

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);

        node1.join(null);
        //node1.getFingerTable().prettyPrint();
        node2.join(node1);
    }
}
