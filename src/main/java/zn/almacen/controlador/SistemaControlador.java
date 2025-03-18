package zn.almacen.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zn.almacen.modelo.Cliente;

import javafx.scene.control.*;
import zn.almacen.modelo.Cuenta;
import zn.almacen.modelo.Pedido;
import zn.almacen.modelo.Producto;
import zn.almacen.servicio.ClienteServicio;
import zn.almacen.servicio.PedidoProductoServicio;
import zn.almacen.servicio.PedidoServicio;
import zn.almacen.servicio.ProductoServicio;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SistemaControlador implements Initializable {

    private static final Logger logger =
            LoggerFactory.getLogger(SistemaControlador.class);

    @Setter
    private Cuenta cuenta;

    private final ObservableList<Producto> productoList =
            FXCollections.observableArrayList();

    private final ObservableList<Cliente> clienteList =
            FXCollections.observableArrayList();

    private final ObservableList<Pedido> pedidoList =
            FXCollections.observableArrayList();

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private PedidoServicio pedidoServicio;

    @Autowired
    private PedidoProductoServicio pedidoProductoServicio;

    //botones navegacion
    @FXML
    private Button btnNuevaVenta;

    @FXML
    private Button btnProuctos;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnProveedores;

    @FXML
    private Button btnPedidos;

    @FXML
    private Button btnDatosEmpresa;

    //tabPane principal
    @FXML
    private TabPane tabPanePrincipal;

    @FXML
    private Tab tabNuevaVenta;

    @FXML
    private Tab tabProductos;

    @FXML
    private Tab tabClientes;

    @FXML
    private Tab tabProveedores;

    @FXML
    private Tab tabPedidos;

    @FXML
    private Tab tabDatosEmpresa;

    //botones apartado Nueva Venta
    @FXML
    private Button btnAgregarAlCarrito;

    @FXML
    private Button btnCrearVenta;

    //campos de texto apartado Nueva Venta
    @FXML
    private TextField codigoTxt;

    @FXML
    private TextField productoTxt;

    @FXML
    private TextField precioTxt;

    @FXML
    private TextField stockTxt;

    @FXML
    private TextField cantidadTxt;

    @FXML
    private TextField dniClienteTxt;

    @FXML
    private TextField nombreClienteTxt;

    @FXML
    private TextField totalTxt;

    //tabla apartado prouctos
    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, Integer> idProductoColumna;

    @FXML
    private TableColumn<Producto, String> codigoProductoColumna;

    @FXML
    private TableColumn<Producto, String> nombreProductoColumna;

    @FXML
    private TableColumn<Producto, String> descripcionProductoColumna;

    @FXML
    private TableColumn<Producto, String> stockProductoColumna;

    @FXML
    private TableColumn<Producto, String> precioProductoColumna;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablaProductos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        logger.info("productoServicio: " + (productoServicio != null ? "Cargado correctamente" : "NULL"));
        configurarColumnasProductos();
    }

    //metodo para abrir la ventana de nueva venta
    public void nuevaVenta(){
        tabPanePrincipal.getSelectionModel().select(tabNuevaVenta);
    }

    public void verTabProductos(){
        tabPanePrincipal.getSelectionModel().select(tabProductos);
        listarProductos();
    }


    //metodo configurar columnas de la tabla de productos
    private void configurarColumnasProductos(){
        idProductoColumna.setCellValueFactory(new PropertyValueFactory<>("producto_id"));
        codigoProductoColumna.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        nombreProductoColumna.setCellValueFactory(new PropertyValueFactory<>("producto"));
        descripcionProductoColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        stockProductoColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        precioProductoColumna.setCellValueFactory(new PropertyValueFactory<>("precio"));
    }

    //metodo para listar los productos en la tabla productos
    private void listarProductos(){
        productoList.clear();
        productoList.addAll(productoServicio.listarProductos());
        tablaProductos.setItems(productoList);
    }
}
