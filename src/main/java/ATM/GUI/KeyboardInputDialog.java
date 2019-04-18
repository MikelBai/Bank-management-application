package ATM.GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * A class used to create a keyboard input dialog.
 */
class KeyboardInputDialog extends JDialog {

    private final JTextField inputField;

    /**
     * Initialize this KeyboardInputDialog with a parent Frame, a message, and a title.
     *
     * @param parent  The parent Frame used to display this KeyboardInputDialog.
     * @param message The message to appear when prompting the user.
     * @param title   The text to appear in the dialog's title bar.
     */
    KeyboardInputDialog(Frame parent, String message, String title) {
        super(parent, title, true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        JLabel inputLabel = new JLabel(message);
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(inputLabel, cs);

        inputField = new JTextField(20);
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(inputField, cs);

        panel.setBorder(new LineBorder(Color.gray));

        JButton button = new JButton("OK");
        button.addActionListener((ActionEvent e) -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    String getInput() {
        return inputField.getText().trim();
    }
}
