package seedu.nuke.command;

import seedu.nuke.common.DataType;
import seedu.nuke.data.task.Task;

import java.util.ArrayList;

/*
    construct the feedback to user String
 */
public class CommandResult {
    private final String feedbackToUser;
    private final DataType dataType;
    private final ArrayList<?> list;

    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
        this.dataType = DataType.NONE;
        this.list = null;
    }

    public CommandResult(String feedbackToUser, DataType dataType, ArrayList<?> listToShow) {
        this.feedbackToUser = feedbackToUser;
        this.dataType = dataType;
        this.list = listToShow;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public DataType getDataType() {
        return dataType;
    }

    public ArrayList<?> getList() {
        return list;
    }
}
