package seedu.nuke.data.file;

import java.util.ArrayList;

public class TaskFileList {
    ArrayList<TaskFile> fileList;

    public TaskFileList() {
        fileList = new ArrayList<>();
    }

    public void add(TaskFile toAdd) {
        fileList.add(toAdd);
    }

    public void delete(TaskFile toDelete) throws TaskFileNotFoundException {
        boolean isFileFoundAndDeleted = fileList.remove(toDelete);
        if (!isFileFoundAndDeleted) {
            throw new TaskFileNotFoundException();
        }
    }

    public void delete(String fileName) throws TaskFileNotFoundException {
        for (TaskFile file : fileList) {
            if (file.getFileName().equals(fileName)) {
                fileList.remove(file);
                return;
            }
        }
        throw new TaskFileNotFoundException();
    }

    @Override
    public String toString() {
        StringBuilder listToString = new StringBuilder();
        for (TaskFile file : fileList) {
            String arrayString = "[\"" + file.getFileName() + "\", \"" + file.getFilePath() + "\"]";
            listToString.append(arrayString).append(" ");
        }
        return listToString.toString();
    }

    public static class TaskFileNotFoundException extends Exception {}
}
