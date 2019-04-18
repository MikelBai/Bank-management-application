package ATM.Session;

/**
 * An interface used to execute commands from user input.
 */
@FunctionalInterface
public interface Command {
    void execute();
}
