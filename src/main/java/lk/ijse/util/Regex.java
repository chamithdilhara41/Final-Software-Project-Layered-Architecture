package lk.ijse.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static boolean isTextFieldValid(TextField textField, String text){
        String filed = "";

        switch (textField){
            case ID:
                filed = "^([A-Z][0-9]{3})$";
                break;
            case NAME:
                filed = "(^[a-zA-Z.][a-zA-Z.\\s]{0,20}[a-zA-Z.]$)";
                break;
            case EMAIL:
                filed = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;
            case ADDRESS:
                filed = "^([A-z0-9]|[-/,.@+]|\\s){4,}$";
                break;
            case CONTACT:
                filed = "^(\\+94|0)([1-9]\\d{1})(\\d{7})$";
                break;
            case SALARY:
                filed = "^([0-9]){1,}[.]([0-9]){1,}$";
                break;
            case DATE:
                filed = "^\\d{4}-\\d{2}-\\d{2}$";
                break;
            case GENDER:
                filed = "^(Male|Female|male|female|Other)$";
                break;
            case ACCOUNTNo:
                filed = "^\\d+$";
                break;
            case AMOUNT:
                filed = "^\\d+(\\.\\d+)?$";
                break;
            case VEHICLENo:
                filed = "^([a-zA-Z]{1,3}|((?!0*-)[0-9]{1,3}))-[0-9]{4}(?<!0{4})$";
                break;
            case VEHICLETYPE:
                filed = "^[A-Za-z\\s]+$";
                break;
            case PASSWORD:
                filed = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
                break;
            case USERNAME:
                filed = "^[a-zA-Z0-9_]{3,20}$";
                break;
            case WEIGHT:
                filed = "^[1-9]\\d*(\\.\\d+)?$";
                break;
            case SID:
                filed = "^([Ss][Tt][0-9]{3})$";
                break;

        }

        Pattern pattern = Pattern.compile(filed);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean setTextColorLogin(TextField location, javafx.scene.control.TextField textField){
        if (Regex.isTextFieldValid(location, textField.getText())){
            textField.setStyle("-fx-border-color:  #3bf63b; -fx-background-radius: 10 10 10 10; -fx-border-radius: 10 10 10 10;");
            return true;
        }else {
            textField.setStyle("-fx-border-color: #f33232;-fx-background-radius: 10 10 10 10; -fx-border-radius: 10 10 10 10;");
            return false;
        }
    }

    public static boolean setTextColor(TextField location, javafx.scene.control.TextField textField){
        if (Regex.isTextFieldValid(location, textField.getText())){
            textField.setStyle("-fx-border-color: #3bf63b; -fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15;");
            return true;
        }else {
            textField.setStyle("-fx-border-color: #f33232; -fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15;");
            return false;
        }
    }
}
