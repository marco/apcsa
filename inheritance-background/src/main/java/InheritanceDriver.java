/**
 * Inheritance Driver class.
 * @author jddevaughnbrown
 *
 */
public class InheritanceDriver {

    /**
     * The main driver method.
     * @param args - arguments to the main method
     */
    public static void main(String[] args) {
        OurArea area = new OurArea(3, 5, 8);

        // Use our area, and show the window.
        Window window = new Window("Inheritance Lecture");

        window.add(area);
        window.addKeyListener(area);
        window.setVisible(true);
    }

}
