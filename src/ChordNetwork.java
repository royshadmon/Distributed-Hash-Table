import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChordNetwork {

    public static void main(String[] args) {

        int totalNodes = 11;
        int totalKeys = 13;
        String resource = "192.168.0.0";

        List<Node> nodes = ChordTester.generateRandomNodeList(totalNodes);
        List<Integer> keys = ChordTester.generateRandomKeyList(totalKeys);

        System.out.println("\nNode " + nodes.get(0) + " is joining.\n");
        Node node0 = nodes.get(0);
        node0.join(null);

        System.out.println("\nNode " + nodes.get(1) + " is joining.\n");
        Node node1 = nodes.get(1);
        node1.join(node0);

        System.out.println("\nInserting keys with id " + keys.get(0) + " and " + keys.get(1) + " at Node "
                + node0 + " and Node " + node1 + "\n");

        node0.insert(keys.get(0), resource);
        node1.insert(keys.get(1), resource);

        System.out.println("\nNode " + nodes.get(2) + " is joining.\n");
        Node node2 = nodes.get(2);
        node2.join(node0);

        System.out.println("\nNode " + nodes.get(3) + " is joining.\n");
        Node node3 = nodes.get(3);
        node3.join(node2);

        System.out.println("\nNode " + nodes.get(4) + " is joining.\n");
        Node node4 = nodes.get(4);
        node4.join(node1);

        System.out.println("\nInserting keys with id " + keys.get(2) + " and " + keys.get(3) + "\n");
        node3.insert(keys.get(2), resource);
        node0.insert(keys.get(3), resource);

        System.out.println("\nNode " + nodes.get(5) + " is joining.\n");
        Node node5 = nodes.get(5);
        node5.join(node2);

        System.out.println("\nInserting key with id " + keys.get(4) + " at Node " + node4 + "\n");
        node4.insert(keys.get(4), resource);

        System.out.println("\nFinding keys, should return null\n");
        System.out.println(node0.find(0));
        System.out.println("End Finding Keys\n");

        System.out.println("\nInserting several keys\n");
        node3.insert(keys.get(5), resource);
        node0.insert(keys.get(6), resource);
        node0.insert(keys.get(7), resource);
        node1.insert(keys.get(8), resource);
        node2.insert(keys.get(9), resource);

        System.out.println("\nNode " + nodes.get(6) + " is joining.\n");
        Node node6 = nodes.get(6);
        node6.join(node5);

        System.out.println("\nNode " + nodes.get(7) + " is joining.\n");
        Node node7 = nodes.get(7);
        node7.join(node4);

        System.out.println("\nNode " + nodes.get(8) + " is joining.\n");
        Node node8 = nodes.get(8);
        node8.join(node7);

        System.out.println("\nInserting several keys\n");
        node8.insert(keys.get(10), resource);
        node4.insert(keys.get(11), resource);
        node6.insert(keys.get(12), resource);

        System.out.println("\nNode " + nodes.get(9) + " is joining.\n");
        Node node9 = nodes.get(9);
        node9.join(node0);

        System.out.println("\nNode " + nodes.get(10) + " is joining.\n");
        Node node10 = nodes.get(10);
        node10.join(node7);

        System.out.println("\n------------Printing final node tables after inserting all nodes-----------");
        node0.prettyPrint();
        node1.prettyPrint();
        node2.prettyPrint();
        node3.prettyPrint();
        node4.prettyPrint();
        node5.prettyPrint();
        node6.prettyPrint();
        node7.prettyPrint();
        node8.prettyPrint();
        node9.prettyPrint();
        node10.prettyPrint();

        System.out.println("\nFinding keys, should be found\n");
        System.out.println(node5.find(64));
        System.out.println(node2.find(232));

        node5.remove(64);
        node5.remove(232);

        System.out.println("\nShould return null, and so this should print true\n");
        System.out.println(node5.find(64) == null);

        System.out.println("\nShould return null, and so this should print true\n");
        System.out.println(node2.find(232) == null);

        System.out.println("End Finding Keys\n");


        System.out.println("\n------------Nodes leave now, then reprint---------------");

        System.out.println("\nNode " + node9 + "is leaving\n");
        node9.leave();

        System.out.println("\nNode " + node0 + "is leaving\n");
        node0.leave();

        System.out.println("\nNode " + node3 + "is leaving\n");
        node3.leave();

        System.out.println("\nNode " + node6 + "is leaving\n");
        node6.leave();

        node0.prettyPrint();
        node1.prettyPrint();
        node2.prettyPrint();
        node3.prettyPrint();
        node4.prettyPrint();
        node5.prettyPrint();
        node6.prettyPrint();
        node7.prettyPrint();
        node8.prettyPrint();
        node9.prettyPrint();
        node10.prettyPrint();

    }

    private static class ChordTester {

        private static Random generator = new Random(0);

        private static List<Node> generateRandomNodeList(int numberOfNodes) {
            List<Integer> ids = generateListOfIds();

            List<Node> nodeList = new ArrayList<>();

            for (int i = 0; i < numberOfNodes; i++) {
                int randomIndex = generator.nextInt(FingerTable.MAX_NODES - i);

                int nodeId = ids.get(randomIndex);
                ids.remove(randomIndex);

                nodeList.add(new Node(nodeId));
            }

            System.out.println("Nodes that have been generated: " + nodeList);
            return nodeList;
        }

        private static List<Integer> generateRandomKeyList(int numberOfKeys) {
            List<Integer> ids = generateListOfIds();
            List<Integer> keyList = new ArrayList<>();

            for (int i = 0; i < numberOfKeys; i++) {
                int randomIndex = generator.nextInt(FingerTable.MAX_NODES - i);
                int keyId = ids.get(randomIndex);
                ids.remove(randomIndex);

                keyList.add(keyId);
            }
            System.out.println("Keys that have been generated: " + keyList);
            return keyList;
        }

        private static List<Integer> generateListOfIds() {
            List<Integer> idList = new ArrayList<>();
            for (int i = 0; i < FingerTable.MAX_NODES; i++) {
                idList.add(i);
            }

            return idList;
        }
    }
}
