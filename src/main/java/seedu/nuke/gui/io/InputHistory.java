package seedu.nuke.gui.io;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

/**
 * Stores the history of the user's input.
 */
public class InputHistory {
    private static ArrayList<String> history = new ArrayList<>();
    private static int currentIndex = -1;
    private static String NONE = "";

    /**
     * Adds the recently entered input into the input history.
     *
     * @param input
     *  The recently entered input
     */
    public static void add(String input) {
        history.add(input);
        currentIndex = getLastIndex(); // Set to last index;
    }

    /**
     * Returns the previous input in the input history.
     *
     * @return
     *  The previous input
     */
    public static String previous() {
        if (currentIndex < 0) {
            return NONE;
        }
        return (currentIndex == 0) ? history.get(currentIndex) : history.get(currentIndex--);
    }

    /**
     * Returns the next input in the input history.
     *
     * @return
     *  The next input
     */
    public static String next() {
        if (currentIndex < 0) {
            return NONE;
        }
        return (currentIndex == getLastIndex()) ? NONE : history.get(++currentIndex);
    }

    private static int getLastIndex() {
        return history.size() - 1;
    }

}
