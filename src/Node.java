import java.util.ArrayList;
import java.util.List;

public class Node {

    private int nodeId;
    private Node predecessor;
    private FingerTable table;
    private List<Integer> keys;

    Node (int nodeId) {
        this.nodeId = hash(nodeId);
        this.table = new FingerTable(nodeId);
        this.keys = new ArrayList<>();
        this.predecessor = null;
    }

    public int getId() {
        return this.nodeId;
    }

    public FingerTable getFingerTable() {
        return this.table;
    }

    public List<Integer> getKeys() {
        return this.keys;
    }

    private void join(Node node) {

        System.out.println("Node " + this.getId() + "is joining");
        if (node == null) {
            this.initNetwork();
        } else {
            this.initFingerTable(node);
        }
    }

    private void initFingerTable(Node node) {
        this.getFingerTable().put(1, node.findSuccessor(computeStart(1)));
        this.predecessor = this.getSuccessor().predecessor;
        this.getSuccessor().predecessor = this;

        for (int i = 1; i <= FingerTable.MAX_ENTRIES - 1; i++) {
            if (inInterval(this.getId(), computeStart(i+1), this.getFingerTable().get(i).getId())) {
                this.getFingerTable().put(i+1, this.getFingerTable().get(i));
            } else {
                this.getFingerTable().put(i+1, node.findSuccessor(computeStart(i+1)));
            }
        }
    }


    private void initNetwork() {
        for (int i = 1; i <= FingerTable.MAX_ENTRIES; i++) {
            this.getFingerTable().put(i, this);
        }

        this.predecessor = this;
    }

    private Node findSuccessor(int id) {
        if (this.getId() == id) return this;
        else return this.findPredecessor(id).getSuccessor();
    }

    private Node findPredecessor(int id) {
        Node predecessor = this;

//        if (this.getSuccessor() == this) return this; // This means that I am the only node in the network
//
//        if (predecessor.getId() == id) {
//            return predecessor.getSuccessor().findPredecessor(id);
//        }

        while (!inInterval(predecessor.getId(), id, predecessor.getSuccessor().getId() + 1)) {
            Node temp = predecessor.findClosestPrecedingFinger(id);

            if (predecessor.getId() == temp.getId()) {
                return this.predecessor;
            }
            predecessor = temp;
        }
        return predecessor;
    }

    private Node findClosestPrecedingFinger(int nodeId) {
        for (int i = FingerTable.MAX_ENTRIES; i >= 1 ; i--) {
            Node node = this.getFingerTable().get(i);

            if (inInterval(this.getId(), node.getId(), nodeId)) {
                return node;
            }
        }

        return this;
    }

    private Node getSuccessor() {
        return this.getFingerTable().get(1);
    }

    private int computeStart(int entry) { return (this.getId() + (int) Math.pow(2, entry)) % FingerTable.MOD; }

    private void prettyPrint() {
        this.table.prettyPrint();
    }

    /************************************************************************************************
     MAIN METHOD
     ***********************************************************************************************/

    public static void main(String[] args) {
        Node node0 = new Node(0);
        Node node1 = new Node(1);
        Node node3 = new Node(3);

        node0.predecessor = node0;
//        node1.predecessor = node0;
//        node3.predecessor = node1;

        node0.table.put(1, node0);
        node0.table.put(2, node0);
        node0.table.put(3, node0);

        node1.initFingerTable(node0);

        for (int i = 1; i <= FingerTable.MAX_ENTRIES; i++) {

            int id = (node1.getId() - (int) Math.pow(2, i - 1));

            id = (id + 8) % 8;

            Node p = node1.findPredecessor(id);
            System.out.println(p.getId());
            p.updateFingerTable(node1, i);
        }

        node0.prettyPrint();
        node1.prettyPrint();



    }

    private void updateFingerTable(Node node, int i) {
        if (inInterval(this.getId(), node.getId()+1, this.getFingerTable().get(i).getId())) {
            this.getFingerTable().put(i, node);
            Node p = this.predecessor;
            p.updateFingerTable(node, i);
        }
    }

    /************************************************************************************************
     PRIVATE STATIC HELPER FUNCTIONS
     ***********************************************************************************************/

    private static int hash(int number) { return number & 0xff; }

    private static boolean inInterval(int start, int nodeId, int end) {
        if (start == end) return false;

        if (start > end) return (start < nodeId || nodeId < end);
        else return start < nodeId && nodeId < end;
    }
}
