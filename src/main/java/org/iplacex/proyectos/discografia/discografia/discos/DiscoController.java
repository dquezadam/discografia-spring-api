package org.iplacex.proyectos.discografia.discografia.discos;

import java.util.List;
import java.util.Optional;

import org.iplacex.proyectos.discografia.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepository;

    @Autowired
    private IArtistaRepository artistaRepository;

    @SuppressWarnings("null")
    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Disco> HandlePostDiscoRequest(@RequestBody Disco disco){
        if (artistaRepository.existsById(disco.idArtista)){
            return new ResponseEntity<>(discoRepository.save(disco), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
    }

    @GetMapping(
        value = "/disco",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscoRequest(){
        return new ResponseEntity<>(discoRepository.findAll(), HttpStatus.OK);
    }

    @SuppressWarnings("null")
    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Disco> HandleGetDiscoRequest(@PathVariable String id){
        Optional<Disco> disco = discoRepository.findById(id);
        return disco.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) {
        return new ResponseEntity<>(discoRepository.findDiscosByIdArtista(id), HttpStatus.OK);
    }
}
