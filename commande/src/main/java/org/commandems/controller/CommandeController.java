package org.commandems.controller;


import lombok.RequiredArgsConstructor;
import org.commandems.exceptions.CommandeNotFoundException;
import org.commandems.exceptions.ImpossibleAjouterCommandeException;
import org.commandems.model.Commande;
import org.commandems.repository.CommandeDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CommandeController {

    private final CommandeDao commandesDao;

    @PostMapping (value = "/commandes")
    public ResponseEntity<Commande> ajouterCommande(@RequestBody Commande commande){

        Commande nouvelleCommande = commandesDao.save(commande);

        if(nouvelleCommande == null) throw new ImpossibleAjouterCommandeException("Impossible d'ajouter cette commande");

        return new ResponseEntity<Commande>(commande, HttpStatus.CREATED);
    }

    @GetMapping(value = "/commandes/{id}")
    public Optional<Commande> recupererUneCommande(@PathVariable int id){

        Optional<Commande> commande = commandesDao.findById(id);

        if(!commande.isPresent()) throw new CommandeNotFoundException("Cette commande n'existe pas");

        return commande;
    }

    /*
     * Permet de mettre à jour une commande existante.
     * save() mettra à jours uniquement les champs renseignés dans l'objet commande reçu. Ainsi dans ce cas, comme le champs date dans "commande" n'est
     * pas renseigné, la date précédemment enregistrée restera en place
     **/
    @PutMapping(value = "/commandes")
    public void updateCommande(@RequestBody Commande commande) {

        commandesDao.save(commande);
    }
}