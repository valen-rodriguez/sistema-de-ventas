package zn.almacen.controlador;

import com.itextpdf.text.*;
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
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Component
public class SistemaControlador implements Initializable {

    @Setter
    private Cuenta cuenta;

    //creacion de los objetos de las clases
    public LocalDateTime fecha;

    private Cliente cliente;

    private Producto producto;

    private PedidoProducto pedidoProducto;

    private Pedido pedido;

    private DatosEmpresa datosEmpresa;

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

    @Autowired
    private DatosEmpresaServicio datosEmpresaServicio;


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

    //variable total a pagar
    private double totalPagar;

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
        this.cliente = new Cliente();
        this.pedidoProducto = new PedidoProducto();
        this.pedido = new Pedido();
        this.datosEmpresa = new DatosEmpresa();

        tablaProductos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        cargarTextoProducto();
        tablaCarrito.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnasCarrito();
        cargarProductoTabla();
        buscarCliente();
    }

    //-------------------------------- MÉTODOS DEL APARTADO NUEVA VENTA --------------------------------//

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
    private void cargarProductoTabla(){
        cantidadTxt.setOnKeyPressed(event ->{
            if (event.getCode() == KeyCode.ENTER){
                agregarProductoCarrito();
            }
        });
    }

    //metodo para cargar el producto en la tabla (botón)
    public void agregarProductoCarrito() {
        try {

            //verifico si el usuario ingreso el codigo dle producto antes de agregar el producto en el carrito
            if (cantidadTxt.getText().isEmpty()){
                mostrarMensaje("Error", "Ingrese la cantidad");
                return;
            }else if (productoTxt.getText().isEmpty()){
                mostrarMensaje("Error", "No ha ingresado ningun producto");
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
                this.producto.setTotal(total);

                Integer codigo = productoCarrito.getCodigo();
                //valido producto (si esta en la lista o no)
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
                mostrarMensaje("Error", "No hay suficiente stock");
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

    //metodo buscar cliente por dni
    private void buscarCliente(){
        dniClienteTxt.setOnKeyPressed(event ->{
        if (event.getCode() == KeyCode.ENTER) {
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
                    mostrarMensaje("Error", "No se encontro un cliente con ese DNI.");
                    dniClienteTxt.requestFocus();
                }
            } catch (NumberFormatException e) {
                mostrarMensaje("Error", "Ingrese un DNI valido");
                dniClienteTxt.requestFocus();
            }
        }
    });
    }

    //metodo para calcular el total a pagar
    private void totalAPagar() {
        this.totalPagar = 0;
        for (Producto producto : productoCarritoList) {
            this.totalPagar = this.totalPagar + producto.getTotal();
        }
        totalTxt.setText("$" + this.totalPagar);
    }

    //metodo para crear una venta
    public void crearVenta(){
        if (totalTxt.getText().isEmpty()){
            mostrarMensaje("Error", "Ingrese al menos un producto");
        } else if (nombreClienteTxt.getText().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el cliente");
        } else {
            //crea el pedido
            Pedido pedidoNuevo = new Pedido();
            pedidoNuevo.setCliente_id(this.cliente.getCliente_id());
            pedidoNuevo.setCuenta_id(this.cuenta.getCuenta_id());
            pedidoNuevo.setPrecioTotal(this.totalPagar);
            pedidoNuevo.setFecha(LocalDateTime.now());
            pedidoServicio.agregarPedido(pedidoNuevo);
            this.pedido = pedidoNuevo;

            //recorre lista de productos en el carrito
            for (Producto producto : productoCarritoList){
                //guarda el pedidoProducto en la base de datos
                PedidoProducto pedidoProducto = new PedidoProducto();
                pedidoProducto.setProductoId(producto);
                pedidoProducto.setPedidoId(pedidoNuevo);
                pedidoProducto.setCantidad(producto.getCantidadIngresada().intValue());
                pedidoProductoServicio.agregarPedidoProducto(pedidoProducto);

                //asigna el nuevo stock a los productos
                double cantidadNueva = producto.getCantidad() - producto.getCantidadIngresada();
                producto.setCantidad((int) cantidadNueva);
                productoServicio.agregarProducto(producto);

            }
            mostrarMensaje("Informacion", "Se ha creado una nueva venta");
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

            //limpiar formulario producto
            limpiarFormularioProducto();

            codigoTxt.requestFocus();
        }
    }

    //metodo para limpiar el formulario del producto
    public void limpiarFormularioProducto(){
        codigoTxt.clear();
        productoTxt.clear();
        precioTxt.clear();
        stockTxt.clear();
        cantidadTxt.clear();
        codigoTxt.setEditable(true);
    }

    //metodo para limpiar todos los formularios
    public void limpiarFormulariosTabVentas(){
        limpiarFormularioProducto();
        //limpiar formulario del cliente
        dniClienteTxt.clear();
        nombreClienteTxt.clear();
    }

    //metodo recibo en pdf
    private void pdf() {
        try {
            //crea la carpeta si no existe
            File carpetaPdf = new File("src/main/java/zn/almacen/pdf");
            if (!carpetaPdf.exists()) {
                carpetaPdf.mkdirs(); // Crea la carpeta y cualquier directorio padre necesario
            }

            File file = new File(carpetaPdf, "venta" + this.pedido.getId() + ".pdf");
            FileOutputStream archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);

            doc.open();

            PdfPTable Encabezado =  new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] ColumnaEncabezado = new float[]{20f, 30f, 70f, 40};
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

            //datos del cliente
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
            Paragraph fecha = new Paragraph("Factura: " + this.pedido.getId() + "\nFecha: " + fechaFormateada, fuenteNormal);
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
            mostrarMensaje("Información", "Recibo creado");
        } catch (Exception e) {
            mostrarMensaje("Error", "Error al crear el PDF: " + e.getMessage());
        }
    }

    //metodo eliminar producto carrito
    public void eliminarProductoCarrito(){
        var producto = tablaCarrito.getSelectionModel().getSelectedItem();
        if (producto != null){
            productoCarritoList.remove(producto);
            tablaCarrito.setItems(productoCarritoList);
            tablaCarrito.refresh();
            totalAPagar();
        }else{
            mostrarMensaje("Error", "Debe seleccionar un producto");
        }
    }



    //metodo agregar celda en tabla pdf
    private static void agregarCelda(PdfPTable tabla, String texto, Font fuente) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
    }

    //metodo para agregar una fila de producto a la tabla pdf
    private static void agregarFilaProducto(PdfPTable tabla, String producto, String cantidad, String total, Font fuente){
        agregarCelda(tabla, producto, fuente);
        agregarCelda(tabla, cantidad, fuente);
        agregarCelda(tabla, total, fuente);
    }

    //-------------------------------- FIN DEL APARTADO NUEVA VENTA --------------------------------//



    //-------------------------------- MÉTODOS DEL APARTADO PRODUCTOS --------------------------------//

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










    //-------------------------------- FIN DEL APARTADO PRODUCTOS --------------------------------//

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

    private void mostrarMensaje(String titulo, String mensaje){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
