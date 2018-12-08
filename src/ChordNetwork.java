import java.util.ArrayList;
import java.util.List;

public class ChordNetwork {

    public static void main(String[] args) {

        // Make 2 lists of numbers from 0 to 255

        List<Integer> NodeList = new ArrayList<>();
        List <Integer> KeyList = new ArrayList<>();

        for (int i = 0; i < FingerTable.MAX_NODES; i++) {
            NodeList.add(i);
            KeyList.add(i);
        }

        int totalNodes = 11;
        int totalKeys = 13;

        List<Node> nodes = new ArrayList<>();
        List<Integer> keys = new ArrayList<>();

        for (int i = 0; i < totalNodes; i++) {
            int index = (int) (Math.random()*(255-i));
            int num = NodeList.get(index);
            System.out.println("Adding Node " + num);
            nodes.add(new Node(num));
            NodeList.remove(index);
        }

        for (int i = 0; i < totalKeys; i++) {
            int index = (int) (Math.random() * (255-i));
            int num = KeyList.get(index);

            keys.add(num);
            KeyList.remove(index);
        }

        Node node0 = nodes.get(0);
        node0.join(null);

        Node node1= nodes.get(1);
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



//        System.out.println(node0.getKeys().get(0)==255);
//        System.out.println(node10.getKeys().get(0)==0);
//
//        System.out.println(node1.getKeys().get(0)==2);
//        System.out.println(node2.getKeys().get(0)==4);
//        System.out.println(node3.getKeys().get(0)==8);
//
//        System.out.println(node4.getKeys().size());
//
//        System.out.println(node5.getKeys().get(0)==17);

    }
}
