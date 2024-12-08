package com.example.springtest.service;

import com.example.springtest.entity.*;

import java.util.List;

public interface ServiceExamen {
    Clinique addClinique(Clinique clinique);

    Medecin addMedecinAndAssignToClinique(Medecin medecin, Long idClinique);

    Patient addPatient (Patient patient);

    RendezVous addRDVAndAssignMedAndPatient(RendezVous rendezVous, Long idMedecin, Long idPatient);

    List<RendezVous> getRendezVousByCliniqueAndSpecialite(Long idClinique, Specialite specialite);

    int getNbrRendezVousMedecin(Long idMedecin);


}
