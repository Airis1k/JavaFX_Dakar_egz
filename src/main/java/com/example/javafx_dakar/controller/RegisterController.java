package com.example.javafx_dakar.controller;

import com.example.javafx_dakar.Main;
import com.example.javafx_dakar.model.User;
import com.example.javafx_dakar.model.UserDAO;
import com.example.javafx_dakar.utils.BCryptPassword;
import com.example.javafx_dakar.utils.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField emailField;
    @FXML
    private Label errorLabel;
    @FXML
    private CheckBox isAdmin;

    @FXML
    protected void onRegisterButtonClick(ActionEvent event) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String email = emailField.getText();

        if (!Validation.isValidUsername(username)) {
            errorLabel.setText("Username is incorrect! Just capital and lower cases, length between 5 and 13!");
        } else if (!Validation.isValidPassword(password)) {
            errorLabel.setText("Password is incorrect! Just capital and lower cases, special symbols and length between 5 and 16!");
        } else if (!confirmPassword.equals(password)) {
            errorLabel.setText("Passwords are not equal!");
        } else if (!Validation.isValidEmail(email)) {
            errorLabel.setText("Email is incorrect! Just capital and lower cases, numbers, special symbols!");
        } else {
            // 1-a paimam ir uzBCryptinam passworda ir tada perduodame per parametrus naujam sukurtam useriui.
            String bCryptPassword = BCryptPassword.hashPassword(password);

            boolean isAdminCheck = isAdmin.isSelected();

            // Pridedame useri i DB.
            User user = new User(username, bCryptPassword, email, isAdminCheck);
            UserDAO.insert(user);

            // Sukurus nauja useri, griztam i LogIn langa.
            // Vaizdo uzkrovimas.
            Parent root = FXMLLoader.load(Main.class.getResource("login-view.fxml"));
            Stage loginWindow = new Stage();
            loginWindow.setTitle("Login window");
            loginWindow.setScene(new Scene(root, 600, 400));
            // Lango parodymas.
            loginWindow.show();
            // Kodas reikalingas paslepti pries tai buvusi langa.
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }
    }
}
