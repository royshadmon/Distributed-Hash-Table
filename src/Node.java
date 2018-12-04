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
        System.out.println("Node "+ this + "is joining");
        if (node == null) {
            this.initNetwork();
        } else {
            boolean loneNodeMode = this.initFingerTable(node);
            if (loneNodeMode)
                this.updateOthers(node);
            else
                this.updateOthers(null);
        }
    }

    private boolean updateOthers(Node loneNode) {
        if (loneNode != null) {
            // The node provided is the only other node in the network
            this.loneNodeUpdate(loneNode);
            System.out.println("Exiting Lone Node State");
            return true;

        } else {
            // There's more than one other node in the network
            this.update_others();
            for (int i = 1; i <= FingerTable.MAX_ENTRIES ; i++) {
//                System.out.println(this.findPredecessor(this.computeUpdateIndex(i-1)));
            }

            return false;
        }

    }

    private void loneNodeUpdate(Node loneNode) {
        for (int i = 1; i <= FingerTable.MAX_ENTRIES; i++) {
            int start = loneNode.computeStart(i);

            if (inInterval(loneNode.getId(), start, this.getId() + 1)) {
                loneNode.put(i, this);
            } else {
                loneNode.put(i, loneNode);
            }
        }
    }

    private boolean initFingerTable(Node node) {

        if (node.predecessor.getId() == node.getId()) {
            System.out.println(node +" is the Lone node in the network");
            this.loneStateInit(node);

            return true;
        }

        this.put(1, node.findSuccessor(computeStart(1)));

        this.predecessor = this.getSuccessor().predecessor;
        this.getSuccessor().predecessor = this;

        for (int i = 2; i <= FingerTable.MAX_ENTRIES; i++) {
            if (inInterval(this.getId() + 1, computeStart(i), this.get(i-1).getId())) {
                this.put(i, this.getFingerTable().get(i-1));
            } else {
                this.put(i, node.findSuccessor(computeStart(i)));
            }
        }

        return false;
    }

    private void loneStateInit(Node loneNode) {
        for (int i = 1; i <= FingerTable.MAX_ENTRIES; i++) {
            if (inInterval(loneNode.getId(), this.computeStart(i), this.getId()))
                this.put(i, this);
            else
                this.put(i, loneNode);
        }

        this.predecessor = loneNode;
        loneNode.predecessor = this;
    }

    public void update_others() {
        for (int i = 1; i <= FingerTable.MAX_ENTRIES; i++) {
            int updateIndex = computeUpdateIndex(i-1);
            System.out.println("compute upate index " + computeUpdateIndex(i-1));
            Node pred;

            pred = this.findPredecessor(updateIndex);

            if (pred.getSuccessor().getId() == updateIndex) pred = pred.getSuccessor();

            pred.update_finger_table(this, i);

        }
    }

    private void update_finger_table(Node node, int i) {
        if (inInterval(this.getId() + 1, node.getId(), this.getFingerTable().get(i).getId())) {
            this.getFingerTable().put(i, node);
            Node pred = this.predecessor;
            pred.update_finger_table(node, i);
        } else if (this.computeStart(i) == node.nodeId) {
            this.getFingerTable().put(i, node);
            Node pred = this.predecessor;
            pred.update_finger_table(node, i);
        }
    }

    private void initNetwork() {
        System.out.println("Network is entering Lone Node State");
        for (int i = 1; i <= FingerTable.MAX_ENTRIES; i++) {
            this.getFingerTable().put(i, this);
        }

        this.predecessor = this;
    }

    private Node findSuccessor(int id) {
        return this.findPredecessor(id).getSuccessor();
    }

    private Node findPredecessor(int id) {

        Node predecessor = this;

        while (!inInterval(predecessor.getId(), id, predecessor.getSuccessor().getId() + 1)) {
            Node temp = predecessor.findClosestPrecedingFinger(id);


            if (predecessor.getId() == temp.getId()) {
//                break;
                return temp.predecessor; //break
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


    /************************************************************************************************
     HELPER METHODS
     ***********************************************************************************************/

    private Node getSuccessor() {
        return this.getFingerTable().get(1);
    }

    private int computeStart(int entryNumber) {
        return FingerTable.computeStart(this.getId(), entryNumber);
    }

    private int computeUpdateIndex(int index) {
        int result = this.getId() - (int) Math.pow(2, index) + FingerTable.MOD;
        result = result % FingerTable.MOD;
        return result;
        //return ((this.getId() - (int) Math.pow(2, index)) + FingerTable.MOD) % FingerTable.MOD;
    }

    private void prettyPrint() {
        this.table.prettyPrint();
    }

    public String toString() {
        return "" + this.getId();
    }

    private void put(int entryNumber, Node node) {
        this.getFingerTable().put(entryNumber, node);
    }

    private Node get(int entryNumber) {
        return this.getFingerTable().get(entryNumber);
    }

    /************************************************************************************************
     MAIN METHOD
     ***********************************************************************************************/

    public static void main(String[] args) {
        Node node0 = new Node(7);
        node0.join(null);

        Node node2 = new Node(0);
        node2.join(node0);

        Node node4 = new Node(2);
        node4.join(node2);
//        System.out.println(node0.findSuccessor(node4.computeStart(1)));

        Node node5 = new Node(1);
        node5.join(node4);

        System.out.println(node2.findSuccessor(node5.computeStart(1)));

        node0.prettyPrint();
        node2.prettyPrint();
        node4.prettyPrint();
        node5.prettyPrint();

    }

    /************************************************************************************************
     PRIVATE STATIC HELPER FUNCTIONS
     ***********************************************************************************************/

    private static int hash(int number) { return number & 0xff; }

    private static boolean inInterval(int start, int nodeId, int end) {
        //if (start == end) return false;

        if (start > end) return (start < nodeId || nodeId < end);
        else return start < nodeId && nodeId < end;
    }
}
