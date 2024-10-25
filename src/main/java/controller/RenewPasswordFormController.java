package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.LoginService;
import util.ServiceType;

import java.io.IOException;

public class RenewPasswordFormController {

    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private TextField txtNewPassword;

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnSubmitOnAction(ActionEvent event) { LoginService service = ServiceFactory.getInstance().getServiceType(ServiceType.LOGIN);
        if(!hasEmptyFields()||isSamePassword()){
            new Alert(Alert.AlertType.CONFIRMATION,"New Password Created\n Login to Continue.");
            if (service.createPassword(txtConfirmPassword.getText())) {
                Stage stage = new Stage();
                try {
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Empty or different Password fields!!").show();
        }
    }

    private boolean isSamePassword() {
        if((txtNewPassword.getText()).equals(txtConfirmPassword.getText())){
            return true;
        }else{
            new Alert(Alert.AlertType.ERROR,"Password fields doesn't match");
            return false;
        }
    }

    private boolean hasEmptyFields() {
        return (txtNewPassword.getText()).isEmpty() || (txtConfirmPassword.getText()).isEmpty();
    }
}
