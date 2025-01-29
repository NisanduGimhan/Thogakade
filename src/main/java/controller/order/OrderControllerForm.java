package controller.order;

import com.jfoenix.controls.JFXTextField;
import service.custom.impl.CustomerServiceImpl;
import service.custom.impl.ItemServiceimpl;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.*;
import service.custom.impl.OrderServiceimpl;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderControllerForm implements Initializable {

    public ComboBox txtCustId;
    public ComboBox comboBoxId;
    public TextField txtName;
    public ComboBox comboBoxItem;
    public JFXTextField buyingQty;
    public TableView<Cart> tblOrders;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQtyOnHand;
    public TableColumn colUnitPrice;
    public TableColumn colTotals;
    public Button btnplaceOrder;
    public Label lblNetTotal;
    public JFXTextField txtOrderId;
    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtItem;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtStock;

    @FXML
    private TextField txtUnitPrice;
    ObservableList<Cart> cartList = FXCollections.observableArrayList();
    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

       Double total= Double.parseDouble(txtUnitPrice.getText())*Integer.parseInt(buyingQty.getText());

        cartList.add( new Cart(
                comboBoxItem.getValue().toString(),
                txtDesc.getText(),
                Integer.parseInt(buyingQty.getText()),
                Double.parseDouble(txtUnitPrice.getText()),
                total

        ));

        tblOrders.setItems(cartList);
        buyingQty.clear();

        calcNetTotal();
        comboBoxId.setDisable(false);

    }



    private  void loadDateAndTime(){
        Date date= new Date();
        SimpleDateFormat f= new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(date);

        lblDate.setText(f.format(date));
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime cTime=LocalTime.now();
            lblTime.setText(
                    cTime.getHour() + ":" + cTime.getMinute() + ":" + cTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
                );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private  void loadItemsId(){


    }

    private void loadCustomerIds(){
        List<String> allCustomers = OrderServiceimpl.getInstance().getAllCustomers();
        ObservableList<String> list = FXCollections.observableArrayList(allCustomers);
        comboBoxId.setItems(list);

    }

    private  void loadItemIds(){
        List<String> allItems = OrderServiceimpl.getInstance().getAllItems();
        ObservableList<String> list = FXCollections.observableArrayList(allItems);
        comboBoxItem.setItems(list);

        comboBoxItem.getSelectionModel().selectedItemProperty().addListener(((observableValue, ooldValue, newValue) ->{
            if (newValue!=null){
                comboBoxId.setDisable(true);

            }else {
                comboBoxId.setDisable(false);
            }
        } ));
    }
    private void SetTextForFieldsWhenItSelected(String id){

       Customer customer= CustomerServiceImpl.getInstance().searchCustomer(id);


        txtName.setText(customer.getName());
       txtAddress.setText(customer.getAddress());
       txtSalary.setText(String.valueOf(customer.getSalary()));

    }

    private void SetTextForFieldsWhenItSelectedItems(String id){


        Item item = ItemServiceimpl.getInstance().searchItem(id);


        txtDesc.setText(item.getDescription());
        txtStock.setText(String.valueOf(item.getQty()));
        txtUnitPrice.setText(String.valueOf(item.getPrice()));

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemcode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotals.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadDateAndTime();
        loadCustomerIds();
        loadItemIds();

        comboBoxId.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue!=null){
                SetTextForFieldsWhenItSelected((String) newValue);
            }

        }));

        comboBoxItem.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue!=null){
                SetTextForFieldsWhenItSelectedItems((String) newValue);
            }

        }));
    }
    private void calcNetTotal(){
        Double netTotal=0.0;

        for (Cart tm: cartList){
            netTotal+=tm.getTotal();
        }

        lblNetTotal.setText(netTotal.toString());
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        List<OrderDetails> orderDetailsList=new ArrayList<>();
        cartList.forEach(cart -> {
          orderDetailsList.add(new OrderDetails(
                  txtOrderId.getText(),
                  cart.getItemcode(),
                  cart.getQty(),
                  cart.getUnitPrice()
          ))  ;
        });


        try {
            boolean  isplaceorder = OrderServiceimpl.getInstance().placeorder(
                    new Order(
                            txtOrderId.getText(),
                            lblDate.getText(),
                            comboBoxId.getValue().toString(),
                            orderDetailsList

                    )

            );
            if (isplaceorder){
                new Alert(Alert.AlertType.INFORMATION,"Order placed").show();
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Order Not Placed").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




    }

    public void btnDeleteOnActin(ActionEvent actionEvent) {
        Cart selectedCart = tblOrders.getSelectionModel().getSelectedItem();
        if (selectedCart != null) {
            cartList.remove(selectedCart);
            calcNetTotal();
        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an item to delete!");
            alert.show();
        }
    }
}
