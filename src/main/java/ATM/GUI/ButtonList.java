package ATM.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * A class used to display an arbitrarily long list of buttons.
 */
class ButtonList extends JDialog {

    private int selection;

    /**
     * Initialize this ButtonsList with a Frame, a list of options, and a title.
     *
     * @param parent  The parent Frame used to display this ButtonsList.
     * @param options The options corresponding to each button. The text for
     *                each button will correspond to the toString of each option.
     * @param title   The title bar text.
     */
    ButtonList(Frame parent, List<?> options, String title) {
        super(parent, title, true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        int i = 0;
        for (Object option : options) {
            final int j = i++; // Variable passed to lambda must be final
            JButton button = new JButton(option.toString());
            button.addActionListener((ActionEvent e) -> {
                setSelection(j);
                dispose();
            });

            cs.gridx = 0;
            cs.gridy = j;
            panel.add(button, cs);
        }

        getContentPane().add(panel, BorderLayout.CENTER);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    /**
     * Get the index of the option selected by the user.
     *
     * @return an index corresponding to one of the options.
     */
    int getSelection() {
        return selection;
    }

    private void setSelection(int selection) {
        this.selection = selection;
    }
}
