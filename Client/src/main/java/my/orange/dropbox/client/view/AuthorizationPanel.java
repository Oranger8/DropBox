package my.orange.dropbox.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AuthorizationPanel extends Panel {

    private JTextField passwordField;
    private JButton loginButton, registerButton;

    public AuthorizationPanel() {
        JTextField loginField = new JTextField();
        loginField.setPreferredSize(FIELD_DIMENSION);
        constraints.insets = new Insets(10, 10, 0, 10);
        constraints.gridwidth = 2;
        add(loginField, constraints);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(FIELD_DIMENSION);
        constraints.insets = new Insets(0, 10, 0, 10);
        constraints.gridy = 1;
        add(passwordField, constraints);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        constraints.insets = new Insets(0, 10, 10, 10);
        constraints.gridwidth = 1;
        constraints.gridy = 2;
        add(registerButton, constraints);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        constraints.insets = new Insets(0, 10, 10, 10);
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        add(loginButton, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {

        }

        if (e.getSource() == registerButton) {

        }
    }
}
