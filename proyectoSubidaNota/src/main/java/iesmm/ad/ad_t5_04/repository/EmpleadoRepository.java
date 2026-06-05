package iesmm.ad.ad_t5_04.repository;

import iesmm.ad.ad_t5_04.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    List<Empleado> findByNombreContainingIgnoreCase(String nombre);

    Optional<Empleado> findByEmail(String email);
}
