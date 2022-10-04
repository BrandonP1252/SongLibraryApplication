module com.perezcalle.songlibrary {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.perezcalle.songlibrary to javafx.fxml;
    exports com.perezcalle.songlibrary;
}