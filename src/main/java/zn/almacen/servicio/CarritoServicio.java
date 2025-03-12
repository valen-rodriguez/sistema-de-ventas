//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Service;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class CarritoServicio {
//
//    private static final String CARRITO_SESSION_KEY = "carrito";
//
//    // Obtener el carrito de la sesión
//    public List<Producto> obtenerCarrito(HttpSession session) {
//        List<Producto> carrito = (List<Producto>) session.getAttribute(CARRITO_SESSION_KEY);
//        if (carrito == null) {
//            carrito = new ArrayList<>();
//            session.setAttribute(CARRITO_SESSION_KEY, carrito);
//        }
//        return carrito;
//    }
//
//    // Agregar un producto al carrito
//    public void agregarProducto(HttpSession session, Producto producto) {
//        List<Producto> carrito = obtenerCarrito(session);
//        carrito.add(producto);
//    }
//
//    // Eliminar un producto del carrito
//    public void eliminarProducto(HttpSession session, Long productoId) {
//        List<Producto> carrito = obtenerCarrito(session);
//        carrito.removeIf(p -> p.getId().equals(productoId));
//    }
//
//    // Vaciar el carrito (cuando se realiza la compra)
//    public void vaciarCarrito(HttpSession session) {
//        session.removeAttribute(CARRITO_SESSION_KEY);
//    }
//}