module com.airline {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.airline.controllers to javafx.fxml;
    exports com.airline.controllers;
    exports com.airline.controllers.Admin.Controllers;
    opens com.airline.controllers.Admin.Controllers to javafx.fxml;
    exports com.airline.controllers.Staff.Controllers;
    opens com.airline.controllers.Staff.Controllers to javafx.fxml;
    exports com.airline.controllers.Client.Controllers;
    opens com.airline.controllers.Client.Controllers to javafx.fxml;
    opens com.airline.entities to javafx.base;

}