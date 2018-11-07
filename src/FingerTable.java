import java.util.HashMap;

class FingerTable {
    private int hostNodeId;
    private HashMap<Integer, ChordNode> fingerTable = new HashMap<>();

    FingerTable(Node node) {
        this.hostNodeId = node.getId();
    }

    FingerTable(int hostNodeId) {
        this.hostNodeId = hostNodeId;
    }

    public int getHostNodeId() {
        return hostNodeId;
    }

    public ChordNode get(int index) {
        // Essentially returns successor
        return this.fingerTable.get(index);
    }

    public void set(int index, ChordNode successor) {
        fingerTable.put(index, successor);
    }

    protected void prettyPrint() {

    }
}