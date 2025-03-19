package zn.almacen.servicio;

import zn.almacen.modelo.Cliente;
import zn.almacen.modelo.Producto;

import java.util.List;
import java.util.Optional;

public interface IClienteServicio {

    List<Cliente> listarClientes();

    Cliente buscarClientePorDni(Integer dni);

    Cliente buscarClientePorId(Integer clienteId);

    void guardarCliente(Cliente cliente);

    void eliminarCliente(Cliente cliente);

}
