package seedu.nuke.task;

import seedu.nuke.format.DateTime;

import java.util.ArrayList;

import static seedu.nuke.common.Constants.NO_ICON;
import static seedu.nuke.common.Constants.YES_ICON;

public class Task {
    protected String description;
    protected boolean isDone;
    protected int priority;
    protected DateTime deadline;
    protected ArrayList<String> files;
    protected String moduleCode;


    /**
     * constructor for the simplest task.
     *
     * @param description the description of the task
     * @param moduleCode the module code of the module which the task belongs to
     */
    public Task(String description, String moduleCode) {
        this.description = description;
        this.files = new ArrayList<>();
        this.deadline = null;
        this.priority = -1;
        this.isDone = false;
        this.moduleCode = moduleCode;
    }

    /**
     * constructor for the simplest task.
     *
     * @param description the description of the task
     * @param dateTime the deadline of the task
     * @param priority the priority of the task
     */
    public Task(String description, DateTime dateTime, int priority) {
        this.description = description;
        this.files = new ArrayList<>();
        this.deadline = dateTime;
        this.priority = priority;
        this.isDone = false;
    }

    /**
     * moduleCode getter method.
     *
     * @return the module code of the module which the task belongs to
     */
    public String getModuleCode() {
        return moduleCode;
    }

    /**
     * description getter method.
     *
     * @return the description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * deadline getter method.
     *
     * @return the deadline of the task
     */
    public DateTime getDeadline() {
        return deadline;
    }

    /**
     * deadline setter method.
     *
     * @param deadline the deadline of the task
     */
    public void setDeadline(DateTime deadline) {
        this.deadline = deadline;
    }

    /**
     * priority getter method.
     *
     * @return the priority of the task
     */
    public int getPriority() {
        return priority;
    }

    /**
     * return an icon representing the status of the task.
     *
     * @return a String of the icon.
     */
    public String getStatusIcon() {
        return (isDone ? YES_ICON : NO_ICON);
    }

    /**
     * files getter method.
     *
     * @return an ArrayList of String representing the files which the task is associated with
     */
    public ArrayList<String> getFiles() {
        return files;
    }

    /**
     * override the toString method for printing the task as a string.
     *
     * @return a string of the tasks information
     */
    public String toString() {
        StringBuilder printOut = new StringBuilder(getStatusIcon() + " " + getDescription() + " by " + getDeadline());
        for (String file : files) {
            printOut.append(" ").append(file);
        }
        return printOut.toString();
    }

    public void addFile(String filePath) {
        files.add(filePath);
    }

}
