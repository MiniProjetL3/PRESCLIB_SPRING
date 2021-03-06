package presc_lib.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fasterxml.jackson.annotation.JsonFormat;

import presc_lib.entities.Validation;

public interface ValidationRepository extends JpaRepository<Validation, Long>
{ 
	//DateFormat df=new SimpleDateFormat("dd/mm/yyyy");
	//and dateV=+df.parse(\"26/03/2020\")
	@Query(value = "SELECT * FROM Validation u where u.id_contenu= :idC ORDER BY dateV Asc",nativeQuery = true)
	public List<Validation> findValuesByContent(@Param("idC")  Long idC);
	
	@Query(value = "SELECT * FROM Validation v where v.id_contenu= :idC And v.id_fiche_infirmier= :idF ORDER BY dateV Asc",nativeQuery = true)
	public List<Validation> listValidationByContenuAndFile(@Param("idC")  Long idC,@Param("idF")  Long idF);
	
	@Query(value = "SELECT * FROM Validation v where  v.id_fiche_infirmier= :idF ORDER BY dateV Asc",nativeQuery = true)
	public List<Validation> listValidationByFileCare(@Param("idF")  Long idF);
	
	
	@Query(value = "SELECT count(*) FROM Validation v where  v.id_fiche_infirmier= :idF and v.etat=false ",nativeQuery = true)
	public int validationEtatFalse(@Param("idF")  Long idF);
	
	
	
	
	
	
	@Transactional 
	@Modifying
	@Query(value = "update Validation u set u.etat = false where u.id_contenu= :idC",nativeQuery = true
	  )
	public void stopValidationByContenu(@Param("idC")  Long idC);
}