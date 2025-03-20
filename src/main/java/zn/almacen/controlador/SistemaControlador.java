package zn.almacen.controlador;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.control.TextField;
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

    private Producto producto;

    //listas
    private final ObservableList<Producto> productoList =
            FXCollections.observableArrayList();

    private final ObservableList<Producto> productoCarritoList =
            FXCollections.observableArrayList();

    private final ObservableList<Cliente> clienteList =
            FXCollections.observableArrayList();

    private final ObservableList<Pedido> pedidoList =
            FXCollections.observableArrayList();


    //servicios
    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private PedidoServicio pedidoServicio;

    @Autowired
    private PedidoProductoServicio pedidoProductoServicio;


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

    //apartado Nueva Venta
    //campos de texto
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

    //tabla carrito
    @FXML
    private TableView<Producto> tablaCarrito;

    @FXML
    private TableColumn<Producto, Integer> codigoProductoCarritoColumna;

    @FXML
    private TableColumn<Producto, String> nombreProductoCarritoColumna;

    @FXML
    private TableColumn<Producto, String> descripcionProductoCarritoColumna;

    @FXML
    private TableColumn<Producto, String> cantidadProductoCarritoColumna;

    @FXML
    private TableColumn<Producto, String> precioProductoCarritoColumna;

    @FXML
    private TableColumn<Producto, String> totalProductoCarritoColumna;

    //apartado Productos
    //tabla
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
        this.producto = new Producto();
        tablaProductos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        cargarTextoProducto();
        tablaCarrito.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnasCarrito();
        cargarProductoTabla();
    }


    //metodos de la ventana nueva venta
    //metodo para abrir la ventana de nueva venta
    public void verTabNuevaVenta(){
        tabPanePrincipal.getSelectionModel().select(tabNuevaVenta);
    }

    //metodo para cargar el producto en los campos de texto al apretar la tecla enter
    private void cargarTextoProducto() {
        codigoTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    Integer codigo = Integer.parseInt(codigoTxt.getText());
                    Producto productoCodigo = productoServicio.buscarProductoPorCodigo(codigo);
                    if (productoCodigo != null) {
                        productoTxt.setText(productoCodigo.getProducto());
                        precioTxt.setText(String.valueOf(productoCodigo.getPrecio()));
                        stockTxt.setText(String.valueOf(productoCodigo.getCantidad()));

                        // Asignar los valores al objeto producto
                        this.producto.setProducto_id(productoCodigo.getProducto_id());
                        this.producto.setProveedor_id(productoCodigo.getProveedor_id());
                        this.producto.setCodigo(productoCodigo.getCodigo());
                        this.producto.setProducto(productoCodigo.getProducto());
                        this.producto.setDescripcion(productoCodigo.getDescripcion());
                        this.producto.setCantidad(productoCodigo.getCantidad());
                        this.producto.setPrecio(productoCodigo.getPrecio());

                        cantidadTxt.requestFocus();
                    } else {
                        mostrarMensaje("Error", "No se encontró un producto con ese código.");
                    }
                } catch (NumberFormatException e) {
                    mostrarMensaje("Error", "Ingrese un código válido.");
                }
            }
        });
    }

    //metodo para cargar el producto en la tabla (ENTER)
    private void cargarProductoTabla(){
        cantidadTxt.setOnKeyPressed(event ->{
            if (event.getCode() == KeyCode.ENTER){
                agregarProductoCarrito();
            }
        });
    }

    //metodo para cargar el producto en la tabla (boton)
    public void agregarProductoCarrito() {
        try {

            //verifioco si el usuario ingreso el codigo dle producto antes de agregar el producto en el carrito
            if (cantidadTxt.getText().isEmpty() || productoTxt.getText().isEmpty()){
                mostrarMensaje("Error", "No hay producto que agregar");
                return;
            }

            Double cantidadIngresadaTxt = Double.parseDouble(cantidadTxt.getText());

            if (cantidadIngresadaTxt <= 0) {
                mostrarMensaje("Error", "Ingrese una cantidad válida.");
                return;
            }

            //guardo el stock en una variable aparte para compararla con la cantidad ingresada por el usuario
            Integer stock = this.producto.getCantidad();

            //valido que haya mas stock que la cantidad ingresada
            if (cantidadIngresadaTxt <= stock) {

                //guardo el producto en un nuevo objeto tipo producto para evitar errores
                var productoCarrito = new Producto();
                productoCarrito.setProducto_id(this.producto.getProducto_id());
                productoCarrito.setProveedor_id(this.producto.getProveedor_id());
                productoCarrito.setCodigo(this.producto.getCodigo());
                productoCarrito.setProducto(this.producto.getProducto());
                productoCarrito.setDescripcion(this.producto.getDescripcion());
                productoCarrito.setCantidad(this.producto.getCantidad());
                productoCarrito.setPrecio(this.producto.getPrecio());
                productoCarrito.setCantidadIngresada(cantidadIngresadaTxt);
                double total = cantidadIngresadaTxt * this.producto.getPrecio();
                productoCarrito.setTotal(total);
                Integer codigo = productoCarrito.getCodigo();

                //valido producto (si esta en la lista o no)
                boolean repetido = false;
                for (Producto producto : productoCarritoList) {
                    if (producto.getCodigo().equals(codigo)) {
                        repetido = true;
                        break;
                    }
                }

                if (!repetido) {
                    productoCarritoList.add(productoCarrito);
                    tablaCarrito.setItems(productoCarritoList);
                    tablaCarrito.refresh();
                } else {
                    mostrarMensaje("Error", "El producto ya está en el carrito");
                }
                limpiarFormulario();
                codigoTxt.requestFocus();
            } else {
                mostrarMensaje("Error", "Ingrese la cantidad");
            }
        } catch (Exception e) {
            mostrarMensaje("Error", "Ingrese un número válido para la cantidad.");
        }
    }

    //metodo configurar columnas de la tabla del carrito
    private void configurarColumnasCarrito(){
        codigoProductoCarritoColumna.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        nombreProductoCarritoColumna.setCellValueFactory(new PropertyValueFactory<>("producto"));
        descripcionProductoCarritoColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        cantidadProductoCarritoColumna.setCellValueFactory(cellData -> {
            Producto producto = cellData.getValue();
            Double cantidadIngresadaDouble = producto.getCantidadIngresada();
            Integer cantidadIngresada = cantidadIngresadaDouble.intValue();
            String cantidadFormateada = String.valueOf(cantidadIngresada);
            return new SimpleStringProperty(cantidadFormateada);
        });
        precioProductoCarritoColumna.setCellValueFactory(new PropertyValueFactory<>("precio"));
        totalProductoCarritoColumna.setCellValueFactory(cellData -> {
                    Producto producto = cellData.getValue();
                    double total = producto.getCantidadIngresada() * producto.getPrecio();
                    String totalFormateado = String.format("%.2f", total);
                    return new SimpleStringProperty(totalFormateado);

        });
    }



    //metodo apartado Productos
    //metodo para abrir la ventana de productos
    public void verTabProductos(){
        tabPanePrincipal.getSelectionModel().select(tabProductos);
        configurarColumnasProductos();
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

    //metodo para listar los productos
    private void listarProductos(){
        productoList.clear();
        productoList.addAll(productoServicio.listarProductos());
        tablaProductos.setItems(productoList);
    }

    //metodo para abrir la ventana de clientes
    public void verTabClientes(){
        tabPanePrincipal.getSelectionModel().select(tabClientes);
    }

    //metodo para abrir la ventana de proveedores
    public void verTabProovedores(){
        tabPanePrincipal.getSelectionModel().select(tabProveedores);
    }

    //metodo para abrir la ventana de pedidos
    public void verTabPedidos(){
        tabPanePrincipal.getSelectionModel().select(tabPedidos);
    }

    //metodo para abrir la ventana de datos de la empresa
    public void verTabDatos(){
        tabPanePrincipal.getSelectionModel().select(tabDatosEmpresa);
    }

    public void limpiarFormulario(){
        codigoTxt.clear();
        productoTxt.clear();
        precioTxt.clear();
        stockTxt.clear();
        cantidadTxt.clear();
    }

    private void mostrarMensaje(String titulo, String mensaje){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


}
