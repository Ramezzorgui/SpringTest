package com.example.springtest.service;

import com.example.springtest.entity.*;
import com.example.springtest.repository.CliniqueRepository;
import com.example.springtest.repository.MedecinRepository;
import com.example.springtest.repository.PatientRepository;
import com.example.springtest.repository.RendezVousRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@RestController
@Service
@Slf4j
public class ServiceExamenImpl implements ServiceExamen{
    @Autowired
    private CliniqueRepository cliniqueRepository;
    @Autowired
    private MedecinRepository medecinRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private RendezVousRepository rendezVousRepository;

    //Ajouter un clinique
    @Override
    public Clinique addClinique(Clinique clinique) {
        return cliniqueRepository.save(clinique);
    }

    //Ajouter un medecin en l'affectant à une clinique
    @Override
    public Medecin addMedecinAndAssignToClinique(Medecin medecin, Long idClinique) {
         Clinique c= cliniqueRepository.findById(idClinique).orElse(null);
         List<Medecin> list=new ArrayList<>();
         list.add(medecin);
         if (c.getMedecins()== null){
             c.setMedecins(list);
         }
         else {
             c.getMedecins().add(medecin);
         }
         return medecinRepository.save(medecin);
    }

    @Override
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

   //Ajouter un rendezvous et l'affecter à la fois au medecin et au patient
    @Override
    @Transactional
    public RendezVous addRDVAndAssignMedAndPatient(RendezVous rendezVous, Long idMedecin, Long idPatient) {
        Medecin medecin= medecinRepository.findById(idMedecin).orElse(null);
        Patient patient= patientRepository.findById(idPatient).orElse(null);
        rendezVous.setPatient(patient);
        rendezVous.setMedecin(medecin);
        return rendezVousRepository.save(rendezVous);
    }

    //Afficher la liste des rendezvous du clinique dont la specialité est Cardiologue
    @Override
    public List<RendezVous> getRendezVousByCliniqueAndSpecialite(Long idClinique, Specialite specialite) {
        Clinique clinique= cliniqueRepository.findById(idClinique).orElse(null);
        List<RendezVous> list= rendezVousRepository.findAll();
        List<RendezVous> resultat= new ArrayList<>();
        for (RendezVous r:list){
            Medecin medecin=r.getMedecin();
            if(medecin.getCliniques()!=null){
                for(Clinique clinique1:medecin.getCliniques()){
                    if(clinique.equals(clinique1) && medecin.getSpecialite().equals(specialite)){
                        resultat.add(r);
                    }
                }
            }
        }
        return resultat;
    }
    //Afficher le nombre des rendezVous d'un medecin
    @Override
    public int getNbrRendezVousMedecin(Long idMedecin) {
        int nb=0;
        Medecin medecin= medecinRepository.findById(idMedecin).orElse(null);
        List<RendezVous> list=rendezVousRepository.findAll();
        for (RendezVous r:list){
            if(r.getMedecin().equals(medecin)){
                nb++;
            }
        }
        return nb;
    }

    /*//Service programmé automatiquement permet d'afficher les rndv dont la date est > à la date systeme toutes les 30 secondes
    @Scheduled(cron= "30 * * * * *")
    public void retrieveRendezVous(){
        List<RendezVous> list=rendezVousRepository.findAll();
        for (RendezVous r:list){
            if(r.getDateRDV().after(new Date())){
                log.info("la liste des RendezVous:"+r.getDateRDV()+"Medecin:"+r.getMedecin().getNomMedecin() +"Pattient:"+r.getPatient().getNomPatient());
            }
        }
        }*/

}
