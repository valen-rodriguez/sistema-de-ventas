package zn.almacen.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zn.almacen.modelo.Cliente;
import zn.almacen.repositorio.ClienteRepositorio;

import java.util.List;

@Service
public class ClienteServicio implements IClienteServicio{

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepositorio.findAll();
    }

    @Override
    public Cliente buscarClientePorDni(Integer dni) {
        return clienteRepositorio.findClienteByDni(dni).orElse(null);
    }

    @Override
    public Cliente buscarClientePorId(Integer idCliente) {
        return clienteRepositorio.findById(idCliente).orElse(null);
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        clienteRepositorio.save(cliente);
    }

    @Override
    public void eliminarCliente(Cliente cliente) {
        clienteRepositorio.delete(cliente);
    }
}
