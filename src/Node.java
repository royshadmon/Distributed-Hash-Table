import java.util.List;
import java.util.ArrayList;

public class Node {

    private int nodeId;
    public Node successor;
    public Node predecessor;
    private FingerTable table;
    private List<Integer> keys = new ArrayList<>();

    Node(int id) {
        this.nodeId = hash(id);
        this.table = new FingerTable(this.nodeId);  //Don't uncomment unless finger tables get populated on their own
        successor = null;
        predecessor = null;
    }

    public void join(Node node) {
        if (node != null) {
            this.initFingerTable(node);
            this.updateOthers();
        }
        else {
            for (int i = 1; i <= FingerTable.MAX_ENTRIES; i++) {
                this.table.put(i, this);
            }
            this.predecessor = this;
        }
    }

    private void initFingerTable(Node node) {
        this.table.put(1, node.findSuccessor(this.table.get(1)));

        this.predecessor = this.successor.predecessor;
        this.successor.predecessor = this;

        for (int i = 1; i < FingerTable.MAX_ENTRIES - 1; i++) {
            if (this.table.get(i+1).getId() >= this.getId() && this.table.get(i+1).getId() < this.table.get(i).getId()) {
                this.table.put(i + 1, this.table.get(i));
            }
            else {
                this.table.put(i+1, node.findSuccessor(this.table.get(i+1)));
            }
        }
    }

    private void updateOthers() {
        for (int i = 1; i <= FingerTable.MAX_ENTRIES; i++) {
            Node temp = new Node(this.getId() - (int) Math.pow(2,i-1));
            Node p = findPredecessor(temp);
            p.updateFingerTable(this, i);
        }
    }

    private void updateFingerTable(Node node, int i) {
        if (node.getId() >= this.getId() && node.getId() < this.table.get(i).getId()) {
            this.table.put(i, node);
            Node p = this.predecessor;
            p.updateFingerTable(node, i);
        }
    }

    public Node findSuccessor(Node node) {

        if (this.equals(node)) return this;

        Node nPrime = this.findPredecessor(node);
        return nPrime.successor;
    }

    public Node findPredecessor(Node node) {
        if (this.successor.equals(node)) return this;

        Node nPrime = this;

        /*
         *  node.getId() should not be between nPrime.getId() and nPrime.successor
         *  nPrime.getId() < node.getId() <= nPrime.successor.getId()
         *  nPrime.getId() >= node.getId() || node.getId > nPrime.successor.getId()
         */

        while (!(nPrime.getId() < node.getId() && node.getId() <= nPrime.successor.getId())) {

            if (nPrime.equals(nPrime.closestPrecedingFinger(node))) return nPrime;
            else nPrime = nPrime.closestPrecedingFinger(node);
        }

        return nPrime;

    }

    public Node closestPrecedingFinger(Node node) {

        for (int i = FingerTable.MAX_ENTRIES; i >=1; i--) {
            Node fingerNodeI = this.table.get(i);
            if (this.getId() < fingerNodeI.getId() && fingerNodeI.getId() < node.getId())
                return fingerNodeI;
        }

        return this;
    }

    private int hash(int number) { return number & 0xff; }

    /************************************************************************************************

       GETTERS AND SETTERS

     ***********************************************************************************************/

    public int getId() { return nodeId; }

    public void setId(int id) { this.nodeId = id; }

    public FingerTable getFingerTable() { return this.table; }

    public void setFingerTable(FingerTable fingerTable) { this.table = fingerTable; }

    public List<Integer> getKeys() { return keys; }

    public void setKeys(List<Integer> keys) { this.keys = keys; }
}