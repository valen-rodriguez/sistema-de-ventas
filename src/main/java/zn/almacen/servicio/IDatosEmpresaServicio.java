package zn.almacen.servicio;

import org.springframework.stereotype.Service;
import zn.almacen.modelo.DatosEmpresa;

@Service
public interface IDatosEmpresaServicio{
    DatosEmpresa verDatos(Integer id);
}
