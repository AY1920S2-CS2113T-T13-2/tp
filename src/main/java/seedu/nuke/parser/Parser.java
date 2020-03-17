package seedu.nuke.parser;

import seedu.nuke.command.*;
import seedu.nuke.command.module.AddModuleCommand;
import seedu.nuke.command.module.DeleteModuleCommand;
import seedu.nuke.command.category.AddCategoryCommand;
import seedu.nuke.command.category.DeleteCategoryCommand;
import seedu.nuke.command.module.ListModuleCommand;
import seedu.nuke.command.prompt.DeleteConfirmationPrompt;
import seedu.nuke.command.prompt.ConfirmationStatus;
import seedu.nuke.command.prompt.ListNumberPrompt;
import seedu.nuke.command.task.AddTaskCommand;
import seedu.nuke.command.task.DeleteTaskCommand;
import seedu.nuke.exception.InvalidFormatException;
import seedu.nuke.format.DateTime;
import seedu.nuke.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static seedu.nuke.util.ExceptionMessage.*;
import static seedu.nuke.util.Message.*;

/**
 * <h3>Parser</h3>
 * The <b>Parser</b> interprets the user input that is read by the <b>UI</b>.
 * The <b>Parser</b> then converts the input into a <b>Command</b> to be executed by the <b>Nuke</b> program.
 */
public class Parser {
    public static final String MODULE_CODE_PREFIX = "-m";
    public static final String CATEGORY_NAME_PREFIX = "-c";
    public static final String TASK_DESCRIPTION_PREFIX = "-t";
    public static final String PRIORITY_PREFIX = "-p";
    public static final String DEADLINE_PREFIX = "-d";
    public static final String ALL_FLAG = "-a";
    public static final String EXACT_FLAG = "-e";

