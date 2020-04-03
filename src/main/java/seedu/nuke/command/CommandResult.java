package seedu.nuke.command;

import seedu.nuke.directory.Directory;
import seedu.nuke.directory.DirectoryLevel;

import java.util.ArrayList;

/**
 * construct the feedback to user String.
 */
public class CommandResult {
    private final String feedbackToUser;
    private final DirectoryLevel directoryLevel;
    private final ArrayList<Directory> shownList;
    private final ArrayList<String> helpGuide;

    /**
     * Constructor of the CommandResult class.
     *
     * @param feedbackToUser The feedback to be printed to the user.
     * @param directoryLevel The directoryLevel the user is at currently.
     * @param listToShow the list of Directory to be printed to the user.
     * @param helpGuide the helping guide to be printed to the user.
     */
    public CommandResult(String feedbackToUser, DirectoryLevel directoryLevel,
                          ArrayList<Directory> listToShow, ArrayList<String> helpGuide) {
        this.feedbackToUser = feedbackToUser;
        this.directoryLevel = directoryLevel;
        this.shownList = listToShow;
        this.helpGuide = helpGuide;
    }

    /**
     * Constructs a command result that contains both feedback message and list to show.
     *
     * @param feedbackToUser
     *  The feedback message to be shown to the user
     * @param directoryLevel
     *  The data type of the list
     * @param listToShow
     *  The list to be shown to the user
     */
    public CommandResult(String feedbackToUser, DirectoryLevel directoryLevel, ArrayList<Directory> listToShow) {
        this(feedbackToUser, directoryLevel, listToShow, null);
    }

    public CommandResult(String feedbackToUser, ArrayList<String> helpGuide) {
        this(feedbackToUser, DirectoryLevel.NONE, null, helpGuide);
    }

    /**
     * Constructs a command result that contains the feedback message only.
     * @param feedbackToUser
     *  The feedback message to be shown to the user
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, DirectoryLevel.NONE, null, null);
    }

    /**
     * Returns the feedback message to be shown to the user.
     *
     * @return
     *  The feedback message to be shown to the user
     */
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    /**
     * Returns the level of the list to be shown to the user.
     *
     * @return
     *  The level of the list
     */
    public DirectoryLevel getDirectoryLevel() {
        return directoryLevel;
    }

    /**
     * Returns the list to be shown to the user.
     *
     * @return
     *  The list to be shown to the user
     */
    public ArrayList<Directory> getShownList() {
        return shownList;
    }

    public ArrayList<String> getHelpGuide() {
        return helpGuide;
    }
}
