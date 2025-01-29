package controller.items;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import service.custom.impl.ItemServiceimpl;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ItemControllerForm implements Initializable {

    @FXML
    private TableColumn colCode;

    @FXML
    private TableColumn colDescription;

    @FXML
    private TableColumn colPrice;

    @FXML
    private TableColumn colQuantity;

    @FXML
    private TableView tblItem;

    @FXML
    private JFXTextField txtCode;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQuantity;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        boolean isAdd= ItemServiceimpl.getInstance().addItem(
                new Item(
                        txtCode.getText(),
                        txtDescription.getText(),
                       Double.parseDouble(txtPrice.getText()),
                       Integer.parseInt(txtQuantity.getText())
                ));

        if (isAdd){
            new Alert(Alert.AlertType.INFORMATION,"Item Added!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Item Not Added").show();
        }
        loadTable();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        boolean isDelete= ItemServiceimpl.getInstance().deleteItem(
                txtCode.getText()
        );

        if (isDelete){
            new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Item Not Deleted").show();
        }
        loadTable();

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Item item= ItemServiceimpl.getInstance().searchItem(
                txtCode.getText()
        );

        if (item!= null){
            txtDescription.setText(item.getDescription());
            txtPrice.setText(String.valueOf(item.getPrice()));
            txtQuantity.setText(String.valueOf(item.getQty()));
        }else {
            txtDescription.setText(null);
            txtQuantity.setText(null);
            txtPrice.setText(null);
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean isUpdated= ItemServiceimpl.getInstance().updateItem(
                new Item(
                    txtCode.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtPrice.getText()) ,
                    Integer.parseInt(txtQuantity.getText())
                )
        );

        if (isUpdated){
            new Alert(Alert.AlertType.INFORMATION,"Item Updated!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Item Not Updated").show();
        }
        loadTable();
    }

    public void loadTable(){
        List<Item> all = ItemServiceimpl.getInstance().getAll();
        ObservableList<Item> list = FXCollections.observableArrayList();
        list.addAll(all);
        tblItem.setItems(list);
    }
    private void setTextForFieldsWhenItSelect(Item c){
        txtCode.setText(c.getCode());
        txtDescription.setText(c.getDescription());
        txtPrice.setText(String.valueOf(c.getPrice()));
        txtQuantity.setText(String.valueOf(c.getQty()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));

        loadTable();

        tblItem.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) ->{
            setTextForFieldsWhenItSelect((Item) newValue);
        }
        ));
    }


}
