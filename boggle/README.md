## Assignment Specification
http://coursera.cs.princeton.edu/algs4/assignments/boggle.html

## Checklist 
http://coursera.cs.princeton.edu/algs4/checklists/boggle.html

## Programming Assignment 4: Boggle

Write a program to play the word game Boggle<sup>®</sup>.

**The Boggle game.** Boggle is a word game designed by Allan Turoff and distributed by Hasbro. It involves a board made up of 16 cubic dice, where each die has a letter printed on each of its 6 sides. At the beginning of the game, the 16 dice are shaken and randomly distributed into a 4-by-4 tray, with only the top sides of the dice visible. The players compete to accumulate points by building _valid_ words from the dice, according to these rules:

*   A valid word must be composed by following a sequence of _adjacent dice_—two dice are adjacent if they are horizontal, vertical, or diagonal neighbors.
*   A valid word can use each die at most once.
*   A valid word must contain at least 3 letters.
*   A valid word must be in the dictionary (which typically does not contain proper nouns).

Here are some examples of valid and invalid words:

<table class="image" align="center">

<tbody>

<tr>

<td><img src="./readme_images/pins.png"></td>

<td>   </td>

<td><img src="./readme_images/pines.png"></td>

<td>   </td>

<td><img src="./readme_images/dates.png"></td>

</tr>

<tr>

<td class="caption" align="center">`PINS`  
(_valid_)</td>

<td>   </td>

<td class="caption" align="center">`PINES`  
(_valid_)</td>

<td>   </td>

<td class="caption" align="center">`DATES`  
(_invalid—dice not adjacent_)</td>

</tr>

<tr>

<td> </td>

</tr>

<tr>

<td><img src="./readme_images/pint.png"></td>

<td>   </td>

<td><img src="./readme_images/tepee.png"></td>

<td>   </td>

<td><img src="./readme_images/sid.png"></td>

</tr>

<tr>

<td class="caption" align="center">`PINT`  
(_invalid—path not sequential_)</td>

<td>   </td>

<td class="caption" align="center">`TEPEE`  
(_invalid—die used more than once_)</td>

<td>   </td>

<td class="caption" align="center">`SID`  
(_invalid—word not in dictionary_)</td>

</tr>

</tbody>

</table>

**Scoring.** Words are scored according to their length, using this table:

<table align="center" cellspacing="1" border="0">

<tbody>

<tr bgcolor="#444444">

<td align="center">  _<font color="white">word length</font>_  </td>

<td align="center">  _<font color="white">points</font>_  </td>

</tr>

<tr bgcolor="#DDDDDD">

<td align="center">0–2</td>

<td align="center">0</td>

</tr>

<tr bgcolor="#DDDDDD">

<td align="center">3–4</td>

<td align="center">1</td>

</tr>

<tr bgcolor="#DDDDDD">

<td align="center">5</td>

<td align="center">2</td>

</tr>

<tr bgcolor="#DDDDDD">

<td align="center">6</td>

<td align="center">3</td>

</tr>

<tr bgcolor="#DDDDDD">

<td align="center">7</td>

<td align="center">5</td>

</tr>

<tr bgcolor="#DDDDDD">

<td align="center">  8+</td>

<td align="center">11</td>

</tr>

</tbody>

</table>

**The _Qu_ special case.** In the English language, the letter `Q` is almost always followed by the letter `U`. Consequently, the side of one die is printed with the two-letter sequence `Qu` instead of `Q` (and this two-letter sequence must be used together when forming words). When scoring, `Qu` counts as two letters; for example, the word `QuEUE` scores as a 5-letter word even though it is formed by following a sequence of only 4 dice.

<p align="center">
<img src="./readme_images/Qu.png">
</p>

**Your task.** Your challenge is to write a Boggle solver that finds _all_ valid words in a given Boggle board, using a given dictionary. Implement an immutable data type `BoggleSolver` with the following API:

<pre>

