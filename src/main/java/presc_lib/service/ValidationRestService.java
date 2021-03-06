package presc_lib.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import presc_lib.entities.Tests;
import presc_lib.entities.Validation;
import presc_lib.exception.EntityException;
import presc_lib.exception.ResourceNotFoundException;
import presc_lib.metier.IValidationMetier;

@RestController 
@CrossOrigin("*")
public class ValidationRestService {
	@Autowired
	private IValidationMetier iValidationMetier;

	@RequestMapping(value = "/validations",method = RequestMethod.POST)
	public Validation save(@RequestBody Validation entity) {
		return iValidationMetier.save(entity);
	}

	@RequestMapping(value = "/validations/{id}",method = RequestMethod.PUT)
	public Validation update(@PathVariable Long id,@RequestBody Validation entity) {
		return iValidationMetier.update(id, entity);
	}

	/***GetALL
	 * @RequestMapping(value = "validations",method = RequestMethod.GET) public
	 * List<Validation> getAll() { return iValidationMetier.getAll(); }
	 */

	@RequestMapping(value = "/validations/Content/{idC}",method = RequestMethod.GET)
	public List<Validation> getValuesByContent(@PathVariable  Long idC) {
		return iValidationMetier.getValuesByContent(idC);
	}
    
	@RequestMapping(value = "/validations/{id}",method = RequestMethod.GET)
	public Validation getById(@PathVariable  Long id) throws EntityException,ResourceNotFoundException {
		try {
			Validation v=iValidationMetier.getById(id);
						if(v==null)
						{
							
							throw new ResourceNotFoundException("Value not found");
						}
						return v;
			
		} catch (EntityException e) {
			throw new EntityException("Internal Server Exception while getting exception");
				}
	}

	/***stop validation
	 * @RequestMapping(value = "/stopValidation",method = RequestMethod.PUT) public
	 * void stop(@PathVariable Long id) { iValidationMetier.stop(id); }
	 */
	
	@RequestMapping(value = "/validationsByContenuAndFileCare",method = RequestMethod.GET)
	public List<Validation> validationsByContenuAndFileCare(
			@RequestParam(name="idC") Long idC,
			@RequestParam(name="idF") Long idF) throws EntityException,ResourceNotFoundException {
		try {
			List<Validation> v=iValidationMetier.listValidationByContenuAndFile(idC, idF);
						if(v==null)
						{
							
							throw new ResourceNotFoundException("Value not found");
						}
						return v;
			
		} catch (EntityException e) {
			throw new EntityException("Internal Server Exception while getting exception");
				}
	}

	
	@RequestMapping(value = "/validationsByFileCare",method = RequestMethod.GET)
	public List<Validation> validationsByFileCare(
			@RequestParam(name="idF") Long idF) throws EntityException,ResourceNotFoundException {
		try {
			List<Validation> v=iValidationMetier.listValidationByFileCare(idF);
						if(v==null)
						{
							
							throw new ResourceNotFoundException("Value not found");
						}
						return v;
			
		} catch (EntityException e) {
			throw new EntityException("Internal Server Exception while getting exception");
				}
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/nbreValidationsDisable",method = RequestMethod.GET)
	public int validationsDisable(@RequestParam(name="id") Long idF)  
	{
		
			return iValidationMetier.validationEtatFalse(idF);
						
						
			
		
	}


}
