package presc_lib.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import presc_lib.entities.Historique_Hospitalisation;
import presc_lib.entities.Prescription;
import presc_lib.exception.EntityException;
import presc_lib.exception.ResourceNotFoundException;
import presc_lib.metier.IPrescriptionMetier;

@RestController
@CrossOrigin("*")
public class PrescriptionRestService {
	@Autowired
   private IPrescriptionMetier iPrescriptionMetier;

   @RequestMapping(value = "/prescriptions",method = RequestMethod.POST)
public Prescription save(@RequestBody Prescription entity) {
	return iPrescriptionMetier.save(entity);
}

@RequestMapping(value = "/prescriptions/{id}",method = RequestMethod.PUT)
public Prescription update(@PathVariable Long id,@RequestBody Prescription entity) {
	return iPrescriptionMetier.update(id, entity);
}

@RequestMapping(value = "/prescriptions",method = RequestMethod.GET)
public List<Prescription> getAll() {
	return iPrescriptionMetier.getAll();
}

@RequestMapping(value = "/activatePrescriptions",method = RequestMethod.GET)
public List<Prescription> ActivatePrescription() throws EntityException,ResourceNotFoundException
{
	     try {
				List<Prescription> liste=iPrescriptionMetier.ActivatePrescription();
							if(liste.size()==0)
							{
								
								throw new ResourceNotFoundException("Prescription not found");
							}
							return liste;
				
			} catch (EntityException e) {
				throw new EntityException("Internal Server Exception while getting exception");
					}

}




@RequestMapping(value = "/prescriptions/{id}",method = RequestMethod.GET)
public Prescription getById(@PathVariable Long id)throws EntityException,ResourceNotFoundException {
	
	 try {
			Prescription presc=iPrescriptionMetier.getById(id);
						if(presc==null)
						{
							
							throw new ResourceNotFoundException("Prescription not found");
						}
						return presc;
			
		} catch (EntityException e) {
			throw new EntityException("Internal Server Exception while getting exception");
				}
}

//ici on archive une prescription(by id prescription) ainsi que leurs le contenu
@RequestMapping(value = "/archivePrescription/{id}",method = RequestMethod.GET)
public void stop(@PathVariable Long id) {
	iPrescriptionMetier.archivePresc(id);
}



@RequestMapping(value = "/allPatientPrescriptionByService",method = RequestMethod.GET)
public Page<Prescription> allPatientPrescriptionByService(
		@RequestParam(name="id") Long idH,
		@RequestParam(name="page",defaultValue="0") int page,
		@RequestParam(name="size",defaultValue="5") int size)throws EntityException,ResourceNotFoundException {
	
	 try {
		 Page<Prescription> presc=iPrescriptionMetier.allPatientPrescriptionByService(idH,PageRequest.of(page, size));
						if(presc==null)
						{
							
							throw new ResourceNotFoundException("has not Prescription yet on this service");
						}
						return presc;
			
		} catch (EntityException e) {
			throw new EntityException("Internal Server Exception while getting exception");
				}
}

//All prescription in service where patient réside
@RequestMapping(value = "/allPrescriptionInCurrentService",method = RequestMethod.GET)
public Page<Prescription> allPrescriptionInCurrentService(
		@RequestParam(name="id") Long idH,
		@RequestParam(name="page",defaultValue="0") int page,
		@RequestParam(name="size",defaultValue="5") int size)throws EntityException,ResourceNotFoundException {
	
	 try {
		 Page<Prescription> presc=iPrescriptionMetier.allPrescriptionInCurrentService(idH,PageRequest.of(page, size));
						if(presc==null)
						{
							
							throw new ResourceNotFoundException("has not Prescription yet on this service");
						}
						return presc;
			
		} catch (EntityException e) {
			throw new EntityException("Internal Server Exception while getting exception");
				}
}



@RequestMapping(value = "/nbrePrescriptionActifInHosp",method = RequestMethod.GET)
public int nbrePrescriptionActifInHosp(@RequestParam(name="id") Long idH) {
	return iPrescriptionMetier.nbrePatientPrescriptionByHosp(idH);
}

@RequestMapping(value = "/nbrePrescriptionActifByPatient",method = RequestMethod.GET)
public int nbrePrescriptionActifByPatient(@RequestParam(name="id") Long id) {
	return iPrescriptionMetier.nbrePrescriptionActifByPatient(id);
}

//select la list des prescription actif par patient
@RequestMapping(value = "/actifPrescriptionByPatient",method = RequestMethod.GET)
public Page<Prescription> ListprescriptionActifByPatient(
		@RequestParam(name="id") Long id,
		@RequestParam(name="page",defaultValue="0") int page,
		@RequestParam(name="size",defaultValue="5") int size)throws EntityException,ResourceNotFoundException {
	
	 try {
		 Page<Prescription> presc=iPrescriptionMetier.ListPrescriptionActifByPatient(id, PageRequest.of(page, size));
						if(presc==null)
						{
							
							throw new ResourceNotFoundException("has no actif Prescription");
						}
						return presc;
			
		} catch (EntityException e) {
			throw new EntityException("Internal Server Exception while getting exception");
				}
}





}
