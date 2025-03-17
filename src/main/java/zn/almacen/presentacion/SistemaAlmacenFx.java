package zn.almacen.presentacion;

import ch.qos.logback.core.util.Loader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import zn.almacen.AlmacenApplication;

public class SistemaAlmacenFx extends Application {

    private ConfigurableApplicationContext applicationContext;



//   public static void main(String[] args) {
//        launch(args);
//    }

    public void init(){
        this.applicationContext = new SpringApplicationBuilder(AlmacenApplication.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(AlmacenApplication.class.getResource("/templates/login.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Scene escena = new Scene(loader.load());
        stage.setScene(escena);
        stage.show();
    }

    @Override
    public void stop(){
        applicationContext.close();
        Platform.exit();
    }
}
