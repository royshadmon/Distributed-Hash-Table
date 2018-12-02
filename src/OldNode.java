import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OldNode {

    private int nodeId;
    private OldFingerTable table;
    private static int m = OldFingerTable.MAX_ENTRIES;

    private List<Integer> keys;

    OldNode(int id) {
        this.nodeId = hash(id);
        this.table = new OldFingerTable(this.nodeId);
        this.keys = new ArrayList<>();
    }

    public void join(OldNode oldNode) {
        if (oldNode == null) {
            System.out.println("Initialized Network");
            System.out.println("OldNode " + this.getId() + " has joined");
            System.out.println("----------------------");
            this.initNetwork();
        }
        else {
            this.initializeNode(oldNode);
            System.out.println("OldNode " + this.getId() + " has joined");
            System.out.println("Migrating keys to oldNode " + this.getId());
            System.out.println();

            this.migrateKeys();
        }
    }

    private void migrateKeys() {
        // 1. This function should find the successor of the node, from the finger table,
        // 2. Update the successor's key set to remove keys it should no longer manage.
        // 3. Add those keys to this node's key set

        // Should work even when there are no keys in the system

        List<Integer> newKeys = this.getSuccessor().updateKeys(this.getId());
        System.out.println("Adding them to new node " + this.getId());
        this.getKeys().addAll(newKeys);
        System.out.println("----------------------");
        System.out.println();
    }

    private List<Integer> updateKeys(int ID) {
        System.out.println("Updating keys of OldNode " + this.getId());
        System.out.println();
        List<Integer> removedKeys = new ArrayList<>();

        for (int i=0; i < this.getKeys().size(); i++) {
            /* if the key is less than or equal to the ID, but not equal to the current node's ID,
             it should be removed from this node's keyset */
            int key = this.getKeys().get(i);
            if (key <= ID && key !=this.getId()) {
                System.out.println("Removing key with ID: " + key);
                removedKeys.add(key);
                this.getKeys().remove(i);
                i--;
            }

        }
        return removedKeys;
    }

    private void initializeNode(OldNode oldNode) {
        List<OldNode> oldNodeList = oldNode.getActiveNodes();
        oldNodeList.add(this);
        oldNodeList.sort(new NodeComparator());

        this.initFingerTable(oldNodeList);
        this.updateOthers(oldNodeList);
    }

    private void updateOthers(List<OldNode> list) { list.forEach(oldNode -> oldNode.updateFingerTable(list)); }

    private void updateFingerTable(List<OldNode> list) {
        this.initFingerTable(list);
    }

    private void initFingerTable(List<OldNode> list) {
        for (int i =1; i <= m; i++) {
            int val = OldFingerTable.hash(this.nodeId, i);
            OldNode successor = this.findSuccessor(val, list);
            this.table.put(i, successor);
        }
//        this.table.prettyPrint(); // Need this in final submission but commented out for debugging
    }

    private OldNode findSuccessor(int val, List<OldNode> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getId() == val) return list.get(i); // May not need this condition
            if (list.get(i).getId() < val && val <= list.get(i+1).getId()) return list.get(i+1);
        }
        return list.get(0);
    }

    private List<OldNode> getActiveNodes(){
        List<OldNode> list = new ArrayList<>();

        OldNode temp = this;
        while (temp.getSuccessor() != this) {
            list.add(temp);
            temp = temp.getSuccessor();
        }
        list.add(temp);

        list.sort(new NodeComparator()); //

        return list;
    }

    // initialize Finger Table of first node joining the network
    private void initNetwork() {
        for (int i = 1; i <= m; i++) {
            this.table.put(i, this);
        }
    }

    /************************************************************************************************

       GETTERS AND SETTERS

     ***********************************************************************************************/

    public int getId() { return this.nodeId; }

    public void setId(int id) { this.nodeId = id; }

    public OldFingerTable getFingerTable() { return this.table; }

    public void setFingerTable(OldFingerTable oldFingerTable) { this.table = oldFingerTable; }

    public List<Integer> getKeys() { return keys; }

    public void setKeys(List<Integer> keys) { this.keys = keys; }


    /************************************************************************************************

     PRIVATE METHODS

     ***********************************************************************************************/

    private OldNode getSuccessor() { return this.table.get(1); }

    private int hash(int number) { return number & 0xff; }

    class NodeComparator implements Comparator<OldNode> {
        public int compare(OldNode a, OldNode b) {
            return a.getId() - b.getId();
        }
    }

    /************************************************************************************************

     TEST METHODS

     ***********************************************************************************************/

    public OldNode findPredecessor(int id) {
        return null;
    }

    public OldNode closestPrecedingFinger(int id) {

        // This should get the node in the OldFingerTable with the highest ID less than id
        OldNode max = this.getFingerTable().get(0);
        for (int i = 1; i <= m; i++) {
            OldNode oldNode = this.getFingerTable().get(i);
            if (oldNode.getId() < id && oldNode.getId() > max.getId()) {
                max = oldNode;
            }
        }

        return max;
    }
}