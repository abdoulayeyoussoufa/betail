package sn.seysoo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A VolMessage.
 */

@Document(collection = "vol_message")
public class VolMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    public String getId() {
        return id;
    }
    
    @DBRef
    Animal animal;
    
    @DBRef
    FicheGeolocalisatio  dernierePosition;
    
    @DBRef
    Police police;
    
    

    public FicheGeolocalisatio getDernierePosition() {
		return dernierePosition;
	}

	public Police getPolice() {
		return police;
	}

	public void setPolice(Police police) {
		this.police = police;
	}

	public void setDernierePosition(FicheGeolocalisatio dernierePosition) {
		this.dernierePosition = dernierePosition;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VolMessage volMessage = (VolMessage) o;
        if (volMessage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, volMessage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VolMessage{" +
            "id=" + id +
            '}';
    }
}
