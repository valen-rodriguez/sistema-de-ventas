package zn.almacen.controlador;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.control.TextField;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zn.almacen.modelo.*;

import javafx.scene.control.*;
import zn.almacen.servicio.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.Optional;


@Component
public class SistemaControlador implements Initializable {

    @Setter
    private Cuenta cuenta;

    //-------------------------------- CLASES --------------------------------//

    private Cliente cliente;

    private Producto producto;

    private Pedido pedido;

    //-------------------------------- LISTAS --------------------------------//

    private final ObservableList<Producto> productoList =
            FXCollections.observableArrayList();

    private final ObservableList<Producto> productoCarritoList =
            FXCollections.observableArrayList();

    private final ObservableList<String> nombreProveedorList =
            FXCollections.observableArrayList();

    private final ObservableList<Proveedor> proveedorList =
            FXCollections.observableArrayList();

    private final ObservableList<Cliente> clienteList =
            FXCollections.observableArrayList();

    private final ObservableList<Pedido> pedidoList =
            FXCollections.observableArrayList();

    private final ObservableList<Pedido> pedidoFechaList =
            FXCollections.observableArrayList();

    //-------------------------------- SERVICIOS --------------------------------//

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private PedidoServicio pedidoServicio;

    @Autowired
    private PedidoProductoServicio pedidoProductoServicio;

    @Autowired
    private DatosEmpresaServicio datosEmpresaServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Autowired
    private CuentaServicio cuentaServicio;

    //-------------------------------- TAB-PANE PRINCIPAL --------------------------------//

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

    //-------------------------------- APARTADO NUEVA VENTA --------------------------------//

    //-------- VARIABLES --------//

    private double totalPagar;

    private double montoAbonado;

    private double vuelto;

    //-------- CAMPOS DE TEXTO --------//

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

    @FXML
    private TextField montoAbonadoTxt;

    @FXML
    private TextField vueltoTxt;

    //-------- TABLA CARRITO --------//

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

    //-------------------------------- APARTADO PRODUCTOS --------------------------------//

    //-------- TABLA PRODUCTOS --------//

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, Integer> idProductoColumna;

    @FXML
    private TableColumn<Producto, String> codigoProductoColumna;

    @FXML
    private TableColumn<Producto, String> nombreProductoColumna;

    @FXML
    private TableColumn<Producto, String> proveedorProductoColumna;

    @FXML
    private TableColumn<Producto, String> descripcionProductoColumna;

    @FXML
    private TableColumn<Producto, String> stockProductoColumna;

    @FXML
    private TableColumn<Producto, String> precioProductoColumna;

    //-------- CAMPOS DE TEXTO --------//

    @FXML
    private TextField codigoProductoTxt;

    @FXML
    private TextField nombreProductoTxt;

    @FXML
    private TextField precioProductoTxt;

    @FXML
    private TextField stockProductoTxt;

    @FXML
    private TextField descripcionProductoTxt;

    @FXML
    private TextField codigoProductoBuscarTxt;
    //-------- COMBO BOX PROVEEDOR --------//

    @FXML
    private ComboBox comboBoxProveedor;

    //-------------------------------- APARTADO CLIENTES --------------------------------//

    //-------- TABLA CLIENTES --------//

    @FXML
    private TableView<Cliente> tablaClientes;

    @FXML
    private TableColumn<Cliente, Integer> idClienteColumna;

    @FXML
    private TableColumn<Cliente, String> nombreClienteColumna;

    @FXML
    private TableColumn<Cliente, String> apellidoClienteColumna;

    @FXML
    private TableColumn<Cliente, Integer> dniClienteColumna;

    @FXML
    private TableColumn<Cliente, Integer> telefonoClienteColumna;

    @FXML
    private TableColumn<Cliente, String> direccionClienteColumna;

    @FXML
    private TableColumn<Cliente, String> razonSocialClienteColumna;

    //-------- CAMPOS DE TEXTO --------//

    @FXML
    private TextField nombreTabClienteTxt;

    @FXML
    private TextField apellidoClienteTxt;

    @FXML
    private TextField dniTabClienteTxt;

    @FXML
    private TextField telefonoClienteTxt;

    @FXML
    private TextField direccionClienteTxt;

    @FXML
    private TextField razonSocialClienteTxt;

    @FXML
    private TextField dniClienteBuscarTxt;

    //-------------------------------- APARTADO PROVEEDORES --------------------------------//

    //-------- TABLA PROVEEDORES --------//

    @FXML
    private TableView<Proveedor> tablaProveedores;

    @FXML
    private TableColumn<Proveedor, Integer> idProveedorColumna;

    @FXML
    private TableColumn<Proveedor, Integer> rucProveedorColumna;

    @FXML
    private TableColumn<Proveedor, String> nombreProveedorColumna;

    @FXML
    private TableColumn<Proveedor, Integer> telefonoProveedorColumna;

    @FXML
    private TableColumn<Proveedor, String> direccionProveedorColumna;

    @FXML
    private TableColumn<Proveedor, String> razonSocialProveedorColumna;

    //-------- CAMPOS DE TEXTO --------//

    @FXML
    private TextField rucTxt;

    @FXML
    private TextField nombreProveedorTxt;

    @FXML
    private TextField telefonoProveedorTxt;

    @FXML
    private TextField direccionProveedorTxt;

    @FXML
    private TextField razonSocialProveedorTxt;

    @FXML
    private TextField rucBuscarTxt;

    //-------------------------------- APARTADO PRODUCTOS --------------------------------//

    //-------- TABLA PRODUCTOS --------//

    @FXML
    private TableView<Pedido> tablaPedidos;

    @FXML
    private TableColumn<Pedido, Integer> idPedidoColumna;

    @FXML
    private TableColumn<Pedido, String> clientePedidoColumna;

    @FXML
    private TableColumn<Pedido, String> vendedorPedidoColumna;

    @FXML
    private TableColumn<Pedido, String> fechaPedidoColumna;

    @FXML
    private TableColumn<Pedido, String> formaDePagoPedidoColumna;

    @FXML
    private TableColumn<Pedido, String> precioPedidoColumna;

    //-------- CAMPOS DE TEXTO --------//

    @FXML
    private TextField fechaBuscarTxt;

    @FXML
    private TextField mercadoPagoTxt;

    @FXML
    private TextField tarjetaTxt;

    @FXML
    private TextField efectivoTxt;

    @FXML
    private TextField totalDiaTxt;

    @FXML
    private TextField idPedidoBuscar;

    //-------------------------------- APARTADO DATOS --------------------------------//

    //-------- CAMPOS DE TEXTO --------//

    @FXML
    private TextField rucEmpresaTxt;

    @FXML
    private TextField nombreEmpresaTxt;

    @FXML
    private TextField direccionEmpresaTxt;

    @FXML
    private TextField telefonoEmpresaTxt;

    @FXML
    private TextField razonSocialEmpresaTxt;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.producto = new Producto();
        this.cliente = new Cliente();
        this.cliente.setCliente_id(0);
        this.pedido = new Pedido();

        //inicialización de la tabla carrito en el apartado de nueva venta
        tablaCarrito.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        cargarTextoProducto();
        configurarColumnasCarrito();
        cargarProductoTabla();
        calcularVuelto();
        buscarClienteVenta();

