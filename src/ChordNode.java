public interface ChordNode {

    int find(int keyId);

    void join(ChordNode node);
    void insert(int keyId);
    void remove(int keyId);
    void prettyPrint();
}