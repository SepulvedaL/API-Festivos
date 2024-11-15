package apifestivos.demo.Festivo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class FestivoService {

    private final FestivoRepository productRepository;


    public FestivoService(FestivoRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Festivos> getFestivo(){
        return this.productRepository.findAll();
    }

    public ResponseEntity<Object> nuevoFestivo(Festivos festivos) {
        Optional<Festivos> res = productRepository.findFestivotByNombre(festivos.getNombre());
        HashMap<String, Object> datos = new HashMap<>();

        if(res.isPresent()){
            //throw new IllegalStateException("Ya se registro el producto");
            datos.put("error", true);
            datos.put("messsage", "Ya existe un Festivo con ese nombre");

            return new ResponseEntity<>(
                datos,
                HttpStatus.CONFLICT
            );
        }
        datos.put("messsage", "Se guardo con éxito");

        productRepository.save(festivos);
        datos.put("datos", festivos);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
            );
    }

    public ResponseEntity<Object> modificarFestivo(Festivos festivos) {
        Optional<Festivos> res = productRepository.findFestivotByNombre(festivos.getNombre());
        HashMap<String, Object> datos = new HashMap<>();

        if(res.isPresent() && festivos.getId()==null){
            //throw new IllegalStateException("Ya se registro el producto");
            datos.put("error", true);
            datos.put("messsage", "Ya existe un producto con ese nombre");

            return new ResponseEntity<>(
                datos,
                HttpStatus.CONFLICT
            );
        }
        datos.put("messsage", "Se guardo con éxito");

        if(festivos.getId()!=null){
            datos.put("messsage", "Se actualizo con éxito");
        }
        productRepository.save(festivos);
        datos.put("datos", festivos);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
            );
    }

    public ResponseEntity<Object> deleteFestivo(Long id){
        HashMap<String, Object> datos = new HashMap<>();
        boolean existe = this.productRepository.existsById(id);
        if(!existe){
            datos.put("error", true);
            datos.put("messsage", "No existe un Festivo con ese id");
            return new ResponseEntity<>(
                datos,
                HttpStatus.CONFLICT
            );
        }
        productRepository.deleteById(id);
        datos.put("messsage", "Festivo eliminado");
        return new ResponseEntity<>(
            datos,
            HttpStatus.ACCEPTED
        );
    }



    public String verificarFechaFestiva(LocalDate fecha) {
        List<Festivos> festivos = productRepository.findAll();

        for (Festivos festivo : festivos) {
            if (esFestivo(festivo, fecha)) {
                return "La fecha " + fecha + " es festivo: " + festivo.getNombre();
            }
        }
        return "La fecha " + fecha + " no es festivo.";
    }

    private boolean esFestivo(Festivos festivo, LocalDate fecha) {
        switch (festivo.getTipo()) {
            case 1:
                return festivo.getDia() == fecha.getDayOfMonth() && festivo.getMes() == fecha.getMonthValue();
            case 2:
                return FechasUtils.esPuenteFestivo(festivo, fecha);
            case 3:
            case 4:
                return FechasUtils.esFestivoBasadoEnPascua(festivo, fecha, festivo.getTipo());
            default:
                return false;
        }
    }
}
