module com.khanfar.dijikstragui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.apache.poi.ooxml;
    requires java.desktop;

    opens com.khanfar.dijikstragui to javafx.fxml;
    exports com.khanfar.dijikstragui;
}