import java.util.HashMap;

class FingerTable {
    private int hostNodeId;

    public static final int MAX_ENTRIES = 3;
    private HashMap<Integer, Node> fingerTable = new HashMap<>();

    FingerTable(Integer hostNodeId) {
        this.hostNodeId = hostNodeId;
    }

    public Node get(Integer entryNumber) {
        if (entryNumber == 0) throw new RuntimeException("Entries start from 1");
        if (entryNumber > MAX_ENTRIES) throw new RuntimeException("Exceeded number of entries. " +
                "Only " + MAX_ENTRIES + " entries are allowed.");
        Integer key = hash(entryNumber);
        return this.fingerTable.get(key);
    }

    public void put(Integer entryNumber, Node node) {
        if (entryNumber == 0) throw new RuntimeException("Entries start from 1");
        if (entryNumber > MAX_ENTRIES) throw new RuntimeException("Exceeded number of entries. " +
                "Only " + MAX_ENTRIES + " entries are allowed.");
        Integer key = hash(entryNumber);
        this.fingerTable.put(key, node);
    }

    public void prettyPrint() {
        System.out.println("----------------------");
        System.out.println("Finger Table - Node " + this.hostNodeId);
        System.out.println("----------------------");
        this.fingerTable.forEach((key, value) -> {
            System.out.print(" Key: " + key);
            System.out.println("      Id: " + value.getId());
        });
        System.out.println("----------------------");
    }

    public static Integer hash(Integer entryNumber) {
        //return this.hostNodeId + ((Math.pow(2,entryNumber)) % (Math.pow(2, MAX_ENTRIES)));
        return this.hostNodeId + (int) ((Math.pow(2, entryNumber - 1)) % (Math.pow(2, MAX_ENTRIES)));
    }

    public static void main(String[] args) {
        FingerTable f = new FingerTable(0);

        //System.out.println(f.hash(3));
        int ent1 = 3;
        int ent2 = 2;
        System.out.println(ent1 % Math.pow(2, MAX_ENTRIES));
        System.out.println(ent2 % Math.pow(2, MAX_ENTRIES));

        f.put(ent1, new Node(10));
        f.put(ent2, new Node(1));


        System.out.println(f.get(ent1).getId());
        System.out.println(f.get(ent2).getId());
    }
}