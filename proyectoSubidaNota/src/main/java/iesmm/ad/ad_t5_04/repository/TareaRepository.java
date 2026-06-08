package iesmm.ad.ad_t5_04.repository;

import iesmm.ad.ad_t5_04.model.EstadoTarea;
import iesmm.ad.ad_t5_04.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findByEstado(EstadoTarea estado);

    List<Tarea> findByEmpleadoId(Long empleadoId);

    List<Tarea> findByTituloContainingIgnoreCase(String titulo);
}
