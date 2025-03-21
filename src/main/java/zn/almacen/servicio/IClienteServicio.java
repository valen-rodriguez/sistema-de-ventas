package zn.almacen.servicio;

import zn.almacen.modelo.Cliente;

import java.util.List;

public interface IClienteServicio {

    List<Cliente> listarClientes();

    Cliente buscarClientePorDni(Integer dni);

    Cliente buscarClientePorId(Integer clienteId);

    void guardarCliente(Cliente cliente);

    void eliminarCliente(Cliente cliente);

}
