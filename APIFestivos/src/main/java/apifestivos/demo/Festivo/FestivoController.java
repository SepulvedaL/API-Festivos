package apifestivos.demo.Festivo;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(path = "api/v1/festivos")
public class FestivoController {

    private final FestivoService festivoService;


    public FestivoController(FestivoService festivoService){
        this.festivoService = festivoService;
    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public List<Festivos> getFestivo(){
        return festivoService.getFestivo();
    }

    @RequestMapping(value = "/agregar", method = RequestMethod.POST)
    public ResponseEntity<Object> registrarFestivo(@RequestBody Festivos festivo){
        return this.festivoService.nuevoFestivo(festivo);
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.PUT)
    public ResponseEntity<Object> actualizarFestivo(@RequestBody Festivos festivo){
        return this.festivoService.modificarFestivo(festivo);
    }

    @RequestMapping(value = "/eliminar/{festivoId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> eliminarFestivo(@PathVariable("festivoId") Long festivoId){
        return this.festivoService.deleteFestivo(festivoId);
    }

    @RequestMapping(value ="/validar", method = RequestMethod.GET)
    //http://localhost:8080/api/v1/festivos/validar?fecha=2023-04-07
    public String verificarFestivo(@RequestParam("fecha") String fechaStr) {
        try {
            LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_DATE);
            return festivoService.verificarFechaFestiva(fecha);
        } catch (DateTimeParseException e) {
            return "Formato de fecha inválido. Use el formato YYYY-MM-DD.";
        }
    }
}
