package zn.almacen.servicio;

import zn.almacen.modelo.Cliente;

import java.util.List;

public interface IClienteServicio {

    public List<Cliente> listarClientes();

    public Cliente buscarClientePorId(Integer clienteId);

    public void guardarCliente(Cliente cliente);

    public void eliminarCliente(Cliente cliente);

}
