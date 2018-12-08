import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChordNetwork {

    public static void main(String[] args) {

        int totalNodes = 11;
        int totalKeys = 13;

        List<Node> nodes = ChordTester.generateRandomNodeList(totalNodes);
        List<Integer> keys = ChordTester.generateRandomKeyList(totalKeys);

        Node node0 = nodes.get(0);
        node0.join(null);

        Node node1 = nodes.get(1);
        node1.join(node0);

        node0.insert(keys.get(0));
        node1.insert(keys.get(1));

        Node node2 = nodes.get(2);
        node2.join(node0);

        Node node3 = nodes.get(3);
        node3.join(node2);

        Node node4 = nodes.get(4);
        node4.join(node1);

        node3.insert(keys.get(2));
        node0.insert(keys.get(3));

        Node node5 = nodes.get(5);
        node5.join(node2);

        node4.insert(keys.get(4));
        node3.insert(keys.get(5));
        node0.insert(keys.get(6));
        node0.insert(keys.get(7));
        node1.insert(keys.get(8));
        node2.insert(keys.get(9));

        Node node6 = nodes.get(6);
        node6.join(node5);

        Node node7 = nodes.get(7);
        node7.join(node4);

        Node node8 = nodes.get(8);
        node8.join(node7);

        node8.insert(keys.get(10));
        node4.insert(keys.get(11));
        node6.insert(keys.get(12));

        Node node9 = nodes.get(9);
        node9.join(node0);

        Node node10 = nodes.get(10);
        node10.join(node7);

        System.out.println("------------Printing final node tables after inserting all nodes-----------");
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


        System.out.println("------------Nodes leave now, then reprint---------------");

        node9.leave();
        node0.leave();
        node3.leave();
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

        ChordTester.generateRandomNodeList(totalNodes);
        ChordTester.generateRandomKeyList(totalKeys);

    }

    private static class ChordTester {

        private static Random generator = new Random(0);

        private static List<Node> generateRandomNodeList(int numberOfNodes) {
            List<Node> nodeList = new ArrayList<>();

            for (int i = 0; i < numberOfNodes; i++) {
                int nodeId = generator.nextInt(FingerTable.MAX_NODES);
                nodeList.add(new Node(nodeId));
            }

            System.out.println("Nodes that have been generated: " + nodeList);
            return nodeList;
        }

        private static List<Integer> generateRandomKeyList(int numberOfKeys) {
            List<Integer> keyList = new ArrayList<>();

            for (int i = 0; i < numberOfKeys; i++) {
                int keyId = generator.nextInt(FingerTable.MAX_NODES);
                keyList.add(keyId);
            }
            System.out.println("Keys that have been generated: " + keyList);
            return keyList;
        }

    }

}
