package controller.register;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.User;
import service.custom.impl.RegisterServiceimpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterFormController implements Initializable {

    public AnchorPane Loadpages;
    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtReEnterPassword;

    @FXML
    void btnLoginPageLoadOnAction(ActionEvent event) throws IOException {

        URL resource = this.getClass().getResource("/view/LoginForm.fxml");

        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        this.Loadpages.getChildren().clear();
        this.Loadpages.getChildren().add(load);

    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {




        boolean isAdd= RegisterServiceimpl.getInstance().addUser(
                new User(
                         0,
                        txtName.getText(),
                        txtEmail.getText(),
                       txtPassword.getText()
                )
        );

        if (isAdd){
            new Alert(Alert.AlertType.INFORMATION,"User Added Successfull!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"User NoT Added").show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
