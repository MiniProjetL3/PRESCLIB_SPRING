package presc_lib.entities;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("VB")

public class Valeur_bool extends Validation implements Serializable{
    
	private Boolean val_bool;

	public Boolean getVal_bool() {
		return val_bool;
	}

	public void setVal_bool(Boolean val_bool) {
		this.val_bool = val_bool;
	}

	public Valeur_bool() {
		super();
		// TODO Auto-generated constructor stub
	}
}
