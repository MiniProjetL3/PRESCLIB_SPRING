package presc_lib.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import presc_lib.dao.TraitementRepository;
import presc_lib.dao.ValidationRepository;
import presc_lib.entities.Traitement;

@Service
public class TraitementMetierImp  implements  ITraitementMetier
{
	@Autowired
     private TraitementRepository traitementRepository;
	
	@Autowired
    private ValidationRepository validationRepository;
	@Override
	public Traitement save(Traitement entity) {
		entity.setEtat(true);
		int nbre=(int)24/entity.getRythme();
		entity.setNbre_par_jour(nbre);
		System.out.println("nbre"+nbre);
		return traitementRepository.save(entity);
	}

	@Override
	public Traitement update(Long id, Traitement entity) {
		entity.setId(id);
		entity.setEtat(true);
		int nbre=(int)24/entity.getRythme();
		entity.setNbre_par_jour(nbre);
		return traitementRepository.save(entity);
	}

	@Override
	public Traitement getById(Long idT) {
		
		return traitementRepository.findById(idT).orElse(null);
	}

	@Override
	public void stop(Long idT) {
		traitementRepository.stopTraitement(idT);
		validationRepository.stopValidationByContenu(idT);
	}

	
	@Override
	public List<Traitement> getAllContentByPrescription(Long idP) {
		return traitementRepository.findAllTraitmentByPrescription(idP);
	}
	
	//on l'utilise pas
	@Override
	public List<Traitement> getAll() {
		
		return traitementRepository.findAll();
	}

	@Override
	public List<Traitement> getActifContentByPrescription(Long idP) {
		// TODO Auto-generated method stub
		return traitementRepository.findActifTraitmentByPrescription(idP);
	}

}
