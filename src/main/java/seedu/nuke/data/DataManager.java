package seedu.nuke.data;

import seedu.nuke.directory.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * the manager that manages all tasks of all modules deals with operations in task-level.
 */
public class DataManager {

    private ArrayList<Task> allTasks = new ArrayList<>();

    /**
     * Constructor for DataManager class.
     * @param moduleManager the ModuleManager object for the module.
     */
    public DataManager(ModuleManager moduleManager) {
        allTasks = ModuleManager.getAllTasks();
    }

    public void setAllTasks(ArrayList<Task> allTasks) {
        this.allTasks = allTasks;
    }

    /**
     * sort all tasks of all modules in ascending order of deadlines.
     */
    public void sortAllTasks() {
        Collections.sort(allTasks, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                String t1Deadline = t1.getDeadline().isPresent() ? t1.getDeadline().toShow() : "";
                String t2Deadline = t2.getDeadline().isPresent() ? t2.getDeadline().toShow() : "";
                return t1Deadline.compareToIgnoreCase(t2Deadline);
            }
        });
    }

    public ArrayList<Task> getAllTasks() {
        return allTasks;
    }

    public int countAllTasks() {
        return allTasks.size();
    }

    /**
     * return an orders lists of all tasks of all modules.
     * @return an ArrayList of String which represents the ordered list
     */
    public ArrayList<String> checkDeadline() {
        ArrayList<String> deadlines = new ArrayList<>();
        sortAllTasks();

        for (Task task: allTasks) {
            deadlines.add(String.format("%-30s", task.getDescription()) + " "
                    + String.format("%-8s", task.getParent().getParent().getModuleCode()) + "   deadline: "
                    + task.getDeadline());
        }
        return deadlines;
    }

    public void addTask(Task toAdd) {
        this.allTasks.add(toAdd);
    }

    public void removeTask(Task toRemove) {
        this.allTasks.remove(toRemove);
    }
}
