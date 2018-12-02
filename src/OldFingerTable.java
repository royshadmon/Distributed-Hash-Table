import java.util.HashMap;

class OldFingerTable {
    private int hostNodeId;

    static final int MAX_ENTRIES = 3;

    private static final int MOD = (int) (Math.pow(2, MAX_ENTRIES));
    private HashMap<Integer, OldNode> fingerTable = new HashMap<>();

    OldFingerTable(Integer hostNodeId) {
        this.hostNodeId = hostNodeId;
    }

    public OldNode get(Integer entryNumber) {
        if (entryNumber == 0) throw new RuntimeException("Entries start from 1");
        if (entryNumber > MAX_ENTRIES) throw new RuntimeException("Exceeded number of entries. " +
                "Only " + MAX_ENTRIES + " entries are allowed.");

        Integer key = hash(entryNumber);
        return this.fingerTable.get(key);
    }

    public void put(Integer entryNumber, OldNode oldNode) {
        if (entryNumber == 0) throw new RuntimeException("Entries start from 1");
        if (entryNumber > MAX_ENTRIES) throw new RuntimeException("Exceeded number of entries. " +
                "Only " + MAX_ENTRIES + " entries are allowed.");

        Integer key = hash(entryNumber);
        this.fingerTable.put(key, oldNode);
    }

    public void prettyPrint() {
        System.out.println("----------------------");
        System.out.println("Finger Table - OldNode " + this.hostNodeId);
        System.out.println("----------------------");
        this.fingerTable.forEach((key, value) -> {
            System.out.print(" Key: " + key);
            System.out.println("      Id: " + value.getId());
        });
        System.out.println("----------------------");
    }

    private Integer hash(Integer entryNumber) {
        int index = this.hostNodeId + (int) ((Math.pow(2, entryNumber - 1)));
        index = index % MOD;
        return index;

    }

    static Integer hash(int hostNodeId, int entryNumber) {
        int index = hostNodeId + (int) ((Math.pow(2, entryNumber - 1)));
        index = index % MOD;
        return index;
    }

    public static void main(String[] args) {
        OldFingerTable f = new OldFingerTable(0);

        System.out.println(f.hash(3));
        int ent1 = 3;
        int ent2 = 2;
        System.out.println(ent1 % Math.pow(2, MAX_ENTRIES));
        System.out.println(ent2 % Math.pow(2, MAX_ENTRIES));

        f.put(ent1, new OldNode(10));
        f.put(ent2, new OldNode(1));

        System.out.println(f.get(ent1).getId());
        System.out.println(f.get(ent2).getId());

    }
}