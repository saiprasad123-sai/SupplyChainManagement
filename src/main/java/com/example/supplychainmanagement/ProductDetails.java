package com.example.supplychainmanagement;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import static com.example.supplychainmanagement.SupplyChainManagement.searchval;

public class ProductDetails {
    TableView<Product> productTable = new TableView<>();
    public Pane getProductsByName(String searchText){
        TableView<Product> table = new TableView<Product>();
        TableColumn idCol = new TableColumn("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn priceCol = new TableColumn("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn quantityCol = new TableColumn("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));



        table.setItems(Product.getProductsByname(searchText));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(idCol,nameCol,priceCol,quantityCol);
        table.setPrefSize(SupplyChainManagement.width-40,SupplyChainManagement.height-70);

        productTable = table;
        Pane tpane = new Pane();
        tpane.getChildren().add(table);
        tpane.setPrefSize(SupplyChainManagement.width,SupplyChainManagement.height);
        tpane.setTranslateX(20);
        tpane.setTranslateY(60);

        return tpane;
    }
    public Pane getAllProducts(){
        TableView<Product> table = new TableView<Product>();
        TableColumn idCol = new TableColumn("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn priceCol = new TableColumn("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn quantityCol = new TableColumn("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));



        table.setItems(Product.getAllProducts());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(idCol,nameCol,priceCol,quantityCol);
        table.setPrefSize(SupplyChainManagement.width-40,SupplyChainManagement.height-70);


        productTable = table;
        Pane tpane = new Pane();
        tpane.setPrefSize(SupplyChainManagement.width,SupplyChainManagement.height);
        tpane.getChildren().add(table);
        tpane.setTranslateX(20);
        tpane.setTranslateY(60);

        return tpane;
    }
    public int getProductId(){

        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if(selectedProduct!=null){
            int a =   selectedProduct.getId();
            productTable.getSelectionModel().clearSelection();
            return a;
        }
        return -1;
    }
}