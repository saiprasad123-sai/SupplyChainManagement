package com.example.supplychainmanagement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class SupplyChainManagement extends Application {

    private Button loginButton;
    Pane bodyPane;

    public static final int width = 700,height = 700,upperLine = 50;
    public static String searchval;

    boolean customerLoggedIn = false;
    String customerEmail = "";

    ProductDetails productdetails = new ProductDetails();
    Label welcomeUser = new Label("");

    private Pane headerBar(){
        Pane topPane = new Pane();
        topPane.setTranslateY(10);
        topPane.setPrefSize(width,upperLine);
        TextField searchbar = new TextField();
        searchbar.setPromptText("Please Search Here");
        searchbar.setTranslateX(100);
        searchval = searchbar.getText();

        loginButton = new Button("login");
        loginButton.setTranslateX(400);
        loginButton.setOnAction(actionEvent -> {
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(loginPage());
        });

        welcomeUser.setText("");
        welcomeUser.setTranslateX(470);
        welcomeUser.setTranslateY(5);

        Button searchButton = new Button("search");
        searchButton.setTranslateX(300);
        searchButton.setOnAction(actionEvent -> {
            String searchval = searchbar.getText();
            bodyPane.getChildren().clear();
            if(!customerLoggedIn)
            bodyPane.getChildren().add(productdetails.getProductsByName(searchval));
            else
                bodyPane.getChildren().addAll(productdetails.getProductsByName(searchval),loginSuccess(customerEmail));
        });



        Button getAllBut = new Button("All Products");
        getAllBut.setTranslateX(550);
        getAllBut.setOnAction(actionEvent -> {
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(productdetails.getAllProducts());
        });

        topPane.getChildren().addAll(searchbar,loginButton,searchButton,welcomeUser);

        return topPane;
    }

    private GridPane loginPage(){
        Label emailLabel = new Label("E-mail:");
        Label passwordLabel  = new Label("Password:");
        TextField emailText = new TextField();
        emailText.setPromptText("Please enter your E-mail");
        PasswordField passwordtext = new PasswordField();
        passwordtext.setPromptText("Please enter your password");

        Button localloginbut = new Button("login");


        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Login Message");
        dialog.setContentText("Login failed! Please enter Valid Email and Password");
        ButtonType buttonType = new ButtonType("Okay" ,ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonType);

        Button resetBut = new Button("reset");
        resetBut.setOnAction(actionEvent -> {
            passwordtext.setText("");
            emailText.setText("");
            welcomeUser.setText("");
        });

        GridPane gridpane = new GridPane();
        gridpane.setVgap(15);
        gridpane.setHgap(15);
        localloginbut.setOnAction(actionEvent -> {
            String etext = emailText.getText();
            String ptext = passwordtext.getText();
            if(etext.isBlank() || ptext.isBlank()){
                //welcomeUser.setText("Please Enter Complete Details");
                dialog.showAndWait();
            }
            else{
                if(login.customerLogin(etext,ptext)){
                    customerLoggedIn = true;
                    customerEmail = etext;
                    //welcomeUser.setText("Welcome "+etext);
                    bodyPane.getChildren().clear();

                    bodyPane.getChildren().add(loginSuccess(etext));

                }
                else{
                    //welcomeUser.setText("Please Enter Valid Email or Password");
//                    passwordtext.setText("");
//                    emailText.setText("");
                    dialog.showAndWait();

                }
            }
        });

        gridpane.setMinSize(bodyPane.getWidth(),bodyPane.getHeight());

        gridpane.setAlignment(Pos.CENTER);

        gridpane.add(emailLabel,0,0);
        gridpane.add(emailText,1,0);
        gridpane.add(passwordLabel,0,1);
        gridpane.add(passwordtext,1,1);
        gridpane.add(localloginbut ,0,2);
        gridpane.add(resetBut,1,2);

        return gridpane;
    }
    private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(width,height+upperLine);

        bodyPane = new Pane();
        bodyPane.setPrefSize(width,height);
        bodyPane.setTranslateY(upperLine);

        //bodyPane.getChildren().addAll(productdetails.getAllProducts());

        root.getChildren().addAll(headerBar(),bodyPane);

        return root;
    }

    private Pane loginSuccess(String email){
        Pane sPane = new Pane();

        Label CustomerMail = new Label();
        CustomerMail.setTranslateX(20);
        CustomerMail.setTranslateY(20);
        CustomerMail.setText("Welcome "+email);

        Button logout = new Button("logOut");
        logout.setTranslateX(550);
        logout.setTranslateY(20);
        logout.setOnAction(actionEvent -> {
            customerLoggedIn = false;
            welcomeUser.setText("");
            bodyPane.getChildren().clear();
            loginPage();
        });

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Order Message");
        dialog.setContentText("Hurray! Product Ordered");
        ButtonType buttonType = new ButtonType("Okay" ,ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonType);

        Dialog<String> dialog1 = new Dialog<>();
        dialog1.setTitle("Order Message");
        dialog1.setContentText("OOPS! Select Product to Order");
        ButtonType buttonType1 = new ButtonType("Okay" ,ButtonBar.ButtonData.OK_DONE);
        dialog1.getDialogPane().getButtonTypes().add(buttonType1);

        Button orderBut = new Button("Order Now");
        orderBut.setTranslateX(400);
        orderBut.setTranslateY(20);
        orderBut.setOnAction(actionEvent -> {
            int pid = productdetails.getProductId();
            if(order.orderProduct(pid,email)==true) {
                //pid = -1;
                dialog.showAndWait();
            }
            else{
                dialog1.showAndWait();
            }
        });


        Button localgetProduct = new Button("AllProducts");
        localgetProduct.setTranslateX(300);
        localgetProduct.setTranslateY(20);
        localgetProduct.setOnAction(actionEvent -> {
            sPane.getChildren().add(productdetails.getAllProducts());
        });
        sPane.getChildren().addAll(logout,CustomerMail,localgetProduct,orderBut);

        return sPane;
    }

    @Override
    public void start(Stage stage) throws IOException {
        //  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Supply Chain Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}