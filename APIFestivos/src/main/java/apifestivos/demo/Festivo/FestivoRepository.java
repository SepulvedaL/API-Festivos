package apifestivos.demo.Festivo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface FestivoRepository extends JpaRepository<Festivos, Long>{
    
    //@Query("Select * From Product p Where p.nombre = ?12")
    Optional<Festivos> findFestivotByNombre(String nombre);
}
