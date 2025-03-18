package zn.almacen.presentacion;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import zn.almacen.AlmacenApplication;

public class SistemaDeVentasFx extends Application {

    private ConfigurableApplicationContext applicationContext;

    public void init(){
        this.applicationContext = new SpringApplicationBuilder(AlmacenApplication.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(AlmacenApplication.class.getResource("/templates/login.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Scene login = new Scene(loader.load());
        stage.setScene(login);
        stage.setTitle("Iniciar sesión");
        stage.show();
    }

    @Override
    public void stop(){
        applicationContext.close();
        Platform.exit();
    }
}
