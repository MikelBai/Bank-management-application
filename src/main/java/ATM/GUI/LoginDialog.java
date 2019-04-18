package ATM.GUI;

import ATM.LoginManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * A class used to create a login dialog.
 */
public class LoginDialog extends JDialog {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private boolean succeeded;

    /**
     * Initialize this LoginDialog with a Frame and a LoginManager.
     *
     * @param parent       The parent Frame used to display this LoginDialog.
     * @param loginManager The LoginManager used for login authentication.
     */
    public LoginDialog(Frame parent, LoginManager loginManager) {
        super(parent, "Login", true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        // Username label text
        JLabel usernameLabel = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(usernameLabel, cs);

        // Username field
        usernameField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(usernameField, cs);

        // Password label text
        JLabel passwordLabel = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(passwordLabel, cs);

        // Password field
        passwordField = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(passwordField, cs);

        panel.setBorder(new LineBorder(Color.gray));

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener((ActionEvent e) -> {
            if (loginManager.login(getUsername(), getPassword())) {
                JOptionPane.showMessageDialog(this,
                        "Login successful",
                        "Login",
                        JOptionPane.INFORMATION_MESSAGE);
                succeeded = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password",
                        "Login",
                        JOptionPane.ERROR_MESSAGE);
                usernameField.setText("");
                passwordField.setText("");
                succeeded = false;
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    private String getPassword() {
        return new String(passwordField.getPassword());
    }

    public boolean isSucceeded() {
        return succeeded;
    }
}
