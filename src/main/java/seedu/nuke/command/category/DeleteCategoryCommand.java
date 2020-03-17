package seedu.nuke.command.category;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.data.category.Category;
import seedu.nuke.data.category.CategoryList;
import seedu.nuke.data.module.ModuleList;

import java.util.regex.Pattern;

import static seedu.nuke.parser.Parser.MODULE_CODE_PREFIX;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_CATEGORY_NOT_FOUND;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_MODULE_NOT_FOUND;
import static seedu.nuke.util.Message.MESSAGE_DELETE_CATEGORY_SUCCESS;

public class DeleteCategoryCommand extends Command {
    public static final String COMMAND_WORD = "delc";
    public static final String FORMAT = COMMAND_WORD + " <category name>";
    public static final Pattern[] REGEX_FORMATS = {
            Pattern.compile("(?<identifier>^\\s*([^-]+))"),
            Pattern.compile("(?<moduleCode>(?:" + MODULE_CODE_PREFIX + " [^-]+))"),
            Pattern.compile("(?<invalid>(?:-(?:[^m].*|[m]\\S+)))")
    };

    private String moduleCode;
    private String categoryName;

    public DeleteCategoryCommand(String moduleCode, String categoryName) {
        this.moduleCode = moduleCode;
        this.categoryName = categoryName;
    }

    /**
     * Executes the <b>Delete Category Command</b> to delete a <b>Category</b> with the <code>category name</code> from the
     * <b>Category List</b>.
     *
     * @return The <b>Command Result</b> of the execution
     * @see Category
     * @see CategoryList
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        try {
            Category deletedCategory = ModuleList.retrieve(moduleCode).delete(categoryName);
            return new CommandResult(MESSAGE_DELETE_CATEGORY_SUCCESS(deletedCategory.getCategoryName()));
        } catch (ModuleList.ModuleNotFoundException e) {
            return new CommandResult(MESSAGE_MODULE_NOT_FOUND);
        } catch (CategoryList.CategoryNotFoundException e) {
            return new CommandResult(MESSAGE_CATEGORY_NOT_FOUND);
        }
    }
}