        //inicialización de la tabla productos en el apartado de productos
        tablaProductos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnasProductos();
        apretarEnterPasarFormularioProducto();

        //inicialización de la tabla cliente en el apartado de clientes
        tablaClientes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnasClientes();
        apretarEnterPasarFormularioCliente();
        buscarCliente();

        //inicialización de la tabla proveedores en el apartado de proveedores
        tablaProveedores.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnasProveedores();
        apretarEnterPasarFormularioProveedor();
        buscarProveedor();

        //inicialización de la tabla pedidos en el apartado de pedidos
        tablaPedidos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnasPedidos();
        buscarPedidoFecha();
        buscarPedidoId();

        //apartado datos de la empresa
        apretarEnterPasarFormularioDatos();
    }

    //-------------------------------- MÉTODOS DEL APARTADO NUEVA VENTA --------------------------------//

    //metodo para abrir la ventana de nueva venta
    public void verTabNuevaVenta() {
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

                        //asignar los valores al objeto producto
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
    private void cargarProductoTabla() {
        cantidadTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                agregarProductoCarrito();
            }
        });
    }

    //metodo para cargar el producto en la tabla (botón)
    public void agregarProductoCarrito() {
        try {

            //verifico si el usuario ingreso el codigo dle producto antes de agregar el producto en el carrito
            if (cantidadTxt.getText().isEmpty()) {
                mostrarMensaje("Error", "Ingrese la cantidad.");
                return;
            } else if (productoTxt.getText().isEmpty()) {
                mostrarMensaje("Error", "No ha ingresado ningún producto.");
            }

            Double cantidadIngresadaTxt = Double.parseDouble(cantidadTxt.getText());

            if (cantidadIngresadaTxt <= 0) {
                mostrarMensaje("Error", "Ingrese una cantidad válida.");
                return;
            }

            //guardo el stock en una variable aparte para compararla con la cantidad ingresada por el usuario
            Integer stock = this.producto.getCantidad();

            //valido que haya más stock que la cantidad ingresada
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
                this.producto.setTotal(total);

                Integer codigo = productoCarrito.getCodigo();
                //valido producto (si está en la lista o no)
                var repetido = false;
                Producto productoExistente = null;
                for (Producto producto : productoCarritoList) {
                    if (producto.getCodigo().equals(codigo)) {
                        repetido = true;
                        productoExistente = producto;
                        break;
                    }
                }

                if (!repetido) {
                    productoCarritoList.add(productoCarrito);
                } else {
                    //si el producto ya está en la lista, actualizo la cantidad y el total
                    double nuevaCantidad = cantidadIngresadaTxt;
                    productoExistente.setCantidadIngresada(nuevaCantidad);
                    productoExistente.setTotal(nuevaCantidad * productoExistente.getPrecio());
                }

                //actualizo la tabla y el total a pagar
                tablaCarrito.setItems(productoCarritoList);
                tablaCarrito.refresh();
                totalAPagar();

                //limpio el formulario
                limpiarFormularioProducto();
                codigoTxt.requestFocus();
            } else {
                mostrarMensaje("Error", "No hay suficiente stock.");
            }
        } catch (Exception e) {
            mostrarMensaje("Error", "Ingrese un número válido para la cantidad.");
        }
    }

    //metodo para cargar el producto en el formulario
    public void cargarProductoCarritoAFormulario() {
        var producto = tablaCarrito.getSelectionModel().getSelectedItem();
        if (producto != null) {
            codigoTxt.setText(String.valueOf(producto.getCodigo()));
            codigoTxt.setEditable(false);
            productoTxt.setText(producto.getProducto());
            precioTxt.setText(String.valueOf(producto.getPrecio()));
            stockTxt.setText(String.valueOf(producto.getCantidad()));
            cantidadTxt.setText(String.valueOf(producto.getCantidadIngresada()));
        }
    }

    //metodo configurar columnas de la tabla del carrito
    private void configurarColumnasCarrito() {
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

    //metodo buscar cliente por dni (botón)
    public void btnBuscarClienteVenta() {
        try {
            Integer dni = Integer.parseInt(dniClienteTxt.getText());
            Cliente clienteDni = clienteServicio.buscarClientePorDni(dni);
            if (clienteDni != null) {
                nombreClienteTxt.setText(clienteDni.getNombre() + " " + clienteDni.getApellido());

                //asignar valores al objeto cliente
                this.cliente.setCliente_id(clienteDni.getCliente_id());
                this.cliente.setNombre(clienteDni.getNombre());
                this.cliente.setApellido(clienteDni.getApellido());
                this.cliente.setDni(clienteDni.getDni());
                this.cliente.setTelefono(clienteDni.getTelefono());
                this.cliente.setDireccion(clienteDni.getDireccion());
                this.cliente.setRazon_social(clienteDni.getRazon_social());
            } else {
                mostrarMensaje("Error", "No se encontró un cliente con ese DNI.");
                dniClienteTxt.requestFocus();
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Error", "Ingrese un DNI valido.");
            dniClienteTxt.requestFocus();
        }
    }

    //metodo buscar cliente por dni (ENTER)
    private void buscarClienteVenta() {
        dniClienteTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnBuscarClienteVenta();
            }
        });
    }

    //metodo para calcular el total a pagar
    private void totalAPagar() {
        this.totalPagar = 0;
        for (Producto producto : productoCarritoList) {
            this.totalPagar = this.totalPagar + producto.getTotal();
        }
        String totalFormateado = String.format("%.2f", this.totalPagar);
        totalTxt.setText(totalFormateado);
    }

    //metodo para crear una venta
    public void crearVenta() {
        if (totalTxt.getText().isEmpty() || this.totalPagar == 0) {
            mostrarMensaje("Error", "Ingrese al menos un producto.");
        } else {
            //crea el pedido
            Pedido pedidoNuevo = new Pedido();
            if (!nombreClienteTxt.getText().isEmpty()) {
                pedidoNuevo.setCliente_id(this.cliente.getCliente_id());
            }
            pedidoNuevo.setCuenta_id(this.cuenta.getCuenta_id());
            pedidoNuevo.setPrecio_total(this.totalPagar);
            pedidoNuevo.setFecha(LocalDateTime.now());

            //verifico que metodo de pago utiliza
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Método de pago");
            alert.setContentText("¿Cuál es el método de pago?");

            ButtonType btnEfectivo = new ButtonType("Efectivo");
            ButtonType btnMercadoPago = new ButtonType("Mercado Pago");
            ButtonType btnTarjeta = new ButtonType("Tarjeta");
            alert.getButtonTypes().setAll(btnEfectivo, btnMercadoPago, btnTarjeta);

            Optional<ButtonType> resultado = alert.showAndWait();

            String formaDePago = "Efectivo";
            if (resultado.isPresent() && resultado.get() == btnEfectivo) {
                formaDePago = "Efectivo";
            }if (resultado.isPresent() && resultado.get() == btnMercadoPago){
                formaDePago = "Mercado Pago";
            }if (resultado.isPresent() && resultado.get() == btnTarjeta){
                formaDePago = "Tarjeta";
            }

            pedidoNuevo.setForma_de_pago(formaDePago);
            pedidoServicio.agregarPedido(pedidoNuevo);
            this.pedido = pedidoNuevo;

            //recorre lista de productos en el carrito
            for (Producto producto : productoCarritoList) {
                //guarda el pedidoProducto en la base de datos
                PedidoProducto pedidoProducto = new PedidoProducto();
                pedidoProducto.setProductoId(producto.getProducto_id());
                pedidoProducto.setPedido_Id(pedidoNuevo.getPedido_id());
                pedidoProducto.setCantidad(producto.getCantidadIngresada().intValue());
                pedidoProductoServicio.agregarPedidoProducto(pedidoProducto);

                //asigna el nuevo stock a los productos
                double cantidadNueva = producto.getCantidad() - producto.getCantidadIngresada();
                producto.setCantidad((int) cantidadNueva);
                productoServicio.agregarProducto(producto);
            }


            mostrarMensaje("Información", "Se ha creado una nueva venta.");
            pdf();

            //limpiar la tabla del carrito
            productoCarritoList.clear();
            tablaCarrito.setItems(productoCarritoList);
            tablaCarrito.refresh();

            //limpiar formulario del cliente
            dniClienteTxt.clear();
            nombreClienteTxt.clear();

            //limpiar formulario total a pagar y resetear la variable a 0
            totalTxt.clear();
            this.totalPagar = 0;

            limpiarFormulariosTabVentas();

            //elimino el cliente en caso de que haya uno
            this.cliente = null;

            codigoTxt.requestFocus();
        }
    }

    //metodo para calcular el vuelto en efectivo de una venta
    private void calcularVuelto() {
        montoAbonadoTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (montoAbonadoTxt.getText().isEmpty()) {
                    mostrarMensaje("Error", "Ingrese el monto abonado.");
                } else {
                    try {
                        //veo si puso , en vez de . para los decimales y lo cambio en caso de ser necesario
                        String montoAbonadoStr = montoAbonadoTxt.getText();
                        if (montoAbonadoStr.contains(",")) {
                            montoAbonadoStr = montoAbonadoStr.replace(",", ".");
                        }
                        this.montoAbonado = Double.parseDouble(montoAbonadoStr);
                        montoAbonadoTxt.setText("$" + montoAbonadoStr);

                        if (this.montoAbonado < this.totalPagar) {
                            mostrarMensaje("Error", "El monto abonado es menor al total del pedido.");
                        } else {
                            this.vuelto = this.montoAbonado - this.totalPagar;
                            //formateo el vuelto a solo 2 decimales
                            String vueltoFormateado = String.format("%.2f", this.vuelto);
                            vueltoTxt.setText("$" + vueltoFormateado);
                        }
                    } catch (Exception e) {
                        mostrarMensaje("Error", "Ingrese un monto válido.");
                    }
                }
            }
        });
    }

    //metodo para limpiar el formulario del producto
    public void limpiarFormularioProducto() {
        codigoTxt.clear();
        productoTxt.clear();
        precioTxt.clear();
        stockTxt.clear();
        cantidadTxt.clear();
        codigoTxt.setEditable(true);
    }

    //metodo para limpiar todos los formularios
    public void limpiarFormulariosTabVentas() {
        limpiarFormularioProducto();
        //limpiar formulario del cliente
        dniClienteTxt.clear();
        nombreClienteTxt.clear();
        //limpiar formulario del total
        totalTxt.clear();
        montoAbonadoTxt.clear();
        vueltoTxt.clear();
    }

    //metodo eliminar producto carrito
    public void eliminarProductoCarrito() {
        var producto = tablaCarrito.getSelectionModel().getSelectedItem();
        if (producto != null) {
            productoCarritoList.remove(producto);
            tablaCarrito.setItems(productoCarritoList);
            tablaCarrito.refresh();
            totalAPagar();
        } else {
            mostrarMensaje("Error", "Debe seleccionar un producto.");
        }
    }

    //metodo recibo en pdf
    private void pdf() {
        try {
            int id = this.pedido.getPedido_id();

            File file = new File("src/main/resources/zn/pdf/venta"+id+".pdf");
            FileOutputStream archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);

            doc.open();

            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] ColumnaEncabezado = new float[]{20f, 30f, 70f, 40};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            // Logo
            Image img = Image.getInstance("src/main/resources/templates/multimedia/tuLogo.png");
            Encabezado.addCell(img);

            //fuentes
            Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
            Font fuenteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLUE);
            Font fuenteNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

            //titulo
            Paragraph titulo = new Paragraph("RECIBO", fuenteTitulo);

            //datos del vendedor
            Paragraph datosVendedor = new Paragraph("Vendedor:\n", fuenteNormal);
            datosVendedor.setAlignment(Element.ALIGN_RIGHT);
            String nombreVendedor = this.cuenta.getNombre() + " " + this.cuenta.getApellido();
            datosVendedor.add(nombreVendedor);

            Encabezado.addCell("");
            Encabezado.addCell(titulo);
            Encabezado.addCell(datosVendedor);
            doc.add(Encabezado);

            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(" "));

            //datos del cliente (si hay)
            if (this.cliente.getCliente_id() != 0) {
                Paragraph datosCliente = new Paragraph("Datos del cliente", fuenteSubtitulo);
                datosCliente.setAlignment(Element.ALIGN_LEFT);
                doc.add(datosCliente);

                String nombreCliente = this.cliente.getNombre() + " " + this.cliente.getApellido();
                String dni = String.valueOf(this.cliente.getDni());
                String telefono = String.valueOf(this.cliente.getTelefono());
                String direccion = this.cliente.getDireccion();
                String razonSocial = this.cliente.getRazon_social();

                Paragraph infoCliente = new Paragraph(
                        "Nombre: " + nombreCliente + "\n" +
                                "DNI: " + dni + "\n" +
                                "Teléfono: " + telefono + "\n" +
                                "Dirección: " + direccion + "\n" +
                                "Razón social: " + razonSocial + "\n"
                );
                infoCliente.setAlignment(Element.ALIGN_LEFT);
                doc.add(infoCliente);
                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));
            }


            //tabla de productos
            PdfPTable tablaPdf = new PdfPTable(3);
            tablaPdf.setWidthPercentage(100);

            //encabezado de la tabla
            agregarCelda(tablaPdf, "Producto", fuenteSubtitulo);
            agregarCelda(tablaPdf, "Cantidad", fuenteSubtitulo);
            agregarCelda(tablaPdf, "Total", fuenteSubtitulo);

            //cargar productos en la tabla
            for (Producto producto : productoCarritoList) {
                String nombreProducto = producto.getProducto();
                String cantidadIngresada = String.valueOf(producto.getCantidadIngresada());
                String total = String.valueOf(producto.getTotal());

                agregarFilaProducto(tablaPdf, nombreProducto, cantidadIngresada, total, fuenteNormal);

            }
            tablaPdf.setHorizontalAlignment(Element.ALIGN_CENTER);
            doc.add(tablaPdf);

            doc.add(new Paragraph(" "));

            //total
            Paragraph total = new Paragraph("TOTAL: $" + this.totalPagar, fuenteSubtitulo);
            doc.add(total);

            //factura y fecha;
            String fechaCompleta = String.valueOf(this.pedido.getFecha());
            String fechaFormateada = fechaCompleta.substring(0, 10);
            Paragraph fecha = new Paragraph("Factura: " + this.pedido.getPedido_id() + "\nFecha: " + fechaFormateada, fuenteNormal);
            fecha.setAlignment(Element.ALIGN_LEFT);
            doc.add(fecha);

            //datos de la empresa
            Paragraph datosDeLaEmpresa = new Paragraph("Datos empresa", fuenteSubtitulo);
            datosDeLaEmpresa.setAlignment(Element.ALIGN_RIGHT);
            doc.add(datosDeLaEmpresa);

            //cargar datos de la empresa
            var datosEmpresaNuevo = datosEmpresaServicio.verDatos(1);
            String ruc = String.valueOf(datosEmpresaNuevo.getRuc());
            String nombre = datosEmpresaNuevo.getNombre();
            String telefonoEmpresa = String.valueOf(datosEmpresaNuevo.getTelefono());
            String direccionEmpresa = datosEmpresaNuevo.getDireccion();
            String razonSocialEmpresa = datosEmpresaNuevo.getRazon_social();

            Paragraph infoEmpresa = new Paragraph(
                    "RUC: " + ruc + "\n" +
                            "Empresa: " + nombre + "\n" +
                            "Teléfono: " + telefonoEmpresa + "\n" +
                            "Dirección: " + direccionEmpresa + "\n" +
                            "Razón social: " + razonSocialEmpresa + "\n"
            );
            infoEmpresa.setAlignment(Element.ALIGN_RIGHT);
            doc.add(infoEmpresa);
            doc.close();
            archivo.close();
            mostrarMensaje("Información", "Recibo creado.");
        } catch (Exception e) {
            mostrarMensaje("Error", "Error al crear el PDF: " + e.getMessage());
        }
    }

    //metodo agregar celda en tabla pdf
    private static void agregarCelda(PdfPTable tabla, String texto, Font fuente) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
    }

    //metodo para agregar una fila de producto a la tabla pdf
    private static void agregarFilaProducto(PdfPTable tabla, String producto, String cantidad, String total, Font fuente) {
        agregarCelda(tabla, producto, fuente);
        agregarCelda(tabla, cantidad, fuente);
        agregarCelda(tabla, total, fuente);
    }

    //-------------------------------- FIN DEL APARTADO NUEVA VENTA --------------------------------//


    //-------------------------------- MÉTODOS DEL APARTADO PRODUCTOS --------------------------------//

    //metodo para abrir la ventana de productos
    public void verTabProductos() {
        tabPanePrincipal.getSelectionModel().select(tabProductos);
        configurarColumnasProductos();
        listarProductos();
    }

    //metodo configurar columnas de la tabla de productos
    private void configurarColumnasProductos() {
        idProductoColumna.setCellValueFactory(new PropertyValueFactory<>("producto_id"));
        codigoProductoColumna.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        //columna proveedor
        proveedorProductoColumna.setCellValueFactory(cellData -> {
            Producto producto = cellData.getValue();
            var proveedorId = producto.getProveedor_id();
            var proveedor = proveedorServicio.buscarProveedorPorId(proveedorId);
            return new SimpleStringProperty(proveedor != null ? proveedor.getNombre() : "N/A");
        });
        nombreProductoColumna.setCellValueFactory(new PropertyValueFactory<>("producto"));
        descripcionProductoColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        stockProductoColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        precioProductoColumna.setCellValueFactory(new PropertyValueFactory<>("precio"));
    }

    //metodo para listar los productos
    public void listarProductos() {
        productoList.clear();
        tablaProductos.refresh();
        productoList.addAll(productoServicio.listarProductos());
        tablaProductos.setItems(productoList);
        tablaProductos.refresh();
    }

    //metodo para ver los proveedores en el comboBox
    public void setProveedores() {
        //borro la lista ya que en caso de apretar varias veces se repiten los nombren
        nombreProveedorList.clear();
        comboBoxProveedor.setItems(nombreProveedorList);
        for (Proveedor proveedor : proveedorServicio.listarProveedores()) {
            var nombreProveedor = proveedor.getNombre();
            nombreProveedorList.add(nombreProveedor);
        }
        comboBoxProveedor.setItems(nombreProveedorList);
    }

    //metodo para agregar un producto a la base de datos
    public void agregarProducto() {
        var producto = new Producto();

        //valido que todos los campos estén llenos
        String nombreProveedor = String.valueOf(comboBoxProveedor.getValue());
        mostrarMensaje("info", nombreProveedor);

        if (codigoProductoTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el código.");
            codigoProductoTxt.requestFocus();
        } else if (nombreProductoTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el nombre.");
            nombreProductoTxt.requestFocus();
        } else if (descripcionProductoTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese la descripción.");
            descripcionProductoTxt.requestFocus();
        } else if (precioProductoTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el precio.");
            precioProductoTxt.requestFocus();
        } else if (stockProductoTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el stock.");
            stockProductoTxt.requestFocus();
        } else if (nombreProveedor == null) {
            mostrarMensaje("Error", "Seleccione un proveedor.");
            comboBoxProveedor.requestFocus();
        } else {
            try {
                //le doy formato a las variables para asignarlas al producto
                Integer codigo = Integer.parseInt(codigoProductoTxt.getText().trim());
                String nombre = nombreProductoTxt.getText().trim();
                String descripcion = descripcionProductoTxt.getText().trim();
                String precioStr = precioProductoTxt.getText();
                if (precioStr.contains(",")) {
                    precioStr = precioStr.replace(",", ".");
                }

                //pone la primera letra del nombre y la descripción en mayúsculas y todas las demás en minúsculas
                nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
                descripcion = descripcion.substring(0, 1).toUpperCase() + descripcion.substring(1).toLowerCase();
                double precio = Double.parseDouble(precioStr);
                Integer stock = Integer.parseInt(stockProductoTxt.getText());
                var proveedor = new Proveedor();
                Integer proveedorId;

                try {
                    proveedor = proveedorServicio.buscarProveedorPorNombre(nombreProveedor);
                    proveedorId = proveedor.getProveedor_id();
                } catch (Exception e) {
                    mostrarMensaje("Error", "Seleccione un proveedor.");
                    comboBoxProveedor.requestFocus();
                    return;
                }

                //valido producto (si existe o no)
                var repetido = false;
                Producto productoExistente = null;
                for (Producto productoLista : productoList) {
                    if (productoLista.getCodigo().equals(codigo)) {
                        repetido = true;
                        productoExistente = productoLista;
                        break;
                    }
                }

                try {
                    producto.setCodigo(codigo);
                    producto.setProveedor_id(proveedorId);
                    producto.setProducto(nombre);
                    producto.setDescripcion(descripcion);
                    producto.setCantidad(stock);
                    producto.setPrecio(precio);
                } catch (Exception e) {
                    mostrarMensaje("Error", e.getMessage());
                    return;
                }

                if (!repetido) {
                    productoServicio.agregarProducto(producto);
                    mostrarMensaje("Información", "Se agregó un nuevo producto a la base de datos.");
                    listarProductos();
                } else {
                    //si el producto ya está en la lista, actualizo lo modificado
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmación");
                    alert.setHeaderText("Modificar Producto");
                    alert.setContentText("¿Querés confirmar la modificación del producto: " + productoExistente.getProducto() + "?");

                    ButtonType botonSi = new ButtonType("Sí");
                    ButtonType botonNo = new ButtonType("No");
                    alert.getButtonTypes().setAll(botonSi, botonNo);

                    // Mostrar la ventana modal y esperar la respuesta del usuario
                    Optional<ButtonType> resultado = alert.showAndWait();

                    if (resultado.isPresent() && resultado.get() == botonSi) {
                        productoExistente.setProducto(producto.getProducto());
                        productoExistente.setDescripcion(producto.getDescripcion());
                        productoExistente.setPrecio(producto.getPrecio());
                        productoExistente.setCantidad(producto.getCantidad());

                        // Llamo al metodo de actualización en el servicio
                        productoServicio.agregarProducto(productoExistente);
                        listarProductos();
                        mostrarMensaje("Información", "Producto modificado correctamente.");
                    }
                }

            } catch (Exception e) {
                mostrarMensaje("Error", e.getMessage());
            }
        }
    }

    //metodo para que cuando se apriete el enter en los formularios pase automáticamente al siguiente
    private void apretarEnterPasarFormularioProducto() {
        codigoProductoTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                nombreProductoTxt.requestFocus();
            }
        });
        nombreProductoTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                descripcionProductoTxt.requestFocus();
            }
        });
        descripcionProductoTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                precioProductoTxt.requestFocus();
            }
        });
        precioProductoTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                stockProductoTxt.requestFocus();
            }
        });
        stockProductoTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                comboBoxProveedor.requestFocus();
            }
        });

        //si se aprieta enter en el combo box se agrega el producto
        comboBoxProveedor.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                agregarProducto();
            }
        });

    }

    //metodo para cargar un producto en el formulario para poder modificarlo
    public void cargarProductoFormulario() {
        var producto = tablaProductos.getSelectionModel().getSelectedItem();
        if (producto != null) {
            codigoProductoTxt.setText(String.valueOf(producto.getCodigo()));
            nombreProductoTxt.setText(producto.getProducto());
            descripcionProductoTxt.setText(producto.getDescripcion());
            precioProductoTxt.setText(String.valueOf(producto.getPrecio()));
            stockProductoTxt.setText(String.valueOf(producto.getCantidad()));
            var proveedor = proveedorServicio.buscarProveedorPorId(producto.getProveedor_id());
            comboBoxProveedor.setValue(proveedor.getNombre());
        }
    }

    //metodo para buscar un producto por codigo
    public void buscarProducto() {
        if (!codigoProductoBuscarTxt.getText().isEmpty()) {
            try {
                Integer codigo = Integer.parseInt(codigoProductoBuscarTxt.getText());
                var producto = productoServicio.buscarProductoPorCodigo(codigo);
                if (producto != null) {
                    productoList.clear();
                    productoList.add(producto);
                    tablaProductos.setItems(productoList);
                    tablaProductos.refresh();
                } else {
                    mostrarMensaje("Error", "No se encontró ningún producto con el código: " + codigo + ".");
                }
            } catch (Exception e) {
                mostrarMensaje("Error", "Ingrese un código válido.");
            }
        } else {
            mostrarMensaje("Error", "Debe ingresar un código.");
        }
    }

    //metodo para eliminar un producto de la base de datos
    public void eliminarProducto() {
        var producto = tablaProductos.getSelectionModel().getSelectedItem();
        if (producto != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Modificar Producto");
            alert.setContentText("¿Querés confirmar la eliminación del producto: " + producto.getProducto() + "?");

            ButtonType botonSi = new ButtonType("Sí");
            ButtonType botonNo = new ButtonType("No");
            alert.getButtonTypes().setAll(botonSi, botonNo);

            // Mostrar la ventana modal y esperar la respuesta del usuario
            Optional<ButtonType> resultado = alert.showAndWait();

            if (resultado.isPresent() && resultado.get() == botonSi) {
                productoServicio.eliminarProducto(producto);
                listarProductos();
                mostrarMensaje("Información", "Producto eliminado correctamente.");
            }
        }
    }

    //metodo para limpiar todos los formularios del tab
    public void limpiarFormularioTabProducto() {
        codigoProductoTxt.clear();
        nombreProductoTxt.clear();
        descripcionProductoTxt.clear();
        precioProductoTxt.clear();
        stockProductoTxt.clear();
        comboBoxProveedor.setValue(null);
        codigoProductoBuscarTxt.clear();
    }

    //-------------------------------- FIN DEL APARTADO PRODUCTOS --------------------------------//


    //-------------------------------- MÉTODOS DEL APARTADO CLIENTES --------------------------------//

    //metodo para abrir la ventana de clientes
    public void verTabClientes() {
        tabPanePrincipal.getSelectionModel().select(tabClientes);
        configurarColumnasClientes();
        listarClientes();
    }

    //metodo configurar columnas de la tabla de clientes
    private void configurarColumnasClientes() {
        idClienteColumna.setCellValueFactory(new PropertyValueFactory<>("cliente_id"));
        nombreClienteColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidoClienteColumna.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        dniClienteColumna.setCellValueFactory(new PropertyValueFactory<>("dni"));
        telefonoClienteColumna.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        direccionClienteColumna.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        razonSocialClienteColumna.setCellValueFactory(new PropertyValueFactory<>("razon_social"));
    }

    //metodo para listar los clientes
    public void listarClientes() {
        clienteList.clear();
        tablaClientes.refresh();
        clienteList.addAll(clienteServicio.listarClientes());
        tablaClientes.setItems(clienteList);
        tablaClientes.refresh();
    }

    // metodo para agregar un cliente a la base de datos o modificar uno existente
    public void agregarCliente() {

        //valido que estén todos los campos llenos
        if (nombreTabClienteTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el nombre del cliente.");
            nombreTabClienteTxt.requestFocus();
        } else if (apellidoClienteTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el apellido del cliente.");
            apellidoClienteTxt.requestFocus();
        } else if (dniTabClienteTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el DNI del cliente.");
            dniTabClienteTxt.requestFocus();
        } else if (telefonoClienteTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el teléfono del cliente.");
            telefonoClienteTxt.requestFocus();
        } else if (direccionClienteTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese la dirección del cliente.");
            direccionClienteTxt.requestFocus();
        } else if (razonSocialClienteTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese la razón social del cliente.");
            razonSocialClienteTxt.requestFocus();
        } else {

            var cliente = new Cliente();

            try {
                String nombre = nombreTabClienteTxt.getText();
                String apellido = apellidoClienteTxt.getText();
                String direccion = direccionClienteTxt.getText();
                String razonSocial = razonSocialClienteTxt.getText();

                //pone la primera letra del nombre y apellido en mayúscula y las demás en minúscula
                nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
                apellido = apellido.substring(0, 1).toUpperCase() + apellido.substring(1).toLowerCase();
                razonSocial = razonSocial.substring(0, 1).toUpperCase() + razonSocial.substring(1).toLowerCase();

                int dni = Integer.parseInt(dniTabClienteTxt.getText());
                int telefono = Integer.parseInt(telefonoClienteTxt.getText());

                cliente.setNombre(nombre);
                cliente.setApellido(apellido);
                cliente.setDni(dni);
                cliente.setTelefono(telefono);
                cliente.setDireccion(direccion);
                cliente.setRazon_social(razonSocial);

                //veo si se repite o no
                Cliente clienteExistente = null;
                var repetido = false;
                for (Cliente clienteLista : clienteList) {
                    if (dni == clienteLista.getDni()) {
                        repetido = true;
                        clienteExistente = clienteLista;
                        break;
                    }
                }

                if (!repetido) {
                    //si no se repite se guarda
                    clienteServicio.guardarCliente(cliente);
                    mostrarMensaje("Información", "Se ha guardado un nuevo cliente en la base de datos");
                } else {
                    //si se repite se actualiza exceptuando el dni
                    clienteExistente.setNombre(cliente.getNombre());
                    clienteExistente.setApellido(cliente.getApellido());
                    clienteExistente.setTelefono(cliente.getTelefono());
                    clienteExistente.setDireccion(cliente.getDireccion());
                    clienteExistente.setRazon_social(cliente.getRazon_social());
                    clienteServicio.guardarCliente(clienteExistente);
                    listarClientes();
                    mostrarMensaje("Información", "Se ha actualizado el cliente con DNI: " + dni);
                }
                limpiarFormularioCliente();
                listarClientes();
            } catch (Exception e) {
                mostrarMensaje("Error", e.getMessage());
            }
        }
    }

    //metodo para avanzar en el formulario al apretar enter
    private void apretarEnterPasarFormularioCliente() {
        nombreTabClienteTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                apellidoClienteTxt.requestFocus();
            }
        });
        apellidoClienteTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                dniTabClienteTxt.requestFocus();
            }
        });
        dniTabClienteTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                telefonoClienteTxt.requestFocus();
            }
        });
        telefonoClienteTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                direccionClienteTxt.requestFocus();
            }
        });
        direccionClienteTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                razonSocialClienteTxt.requestFocus();
            }
        });
        //si se aprieta enter en el text field razon social se agrega el producto
        razonSocialClienteTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                agregarCliente();
            }
        });
    }

    //metodo para cargar un cliente de la tabla en el formulario
    public void cargarClienteFormulario() {
        var cliente = tablaClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            nombreTabClienteTxt.setText(cliente.getNombre());
            apellidoClienteTxt.setText(cliente.getApellido());
            dniTabClienteTxt.setText(String.valueOf(cliente.getDni()));
            telefonoClienteTxt.setText(String.valueOf(cliente.getTelefono()));
            direccionClienteTxt.setText(cliente.getDireccion());
            razonSocialClienteTxt.setText(cliente.getRazon_social());
            dniTabClienteTxt.setEditable(false);
        }
    }

    //metodo para limpiar el formulario de cliente
    public void limpiarFormularioCliente() {
        nombreTabClienteTxt.clear();
        apellidoClienteTxt.clear();
        dniTabClienteTxt.clear();
        telefonoClienteTxt.clear();
        direccionClienteTxt.clear();
        razonSocialClienteTxt.clear();
        dniClienteBuscarTxt.clear();
        dniTabClienteTxt.setEditable(true);
    }

    //metodo para eliminar un cliente de la base de datos
    public void eliminarCliente() {
        var cliente = tablaClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Eliminar Cliente");
            alert.setContentText("¿Querés confirmar la eliminación del cliente con DNI: " + cliente.getDni() + "?");

            ButtonType botonSi = new ButtonType("Sí");
            ButtonType botonNo = new ButtonType("No");
            alert.getButtonTypes().setAll(botonSi, botonNo);

            Optional<ButtonType> resultado = alert.showAndWait();

            if (resultado.isPresent() && resultado.get() == botonSi) {
                clienteServicio.eliminarCliente(cliente);
                listarProductos();
                mostrarMensaje("Información", "Cliente eliminado correctamente.");
                listarClientes();
            }
        }
    }

    public void btnBuscarCliente() {
        if (dniClienteBuscarTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese un DNI para buscar al cliente.");
            dniClienteBuscarTxt.requestFocus();
        } else {
            try {
                int dni = Integer.parseInt(dniClienteBuscarTxt.getText());
                var cliente = clienteServicio.buscarClientePorDni(dni);
                if (cliente != null) {
                    clienteList.clear();
                    clienteList.add(cliente);
                    tablaClientes.setItems(clienteList);
                    tablaClientes.refresh();
                } else {
                    mostrarMensaje("Error", "No se encontró ningún cliente con el DNI: " + dni + ".");
                }
            } catch (Exception e) {
                mostrarMensaje("Error", "Ingrese un dni valido");
            }
        }
    }

    private void buscarCliente() {
        dniClienteBuscarTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnBuscarCliente();
            }
        });
    }


    //-------------------------------- FIN DEL APARTADO CLIENTES --------------------------------//


    //-------------------------------- MÉTODOS DEL APARTADO PROVEEDORES --------------------------------//


    //metodo para abrir la ventana de proveedores
    public void verTabProveedores() {
        tabPanePrincipal.getSelectionModel().select(tabProveedores);
        configurarColumnasProveedores();
        listarProveedores();
    }

    //metodo configurar columnas de la tabla de proveedores
    private void configurarColumnasProveedores() {
        idProveedorColumna.setCellValueFactory(new PropertyValueFactory<>("proveedor_id"));
        rucProveedorColumna.setCellValueFactory(new PropertyValueFactory<>("ruc"));
        nombreProveedorColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        telefonoProveedorColumna.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        direccionProveedorColumna.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        razonSocialProveedorColumna.setCellValueFactory(new PropertyValueFactory<>("razon_social"));
    }

    //metodo para listar los proveedores
    public void listarProveedores() {
        proveedorList.clear();
        tablaProveedores.refresh();
        proveedorList.addAll(proveedorServicio.listarProveedores());
        tablaProveedores.setItems(proveedorList);
        tablaProveedores.refresh();
    }

    // metodo para agregar un cliente a la base de datos o modificar uno existente
    public void agregarProveedor() {

        //valido que estén todos los campos llenos
        if (rucTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el RUC del proveedor.");
            rucTxt.requestFocus();
        } else if (nombreProveedorTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el nombre del proveedor.");
            nombreProveedorTxt.requestFocus();
        } else if (telefonoProveedorTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el teléfono del proveedor.");
            telefonoProveedorTxt.requestFocus();
        } else if (direccionProveedorTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese la dirección del proveedor.");
            direccionProveedorTxt.requestFocus();
        } else if (razonSocialProveedorTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese la razón social del proveedor.");
            razonSocialProveedorTxt.requestFocus();
        } else {

            var proveedor = new Proveedor();

            try {
                String nombre = nombreProveedorTxt.getText();
                String direccion = direccionProveedorTxt.getText();
                String razonSocial = razonSocialProveedorTxt.getText();

                //pone la primera letra del nombre y apellido en mayúscula y las demás en minúscula
                nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
                direccion = direccion.substring(0, 1).toUpperCase() + direccion.substring(1);
                razonSocial = razonSocial.substring(0, 1).toUpperCase() + razonSocial.substring(1).toLowerCase();
                int telefono = Integer.parseInt(telefonoProveedorTxt.getText());
                int ruc = Integer.parseInt(rucTxt.getText());

                proveedor.setRuc(ruc);
                proveedor.setNombre(nombre);
                proveedor.setTelefono(telefono);
                proveedor.setDireccion(direccion);
                proveedor.setRazon_social(razonSocial);

                //veo si se repite o no
                Proveedor proveedorExistente = null;
                var repetido = false;
                for (Proveedor proveedorLista : proveedorList) {
                    if (ruc == proveedorLista.getRuc()) {
                        repetido = true;
                        proveedorExistente = proveedorLista;
                        break;
                    }
                }

                if (!repetido) {
                    //si no se repite se guarda
                    proveedorServicio.agregarProveedor(proveedor);
                    mostrarMensaje("Información", "Se ha guardado un nuevo proveedor en la base de datos");
                } else {
                    //si se repite se actualiza exceptuando el RUC
                    proveedorExistente.setNombre(proveedor.getNombre());
                    proveedorExistente.setTelefono(proveedor.getTelefono());
                    proveedorExistente.setDireccion(proveedor.getDireccion());
                    proveedorExistente.setRazon_social(proveedor.getRazon_social());
                    proveedorServicio.agregarProveedor(proveedorExistente);
                    listarClientes();
                    mostrarMensaje("Información", "Se ha actualizado el proveedor con RUC: " + ruc);
                }
                limpiarFormularioProveedor();
                listarProveedores();
            } catch (Exception e) {
                mostrarMensaje("Error", e.getMessage());
            }
        }
    }

    //metodo para limpiar el formulario de proveedor
    public void limpiarFormularioProveedor() {
        rucTxt.clear();
        nombreProveedorTxt.clear();
        telefonoProveedorTxt.clear();
        direccionProveedorTxt.clear();
        razonSocialProveedorTxt.clear();
        rucBuscarTxt.clear();
        rucTxt.setEditable(true);
    }

    //metodo para avanzar en el formulario al apretar enter
    private void apretarEnterPasarFormularioProveedor() {
        rucTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                nombreProveedorTxt.requestFocus();
            }
        });
        nombreProveedorTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                telefonoProveedorTxt.requestFocus();
            }
        });
        telefonoProveedorTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                direccionProveedorTxt.requestFocus();
            }
        });
        direccionProveedorTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                razonSocialProveedorTxt.requestFocus();
            }
        });
        //si se aprieta enter en el text field razon social se agrega el proveedor
        razonSocialProveedorTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                agregarProveedor();
            }
        });
    }

    //metodo para buscar un proveedor por RUC (botón)
    public void btnBuscarProveedor() {
        if (rucBuscarTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese un RUC para buscar al proveedor.");
            rucBuscarTxt.requestFocus();
        } else {
            try {
                int ruc = Integer.parseInt(rucBuscarTxt.getText());
                var proveedor = proveedorServicio.buscarProveedorPorRuc(ruc);
                if (proveedor != null) {
                    proveedorList.clear();
                    proveedorList.add(proveedor);
                    tablaProveedores.setItems(proveedorList);
                    tablaProveedores.refresh();
                } else {
                    mostrarMensaje("Error", "No se encontró ningún cliente con el RUC: " + ruc + ".");
                }
            } catch (Exception e) {
                mostrarMensaje("Error", "Ingrese un RUC valido");
            }
        }
    }

    //metodo para buscar un proveedor por RUC (enter)
    public void buscarProveedor() {
        rucBuscarTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnBuscarProveedor();
            }
        });
    }

    //metodo para cargar un cliente de la tabla en el formulario
    public void cargarProveedorFormulario() {
        var proveedor = tablaProveedores.getSelectionModel().getSelectedItem();
        if (proveedor != null) {
            rucTxt.setText(String.valueOf(proveedor.getRuc()));
            nombreProveedorTxt.setText(proveedor.getNombre());
            telefonoProveedorTxt.setText(String.valueOf(proveedor.getTelefono()));
            direccionProveedorTxt.setText(proveedor.getDireccion());
            razonSocialProveedorTxt.setText(proveedor.getRazon_social());
            rucTxt.setEditable(false);
        }
    }

    //metodo para eliminar un cliente de la base de datos
    public void eliminarProveedor() {
        var proveedor = tablaProveedores.getSelectionModel().getSelectedItem();
        if (proveedor != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Eliminar Proveedor");
            alert.setContentText("¿Querés confirmar la eliminación del proveedor con RUC: " + proveedor.getRuc() + "?");

            ButtonType botonSi = new ButtonType("Sí");
            ButtonType botonNo = new ButtonType("No");
            alert.getButtonTypes().setAll(botonSi, botonNo);

            Optional<ButtonType> resultado = alert.showAndWait();

            if (resultado.isPresent() && resultado.get() == botonSi) {
                proveedorServicio.eliminarProveedor(proveedor);
                listarProductos();
                mostrarMensaje("Información", "Proveedor eliminado correctamente.");
                listarClientes();
            }
        }
        listarProveedores();
    }


    //-------------------------------- FIN DEL APARTADO PROVEEDORES --------------------------------//



    //-------------------------------- MÉTODOS DEL APARTADO PEDIDOS --------------------------------//

    //metodo para abrir la ventana de pedidos
    public void verTabPedidos(){
        tabPanePrincipal.getSelectionModel().select(tabPedidos);
        configurarColumnasPedidos();
        listarPedidos();
    }

    //metodo para configurar las columnas de la tabla pedidos
    private void configurarColumnasPedidos() {
        idPedidoColumna.setCellValueFactory(new PropertyValueFactory<>("pedido_id"));
        clientePedidoColumna.setCellValueFactory(cellData -> {
            Pedido pedido = cellData.getValue();
            var cliente = new Cliente();
            if (pedido.getCliente_id() != null){
                var clienteId = pedido.getCliente_id();
                cliente = clienteServicio.buscarClientePorId(clienteId);
            }else{
                cliente = null;
            }
            return new SimpleStringProperty(cliente != null ? cliente.getNombre() : "N/A");
        });
        vendedorPedidoColumna.setCellValueFactory(cellData -> {
            Pedido pedido = cellData.getValue();
            var cuentaId = pedido.getCuenta_id();
            var cuenta = cuentaServicio.buscarCuentaPorId(cuentaId);
            return new SimpleStringProperty(cuenta != null ? cuenta.getNombre() + " " + cuenta.getApellido() : "N/A");
        });
        fechaPedidoColumna.setCellValueFactory(cellData -> {
            Pedido pedido = cellData.getValue();
            var fecha = pedido.getFecha();
            String fechaStr = String.valueOf(fecha);
            fechaStr = fechaStr.substring(0, 10);
            return new SimpleStringProperty(fechaStr);
        });
        formaDePagoPedidoColumna.setCellValueFactory(cellData -> {
            Pedido pedido = cellData.getValue();
            return new SimpleStringProperty(pedido.getForma_de_pago() != null ? pedido.getForma_de_pago() : "N/A");
        });
        precioPedidoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_total"));
    }

    //metodo para listar los pedidos
    public void listarPedidos() {
        pedidoList.clear();
        tablaPedidos.refresh();
        pedidoList.addAll(pedidoServicio.listarPedidos());
        tablaPedidos.setItems(pedidoList);
        tablaPedidos.refresh();
    }

    //metodo para buscar los pedidos de una fecha (botón)
    public void btnBuscarPedidoPorFecha(){
        if (fechaBuscarTxt.getText().isEmpty()){
            mostrarMensaje("Error", "Ingrese una fecha");
        }else {
            listarPedidos();
            pedidoFechaList.clear();
            double totalEfectivo = 0;
            double totalMercadoPago = 0;
            double totalTarjeta = 0;
            double totalDia;
            try{
                for (Pedido pedido: pedidoList){
                    var fecha = pedido.getFecha();
                    String fechaStr = String.valueOf(fecha);
                    fechaStr = fechaStr.substring(0, 10);
                    if (fechaStr.equals(fechaBuscarTxt.getText())){
                        pedidoFechaList.add(pedido);
                        switch (pedido.getForma_de_pago()) {
                            case "Efectivo" -> totalEfectivo += pedido.getPrecio_total();
                            case "Mercado Pago" -> totalMercadoPago += pedido.getPrecio_total();
                            case "Tarjeta" -> totalTarjeta += pedido.getPrecio_total();
                        }
                    }
                }

                totalDia = totalTarjeta + totalEfectivo + totalMercadoPago;

                String totalEfectivoFormateado = String.format("%.2f", totalEfectivo);
                String totalMercadoPagoFormateado = String.format("%.2f", totalMercadoPago);
                String totalTarjetaFormateado = String.format("%.2f", totalTarjeta);
                String totalDiaFormateado = String.format("%.2f", totalDia);


                mercadoPagoTxt.setText("$" + totalMercadoPagoFormateado);
                tarjetaTxt.setText("$" + totalTarjetaFormateado);
                efectivoTxt.setText("$" + totalEfectivoFormateado);
                totalDiaTxt.setText("$" + totalDiaFormateado);


                tablaPedidos.refresh();
                tablaPedidos.setItems(pedidoFechaList);
                tablaPedidos.refresh();


            }catch (Exception e){
                mostrarMensaje("Error", e.getMessage());
            }
        }
    }

    //metodo para buscar los pedidos de una fecha (enter)
    private void buscarPedidoFecha(){
        fechaBuscarTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnBuscarPedidoPorFecha();
            }
        });
    }

    //metodo para buscar un pedido por id (boton)
    public void btnBuscarPedidoId(){
        if (idPedidoBuscar.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese un ID para buscar el pedido.");
            rucBuscarTxt.requestFocus();
        } else {
            try {
                int id = Integer.parseInt(idPedidoBuscar.getText());
                var pedido = pedidoServicio.buscarPedidoPorId(id);
                if (pedido != null) {
                    pedidoList.clear();
                    pedidoList.add(pedido);
                    tablaPedidos.setItems(pedidoList);
                    tablaPedidos.refresh();
                } else {
                    mostrarMensaje("Error", "No se encontró ningún pedido con el ID: " + id + ".");
                }
            } catch (Exception e) {
                mostrarMensaje("Error", "Ingrese un ID valido");
            }
        }
    }

    //metodo para buscar un pedido por id (enter)
    public void buscarPedidoId(){
        idPedidoBuscar.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnBuscarPedidoId();
            }
        });
    }

    //metodo para ver el recibo de un pedido
    public void verRecibo() {
        var pedido = tablaPedidos.getSelectionModel().getSelectedItem();
        if (pedido == null) {
            mostrarMensaje("Error", "Seleccione un pedido primero.");
            return;
        }
       try {
           int id = pedido.getPedido_id();
           String comando = "cmd /c start " + "src/main/resources/zn/pdf/venta"+id+".pdf";
           Runtime.getRuntime().exec(comando);
       }catch (IOException e) {
           mostrarMensaje("Error", "No se pudo abrir el archivo PDF: " + e.getMessage());
       }
    }

    //-------------------------------- FIN DEL APARTADO PEDIDOS --------------------------------//


    //-------------------------------- MÉTODOS DEL APARTADO DATOS EMPRESA --------------------------------//

    //metodo para abrir la ventana de datos de la empresa
    public void verTabDatos(){
        tabPanePrincipal.getSelectionModel().select(tabDatosEmpresa);
        cargarDatosEmpresa();
    }

    //metodo para cargar los datos de la empresa en los text field
    private void cargarDatosEmpresa(){
        DatosEmpresa datos = datosEmpresaServicio.verDatos(1);
        rucEmpresaTxt.setText(String.valueOf(datos.getRuc()));
        nombreEmpresaTxt.setText(datos.getNombre());
        telefonoEmpresaTxt.setText(String.valueOf(datos.getTelefono()));
        direccionEmpresaTxt.setText(datos.getDireccion());
        razonSocialEmpresaTxt.setText(datos.getRazon_social());
    }

    //metodo para guardar datos de la empresa (botón)
    public void btnGuardarDatosEmpresa() {
        //valido que estén todos los campos llenos
        if (rucEmpresaTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el RUC de la empresa.");
            rucEmpresaTxt.requestFocus();
        } else if (nombreEmpresaTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el nombre de la empresa.");
            nombreEmpresaTxt.requestFocus();
        } else if (telefonoEmpresaTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el teléfono de la empresa.");
            telefonoEmpresaTxt.requestFocus();
        } else if (direccionEmpresaTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese la dirección de la empresa.");
            direccionEmpresaTxt.requestFocus();
        } else if (razonSocialEmpresaTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese la razón social de la empresa.");
            razonSocialEmpresaTxt.requestFocus();
        } else {
            try {
                String nombre = nombreEmpresaTxt.getText();
                String direccion = direccionEmpresaTxt.getText();
                String razonSocial = razonSocialEmpresaTxt.getText();

                //pone la primera letra del nombre y apellido en mayúscula y las demás en minúscula
                nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
                direccion = direccion.substring(0, 1).toUpperCase() + direccion.substring(1);
                razonSocial = razonSocial.substring(0, 1).toUpperCase() + razonSocial.substring(1);
                int telefono = Integer.parseInt(telefonoEmpresaTxt.getText());
                int ruc = Integer.parseInt(rucEmpresaTxt.getText());

                DatosEmpresa datos = datosEmpresaServicio.verDatos(1);
                datos.setRuc(ruc);
                datos.setNombre(nombre);
                datos.setTelefono(telefono);
                datos.setDireccion(direccion);
                datos.setRazon_social(razonSocial);

                datosEmpresaServicio.agregarDatos(datos);
                mostrarMensaje("Información", "Se han guardado los nuevos datos de la empresa");

                cargarDatosEmpresa();
            } catch (Exception e) {
                mostrarMensaje("Error", e.getMessage());
            }
        }
    }

    //metodo para avanzar en el formulario al apretar enter
    private void apretarEnterPasarFormularioDatos() {
        rucEmpresaTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                nombreEmpresaTxt.requestFocus();
            }
        });
        nombreEmpresaTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                telefonoEmpresaTxt.requestFocus();
            }
        });
        telefonoEmpresaTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                direccionEmpresaTxt.requestFocus();
            }
        });
        direccionEmpresaTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                razonSocialEmpresaTxt.requestFocus();
            }
        });
        //si se aprieta enter en el text field razon social se agrega el proveedor
        razonSocialEmpresaTxt.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnGuardarDatosEmpresa();
            }
        });
    }

    //-------------------------------- FIN DEL APARTADO DATOS EMPRESA --------------------------------//

    private void mostrarMensaje(String titulo, String mensaje){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
