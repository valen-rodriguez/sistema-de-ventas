package zn.almacen.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zn.almacen.modelo.DatosEmpresa;
import zn.almacen.repositorio.DatosEmpresaRepositorio;

import java.util.Optional;

@Service
public class DatosEmpresaServicio implements IDatosEmpresaServicio{

    @Autowired
    private DatosEmpresaRepositorio datosEmpresaRepositorio;

    public DatosEmpresaServicio(DatosEmpresaRepositorio datosEmpresaRepositorio) {
        this.datosEmpresaRepositorio = datosEmpresaRepositorio;
    }

    @Override
    public DatosEmpresa verDatos(Integer id) {
        Optional<DatosEmpresa> datosEmpresa = datosEmpresaRepositorio.findById(id);
        return datosEmpresa.orElse(null);
    }
}
