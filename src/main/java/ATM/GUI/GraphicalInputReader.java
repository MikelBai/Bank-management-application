package ATM.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A class used to generate input dialogs and get specific types of input from the user.
 */
public class GraphicalInputReader {

    private final Frame frame;

    /**
     * Initialize this GraphicalInputReader with a Frame.
     *
     * @param frame The Frame used to display input dialogs.
     */
    public GraphicalInputReader(Frame frame) {
        this.frame = frame;
    }

    /**
     * Get a String from the user.
     *
     * @param message The message to appear when prompting the user.
     * @param title   The text to appear in the title bar of the dialog.
     * @return A String retrieved from user input.
     */
    public String getString(String message, String title) {
        KeyboardInputDialog inputDialog = new KeyboardInputDialog(frame, message, title);
        inputDialog.setVisible(true);
        return inputDialog.getInput();
    }

    /**
     * Get a double from the user.
     *
     * @param message The message to appear when prompting the user.
     * @param title   The text to appear in the title bar of the dialog.
     * @return A double retrieved from user input.
     */
    private double getDouble(String message, String title) {
        String inputString;
        while (true) {
            inputString = getString(message, title);
            try {
                return Double.parseDouble(inputString);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Input must be a number", title, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Get an int from the user.
     *
     * @param message The message to appear when prompting the user.
     * @param title   The text to appear in the title bar of the dialog.
     * @return An int retrieved from user input.
     */
    private int getInt(String message, String title) {
        String inputString;
        while (true) {
            inputString = getString(message, title);
            try {
                return Integer.parseInt(inputString);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Input must be an integer", title, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Get a positive double from the user.
     *
     * @param message The message to appear when prompting the user.
     * @param title   The text to appear in the title bar of the dialog.
     * @return A positive double retrieved from user input.
     */
    public double getPositiveDouble(String message, String title) {
        double inputDouble;
        while (true) {
            inputDouble = getDouble(message, title);
            if (inputDouble > 0) {
                return inputDouble;
            } else {
                JOptionPane.showMessageDialog(frame, "Input must be positive", title, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Get a non-negative integer from the user.
     *
     * @param message The message to appear when prompting the user.
     * @param title   The text to appear in the title bar of the dialog.
     * @return A non-negative integer retrieved from user input.
     */
    public int getPositiveInt(String message, String title) {
        int inputInt;
        while (true) {
            inputInt = getInt(message, title);
            if (inputInt >= 0) {
                return inputInt;
            } else {
                JOptionPane.showMessageDialog(frame, "Input must be a non-negative integer", title, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Get a user selection from a list of options.
     *
     * @param options The list of options from which to select. The text
     *                displayed for each option will correspond to its toString.
     * @param title   The text to appear in the title bar of the dialog.
     * @return The index of the selected option.
     */
    public int getSelectionFromOptions(List<?> options, String title) {
        ButtonList buttonList = new ButtonList(frame, options, title);
        buttonList.setVisible(true);
        return buttonList.getSelection();
    }
}
