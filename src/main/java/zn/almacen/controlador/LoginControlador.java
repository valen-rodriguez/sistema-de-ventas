package zn.almacen.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zn.almacen.AlmacenApplication;
import zn.almacen.presentacion.SistemaDeVentasFx;
import zn.almacen.servicio.CuentaServicio;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class LoginControlador implements Initializable {

    private static final Logger logger =
            LoggerFactory.getLogger(LoginControlador.class);


    @Autowired
    private CuentaServicio cuentaServicio;

    @FXML
    private TextField emailTxt;

    @FXML
    private PasswordField passTxt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("Iniciando aplicacion");
    }

    public void iniciarSesion(){

        String mail = emailTxt.getText();
        String password = passTxt.getText();

        if (mail.isEmpty() || password.isEmpty()) {
            // verifica si alguno de los campos está vacío
            if (mail.isEmpty()) {
                mostrarMensaje("Error", "Debe proporcionar un correo electrónico");
                emailTxt.requestFocus();
            } else {
                mostrarMensaje("Error", "Debe proporcionar una contraseña");
                passTxt.requestFocus();
            }
        } else {
            // se valida si tienen datos los campos
            var cuenta = cuentaServicio.buscarCuentaPorMail(mail);
            if (cuenta != null) {
                if (password.equals(cuenta.getPassword())) {
                    // Cambiar de ventana a sistema.fxml
                    try {
                        FXMLLoader loader = new FXMLLoader(AlmacenApplication.class.getResource("/templates/sistema.fxml"));
                        loader.setControllerFactory(SistemaDeVentasFx.applicationContext::getBean);
                        Parent root = loader.load();

                        // Obtener el controlador de la ventana del sistema
                        SistemaControlador controlador = loader.getController();

                        // Pasar la cuenta al controlador de la ventana del sistema
                        controlador.setCuenta(cuenta);

                        // Crear una nueva escena y un nuevo Stage y mostrar la ventana
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("Sistema de Ventas");
                        stage.show();

                        // cerrar la ventana actual
                        Stage currentStage = (Stage) emailTxt.getScene().getWindow();
                        currentStage.close();
                    } catch (IOException e) {
                        e.printStackTrace(); // Manejo básico de excepciones
                        mostrarMensaje("Error", "No se pudo cargar la ventana del sistema.");
                    }
                } else {
                    mostrarMensaje("Incorrecto", "Ha ingresado una contraseña incorrecta");
                    passTxt.requestFocus();
                }
            } else {
                mostrarMensaje("Error", "No se encontró una cuenta con ese correo electrónico");
                emailTxt.requestFocus();
            }
        }
    }

    private void mostrarMensaje(String titulo, String mensaje){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
