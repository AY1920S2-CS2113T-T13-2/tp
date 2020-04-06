package seedu.nuke.data;

import seedu.nuke.directory.Task;
import seedu.nuke.directory.TaskFile;
import seedu.nuke.exception.DataNotFoundException;
import seedu.nuke.exception.DuplicateDataException;
import seedu.nuke.util.DateTime;

import java.util.ArrayList;

public class TaskFileManager {
    ArrayList<TaskFile> fileList;

    public TaskFileManager() {
        fileList = new ArrayList<>();
    }

    public ArrayList<TaskFile> getFileList() {
        return fileList;
    }

    /**
     * Sets the entire File List to a new list.
     *
     * @param fileList
     *  The new File List to be set
     */
    public void setFileList(ArrayList<TaskFile> fileList) {
        this.fileList = fileList;
    }

    /**
     * Finds a file with the specified file name in the File List.
     *
     * @param fileName
     *  The file name of the task
     * @return
     *  The file with the specified file name if found
     * @throws TaskFileNotFoundException
     *  If the file is not found in the File List
     */
    public TaskFile getFile(String fileName) throws TaskFileNotFoundException {
        for (TaskFile file : fileList) {
            if (file.getFileName().equals(fileName)) {
                return file;
            }
        }
        throw new TaskFileNotFoundException();
    }

    /**
     * Checks for duplicates of the same file name in the File List.
     *
     * @param fileName
     *  The file name to check
     * @return
     *  <code>TRUE</code> if there exists a duplicate, and <code>FALSE</code> otherwise
     */
    public boolean contains(String fileName) {
        for (TaskFile file : fileList) {
            if (file.isSameFile(fileName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add a file to the File List.
     *
     * @param toAdd
     *  The file to be added
     * @throws DuplicateTaskFileException
     *  If there exists a duplicate file in the File List with the same file name
     */
    public void add(TaskFile toAdd) throws DuplicateTaskFileException {
        if (contains(toAdd.getFileName())) {
            throw new DuplicateTaskFileException();
        } else {
            fileList.add(toAdd);
        }
    }

    /**
     * Deletes the specified file from the File List.
     *
     * @param toDelete
     *  The task to be deleted
     */
    public void delete(TaskFile toDelete) {
        fileList.remove(toDelete);
    }

    /**
     * Edits a file in the File List.
     *
     * @param toEdit
     *  The file to be edited
     * @param newFileName
     *  The new file name of the file
     * @throws DuplicateTaskFileException
     *  If there are duplicate files with the same file name as the new file name in the File List
     */
    public void edit(TaskFile toEdit, String newFileName) throws DuplicateTaskFileException {
        if (!toEdit.isSameFile(newFileName) && contains(newFileName)) {
            throw new DuplicateTaskFileException();
        }
        toEdit.setFileName(newFileName);
    }

    /**
     * Filter for files in the File List with names that contains the specified keyword.
     * Filtering is done in a case-insensitive manner.
     *
     * @param fileKeyword
     *  The keyword to filter the files
     * @return
     *  The list of filtered files
     */
    public ArrayList<TaskFile> filter(String fileKeyword) {
        ArrayList<TaskFile> filteredFileList = new ArrayList<>();
        for (TaskFile file : fileList) {
            if (file.getFileName().toLowerCase().contains(fileKeyword.toLowerCase())) {
                filteredFileList.add(file);
            }
        }
        return filteredFileList;
    }

    /**
     * Filter for files in the File List with names that matches <b>exactly</b> the specified keyword.
     * Filtering is done in a case-insensitive manner.
     *
     * @param fileKeyword
     *  The keyword to filter the files
     * @return
     *  The list of filtered files
     */
    public ArrayList<TaskFile> filterExact(String fileKeyword) {
        // Returns all tasks in the File List if no keyword is provided.
        String noKeyword = "";
        if (fileKeyword.equals(noKeyword)) {
            return this.getFileList();
        }

        ArrayList<TaskFile> filteredFileList = new ArrayList<>();
        for (TaskFile file : fileList) {
            if (file.getFileName().toLowerCase().equals(fileKeyword.toLowerCase())) {
                filteredFileList.add(file);
            }
        }
        return filteredFileList;
    }

    /**
     * Returns a string representation of the File List.
     * @return
     *  The string representation of the File List
     */
    @Override
    public String toString() {
        StringBuilder listToString = new StringBuilder();
        for (TaskFile file : fileList) {
            String arrayString = "[\"" + file.getFileName() + "\", \"" + file.getFilePath() + "\"]";
            listToString.append(arrayString).append(" ");
        }
        return listToString.toString();
    }

    public static class TaskFileNotFoundException extends DataNotFoundException {
    }

    public static class DuplicateTaskFileException extends DuplicateDataException {
    }
}
