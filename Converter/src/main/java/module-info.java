module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.context;
    requires rxjavafx;
    requires io.reactivex.rxjava2;
    requires java.desktop;

    opens org.example to javafx.fxml;
    exports org.example;
}
