package compressor;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

import structures.BinaryTreeNode;
import structures.LinkedBinaryNode;

/**
 * A decompressor that can decompress a file created by `Compressor`.
 *
 * @author skunkmb
 */
public class Decompressor {
    /**
     * Decompresses a file after asking for user input.
     *
     * @param args Arguments passed to this program. These are ignored.
     * @throws IOException An `IOException` if a file cannot be found.
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What file do you want to decompress?");
        String toDecompress = scanner.nextLine();
        System.out.println("Where do you want to save the decompressed file?");
        String location = scanner.nextLine();
        scanner.close();
        decompressAndWrite(toDecompress, location);
        System.out.println("Done.");
    }

    /**
     * Decompresses a file at a location and writes it to another file.
     *
     * @param inLocation The location of the compressed file.
     * @param outLocation The output location.
     * @throws IOException An `IOException` if a file cannot be found.
     */
    private static void decompressAndWrite(String inLocation, String outLocation) throws IOException {
        byte[] input = FileUtils.readFileToByteArray(new File(inLocation));
        String decompressedString = decompressBytes(input);
        FileUtils.writeStringToFile(new File(outLocation), decompressedString, "UTF-8");
    }

    /**
     * Completely decompresses a `byte` array into a `String`.
     *
     * @param input The `byte` array to compress.
     * @return The `byte` array representing the `String`.
     * @throws UnsupportedEncodingException If a tree cannot be decrypted
     * with UTF-8.
     */
    public static String decompressBytes(byte[] input) throws UnsupportedEncodingException {
        BinaryTreeNode<CharacterData> rootNode = decodeTree(getTreeString(new String(input, "UTF-8")));
        String decompressedBinary = getDecompressedBinary(input);
        return getDecompressedString(rootNode, decompressedBinary);
    }

    /**
     * Returns a decompressed string for a binary string and a root node
     * of a Huffman tree.
     *
     * @param rootNode The root node of the Huffman tree.
     * @param binaryString The binary string to ues.
     * @return The decompressed string.
     */
    private static String getDecompressedString(
        BinaryTreeNode<CharacterData> rootNode,
        String binaryString
    ) {
        String currentString = "";
        BinaryTreeNode<CharacterData> currentNode = rootNode;

        for (int i = 0; i < binaryString.length(); i++) {
            if (binaryString.charAt(i) == '1') {
                currentNode = currentNode.getRightChild();
            } else {
                currentNode = currentNode.getLeftChild();
            }

            if (currentNode.getData().getIsLeaf()) {
                currentString += currentNode.getData().getCharacter();
                currentNode = rootNode;
            }
        }

        return currentString;
    }

    /**
     * Gets the parts of a compressed string representing the Huffman tree.
     *
     * @param compressed The compressed string.
     * @return The tree representation.
     */
    private static String getTreeString(String compressed) {
        int endIndex = compressed.indexOf("____");
        return compressed.substring(0, endIndex);
    }

    /**
     * Returns a binary string for a byte array.
     *
     * @param compressed The compressed byte array.
     * @return The binary string
     * @throws UnsupportedEncodingException A `UnsupportedEncodingException`
     * if a byte cannot be converted into a binary string.
     */
    private static String getDecompressedBinary(byte[] compressed) throws UnsupportedEncodingException {
        int startIndex = getStartIndexForBinarySection(compressed);
        String binaryString = "";

        for (int i = startIndex; i < compressed.length; i++) {
            binaryString += getBinaryStringForByte(compressed[i]);
        }

        return binaryString;
    }

    /**
     * Returns the starting index for the data portion of a compressed
     * byte array.
     *
     * @param compressed The compressed byte array.
     * @return The starting index.
     */
    private static int getStartIndexForBinarySection(byte[] compressed) {
        for (int i = 0; i < compressed.length - 4; i++) {
            if (
                compressed[i] == '_' && compressed[i + 1] == '_'
                    && compressed[i + 2] == '_' && compressed[i + 3] == '_'
            ) {
                return i + 4;
            }
        }

        return 0;
    }

    /**
     * Returns a binary string for a single byte.
     *
     * @param toConvert The byte to convert.
     * @return The binary string representation.
     */
    private static String getBinaryStringForByte(byte toConvert) {
        if (toConvert < 0) {
            return "1" + getBinaryStringForByte((byte) -toConvert).substring(1);
        }

        return String.format("%8s", Integer.toBinaryString(toConvert)).replace(' ', '0');
    }

    /**
     * Decodes an input string representing a Huffman tree in pre-order
     * notation.
     *
     * @param input The input string to convert.
     * @return The root node for the tree.
     */
    private static BinaryTreeNode<CharacterData> decodeTree(String input) {
        if (input == null || input.length() == 0) {
            throw new NullPointerException("An empty string cannot be decoded.");
        }

        return decodeTree(new StringTokenizer(input, "__"));
    }

    /**
     * Recursively decodes an input string through a `StringTokenizer`
     * representing a Huffman tree in pre-order notation.
     *
     * @param tokenizer The `StringTokenizer` separating nodes in the string.
     * @return The root node for the tree.
     */
    private static BinaryTreeNode<CharacterData> decodeTree(StringTokenizer tokenizer) {
        if (!tokenizer.hasMoreTokens()) {
            return null;
        }

        String nextToken = tokenizer.nextToken();

        if (1 < nextToken.length() && nextToken.charAt(1) == ':') {
            return new LinkedBinaryNode<CharacterData>(
                new CharacterData(nextToken.charAt(0), Integer.parseInt(nextToken.substring(2, nextToken.length()))),
                null,
                null
            );
        }

        BinaryTreeNode<CharacterData> leftNode = decodeTree(tokenizer);
        BinaryTreeNode<CharacterData> rightNode = decodeTree(tokenizer);
        return new LinkedBinaryNode<CharacterData>(
            new CharacterData(Integer.parseInt(nextToken)),
            leftNode,
            rightNode
        );
    }
}
