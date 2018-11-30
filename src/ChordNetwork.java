import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChordNetwork {

    public static void main(String[] args) {
//
//        // Testing findSuccessor, findPredecessor and closestPrecedingFinger
//        Node node0 = new Node(0);
//        FingerTable ftable0 = new FingerTable(node0.getId());
//        Node node1 = new Node(1);
//        FingerTable ftable1 = new FingerTable(node1.getId());
//        Node node3 = new Node(3);
//        FingerTable ftable3 = new FingerTable(node3.getId());
//
//        ftable0.put(1, node1);
//        ftable0.put(2, node3);
//        ftable0.put(3, node0);
//
//        ftable1.put(1, node3);
//        ftable1.put(2, node3);
//        ftable1.put(3, node0);
//
//        ftable3.put(1, node0);
//        ftable3.put(2, node0);
//        ftable3.put(3, node0);
//
//        ftable0.prettyPrint();
//        ftable1.prettyPrint();
//        ftable3.prettyPrint();
//
//        node0.setFingerTable(ftable0);
//        node1.setFingerTable(ftable1);
//        node3.setFingerTable(ftable3);
//
//        Node node6 = new Node(6);
//        node6.join(node3);
//
//        node0.getFingerTable().prettyPrint();
//        node1.getFingerTable().prettyPrint();
//        node3.getFingerTable().prettyPrint();

        Node node0 = new Node(0);
        Node node1 = new Node(1);
        Node node3 = new Node(3);

        ArrayList<Integer> keys0 = new ArrayList<>();
        keys0.add(0);
        keys0.add(1);
        keys0.add(2);
        keys0.add(3);
        keys0.add(4);
        keys0.add(5);
        keys0.add(6);
        keys0.add(7);

        node0.setKeys(keys0);

        node0.join(null);
        node1.join(node0);
        node3.join(node1);


//        node0.getFingerTable().prettyPrint();
//        node1.getFingerTable().prettyPrint();
//        node3.getFingerTable().prettyPrint();

//        System.out.println("Now node 6 joins. Let's see the finger tables again.");

        Node node6 = new Node(6);
        node6.join(node3);

//        node0.getFingerTable().prettyPrint();
//        node1.getFingerTable().prettyPrint();
//        node3.getFingerTable().prettyPrint();
//        node6.getFingerTable().prettyPrint();

        System.out.println("Printing new keys in Node 6");
        node6.getKeys().forEach(key -> System.out.println(key));

        System.out.println("Printing new keys in Node 0");
        node0.getKeys().forEach(key -> System.out.println(key));

        System.out.println("Printing new keys in Node 1");
        node1.getKeys().forEach(key -> System.out.println(key));

        System.out.println("Printing new keys in Node 3");
        node3.getKeys().forEach(key -> System.out.println(key));

    }
}
