package zn.almacen;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import zn.almacen.presentacion.SistemaAlmacenFx;

@SpringBootApplication
public class AlmacenApplication {
    public static void main(String[] args) {
        //SpringApplication.run(AlmacenApplication.class, args);
        Application.launch(SistemaAlmacenFx.class, args);
    }
}