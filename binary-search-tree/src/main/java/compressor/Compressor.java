package compressor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import structures.BinaryTreeNode;
import structures.LinkedBinaryNode;
import structures.LinkedBinaryTreeUtility;

/**
 * A compressor that compresses files using Huffman coding.
 *
 * @author skunkmb
 */
public class Compressor {
    /**
     * Runs the compression program after asking for user input.
     *
     * @param args Arguments passed to this program. These are ignored.
     * @throws IOException An `IOException` if a file cannot be found.
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What file do you want to compress?");
        String toCompress = scanner.nextLine();
        System.out.println("Where do you want to save the compressed file?");
        String location = scanner.nextLine();
        scanner.close();
        compressStringAndWrite(toCompress, location);
        System.out.println("Done.");
    }

    /**
     * Compresses a string and writes the compressed file to a location.
     *
     * @param toCompress The `String` to compress.
     * @param location The location to write the file to.
     * @throws IOException An `IOException` if the file cannot be written to.
     */
    private static void compressStringAndWrite(String toCompress, String location) throws IOException {
        String input = FileUtils.readFileToString(new File(toCompress), "UTF-8");
        byte[] bytes = compressString(input);
        FileUtils.writeByteArrayToFile(new File(location), bytes);
    }

    /**
     * Completely compresses a `String` into a `byte` array.
     *
     * @param input The `String` to compress.
     * @return The `byte` array representing the `String`.
     */
    public static byte[] compressString(String input) {
        BinaryTreeNode<CharacterData> rootNode = getCompressedRootNode(input);
        String binaryString = getBinaryString(input, rootNode);
        String encodedTree = encodeTree(rootNode);
        return getFinalBytes(encodedTree + "____", getBytesForBinaryString(binaryString));
    }

    /**
     * Returns the overall array of bytes to be written to a compressed
     * file, based on an encoded tree and the compressed bytes for the data.
     *
     * @param encodedTree A `String` representing a tree through pre-order
     * notation.
     * @param compressedBytes A `byte` array of compressed bytes to write
     * to use for data storage.
     * @return The final `byte` array.
     */
    private static byte[] getFinalBytes(String encodedTree, byte[] compressedBytes) {
        byte[] encodedBytes = encodedTree.getBytes();
        byte[] finalBytes = ArrayUtils.addAll(encodedBytes, compressedBytes);
        return finalBytes;
    }

    /**
     * Returns a `byte` array based on a `String` of binary digits.
     *
     * @param binaryString The string of binary digits, e.g., "10001101".
     * @return The byte array for the binary string.
     */
    private static byte[] getBytesForBinaryString(String binaryString) {
        String paddedBinaryString = binaryString;

        while (paddedBinaryString.length() % 8 != 0) {
            paddedBinaryString += "0";
        }

        byte[] currentBytes = new byte[paddedBinaryString.length() / 8];

        for (int i = 0; i < paddedBinaryString.length() / 8; i++) {
            String substring;

            if (paddedBinaryString.charAt(i * 8) == '1') {
                substring = '-' + paddedBinaryString.substring(i * 8 + 1, i * 8 + 8);
            } else {
                substring = '+' + paddedBinaryString.substring(i * 8 + 1, i * 8 + 8);
            }

            currentBytes[i] = Byte.parseByte(substring, 2);
        }

        return currentBytes;
    }

    /**
     * Returns a `String` representing a binary tree using pre-order
     * notation, separated by underscores.
     *
     * @param rootNode The root node for the tree.
     * @return The `String` representation.
     */
    private static String encodeTree(BinaryTreeNode<CharacterData> rootNode) {
        String output = "";
        Iterator<CharacterData> iterator = new LinkedBinaryTreeUtility().getPreOrderIterator(rootNode);

        output += iterator.next();

        while (iterator.hasNext()) {
            output += "__" + iterator.next();
        }

        return output;
    }

    /**
     * Returns a binary string representing a string using a Huffman
     * tree (a binary string that can be used with the tree to create the
     * original string).
     *
     * @param string The string to compress.
     * @param rootNode The root node of the tree.
     * @return The binary string.
     */
    private static String getBinaryString(String string, BinaryTreeNode<CharacterData> rootNode) {
        String currentString = "";

        for (char character : string.toCharArray()) {
            currentString += getBinaryStringForChar(character, rootNode);
        }

        return currentString;
    }

    /**
     * Returns a binary string representing a character using a Huffman
     * tree (a binary string that can be used with the tree to create the
     * original character).
     *
     * @param character The character to compress.
     * @param rootNode The root node of the tree.
     * @return The binary string.
     */
    private static String getBinaryStringForChar(char character, BinaryTreeNode<CharacterData> rootNode) {
        if (rootNode.hasLeftChild()) {
            if (rootNode.getLeftChild().getData().getCharacter() == character) {
                return "0";
            }

            String leftBinaryString = getBinaryStringForChar(character, rootNode.getLeftChild());

            if (leftBinaryString != null) {
                return "0" + leftBinaryString;
            }
        }

        if (rootNode.hasRightChild()) {
            if (rootNode.getRightChild().getData().getCharacter() == character) {
                return "1";
            }

            String rightBinaryString = getBinaryStringForChar(character, rootNode.getRightChild());

            if (rightBinaryString != null) {
                return "1" + rightBinaryString;
            }
        }

        return null;
    }

