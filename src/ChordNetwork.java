public class ChordNetwork {

    public static void main(String[] args) {
        SimpleNode node1 = new SimpleNode(1);
        SimpleNode node2 = new SimpleNode(3);
        SimpleNode node3 = new SimpleNode(2);
        SimpleNode node4 = new SimpleNode(6);
        SimpleNode node5 = new SimpleNode(7);
        SimpleNode node6 = new SimpleNode(4);
        SimpleNode node7 = new SimpleNode(10);

        node1.join(null);
        node2.join(node1);
        node3.join(node1);
        node4.join(node1);
        node5.join(node1);
        node6.join(node1);
        node7.join(node1);

    }
}
