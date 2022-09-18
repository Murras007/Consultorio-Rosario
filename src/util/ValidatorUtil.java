package util;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    //"[^\\s]+(.*?)\\.(jpg|jpeg|png|gif|JPG|JPEG|PNG|GIF)$"
    //"([^\\s]+(\\.(?i)(jpe?g|png))$)"
    private static Pattern patternImageExtesions;
    private static Matcher matcher;

    public static boolean isValidExtesion(String[] extensions, File file){
        String str = "";
        for (String extension : extensions) {
            str += extension + "|";
        }
        str = str.substring(0, str.length()-1);
        patternImageExtesions = Pattern.compile("([^\\s]+(\\.(?i)("+str+"))$)");
        matcher = patternImageExtesions.matcher(file.toURI().toString());
        return matcher.matches();
    }

    public static boolean isValidExtesion(String[] extensions, String value){
        String str = "";
        for (String extension : extensions) {
            str += extension + "|";
        }
        str = str.substring(0, str.length()-1);
        patternImageExtesions = Pattern.compile("([^\\s]+(\\.(?i)("+str+"))$)");
        matcher = patternImageExtesions.matcher(value);
        return matcher.matches();
    }

    public static void setDangerBorder(Node node){
        node.setStyle(node.getStyle().concat(";-fx-border-color: danger"));
    }

    public static boolean isEmptyTextFieldWithContainer(TextField textField) {
        if (textField.getText().isEmpty()) {
            setDangerBorder(textField.getParent());
            return true;
        }
        return false;
    }

    public static boolean isTextFieldEmpty(TextField textField) {
        if (textField.getText().isEmpty()) {
            setDangerBorder((Node)textField);
            return false;
        }
        return true;
    }

    public static boolean isComboBoxEmpty(ComboBox comboBox) {
        if (comboBox.getItems().size() == 0 || comboBox.getEditor().getText().isEmpty()) {
            setDangerBorder((Node)comboBox);
            return false;
        }
        return true;
    }

    public static boolean isDatePickerEmpty(DatePicker node) {
        if (node.getEditor().getText().isEmpty()) {
            setDangerBorder((Node)node);
            return false;
        }
        return true;
    }

    public static void removeBorderDanger(Node node) {
        node.setStyle(node.getStyle().replace(";-fx-border-color: danger", ""));
    }

}
