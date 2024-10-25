package controller;

import dto.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.ServiceFactory;
import service.custom.ProductService;
import util.ServiceType;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductsFormController implements Initializable {

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private TableColumn<?, ?> colSupplier;

    @FXML
    private Label lblId;

    @FXML
    private TableView<Product> tblProduct;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSize;

    @FXML
    private TextField txtSupplier;

    ProductService service = ServiceFactory.getInstance().getServiceType(ServiceType.PRODUCT);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Product product = new Product(
                lblId.getText(),
                txtName.getText(),
                cmbCategory.getValue(),
                Integer.parseInt(txtSize.getText()),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText()),
                txtSupplier.getText()
        );
        if (service.addProduct(product)) {
            new Alert(Alert.AlertType.INFORMATION, "Product added Successfully!").show();
            lblId.setText(service.generateId());
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Add Product!").show();
        }
        loadTable();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (service.deleteProduct(lblId.getText())) {
            new Alert(Alert.AlertType.INFORMATION, "Product Deleted Successfully").show();
            lblId.setText(service.generateId());
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Delete Product!").show();
        }
        loadTable();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Product product = new Product(
                lblId.getText(),
                txtName.getText(),
                cmbCategory.getValue(),
                Integer.parseInt(txtSize.getText()),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText()),
                txtSupplier.getText()
        );
        if (service.updateProduct(product)) {
            new Alert(Alert.AlertType.INFORMATION, "Product Updated Successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Update Product!").show();
        }
        loadTable();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblId.setText(service.generateId());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        ObservableList<String> categoryList = FXCollections.observableArrayList();
        categoryList.add("Men");
        categoryList.add("Women");
        categoryList.add("Kids");
        cmbCategory.setItems(categoryList);
        tblProduct.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) ->
        {
            if (null != newValue) {
                setTextToValues(newValue);
            }
        }));
        loadTable();

    }

    private void setTextToValues(Product newValue) {
        lblId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        cmbCategory.setValue(newValue.getCategory());
        txtSize.setText((newValue.getSize()).toString());
        txtPrice.setText((newValue.getPrice()).toString());
        txtQty.setText((newValue.getQty()).toString());
        txtSupplier.setText(newValue.getSupplier());
    }

    private void loadTable() {
        try{
            ObservableList<Product> productsList = service.getAll();
            tblProduct.setItems(productsList);
        }catch (NullPointerException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
