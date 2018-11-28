import java.util.SortedSet;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.Iterator;


public class Node {

    private int nodeId;
    private FingerTable table;
    private static int m = FingerTable.MAX_ENTRIES;

    private List<Integer> keys = new ArrayList<>();


    Node(int id) {
        this.nodeId = hash(id);
        this.table = new FingerTable(this.nodeId);
    }

    public void join(Node node) {
        if (node == null) {
            this.initNetwork();
        }
        else {
            List<Node> nodeList = node.getActiveNodes();
            nodeList.add(this);
            this.initFingerTable(nodeList);


        }
    }

    // Lookup how to sort ArrayList. You may need to the comparator class to sort by ID

    private void initFingerTable(List<Node> nodeList) {
        for (int i = 1; i <= m; i++) {
            int val = FingerTable.hash(i);

            for (Node node:nodeList) {

            }

            this.table.put(i,  );
        }
    }


    public List<Node> getActiveNodes(){
        List<Node> list = new ArrayList<>();

        Node temp = this;

        while (temp.getSuccessor() != this) {
            list.add(temp);
            temp = temp.getSuccessor();
        }
        list.add(temp);

        return list;
    }

    private Node getSuccessor() {
        return this.table.get(1);
    }


    // initialize fingertable of first node joining the network
    private void initNetwork() {
        for (int i = 1; i <= m; i++) {
            this.table.put(i, this);
        }
    }


    private int hash(int number) { return number & 0xff; }

    /************************************************************************************************

       GETTERS AND SETTERS

     ***********************************************************************************************/

    public int getId() { return this.nodeId; }

    public void setId(int id) { this.nodeId = id; }

    public FingerTable getFingerTable() { return this.table; }

    public void setFingerTable(FingerTable fingerTable) { this.table = fingerTable; }

    public List<Integer> getKeys() { return keys; }

    public void setKeys(List<Integer> keys) { this.keys = keys; }
}