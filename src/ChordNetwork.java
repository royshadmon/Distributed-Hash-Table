public class ChordNetwork {

    public static void main(String[] args) {
        // Testing findSuccessor, findPredecessor and closestPrecedingFinger
        Node node0 = new Node(0);
        FingerTable ftable0 = new FingerTable(node0.getId());
        Node node1 = new Node(1);
        FingerTable ftable1 = new FingerTable(node1.getId());
        Node node3 = new Node(3);
        FingerTable ftable3 = new FingerTable(node3.getId());

        node0.successor = node0;
        node1.successor = node1;
        node3.successor = node3;

        node0.predecessor = node3;
        node1.predecessor = node0;
        node3.predecessor = node1;

        ftable0.put(1, node1);
        ftable0.put(2, node3);
        ftable0.put(3, node0);

        ftable1.put(1, node3);
        ftable1.put(2, node3);
        ftable1.put(3, node0);

        ftable3.put(1, node0);
        ftable3.put(2, node0);
        ftable3.put(3, node0);

        ftable0.prettyPrint();
        ftable1.prettyPrint();
        ftable3.prettyPrint();

        node0.setFingerTable(ftable0);
        node1.setFingerTable(ftable1);
        node3.setFingerTable(ftable3);

        System.out.println(node0.findSuccessor(node0).getId());

    }
}
