package com.example.javafx_dakar.controller;

import com.example.javafx_dakar.Main;
import com.example.javafx_dakar.model.*;
import com.example.javafx_dakar.utils.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private TextField idField;
    @FXML
    private TextField teamNameField;
    @FXML
    private TextField nameSurnameField;
    @FXML
    private CheckBox boxField1;
    @FXML
    private CheckBox boxField2;
    @FXML
    private CheckBox boxField3;
    @FXML
    private RadioButton radioButtonField1;
    @FXML
    private RadioButton radioButtonField2;
    @FXML
    private RadioButton radioButtonField3;
    @FXML
    private ComboBox<String> membersField;
    @FXML
    private Label errorLabel;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn teamNameColumn;
    @FXML
    private TableColumn nameSurnameColumn;
    @FXML
    private TableColumn sponsorsColumn;
    @FXML
    private TableColumn racingCarColumn;
    @FXML
    private TableColumn membersColumn;
    @FXML
    private Label nameLabel;
    @FXML
    private Label groupLabel;

    ObservableList<Dakar> list = FXCollections.observableArrayList();
    ObservableList<String> memberList = FXCollections.observableArrayList("1", "2", "3");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        membersField.setItems(memberList);

        ToggleGroup toggleGroup = new ToggleGroup();
        radioButtonField1.setToggleGroup(toggleGroup);
        radioButtonField2.setToggleGroup(toggleGroup);
        radioButtonField3.setToggleGroup(toggleGroup);

        // Parodomas prisijunges vartotojas.
        String username = UserSingleton.getInstance().getUsername();
        nameLabel.setText(username);

        // Parodoma prisijungusio vartotojo role.
        String role = UserDAO.searchByUsername(username);
        groupLabel.setText(role);
    }

    @FXML
    protected void onCreateButtonClick() {
        String teamName = teamNameField.getText();
        String nameSurname = nameSurnameField.getText();
        String sponsors = "";
        String racingCar = "";

        if (!Validation.isValidTeamName(teamName)) {
            errorLabel.setText("Team name is incorrect! Just capital and lower cases!");
        } else if (!Validation.isValidNameSurname(nameSurname)) {
            errorLabel.setText("Name and surname is incorrect! Just capital and lower cases!");
        } else {
            if (boxField1.isSelected()) {
                sponsors = sponsors + boxField1.getText();
            }
            if (boxField1.isSelected() && boxField2.isSelected()) {
                sponsors += ", " + boxField2.getText();
            } else if (boxField2.isSelected()) {
                sponsors += boxField2.getText();
            }
            if (boxField1.isSelected() && boxField3.isSelected()) {
                sponsors += ", " + boxField3.getText();
            } else if (boxField2.isSelected() && boxField3.isSelected()) {
                sponsors += ", " + boxField3.getText();
            } else if (boxField3.isSelected()) {
                sponsors += boxField3.getText();
            }

            if (radioButtonField1.isSelected()) {
                racingCar = radioButtonField1.getText();
            } else if (radioButtonField2.isSelected()) {
                racingCar = radioButtonField2.getText();
            } else if (radioButtonField3.isSelected()) {
                racingCar = radioButtonField3.getText();
            }

            if (sponsors.equals("")) {
                errorLabel.setText("You must select your sponsors!");
            } else if (racingCar.equals("")) {
                errorLabel.setText("You must select your racing car!");
            } else if (membersField.getValue() == null) {
                errorLabel.setText("You must select your member count!");
            } else {
                int userId = UserDAO.searchByUsernameReturnId(nameLabel.getText());

                Dakar dakar = new Dakar(teamName, nameSurname, sponsors, racingCar, Integer.parseInt(membersField.getValue()), userId);
                DakarDAO.insert(dakar);
                errorLabel.setText("Successfully created new entry!");
            }
        }
    }

    @FXML
    protected void onSearchButtonClick() {
        // Naudojamas tam, kad isvalytume anksciau buvusi sarasa.
        list.clear();
        String teamNameKeyword = teamNameField.getText();
        String nameSurnameKeyword = nameSurnameField.getText();
        List<Dakar> dakarList = new ArrayList<>();

        // Jeigu vartotojas nieko neivede tai atspausdinam visa sarasa.
        if (teamNameKeyword.equals("") && nameSurnameKeyword.equals("")) {
            dakarList = DakarDAO.printAll();
        } else if (!teamNameKeyword.equals("") && !nameSurnameKeyword.equals("")) {
            dakarList = DakarDAO.printByTeamNameAndNameSurname(teamNameKeyword, nameSurnameKeyword);
        } else if (!teamNameKeyword.equals("")) {
            dakarList = DakarDAO.printByTeamName(teamNameKeyword);
        } else if (!nameSurnameKeyword.equals("")) {
            dakarList = DakarDAO.printByNameSurname(nameSurnameKeyword);
        }

        for (Dakar dakar : dakarList) {
            // Is DB saraso sudedame elementus i ObservableList (kad juos galetume matyti GUI).
            list.add(new Dakar(dakar.getId(), dakar.getTeamName(), dakar.getNameSurname(), dakar.getSponsors(), dakar.getRacingCars(), dakar.getMembers()));

            // Priskiriame lenteles stulpeliams reiksmes is DB.
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));
            nameSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("nameSurname"));
            sponsorsColumn.setCellValueFactory(new PropertyValueFactory<>("sponsors"));
            racingCarColumn.setCellValueFactory(new PropertyValueFactory<>("racingCars"));
            membersColumn.setCellValueFactory(new PropertyValueFactory<>("members"));
        }

        tableView.setItems(list);
    }

    @FXML
    protected void onUpdateButtonClick() {
        String id = idField.getText();
        String teamName = teamNameField.getText();
        String nameSurname = nameSurnameField.getText();
        String sponsors = "";
        String racingCar = "";

        if (groupLabel.getText().equals("ADMINISTRATORIUS")) {
            if (!Validation.isValidTeamName(teamName)) {
                errorLabel.setText("Team name is incorrect! Just capital and lower cases!");
            } else if (!Validation.isValidNameSurname(nameSurname)) {
                errorLabel.setText("Name and surname is incorrect! Just capital and lower cases!");
            } else {
                if (boxField1.isSelected()) {
                    sponsors = sponsors + boxField1.getText();
                }
                if (boxField1.isSelected() && boxField2.isSelected()) {
                    sponsors += ", " + boxField2.getText();
                } else if (boxField2.isSelected()) {
                    sponsors += boxField2.getText();
                }
                if (boxField1.isSelected() && boxField3.isSelected()) {
                    sponsors += ", " + boxField3.getText();
                } else if (boxField2.isSelected() && boxField3.isSelected()) {
                    sponsors += ", " + boxField3.getText();
                } else if (boxField3.isSelected()) {
                    sponsors += boxField3.getText();
                }

                if (radioButtonField1.isSelected()) {
                    racingCar = radioButtonField1.getText();
                } else if (radioButtonField2.isSelected()) {
                    racingCar = radioButtonField2.getText();
                } else if (radioButtonField3.isSelected()) {
                    racingCar = radioButtonField3.getText();
                }

                if (sponsors.equals("")) {
                    errorLabel.setText("You must select your sponsors!");
                } else if (racingCar.equals("")) {
                    errorLabel.setText("You must select your racing car!");
                } else if (membersField.getValue() == null) {
                    errorLabel.setText("You must select your member count!");
                } else {
                    Dakar dakar = new Dakar(Integer.parseInt(id), teamName, nameSurname, sponsors, racingCar, Integer.parseInt(membersField.getValue()));
                    DakarDAO.update(dakar);
                    errorLabel.setText("You have successfully updated the team!");
                }
            }
        } else {
            // VARTOTOJAS
            errorLabel.setText("You need administrator permission to be able to edit teams!");
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        String id = idField.getText();

        if (groupLabel.getText().equals("ADMINISTRATORIUS")) {
            if (id.equals("")) {
                errorLabel.setText("You must specify which team ID you want to delete.");
            } else if (!Validation.isValidId(id)) {
                errorLabel.setText("You can only write numbers!");
            } else {
                DakarDAO.deleteById(Integer.parseInt(id));
                errorLabel.setText("You have successfully deleted the team!");
            }
        } else {
            // VARTOTOJAS
            errorLabel.setText("You need administrator permission to be able to edit teams!");
        }
    }

    @FXML
    protected void onLogOutButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("login-view.fxml"));
        Stage loginWindow = new Stage();
        loginWindow.setTitle("Login window");
        loginWindow.setScene(new Scene(root, 600, 400));
        loginWindow.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