    private final String WHITESPACES = "\\s+";
    private final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<parameters>.*)");

    /**
     * Parses the input string read by the <b>UI</b> and converts the string into a specific <b>Command</b>, which is
     * to be executed by the <b>Nuke</b> program.
     * <p></p>
     * <b>Note</b>: The user input has to start with a certain keyword (i.e. <i>command word</i>), otherwise an
     * <i>Invalid Command Exception</i> will be thrown.
     *
     * @param input The user input read by the <b>UI/b>
     * @return The <b>corresponding</b> command to be executed
     * @see seedu.nuke.ui.Ui
     * @see Command
     */
    public Command parseInput(String input) {

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(input.trim());

        if (!matcher.matches()) {
            return new InvalidCommand(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        String commandWord = matcher.group("commandWord").toLowerCase();
        String parameters = matcher.group("parameters").trim();

        switch (commandWord) {

        case AddModuleCommand.COMMAND_WORD:
            return prepareAddModuleCommand(parameters);

        case DeleteModuleCommand.COMMAND_WORD:
            return prepareDeleteModuleCommand(parameters);

        case ListModuleCommand.COMMAND_WORD:
            return prepareListModuleCommand(parameters);

        case ListCommand.COMMAND_WORD:
            return prepareListCommand(parameters);

        case AddCategoryCommand.COMMAND_WORD:
            return prepareAddCategoryCommand(parameters);

        case DeleteCategoryCommand.COMMAND_WORD:
            return prepareDeleteCategoryCommand(parameters);

        case AddTaskCommand.COMMAND_WORD:
            return prepareAddTaskCommand(parameters);

        case DeleteTaskCommand.COMMAND_WORD:
            return prepareDeleteTaskCommand(parameters);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        default:
            return new InvalidCommand(MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    private Command prepareAddModuleCommand(String parameters) {
        final Pattern[] ADD_MODULE_FORMAT = AddModuleCommand.REGEX_FORMATS;
        final int INVALID_PARAMETER_FORMATS_INDEX = ADD_MODULE_FORMAT.length - 1;
        Matcher[] matchers = new Matcher[ADD_MODULE_FORMAT.length];

        if (isMissingCompulsoryParameters(ADD_MODULE_FORMAT, matchers, parameters)) {
            return new InvalidCommand(MESSAGE_MISSING_PARAMETERS);
        }

        if (matchers[INVALID_PARAMETER_FORMATS_INDEX].find()) {
            return new InvalidCommand(MESSAGE_INVALID_PARAMETERS);
        }

        String moduleCode = matchers[0].group("identifier").trim();

        return new AddModuleCommand(moduleCode);
    }

    private Command prepareDeleteModuleCommand(String parameters) {
        final Pattern[] DELETE_MODULE_FORMAT = DeleteModuleCommand.REGEX_FORMATS;
        final int INVALID_PARAMETER_FORMATS_INDEX = DELETE_MODULE_FORMAT.length - 1;
        Matcher[] matchers = new Matcher[DELETE_MODULE_FORMAT.length];

        if (isMissingCompulsoryParameters(DELETE_MODULE_FORMAT, matchers, parameters)) {
            return new InvalidCommand(MESSAGE_MISSING_PARAMETERS);
        }

        if (matchers[INVALID_PARAMETER_FORMATS_INDEX].find()) {
            return new InvalidCommand(MESSAGE_INVALID_PARAMETERS);
        }

        String moduleCode = matchers[0].group("identifier").trim();
        String exactFlag = matchers[1].group("exact").trim();
        boolean isExact = !exactFlag.isEmpty();

        return new DeleteModuleCommand(moduleCode, isExact);
    }

    private Command prepareListModuleCommand(String parameters) {
        final Pattern[] LIST_MODULE_FORMAT = ListModuleCommand.REGEX_FORMATS;
        final int INVALID_PARAMETER_FORMATS_INDEX = LIST_MODULE_FORMAT.length - 1;
        Matcher[] matchers = new Matcher[LIST_MODULE_FORMAT.length];

        if (isMissingCompulsoryParameters(LIST_MODULE_FORMAT, matchers, parameters)) {
            return new InvalidCommand(MESSAGE_MISSING_PARAMETERS);
        }

        if (matchers[INVALID_PARAMETER_FORMATS_INDEX].find()) {
            return new InvalidCommand(MESSAGE_INVALID_PARAMETERS);
        }

        String moduleKeyword = matchers[0].group("identifier").trim();
        String allFlag = matchers[1].group("all").trim();
        String exactFlag = matchers[2].group("exact").trim();
        boolean isExact = !exactFlag.isEmpty();

        return new ListModuleCommand(moduleKeyword, isExact);
    }

    private Command prepareAddCategoryCommand(String parameters) {
        final Pattern[] ADD_CATEGORY_FORMAT = AddCategoryCommand.REGEX_FORMATS;
        final int INVALID_PARAMETER_FORMATS_INDEX = ADD_CATEGORY_FORMAT.length - 1;
        Matcher[] matchers = new Matcher[ADD_CATEGORY_FORMAT.length];

        if (isMissingCompulsoryParameters(ADD_CATEGORY_FORMAT, matchers, parameters)) {
            return new InvalidCommand(MESSAGE_MISSING_PARAMETERS);
        }

        if (matchers[INVALID_PARAMETER_FORMATS_INDEX].find()) {
            return new InvalidCommand(MESSAGE_INVALID_PARAMETERS);
        }

        String categoryName = matchers[0].group("identifier").trim();
        String moduleCode = matchers[1].group("moduleCode")
                .replace(MODULE_CODE_PREFIX, "").trim();
        String priority = matchers[2].group("priority")
                .replace(PRIORITY_PREFIX, "").trim();

        try {
            Integer categoryPriority = (priority.isEmpty()) ? null : Integer.parseInt(priority);
            return new AddCategoryCommand(moduleCode, categoryName, categoryPriority);
        } catch (NumberFormatException e) {
            return new InvalidCommand(MESSAGE_INVALID_PRIORITY);
        }
    }

    private Command prepareDeleteCategoryCommand(String parameters) {
        final Pattern[] DELETE_CATEGORY_FORMAT = DeleteCategoryCommand.REGEX_FORMATS;
        final int INVALID_PARAMETER_FORMATS_INDEX = DELETE_CATEGORY_FORMAT.length - 1;
        Matcher[] matchers = new Matcher[DELETE_CATEGORY_FORMAT.length];

        if (isMissingCompulsoryParameters(DELETE_CATEGORY_FORMAT, matchers, parameters)) {
            return new InvalidCommand(MESSAGE_MISSING_PARAMETERS);
        }

        if (matchers[INVALID_PARAMETER_FORMATS_INDEX].find()) {
            return new InvalidCommand(MESSAGE_INVALID_PARAMETERS);
        }

        String categoryName = matchers[0].group("identifier").trim();
        String moduleCode = matchers[1].group("moduleCode")
                .replace(MODULE_CODE_PREFIX, "").trim();

        return new DeleteCategoryCommand(moduleCode, categoryName);
    }

    private Command prepareAddTaskCommand(String parameters) {
        final Pattern[] ADD_TASK_FORMAT = AddTaskCommand.REGEX_FORMATS;
        final int INVALID_PARAMETER_FORMATS_INDEX = ADD_TASK_FORMAT.length - 1;
        Matcher[] matchers = new Matcher[ADD_TASK_FORMAT.length];

        if (isMissingCompulsoryParameters(ADD_TASK_FORMAT, matchers, parameters)) {
            return new InvalidCommand(MESSAGE_MISSING_PARAMETERS);
        }

        if (matchers[INVALID_PARAMETER_FORMATS_INDEX].find()) {
            return new InvalidCommand(MESSAGE_INVALID_PARAMETERS);
        }

        String taskDescription = matchers[0].group("identifier").trim();
        String moduleCode = matchers[1].group("moduleCode")
                .replace(MODULE_CODE_PREFIX, "").trim();
        String categoryName = matchers[2].group("categoryName")
                .replace(CATEGORY_NAME_PREFIX, "").trim();
        String deadline = matchers[3].group("deadline")
                .replace(DEADLINE_PREFIX, "").trim();
        String priority = matchers[4].group("priority")
                .replace(PRIORITY_PREFIX, "").trim();

        try {
            DateTime taskDeadline = (deadline.isEmpty()) ? null : DateTimeFormat.stringToDateTime(deadline);
            Integer taskPriority = (priority.isEmpty()) ? null : Integer.parseInt(priority);
            return new AddTaskCommand(moduleCode, categoryName, taskDescription, taskDeadline, taskPriority);
        } catch (NumberFormatException e) {
            return new InvalidCommand(MESSAGE_INVALID_PRIORITY);
        } catch (DateTimeFormat.InvalidDateTimeException | DateTimeFormat.InvalidDateException | DateTimeFormat.InvalidTimeException e) {
            return new InvalidCommand(MESSAGE_INVALID_DEADLINE_FORMAT);
        }
    }

    private Command prepareDeleteTaskCommand(String parameters) {
        final Pattern[] DELETE_TASK_FORMAT = DeleteTaskCommand.REGEX_FORMATS;
        final int INVALID_PARAMETER_FORMATS_INDEX = DELETE_TASK_FORMAT.length - 1;
        Matcher[] matchers = new Matcher[DELETE_TASK_FORMAT.length];

        if (isMissingCompulsoryParameters(DELETE_TASK_FORMAT, matchers, parameters)) {
            return new InvalidCommand(MESSAGE_MISSING_PARAMETERS);
        }

        if (matchers[INVALID_PARAMETER_FORMATS_INDEX].find()) {
            return new InvalidCommand(MESSAGE_INVALID_PARAMETERS);
        }

        String taskDescription = matchers[0].group("identifier").trim();
        String moduleCode = matchers[1].group("moduleCode")
                .replace(MODULE_CODE_PREFIX, "").trim();
        String categoryName = matchers[2].group("categoryName")
                .replace(CATEGORY_NAME_PREFIX, "").trim();

        return new DeleteTaskCommand(moduleCode, categoryName, taskDescription);
    }

    private Command prepareListCommand(String parameters) {
        final Pattern[] LIST_FORMAT = ListCommand.REGEX_FORMATS;
        final int INVALID_PARAMETER_FORMATS_INDEX = LIST_FORMAT.length - 1;
        Matcher[] matchers = new Matcher[LIST_FORMAT.length];

        if (isMissingCompulsoryParameters(LIST_FORMAT, matchers, parameters)) {
            return new InvalidCommand(MESSAGE_MISSING_PARAMETERS);
        }

        if (matchers[INVALID_PARAMETER_FORMATS_INDEX].find()) {
            return new InvalidCommand(MESSAGE_INVALID_PARAMETERS);
        }

        String moduleKeyword = matchers[0].group("moduleCode")
                .replace(MODULE_CODE_PREFIX, "").trim();
        String categoryKeyword = matchers[1].group("categoryName")
                .replace(CATEGORY_NAME_PREFIX, "").trim();
        String taskKeyword = matchers[2].group("taskDescription")
                .replace(TASK_DESCRIPTION_PREFIX, "").trim();

        return new ListCommand(moduleKeyword, categoryKeyword, taskKeyword);
    }

    private boolean isMissingCompulsoryParameters(Pattern[] formats, Matcher[] matchers, String parameters) {
        // Match patterns
        for (int i = 0; i < formats.length; ++i) {
            matchers[i] = formats[i].matcher(parameters);
        }

        // Check if matches with each pattern except last pattern which checks for invalid parameters
        for (int i = 0; i < formats.length - 1; ++i) {
            if (!matchers[i].find()) {
                return true;
            }
        }

        return false;
    }

    public Command parseInputAsConfirmation(String userInput) {
        switch (userInput) {
        case "yes":
        case "y":
            return new DeleteConfirmationPrompt(ConfirmationStatus.CONFIRM);

        case "no":
        case "n":
            return new DeleteConfirmationPrompt(ConfirmationStatus.ABORT);

        default:
            return new DeleteConfirmationPrompt(ConfirmationStatus.WAIT);
        }
    }

    public Command parseInputAsIndices(String input) {
        final Matcher matcher = ListNumberPrompt.INDICES_FORMAT.matcher(input.trim());

        if (!matcher.matches()) {
            return new ListNumberPrompt(null);
        }

        String indicesString = matcher.group("indices");
        String[] separatedIndicesString = indicesString.split(WHITESPACES);

        // Convert String array to Integer ArrayList and removing duplicates
        ArrayList<Integer> indices = Stream.of(separatedIndicesString)
                .map(Integer::parseInt).distinct()
                .collect(Collectors.toCollection(ArrayList::new));

        // Decrement each index by 1 due to 0-based indexing
        for (int i = 0; i < indices.size(); i++) {
            indices.set(i, indices.get(i)-1);
        }

        return new ListNumberPrompt(indices);
    }


    /** Signals that the user has provided an unrecognised command */
    public static class InvalidCommandException extends InvalidFormatException {}

    /** Signals that the user has not provided a list number. */
    public static class MissingListNumberException extends InvalidFormatException {}

    /** Signals that the user has provided surplus parameters. */
    public static class ExcessParameterException extends InvalidFormatException {}

    /** Signals that the user has not provided sufficient parameters. */
    public static class MissingParameterException extends InvalidFormatException {}
}
