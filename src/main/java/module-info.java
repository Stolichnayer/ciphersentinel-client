module com.ciphersentinel.ciphersentinel
{
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.ciphersentinel.client to javafx.fxml;
    exports com.ciphersentinel.client;
}