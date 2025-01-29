package controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.User;
import org.jasypt.util.text.BasicTextEncryptor;
import service.custom.impl.LoginServiceimpl;

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
        String key ="1234";

        BasicTextEncryptor basicTextEncryptor= new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);

        User user= LoginServiceimpl.getInstance().validateUser(
                txtName.getText(),
                txtPassword.getText()
        );

        if (user == null){

            new Alert(Alert.AlertType.ERROR,"user Not Excist").show();
        }else {
            if (basicTextEncryptor.decrypt(user.getPassword()).equals(txtPassword)){
                URL resource = this.getClass().getResource("/view/DashBoardForm.fxml");

                assert resource != null;
                Parent load = FXMLLoader.load(resource);
                this.Loadpages.getChildren().clear();
                this.Loadpages.getChildren().add(load);
            }

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
