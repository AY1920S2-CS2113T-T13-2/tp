package seedu.nuke;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.command.ExitCommand;

import seedu.nuke.parser.Parser;
import seedu.nuke.ui.Ui;

import static org.fusesource.jansi.Ansi.ansi;
import static seedu.nuke.util.ExceptionMessage.*;

public class Nuke {
    private Ui ui;
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        AnsiConsole.systemInstall();
        ansi().reset();
        System.out.println(ansi().bold().fg(Ansi.Color.RED).a("Hello from NUKE.").reset());
        //System.out.println(ansi().bold().fg(Ansi.Color.BLUE).a(logo).reset());
        System.out.println(ansi().bold().fg(Ansi.Color.MAGENTA).a("Hope to see you soon.").reset());

        Nuke nuke = new Nuke();
        nuke.run();

        AnsiConsole.systemUninstall();
    }

    /**
     * Prints welcome message, initialises task list and runs program until termination.
     * <p></p>
     * <b>Note:</b> Program exits if task list initialisation fails.
     */
    private void run() {
        this.ui = new Ui();

        readInputUntilExit();
    }

    /**
     *  Reads user input from the command line until an <b><i>exit</i></b> command is given.
     *  Each user input is converted into a command by the <b>Parser</b> and executed. The <b>UI.java</b> then displays
     *  any feedback message or necessary information to the user.
     * @see Parser
     * @see Ui
     */
    private void readInputUntilExit() {
        do {
            String input = ui.getInput();
            Command command = new Parser().parseInput(input);
            CommandResult result = command.execute();
            ui.showResult(result);
        } while (!ExitCommand.isExit());
    }
}
