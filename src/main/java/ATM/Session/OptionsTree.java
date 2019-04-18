package ATM.Session;

import ATM.GUI.GraphicalInputReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree used to represent a branching user options menu.
 */
public class OptionsTree {

    private final List<OptionsTree> children;
    private final String option;
    private Command command;

    /**
     * Initialize this OptionsTree with an option name.
     *
     * @param option The option name of this OptionTree's root node.
     */
    public OptionsTree(String option) {
        this.option = option;
        this.children = new ArrayList<>();
        this.command = () -> {}; // Empty command
    }

    /**
     * Initialize an empty OptionsTree.
     */
    public OptionsTree() {
        this("");
    }

    /**
     * Initialize this OptionsTree with an option name and a command.
     *
     * @param option  The option name of this OptionTree's root node.
     * @param command The command for this OptionsTree to execute.
     */
    public OptionsTree(String option, Command command) {
        this(option);
        this.command = command;
    }

    /**
     * Execute this OptionTree's command.
     */
    void executeCommand() {
        this.command.execute();
    }

    /**
     * Get this OptionTree's root option.
     *
     * @return the name of this OptionTree's root option.
     */
    private String getOption() {
        return option;
    }

    /**
     * Add a child node to this OptionsTree.
     *
     * @param child The child to add to this tree.
     */
    public void add(OptionsTree child) {
        children.add(child);
    }

    /**
     * Add a child node to this OptionsTree at the specified index.
     *
     * @param index The index at which to add the child.
     * @param child The child to add to this tree.
     */
    public void add(int index, OptionsTree child) {
        children.add(index, child);
    }

    /**
     * Create a new OptionsTree with a option name and a command, and add it
     * as a child to this OptionsTree.
     *
     * @param option  The option name of the new child.
     * @param command The command of the new child.
     */
    public void add(String option, Command command) {
        children.add(new OptionsTree(option, command));
    }

    /**
     * Create a new OptionsTree with an option name and a command, and add it
     * as a child to this OptionsTree at the specified index.
     *
     * @param index   The index at which to add the child.
     * @param option  The option name of the new child.
     * @param command The command of the new child.
     */
    public void add(int index, String option, Command command) {
        children.add(index, new OptionsTree(option, command));
    }

    /**
     * Return whether this OptionsTree has any children.
     *
     * @return true if this OptionsTree has children, false otherwise.
     */
    boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Return one of this OptionsTree's child nodes based on user input.
     *
     * @return a child node determined from user input, or null if this
     * OptionsTree has no children.
     */
    OptionsTree getChildFromUserInput(GraphicalInputReader inputReader) {
        if (!hasChildren()) {
            return null;
        }
        int userSelection = inputReader.getSelectionFromOptions(children, getOption());
        return children.get(userSelection);
    }

    @Override
    public String toString() {
        return getOption();
    }
}
