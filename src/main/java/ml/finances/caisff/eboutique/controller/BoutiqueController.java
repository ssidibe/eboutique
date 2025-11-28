package ml.finances.caisff.eboutique.controller;


import ml.finances.caisff.eboutique.ApiErrorMessage;
import ml.finances.caisff.eboutique.entities.Boutique;
import ml.finances.caisff.eboutique.services.BoutiqueService;
import ml.finances.caisff.eboutique.services.MyApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/boutiques")
public class BoutiqueController {

    private final BoutiqueService boutiqueService;

    public BoutiqueController(BoutiqueService boutiqueService) {
        this.boutiqueService = boutiqueService;
    }

    @GetMapping
    public List<Boutique> getBoutiques() {
        return boutiqueService.getAllBoutiques();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBoutiqueById(@PathVariable("id") Short id) {
        try {
            Boutique boutique=boutiqueService.findBoutique(id);
            return ResponseEntity.ok(boutique);
        } catch (MyApiException e) {
            return e.getResponse();
        }
    }

    @PostMapping
    public ResponseEntity<?> createBoutique(@RequestBody Boutique boutique) {
        try {
            Boutique b=boutiqueService.create(boutique);
            return ResponseEntity.ok(b);
        } catch (MyApiException e) {
            return e.getResponse();
        }
    }
}
