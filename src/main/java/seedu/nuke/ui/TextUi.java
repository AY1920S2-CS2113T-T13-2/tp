package seedu.nuke.ui;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;
import static seedu.nuke.util.Message.*;


public class TextUi {

    public static final Ansi.Color SYSTEM_COLOR_MESSAGE = BLUE;
    public static final Ansi.Color SYSTEM_COLOR_RESPONSE = GREEN;
    public static final Ansi.Color SYSTEM_COLOR_DIVIDER = BLACK;
    public static final Ansi.Color SYSTEM_COLOR_LOGO = MAGENTA;
    public static final Ansi.Color SYSTEM_COLOR_ALERT = RED;

    //display welcome message
    public static void showWelcomeMessage (){
        AnsiConsole.systemInstall();
        printDivider();
        System.out.print(
                ansi().bold().fg(SYSTEM_COLOR_MESSAGE).a(MESSAGE_WELCOME_1).reset());
        System.out.print(
                ansi().bold().fg(SYSTEM_COLOR_MESSAGE).a(MESSAGE_WELCOME_2).reset());
        printDivider();
        AnsiConsole.systemUninstall();
    }

    /**
     * clear the screen
     */
    public static void clearScreen(){
        AnsiConsole.systemInstall();
        System.out.println(ansi().eraseScreen());
        AnsiConsole.systemUninstall();
    }

    /**
     * display the logo
     */
    public static void displayLogo() {
        AnsiConsole.systemInstall();
        ansi().reset();
        System.out.print(ansi().bold().fg(SYSTEM_COLOR_LOGO).a(MESSAGE_LOGO).reset());
        printDivider();
        AnsiConsole.systemUninstall();
    }

    /**
     * print a divider
     */
    public static void printDivider(){
        AnsiConsole.systemInstall();
        System.out.println( ansi().bold().fg(SYSTEM_COLOR_DIVIDER).a(DIVIDER).reset() );
        AnsiConsole.systemUninstall();
    }

    public static final String DIVIDER = "---------------------------------------------------------------------------------";

}
