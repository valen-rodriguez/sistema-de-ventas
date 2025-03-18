package zn.almacen;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zn.almacen.presentacion.SistemaDeVentasFx;

@SpringBootApplication
public class AlmacenApplication {
    public static void main(String[] args) {

        Application.launch(SistemaDeVentasFx.class, args);
    }
}