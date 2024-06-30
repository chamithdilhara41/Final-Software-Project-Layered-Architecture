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
import lk.ijse.dao.custom.OrderDAO;
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

    private int getEmployeeCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS employee_count FROM employee";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return resultSet.getInt("employee_count");
        }
        return 0;

    }

    private void setSupplierCount(int supplierCount) {
        lblSupplierCount.setText(String.valueOf(supplierCount));
    }

    private int getSupplierCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS supplier_count FROM supplier";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("supplier_count");
        }
        return 0;
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

    private int getBuyerCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS buyer_count FROM buyer";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("buyer_count");
        }
        return 0;
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

//    private void LineChar(){
//        XYChart.Series series = new XYChart.Series<>();
//        series.getData().add(new XYChart.Data("Monday",8)) ;
//        series.getData().add(new XYChart.Data("TuesDay",10)) ;
//        series.getData().add(new XYChart.Data("WendsDay",15)) ;
//        series.getData().add(new XYChart.Data("ThursDay",5)) ;
//        series.getData().add(new XYChart.Data("Friday",5)) ;
//        series.getData().add(new XYChart.Data("Saturday",9)) ;
//        series.getData().add(new XYChart.Data("Sunday",8)) ;
//        LineChart.getData().addAll(series);
//    }

    private void LineChar() {
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

    public static Map<String, Double> getStocksByDay() {
        Map<String, Double> stocksByDay = new HashMap<>();

        String sql = " SELECT date,SUM(TotalWeight) AS total_weight FROM stock GROUP BY date;";

        try (PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            while (resultSet.next()) {
                String date = resultSet.getString("date");
                double totalWeight = resultSet.getDouble("total_weight");
                stocksByDay.put(date, totalWeight);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stocksByDay;
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
