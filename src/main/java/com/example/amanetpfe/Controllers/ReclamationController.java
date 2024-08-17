package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Entities.Reclamation;
import com.example.amanetpfe.Services.Interfaces.IReclamationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reclamation")

public class ReclamationController {
    @Autowired
    private IReclamationService reclamationService ;

    @GetMapping("/showReclamation")
    @Operation(description = "show all reclamations")
    ResponseEntity<List<Reclamation>> getAllReclamations(){
        List<Reclamation> result = reclamationService.allReclamations();
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @GetMapping("/show/{id}")
    @Operation(description = "show one reclamation")
    ResponseEntity<Reclamation> getOneReclamation(@PathVariable("id") Integer idReclamation){
        Reclamation result = reclamationService.retrieveReclamation(idReclamation);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addReclamation")
    @Operation(description = "add new reclamation")
    ResponseEntity<Reclamation> addReclamation(@RequestBody Reclamation reclamation){
        Reclamation result = reclamationService.addReclamtion(reclamation);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/edit/{id}")
    @Operation(description = "edit one reclamation")
    Reclamation editReclamation(@PathVariable("id") Integer idReclamation, @RequestBody Reclamation reclamation){
        return reclamationService.updateReclamation(reclamation);
    }

    @DeleteMapping("/remove/{reclamationId}")
    @Operation(description = "remove user by id")
    ResponseEntity<Reclamation> removeReclamation(@PathVariable("reclamationId") Integer reclamationId){
        reclamationService.removeReclamation(reclamationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create/{idUser}")
    public Reclamation createReclamation(@RequestBody Reclamation reclamation, @PathVariable Integer idUser) {
        return reclamationService.createReclamation(reclamation, idUser);
    }
}
