package controller.customers;

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
import model.Customer;
import service.custom.impl.CustomerServiceImpl;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private TableColumn colAddrees;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colSalary;

    @FXML
    private TableView tblCustomer;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    void btnAddOnAction(ActionEvent event) {
      boolean  isAdd= CustomerServiceImpl.getInstance().addCustomer(
              new Customer(
                      txtId.getText(),
                      txtName.getText(),
                      txtAddress.getText(),
                     Double.parseDouble( txtSalary.getText())
              ));

      if (isAdd){
          new Alert(Alert.AlertType.INFORMATION,"Customer Added Successfully").show();
      }else {
          new Alert(Alert.AlertType.ERROR,"Customer Added Unsuccessfully").show();
      }
        loadTable();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        boolean idDeleted= CustomerServiceImpl.getInstance().deleteCustomer(
                txtId.getText()
        );

        if (idDeleted){
            new Alert(Alert.AlertType.INFORMATION,"Customer Deleted!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Customer Not Deleted").show();
        }
        loadTable();

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

        Customer customer= CustomerServiceImpl.getInstance().searchCustomer(
                txtId.getText()
        );

        if (customer!=null){
           // txtId.setText(customer.getId());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(String.valueOf(customer.getSalary()));
        }else {
            txtName.setText(null);
            txtAddress.setText(null);
            txtSalary.setText(null);
        }


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        boolean isUpdate= CustomerServiceImpl.getInstance().updateCustomer(
                new Customer(
                        txtId.getText(),
                        txtName.getText(),
                        txtAddress.getText(),
                        Double.parseDouble(txtSalary.getText())
                ));
        if (isUpdate){
            new Alert(Alert.AlertType.INFORMATION,"Updated").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Not updated").show();
        }
        loadTable();
    }

    private void loadTable(){
        List<Customer> customerList = CustomerServiceImpl.getInstance().getAll();
        ObservableList<Customer> oblist = FXCollections.observableArrayList();
        oblist.addAll(customerList);
        tblCustomer.setItems(oblist);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddrees.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        loadTable();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldvalue, newValue) -> {
            if (newValue!=null){
                SetTextForFieldsWhenItSelected((Customer) newValue);
            }
        }));
    }

    private void SetTextForFieldsWhenItSelected(Customer customer){
        txtId.setText(customer.getId());
        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
        txtSalary.setText(String.valueOf(customer.getSalary()));

    }

}
