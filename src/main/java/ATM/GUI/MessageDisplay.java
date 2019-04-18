package ATM.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * A class used to display arbitrary messages.
 */
public class MessageDisplay {

    private final Frame frame;

    /**
     * Initialize this MessageDisplay with a Frame.
     *
     * @param frame The Frame used to display messages.
     */
    public MessageDisplay(Frame frame) {
        this.frame = frame;
    }

    /**
     * Show a message.
     *
     * @param message The message to display.
     * @param title   The title bar text.
     */
    public void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Show an error message.
     *
     * @param message The error message to display.
     * @param title   The title bar text.
     */
    public void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