    /**
     * Returns the root node for a Huffman tree based on a `String` to
     * compress.
     *
     * @param input The `String` to compress.
     * @return The root node for the tree.
     */
    private static BinaryTreeNode<CharacterData> getCompressedRootNode(String input) {
        Map<Character, Integer> characterFrequencies = new HashMap<Character, Integer>();

        for (char character : input.toCharArray()) {
            if (characterFrequencies.containsKey(character)) {
                characterFrequencies.put(
                    character,
                    characterFrequencies.get(character) + 1
                );
            } else {
                characterFrequencies.put(character, 1);
            }
        }

        CharacterData[] orderedChars = getOrderedChars(characterFrequencies);
        List<BinaryTreeNode<CharacterData>> orderedNodes = getOrderedNodes(orderedChars);
        while (1 < orderedNodes.size()) {
            BinaryTreeNode<CharacterData> lastNode = orderedNodes.remove(orderedNodes.size() - 1);
            BinaryTreeNode<CharacterData> secondLastNode = orderedNodes.remove(orderedNodes.size() - 1);
            BinaryTreeNode<CharacterData> newNode = new LinkedBinaryNode<CharacterData>(
                new CharacterData(
                    lastNode.getData().getFrequency() + secondLastNode.getData().getFrequency()
                ),
                lastNode,
                secondLastNode
            );

            insertIntoOrderedNodes(orderedNodes, newNode);
        }

        return orderedNodes.get(0);
    }

    /**
     * Returns an array of `CharacterData`s, in order based on character
     * frequencies.
     *
     * @param characterFrequencies A map of characters and the amount of
     * times they appear in the string.
     * @return The array of `CharacterData`s.
     */
    private static CharacterData[] getOrderedChars(Map<Character, Integer> characterFrequencies) {
        List<CharacterData> currentOrderedChars = new ArrayList<CharacterData>();
        Map<Character, Integer> remainingFrequencies = new HashMap<Character, Integer>(characterFrequencies);

        while (!remainingFrequencies.isEmpty()) {
            CharacterData mostCommonChar = getMostCommonChar(remainingFrequencies);
            currentOrderedChars.add(mostCommonChar);
            remainingFrequencies.remove(mostCommonChar.getCharacter());
        }

        return currentOrderedChars.toArray(new CharacterData[0]);
    }

    /**
     * Returns a list of `BinaryTreeNode`s, of `CharacterData`, in order
     * based on character frequencies.
     *
     * @param orderedChars An array of `CharacterData`s, in order by
     * frequency.
     * @return The array of `CharacterData`s.
     */
    private static List<BinaryTreeNode<CharacterData>> getOrderedNodes(CharacterData[] orderedChars) {
        List<BinaryTreeNode<CharacterData>> currentOrderedNodes = new ArrayList<BinaryTreeNode<CharacterData>>();

        for (int i = 0; i < orderedChars.length; i++) {
            currentOrderedNodes.add(new LinkedBinaryNode<CharacterData>(orderedChars[i], null, null));
        }

        return currentOrderedNodes;
    }

    /**
     * Inserts a `BinaryTreeNode` of `CharacterData` into a list of
     * `CharacterData`s based on frequency.
     *
     * @param orderedNodes The existing list of `BinaryTreeNode`s.
     * @param newNode The new `BinaryTreeNode` to insert.
     */
    private static void insertIntoOrderedNodes(
        List<BinaryTreeNode<CharacterData>> orderedNodes,
        BinaryTreeNode<CharacterData> newNode
    ) {
        for (int i = 0; i < orderedNodes.size(); i++) {
            if (orderedNodes.get(i).getData().getFrequency() <= newNode.getData().getFrequency()) {
                orderedNodes.add(i + 1, newNode);
                return;
            }
        }

        orderedNodes.add(newNode);
    }

    /**
     * Gets the most common character in a map of character frequencies.
     *
     * @param characterFrequencies A map of characters and the amount of
     * times they appear in a string.
     * @return The most common character.
     */
    private static CharacterData getMostCommonChar(Map<Character, Integer> characterFrequencies) {
        Character currentMaximumKey = null;

        for (Character key : characterFrequencies.keySet()) {
            if (currentMaximumKey == null || characterFrequencies.get(currentMaximumKey) < characterFrequencies.get(key)) {
                currentMaximumKey = key;
            }
        }

        return new CharacterData(
            currentMaximumKey,
            characterFrequencies.get(currentMaximumKey)
        );
    }
}
