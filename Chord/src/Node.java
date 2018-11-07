public class Node implements ChordNode {

    private int nodeId;
    private FingerTable table;


    Node(byte id) {
        System.out.println(id);
        nodeId = hash(id);
        System.out.println(nodeId);
        table = null;
    }

    public int getId() {
        return nodeId;
    }

    public void setId(byte id) {
        this.nodeId = hash(id);
    }

    public void setId(int id) {
        this.nodeId = id;
    }

    public void find() {

    }

    public void join(Node node) {
        /*
            If node is null, and that means network has been initialized.
            There are ways to do this mentioned in the paper apparently.
        */
    }

    public void insert(byte key, byte value) {
        int keyId = hash(key);
        int valueId = hash(value); // Yes we need to

        // Lots of extra stuff to be done
    }

    public void remove(byte key) {
        int keyId = hash(key);
    }

    public void prettyPrint() {
        System.out.println("Node ID: " + nodeId);
        this.table.prettyPrint();
    }

    private int hash(byte number) {
        return number & 0xff;
    }

    public static void main(String[] args) {
        ChordNode node = new Node((byte) 0);
    }
}