> public class BoggleSolver
> {
>     <font color="gray">// Initializes the data structure using the given array of strings as the dictionary.
>     // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)</font>
>     public BoggleSolver(String[] dictionary)
> 
>     <font color="gray">// Returns the set of all valid words in the given Boggle board, as an Iterable.</font>
>     public Iterable<String> getAllValidWords(BoggleBoard board)
> 
>     <font color="gray">// Returns the score of the given word if it is in the dictionary, zero otherwise.
>     // (You can assume the word contains only the uppercase letters A through Z.)</font>
>     public int scoreOf(String word)
> }

</pre>

It is up to you how you search for and store the words contained in the board, as well as the dictionary used to check them. If you need help getting started, some hints are available on the [checklist](http://coursera.cs.princeton.edu/algs4/checklists/boggle.html).

**The board data type.** We provide an immutable data type [BoggleBoard.java](boggle-test-files/BoggleBoard.java) for representing Boggle boards. It includes constructors for creating Boggle boards from either the 16 Hasbro dice, the distribution of letters in the English language, a file, or a character array; methods for accessing the individual letters; and a method to print out the board for debugging. Here is the full API:

<pre>

> public class BoggleBoard
> {
>     <font color="gray">// Initializes a random 4-by-4 Boggle board.
>     // (by rolling the Hasbro dice)</font>
>     public BoggleBoard()
> 
>     <font color="gray">// Initializes a random m-by-n Boggle board.
>     // (using the frequency of letters in the English language)</font>
>     public BoggleBoard(int m, int n)
> 
>     <font color="gray">// Initializes a Boggle board from the specified filename.</font>
>     public BoggleBoard(String filename)
> 
>     <font color="gray">// Initializes a Boggle board from the 2d char array.
>     // (with 'Q' representing the two-letter sequence "Qu")</font>
>     public BoggleBoard(char[][] a)
> 
>     <font color="gray">// Returns the number of rows.</font>
>     public int rows()
> 
>     <font color="gray">// Returns the number of columns.</font>
>     public int cols()
> 
>     <font color="gray">// Returns the letter in row i and column j.
>     // (with 'Q' representing the two-letter sequence "Qu")</font>
>     public char getLetter(int i, int j)
> 
>     <font color="gray">// Returns a string representation of the board.</font>
>     public String toString()
> }

</pre>

**Testing.** We provide a number of [dictionary and board files](../testing/boggle-testing.zip) for testing.

*   _Dictionaries._ A dictionary consists of a sequence of words, separated by whitespace, in alphabetical order. You can assume that each word contains only the uppercase letters `A` through `Z`. For example, here are the two files [dictionary-algs4.txt](boggle-test-files/dictionary-algs4.txt) and [dictionary-yawl.txt](boggle-test-files/dictionary-yawl.txt):

    <pre>

    > % more dictionary-algs4.txt        % more dictionary-yawl.txt
    > ABACUS                             AA
    > ABANDON                            AAH
    > ABANDONED                          AAHED
    > ABBREVIATE                         AAHING
    > ...                                ...
    > QUEUE                              PNEUMONOULTRAMICROSCOPICSILICOVOLCANOCONIOSIS
    > ...                                ...
    > ZOOLOGY                            ZYZZYVAS

    </pre>

    The former is a list of 6,013 words that appear in _Algorithms 4/e_; the latter is a comprehensive list of 264,061 English words (known as _Yet Another Word List_) that is widely used in word-game competitions.
*   _Boggle boards._ A boggle board consists of two integers _M_ and _N_, followed by the _M_ × _N_ characters in the board, with the integers and characters separated by whitespace. You can assume the integers are nonnegative and that the characters are uppercase letters `A` through `Z` (with the two-letter sequence `Qu` represented as either `Q` or `Qu`). For example, here are the files [board4x4.txt](boggle-test-files/board4x4.txt) and [board-q.txt](boggle-test-files/board-q.txt):

    <pre>

    > % more board4x4.txt        % more board-q.txt
    > 4 4                        4 4
    > A  T  E  E                 S  N  R  T
    > A  P  Y  O                 O  I  E  L
    > T  I  N  U                 E  Qu T  T
    > E  D  S  E                 R  S  A  T

    </pre>

The following test client takes the filename of a dictionary and the filename of a Boggle board as command-line arguments and prints out all valid words for the given board using the given dictionary.

<pre>

> public static void main(String[] args) {
>     In in = new In(args[0]);
>     String[] dictionary = in.readAllStrings();
>     BoggleSolver solver = new BoggleSolver(dictionary);
>     BoggleBoard board = new BoggleBoard(args[1]);
>     int score = 0;
>     for (String word : solver.getAllValidWords(board)) {
>         StdOut.println(word);
>         score += solver.scoreOf(word);
>     }
>     StdOut.println("Score = " + score);
> }

</pre>

Here are two sample executions:

<pre>

> % java-algs4 BoggleSolver dictionary-algs4.txt board4x4.txt    % java-algs4 BoggleSolver dictionary-algs4.txt board-q.txt
> AID                                                            EQUATION
> DIE                                                            EQUATIONS
> END                                                            ...
> ENDS                                                           QUERIES
> ...                                                            QUESTION
> YOU                                                            QUESTIONS
> Score = 33                                                     ...
>                                                                TRIES
>                                                                Score = 84

</pre>

**Performance.** If you choose your data structures and algorithms judiciously, your program can preprocess the dictionary and find all valid words in a random Hasbro board (or even a random 10-by-10 board) in a fraction of a second. To stress test the performance of your implementation, create one `BoggleSolver` object (from a given dictionary); then, repeatedly generate and solve random Hasbro boards. How many random Hasbro boards can you solve per second? _For full credit, your program must be able to solve thousands of random Hasbro boards per second._ The goal on this assignment is raw speed—for example, it's fine to use 10× more memory if the program is 10× faster.

**Interactive game (optional, but fun and no extra work).** Once you have a working version of `BoggleSolver.java`, download, compile, and run [BoggleGame.java](boggle-test-files/BoggleGame.java) to play Boggle against a computer opponent. To enter a word, either type it in the text box or click the corresponding sequence of dice on the board. The computer opponent has various levels of difficulty, ranging from finding only words from popular nursery rhymes (easy) to words that appear in _Algorithms 4/e_ (medium) to finding every valid word (humbling).

<p align="center">
<img src="./readme_images/boggle-gui.png">
</p>

**Challenge for the bored.** Here are some challenges:

*   Find a maximum scoring 4-by-4 Hasbro board. Here is the [best known board](boggle-test-files/board-points4540.txt) (4540 points), which was discovered by Robert McAnany in connection with this Coursera course.
*   Find a maximum scoring 4-by-4 Hasbro board using the [Zinga](boggle-test-files/dictionary-zingarelli2005.txt) list of 584,983 Italian words.
*   Find a minimum scoring 4-by-4 Hasbro board.
*   Find a maximum scoring 5-by-5 Deluxe Boggle board.
*   Find a maximum scoring _N_-by-_N_ board (not necessarily using the Hasbro dice) for different values of _N_.
*   Find a board with the most words (or the most words that are 8 letters or longer).
*   Find a 4-by-4 Hasbro board that scores _exactly_ 2,500, 3,000, 3,500, or 4,000 points.
*   Design an algorithm to determine whether a given 4-by-4 board can be generated by rolling the 16 Hasbro dice.
*   How many words in the dictionary appear in no 4-by-4 Hasbro boards?
*   Add new features to [BoggleGame.java](boggle-test-files/BoggleGame.java).
*   Extend your program to handle arbitrary Unicode letters and dictionaries. You may need to consider alternate algorithms and data structures.
*   Extend your program to handle arbitrary strings on the faces of the dice, generalizing your hack for dealing with the two-letter sequence `Qu`.

Unless otherwise stated, use the [dictionary-yawl.txt](boggle-test-files/dictionary-yawl.txt) dictionary. If you discover interesting boards, you are encouraged to share and describe them in the Discussion Forums.

**Deliverables.** Submit `BoggleSolver.java` and any other supporting files (excluding `BoggleBoard.java` and `algs4.jar`). You may not call any library functions other those in `java.lang`, `java.util`, and `algs4.jar`.

*This assignment was developed by Matthew Drabick and Kevin Wayne, inspired by Todd Feldman and Julie Zelenski's classic [Nifty Boggle](http://www-cs-faculty.stanford.edu/~zelenski/boggle/) assignment from Stanford.  
Copyright © 2013.*