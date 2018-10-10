import javax.swing.JFrame;
/**
 * A top level window within which a scene is hosted, and with which the user interacts.
 * @author jddevaughnbrown
 *
 */
@SuppressWarnings("serial")
public class Window extends JFrame {

    /**
     * The width and height of the window (in pixels).
     */
    public static final int WIDTH = 640, HEIGHT = 640;

    /**
     *  The constructor for the Window class.
     * @param title - the text that shows up in the title bar of the window
     */
    public Window(String title) {
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
    }

}
