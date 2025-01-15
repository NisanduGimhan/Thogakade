package controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class LoginControllerForm {

    public AnchorPane Loadpages;
    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {

        boolean isUserExcist=LoginController.getInstance().validateUser(
                txtName.getText(),
                txtPassword.getText()
        );

        if (!isUserExcist){
            new Alert(Alert.AlertType.ERROR,"user Not Excist").show();
        }else {
            URL resource = this.getClass().getResource("/view/DashBoardForm.fxml");

            assert resource != null;
            Parent load = FXMLLoader.load(resource);
            this.Loadpages.getChildren().clear();
            this.Loadpages.getChildren().add(load);
        }


    }

    public void btnRegisterPageLoadOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/RegisterForm.fxml");

        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        this.Loadpages.getChildren().clear();
        this.Loadpages.getChildren().add(load);
    }
}
