package zn.almacen.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zn.almacen.modelo.DatosEmpresa;

@Repository
public interface DatosEmpresaRepositorio extends JpaRepository<DatosEmpresa, Integer>{
}
