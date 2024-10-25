package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import service.ServiceFactory;
import service.custom.LoginService;
import util.ServiceType;
import java.io.IOException;

public class RecoveryFormController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane scenePane;

    @FXML
    private TextField txtEmail;

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        try {
            scenePane = FXMLLoader.load(getClass().getResource("../view/login_form.fxml"));
            borderPane.setCenter(scenePane);
            borderPane.toFront();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSubmitOnAction(ActionEvent event) {
        LoginService service = ServiceFactory.getInstance().getServiceType(ServiceType.LOGIN);
        if (!(txtEmail.getText()).isEmpty()) {
            if (service.validEmail(txtEmail.getText())) {
                try {
                    AnchorPane view = FXMLLoader.load(getClass().getResource("../view/renew_password_form.fxml"));
                    borderPane.setCenter(view);
                    borderPane.toFront();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Invalid or not found recovery E-mail").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "PLease Enter Your Recovery E-mail Above").show();
        }

    }

}
