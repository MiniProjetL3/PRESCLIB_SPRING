package presc_lib.metier;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import presc_lib.dao.ContenuRepository;
import presc_lib.dao.Historique_HospitalisationRepository;
import presc_lib.dao.PrescriptionRepository;
import presc_lib.entities.Contenu;
import presc_lib.entities.Historique_Hospitalisation;
import presc_lib.entities.Prescription;
import presc_lib.exception.EntityException;
@Service
public class PrescriptionMetierImp implements IPrescriptionMetier{
    @Autowired
	private PrescriptionRepository prescriptionRepository;
    @Autowired
	private Historique_HospitalisationRepository historiqueHRepository;
    
    @Autowired
	private ContenuRepository contenuRepository;
    
    
    
	@Override
	public Prescription save(Prescription entity) {
		entity.setEtat(true);
		//entity.setDateP(new Date());
		return prescriptionRepository.save(entity);
	}

	@Override
	public Prescription update(Long id, Prescription entity) {
		entity.setId(id);
		entity.setEtat(true);
	
		return prescriptionRepository.save(entity);
	}

	@Override
	public List<Prescription> getAll() {
		
		return prescriptionRepository.findAll();
	}

	@Override
	public Prescription getById(Long id) 
	{
		return prescriptionRepository.findById(id).orElse(null);
	}
 
	
	@Override
	public void stop(Long idPatient) {
		//ici on stop tt les préscriptions par patient
		prescriptionRepository.stopPrescription(idPatient);
		List<Prescription> l=prescriptionRepository.listPrescByPatient(idPatient);
		for(int i=0;i<l.size();i++)
		{
			Long id=l.get(i).getId();
			
			contenuRepository.stopContenu(id);}
	}

	@Override
	public List<Prescription> ActivatePrescription() throws EntityException
	{
	return  prescriptionRepository.findActivatePrescription();
	}

	@Override
	public Page<Prescription> allPatientPrescriptionByService(Long idH, Pageable p)
			throws EntityException {
		
		Historique_Hospitalisation h=historiqueHRepository.findById(idH).orElse(null);
		Long idP=h.getPatient().getId();
		//Long idS=h.getService().getId();
		Date dateE=h.getDate_entre();
		Date dateS=h.getDate_sortie();
		System.out.print("idP"+idP);
		Page<Prescription> list=prescriptionRepository.allPatientPrescriptionByService(idP,dateE, dateS, p);
		System.out.print("bonj");
		return list;
	}

	@Override
	public Page<Prescription> allPrescriptionInCurrentService(Long idH, Pageable p)
			throws EntityException {
		Historique_Hospitalisation h=historiqueHRepository.findById(idH).orElse(null);
		Long idP=h.getPatient().getId();
		//Long idS=h.getService().getId();
		Date dateE=h.getDate_entre();
		return prescriptionRepository.allPrescriptionInCurrentService(idP,dateE, p);
	}

	@Override
	public int nbrePatientPrescriptionByHosp(Long idH) {
		Historique_Hospitalisation h=historiqueHRepository.findById(idH).orElse(null);
		Long idP=h.getPatient().getId();
		Date dateS=h.getDate_sortie();
		Date dateE=h.getDate_entre();
		return prescriptionRepository.nbrePatientPrescriptionByHosp(idP, dateE, dateS);
	}

	@Override
	public void archivePresc(Long id) {
		prescriptionRepository.archivePresc(id);
		contenuRepository.stopContenu(id);
		
	}

	@Override
	public int nbrePrescriptionActifByPatient(Long idPatient) {
		
		return prescriptionRepository.nbrePrescriptionActifParPAtient(idPatient);
	}

	@Override
	public Page<Prescription> ListPrescriptionActifByPatient(Long idPatient, Pageable p) throws EntityException {
		
		return prescriptionRepository.ListprescriptionActifByPatient(idPatient, p);
	}
}