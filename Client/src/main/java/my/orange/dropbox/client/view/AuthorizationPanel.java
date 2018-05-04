package my.orange.dropbox.client.view;

import my.orange.dropbox.client.controller.AuthorizationTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AuthorizationPanel extends Panel {

    public AuthorizationPanel() {
        super(new AuthorizationTask());
        JTextField loginField = new JTextField();
        loginField.setPreferredSize(FIELD_DIMENSION);
        constraints.insets = new Insets(10, 10, 0, 10);
        constraints.gridwidth = 2;
        add(loginField, constraints);

        JTextField passwordField = new JPasswordField();
        passwordField.setPreferredSize(FIELD_DIMENSION);
        constraints.insets = new Insets(0, 10, 0, 10);
        constraints.gridy = 1;
        add(passwordField, constraints);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(listener);
        constraints.insets = new Insets(0, 10, 10, 10);
        constraints.gridwidth = 1;
        constraints.gridy = 2;
        add(registerButton, constraints);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(listener);
        constraints.insets = new Insets(0, 10, 10, 10);
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        add(loginButton, constraints);
    }
}
