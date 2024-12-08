package com.example.springtest.controller;

import com.example.springtest.entity.*;
import com.example.springtest.service.ServiceExamen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExamenController {
    @Autowired
    ServiceExamen serviceExamen;

    @PostMapping("/addClinique")
    public Clinique addClinique(@RequestBody Clinique clinique){
        return serviceExamen.addClinique(clinique);
    }
    @PostMapping("/addPatient")
    public Patient addPatient(@RequestBody Patient patient){
        return serviceExamen.addPatient(patient);
    }

    @PostMapping("/addMedecinAndAssignToClinique/{idClinique}")
    public Medecin addMedecinAndAssignToClinique(@RequestBody Medecin medecin,@PathVariable Long idClinique){
        return serviceExamen.addMedecinAndAssignToClinique(medecin,idClinique);
    }

    @PostMapping("/addRDVAndAssignMedAndPatient/{idPatient}/{idMedecin}")
    public RendezVous addRDVAndAssignMedAndPatient(@RequestBody RendezVous rendezVous, @PathVariable Long idMedecin, @PathVariable Long idPatient){
        return serviceExamen.addRDVAndAssignMedAndPatient(rendezVous,idMedecin, idPatient);
    }

    @GetMapping("/getRendezVousByCliniqueAndSpecialite/{idClinique}/{sp}")
    public List getRendezVousByCliniqueAndSpecialite(@PathVariable Long idClinique, @PathVariable Specialite sp){
        return serviceExamen.getRendezVousByCliniqueAndSpecialite(idClinique, sp);
    }

    @GetMapping("/getNbrRendezVousMedecin/{idMedecin}")
    public int getNbrRendezVousMedecin(@PathVariable Long idMedecin){
        return serviceExamen.getNbrRendezVousMedecin(idMedecin);
    }


}
