package presc_lib.metier;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import presc_lib.dao.UserRepository;
import presc_lib.dao.User_ServiceRepository;
import presc_lib.entities.Patient;
import presc_lib.entities.User;
import presc_lib.entities.User_Service;
import presc_lib.exception.EntityException;
@Service
public class UserMetierImp implements IUserMetier{
    @Autowired
	private UserRepository userRepository;
    @Autowired
	private User_ServiceRepository user_serviceRepository;
    @Autowired
    private MailService mailService;
    
	@Override
	public User save(User entity) {
		long code = ThreadLocalRandom.current().nextLong(10000,99999);
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmssSSS");
		String strDate= formatter.format(date);
		String password=strDate+code;
		entity.setEtat(true);
		entity.setPassword(password);
		
		String content="Bonjour, \n"+entity.getNom()+" "+entity.getPrenom()+"\n"+"  Voici Votre mot de passe: "+password;
		try {
			mailService.send(entity.getEmail(), "a.presclib@gmail.com", "Envoie de mpt passe", content);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User u=userRepository.save(entity);
		return u;
	}

	@Override
	public User update(Long id,User entity) {
		entity.setId(id);
		entity.setEtat(true);
		return userRepository.save(entity);
	}

	@Override
	public List<User> getAll() {
		return userRepository.findAllUsers();
	}

	@Override
	public User getById(Long id) {
		//return userRepository.getOne(id);
		return userRepository.findById(id).orElse(null);
	}
	
	
	
	
	
	@Override
	public void stop(Long id)
	{
		
		userRepository.archiverUser(id);
	}

	@Override
	public void affecterUserToSerice(Long idU, Long idS) {
		userRepository.insertUserToServicve(idU, idS);
		
	}

	@Override
	public void stopUserFromSerice(Long idU, Long idS) {
		
		userRepository.stopUserfromService(idU, idS);
		
		
	}
	

	@Override
	public User login(String email, String password) // throws EntityException 
	{
		String pwd="";
		AES crypt=new AES();
		pwd=crypt.encrypt(password, "ssshhhhhhhhhhh!!!!");
        return userRepository.login(email, pwd);
	}

    
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Page<User> searchUser(String mc, Pageable p)// throws EntityException
	{
		
		return userRepository.searchUser(mc,p);
	}

	@Override
	public void enableUser(Long idU) {
		userRepository.enableUser(idU);
		
	}

	

	@Override
	public void releaseUserFromService(Long idU) {
		
			userRepository.releaseUserfromAllServiceActif(idU);
		
	}

	@Override
	public Page<User_Service> findServicesByUser(Long idU,Pageable p) //throws EntityException {
	{	
		
		return user_serviceRepository.findServicesByUser(idU,p);
	}
	
	@Override
	public List<User_Service> findServicesByUserL(Long idU) //throws EntityException {
	{	
		
		return user_serviceRepository.findServicesByUserL(idU);
	}

	@Override
	public Page<User_Service> findHistoriqueServicesByUser(Long idU, Pageable p) throws EntityException {
		// TODO Auto-generated method stub
		return user_serviceRepository.findHistoriqueServicesByUser(idU, p);
	}

	/*@Override
	public boolean checkExistanceUserEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}*/

	@Override
	public boolean checkExistanceUserInfo(String email,String nom, String prenom, Date dateN) {
		
		User u= userRepository.checkUserExistenceByInfo(email, nom, prenom, dateN);
		if(u!=null)
			return true;//il ya
		else return false;// il ya pas
		
	}

	@Override
	public boolean nbreUserWithEmail(User u, Long id) {
				
		int nbre=userRepository.nbreUserWithSameEmail(u.getEmail());
		
		if(nbre>1) return false;
		else {
			if(nbre==0) return true;
			else if(nbre==1)
				{
				User utilisateur=userRepository.findByEmail(u.getEmail());
				if(utilisateur.getId()==id)
				return true;
				else return false;
				}
		}
			
		return false;
	}

	@Override
	public void uploadPhoto(MultipartFile file, Long id) throws Exception {
		
		
	 User p=userRepository.findById(id).orElse(null);
       p.setPhoto(id+".jpg");
       Files.write(Paths.get(System.getProperty("user.home")+"/ecom/presclib/"+p.getPhoto()),file.getBytes());
       userRepository.save(p);
	}
    
	
    public byte[] getPhoto(Long id) throws Exception{
        User p=userRepository.findById(id).orElse(null);
        System.out.print("photo");
        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/ecom/presclib/"+p.getPhoto()));
    }

	@Override
	public List<User_Service> findDoctorsOfServiceSelected(Long idS) {
		
		return user_serviceRepository.findDoctorsOfServiceSelected(idS);
	}
	
	
	

	
}
