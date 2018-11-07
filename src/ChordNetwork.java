public class ChordNetwork {

    public static void main(String[] args) {
        SimpleNode node1 = new SimpleNode(256);
        SimpleNode node2 = new SimpleNode(1);

        node1.join(null);
        node2.join(node1);

    }
}
