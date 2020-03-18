package presc_lib.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Patient implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int CNI;
	private String profession;
	private String nom;
	private String prenom;
	private String adress;
	private Date date_naissance;
	private String sexe;
	private String telephone;
	private String group_sanguin;
	private Boolean etat;
	@ManyToOne
	@JoinColumn(name="id_service")
	private Service service;
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
	 private Collection<Prescription>prescriptions;
	
	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Collection<Prescription> getPrescriptions() {
		return prescriptions;
	}
	public void setPrescriptions(Collection<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getCNI() {
		return CNI;
	}
	public void setCNI(int cNI) {
		CNI = cNI;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public Date getDate_naissance() {
		return date_naissance;
	}
	public void setDate_naissance(Date date_naissance) {
		this.date_naissance = date_naissance;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getGroup_sanguin() {
		return group_sanguin;
	}
	public void setGroup_sanguin(String group_sanguin) {
		this.group_sanguin = group_sanguin;
	}
	public Boolean getEtat() {
		return etat;
	}
	public void setEtat(Boolean etat) {
		this.etat = etat;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	
}
