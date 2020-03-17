package seedu.nuke.command.category;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.data.category.Category;
import seedu.nuke.data.category.CategoryList;
import seedu.nuke.data.module.Module;
import seedu.nuke.data.module.ModuleList;

import java.util.regex.Pattern;

import static seedu.nuke.parser.Parser.MODULE_CODE_PREFIX;
import static seedu.nuke.parser.Parser.PRIORITY_PREFIX;
import static seedu.nuke.util.ExceptionMessage.*;
import static seedu.nuke.util.Message.MESSAGE_ADD_CATEGORY_SUCCESS;


/**
 * <h3>Add Category Command</h3>
 * A <b>Command</b> to add a <b>Category</b> for the user's <i>tasks</i>.
 *
 * @see Command
 * @see seedu.nuke.data.category.Category
 */
public class AddCategoryCommand extends Command {
    public static final String COMMAND_WORD = "addc";
    public static final String FORMAT = COMMAND_WORD + " <category name> -m <module code> -p <priority>";
    public static final Pattern[] REGEX_FORMATS = {
            Pattern.compile("(?<identifier>^\\s*([^-]+))"),
            Pattern.compile("(?<moduleCode>(?:" + MODULE_CODE_PREFIX + " [^-]+))"),
            Pattern.compile("(?<priority>(?:" + PRIORITY_PREFIX + " [^-]+)?)"),
            Pattern.compile("(?<invalid>(?:-(?:[^mp].*|[mp]\\S+)))")
    };

    private String moduleCode;
    private String categoryName;
    private int categoryPriority;

    public AddCategoryCommand(String moduleCode, String categoryName, Integer categoryPriority) {
        this.moduleCode = moduleCode;
        this.categoryName = categoryName;
        this.categoryPriority = (categoryPriority != null) ? categoryPriority : 0;
    }

    @Override
    public CommandResult execute() {
        try {
            Module parentModule = ModuleList.getModule(moduleCode);
            Category toAdd = new Category(parentModule, categoryName, categoryPriority);
            ModuleList.retrieve(moduleCode).add(toAdd);
            return new CommandResult(MESSAGE_ADD_CATEGORY_SUCCESS(categoryName));
        } catch (ModuleList.ModuleNotFoundException e) {
            return new CommandResult(MESSAGE_MODULE_NOT_FOUND);
        } catch (CategoryList.DuplicateCategoryException e) {
            return new CommandResult(MESSAGE_DUPLICATE_CATEGORY);
        }
    }
}
