package zn.almacen;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import zn.almacen.presentacion.SistemaDeVentasFx;

@EnableJpaRepositories("zn.almacen.repositorio")
@SpringBootApplication(scanBasePackages = "zn.almacen")
public class AlmacenApplication {
    public static void main(String[] args){
            Application.launch(SistemaDeVentasFx.class, args);
    }
}