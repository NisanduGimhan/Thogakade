package controller.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class DashBoardForm {

    @FXML
    private AnchorPane Loadpages;

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/CustomerForm.fxml");
        assert resource!=null;
        Parent load = FXMLLoader.load(resource);
        this.Loadpages.getChildren().clear();
        this.Loadpages.getChildren().add(load);


    }

    @FXML
    void btnItemOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/ItemForm.fxml");
        assert resource!=null;
        Parent load = FXMLLoader.load(resource);
        this.Loadpages.getChildren().clear();
        this.Loadpages.getChildren().add(load);


    }

    @FXML
    void btnOrderOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/OrderForm.fxml");
        assert resource!=null;
        Parent load = FXMLLoader.load(resource);
        this.Loadpages.getChildren().clear();
        this.Loadpages.getChildren().add(load);

    }

}
