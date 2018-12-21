package compressor;

/**
 * A set of data for nodes in a a Huffman tree.
 *
 * @author skunkmb
 */
class CharacterData {
    /**
     * Whether or not this is a leaf node. If it is a leaf node,
     * then the `character` property is used, otherwise it is ignored.
     */
    private boolean isLeaf;

    /**
     * The character represented by this node. This is only used if
     * `isLeaf` is true.
     */
    private char character;

    /**
     * The number of times this character, or the children to the node
     * with this data, appears.
     */
    private int frequency;

    /**
     * Constructs a `CharacterData` as a non-leaf node.
     *
     * @param frequency How many times this data's node's children appear.
     */
    CharacterData(int frequency) {
        this.isLeaf = false;
        this.frequency = frequency;
    }

    /**
     * Constructs a `CharacterData` as a leaf node.
     *
     * @param character The character represented.
     * @param frequency How many times this character appears.
     */
    CharacterData(char character, int frequency) {
        this.isLeaf = true;
        this.character = character;
        this.frequency = frequency;
    }

    /**
     * Returns this character.
     *
     * @return This character.
     */
    char getCharacter() {
        return character;
    }

    /**
     * Returns this character's frequency.
     *
     * @return This character's frequency.
     */
    int getFrequency() {
        return frequency;
    }

    /**
     * Returns whether this is a leaf node.
     *
     * @return Whether this is a leaf node.
     */
    boolean getIsLeaf() {
        return isLeaf;
    }

    @Override
    public String toString() {
        if (!isLeaf) {
            return String.valueOf(frequency);
        }

        return character + ":" + String.valueOf(frequency);
    }
}
