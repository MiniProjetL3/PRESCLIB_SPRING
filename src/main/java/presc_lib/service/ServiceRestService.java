package presc_lib.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import presc_lib.entities.Service;
import presc_lib.metier.IServiceMetier;
import presc_lib.metier.ServiceMetierImp;

@RestController
public class ServiceRestService {
    
	@Autowired
	private IServiceMetier iServiceMetier;
    
	@RequestMapping(value = "/services",method = RequestMethod.POST)
	public Service save(@RequestBody Service entity) {
		return iServiceMetier.save(entity);
	}

	@RequestMapping(value = "/services/{id}",method = RequestMethod.PUT)
	public Service update(@PathVariable Long id,@RequestBody Service entity) {
		return iServiceMetier.update(id, entity);
	}

	@RequestMapping(value = "/services",method = RequestMethod.GET)
	public List<Service> getAll() {
		return iServiceMetier.getAll();
	}

	@RequestMapping(value = "/services/{id}",method = RequestMethod.GET)
	public Service getById(@PathVariable Long id) {
		return iServiceMetier.getById(id);
	}
    
	@RequestMapping(value = "/archiveService/{id}",method = RequestMethod.PUT)
	public void stop(@PathVariable Long id) {
		iServiceMetier.stop(id);
	}
}
