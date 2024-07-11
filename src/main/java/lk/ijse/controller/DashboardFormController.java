package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.OrderBO;
import lk.ijse.bo.custom.impl.OrderBOImpl;
import lk.ijse.dao.SuperDAO;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.QueryDAO;
import lk.ijse.dao.custom.impl.QueryDAOImpl;
import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javafx.scene.chart.LineChart;
import lk.ijse.tdm.OrderBuyerTm;
import lk.ijse.dao.custom.impl.OrderDAOImpl;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class DashboardFormController {

    private int supplierCount;

    private int employeeCount;

    private int buyerCount;


    @FXML
    private TableView<OrderBuyerTm> tblOrderBuyer;

    @FXML
    private TableColumn<?, ?> colOrderID;

    @FXML
    private TableColumn<?, ?> colBuyerName;

    @FXML
    private ImageView wishImageView;

    @FXML
    private Label lblGreetings;

    @FXML
    private Label lblEmployeeCount;

    @FXML
    private Label lblSupplierCount;

    @FXML
    private Label lblBuyerCount;

    @FXML
    private LineChart<?, ?> LineChart;

    //dependency injection
    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    QueryDAO queryDAO = new QueryDAOImpl();

    public void initialize() throws SQLException, ClassNotFoundException {
        LineChar();
        setGreeting();
        animateLabelTyping();
        getAllOrderBuyerNames();
        setCellValueFactoryOrderBuyer();
        try {
            supplierCount = getSupplierCount();
            employeeCount = getEmployeeCount();
            buyerCount = getBuyerCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setSupplierCount(supplierCount);
        setEmployeeCount(employeeCount);
        setBuyerCount(buyerCount);
    }

    private void setCellValueFactoryOrderBuyer() {
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colBuyerName.setCellValueFactory(new PropertyValueFactory<>("buyerName"));
    }

    private void setEmployeeCount(int employeeCount) {
        lblEmployeeCount.setText(String.valueOf(employeeCount));
    }

    private int getEmployeeCount() throws SQLException, ClassNotFoundException {

        return queryDAO.employeeCount();
    }

    private void setSupplierCount(int supplierCount) {
        lblSupplierCount.setText(String.valueOf(supplierCount));
    }

    private int getSupplierCount() throws SQLException, ClassNotFoundException {

        return queryDAO.supplierCount();
    }

    public void btnEmployeeListOnAction(javafx.event.ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/EmployeeList.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data,DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }
    public void btnSupplierListOnAction(javafx.event.ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/SupplierList.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data,DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }

    private int getBuyerCount() throws SQLException, ClassNotFoundException {
        return queryDAO.buyerCount();
    }

    private void getAllOrderBuyerNames() throws SQLException, ClassNotFoundException {
        ObservableList<OrderBuyerTm> obList = FXCollections.observableArrayList();
        List<OrderBuyerTm> ordersList = orderBO.getAllOrderBuyerNamesOrder();

        for ( OrderBuyerTm order: ordersList){
            obList.add(new OrderBuyerTm(
                    order.getOrderID(),
                    order.getBuyerName()
            ));
        }
        tblOrderBuyer.setItems(obList);
    }

    private void setBuyerCount(int buyerCount) {
        lblBuyerCount.setText(String.valueOf(buyerCount));
    }

   /* private void LineChar(){
        XYChart.Series series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data("Monday",8)) ;
        series.getData().add(new XYChart.Data("TuesDay",10)) ;
        series.getData().add(new XYChart.Data("WendsDay",15)) ;
        series.getData().add(new XYChart.Data("ThursDay",5)) ;
        series.getData().add(new XYChart.Data("Friday",5)) ;
        series.getData().add(new XYChart.Data("Saturday",9)) ;
        series.getData().add(new XYChart.Data("Sunday",8)) ;
        LineChart.getData().addAll(series);
    }*/

    private void LineChar() throws SQLException, ClassNotFoundException {
        XYChart.Series series = new XYChart.Series();

        Map<String, Double> stocksByDay = getStocksByDay();

        for (Map.Entry<String, Double> entry : stocksByDay.entrySet()) {
            series.getData().add(
                    new XYChart.Data(entry.getKey(), entry.getValue()
                    ));
        }

        LineChart.getData().add(series);
        LineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
    }

    public Map<String, Double> getStocksByDay() throws SQLException, ClassNotFoundException {
        return queryDAO.stocksByDate();
    }


    public void setGreeting() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int hours = c.get(Calendar.HOUR_OF_DAY);

        // Set image and label based on the time of the day
        if (hours >= 0 && hours < 12) {
            lblGreetings.setText("Good Morning !!!");
            wishImageView.setImage(new Image(DashboardFormController.class.getResourceAsStream("/images/morning.png")));
        } else if (hours >= 12 && hours < 18) {
            lblGreetings.setText("Good Afternoon !!!");
            wishImageView.setImage(new Image(DashboardFormController.class.getResourceAsStream("/images/afternoon.png")));
        } else if (hours >= 18 && hours < 22) {
            lblGreetings.setText("Good Evening !!!");
            wishImageView.setImage(new Image(DashboardFormController.class.getResourceAsStream("/images/afternoon.png")));
        } else {
            lblGreetings.setText("Good Night !!!");
            wishImageView.setImage(new Image(DashboardFormController.class.getResourceAsStream("/images/night.png")));
        }
    }

    private void animateLabelTyping() {
        String loginText = lblGreetings.getText();
        int animationDuration = 250;

        lblGreetings.setText("");

        Timeline typingAnimation = new Timeline();


        for (int i = 0; i <= loginText.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration * i), event -> {
                lblGreetings.setText(loginText.substring(0, finalI)); // Update label text with substring
            });
            typingAnimation.getKeyFrames().add(keyFrame);
        }

        typingAnimation.play();
    }

}
