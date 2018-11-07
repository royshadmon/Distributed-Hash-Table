public interface ChordNode {

    final static int FINGER_TABLE_LENGTH = 5;
    final static int MAX_NODES = 256;

    int find(int keyId);

    void join(SimpleNode node);
    void insert(int keyId);
    void remove(int keyId);
    void prettyPrint();
}