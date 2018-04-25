
/**
 *
 @author Bontemps Goblet
**/

public class Contact {

    private String nom, mail, numTel, adresse;

    public Contact() {
    }

    public Contact(String nom, String mail, String numTel, String adresse) {
        this.nom = nom;
        this.mail = mail;
        this.numTel = numTel;
		this.adresse = adresse;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getnom() {
        return nom;
    }

    public void setnom(String nom) {
        this.nom = nom;
    }

    public String getnumTel() {
        return numTel;
    }

    public void setnumTel(String numTel) {
        this.numTel = numTel;
    }
	
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void parseByteArray(byte[] record, char separator) {
        if(record != null) {
            String values = new String(record);
            int index = values.indexOf(separator);
            int lastIndex = values.lastIndexOf(separator);
            this.nom = values.substring(0, index);
            this.numTel = values.substring(index+1, lastIndex);
            this.mail = values.substring(lastIndex+1);
			//this.adresse = values.substring(lastIndex+1);
        }
    }

    public byte[] toByteArray(char separator) {
        return (this.nom + separator + this.numTel + separator +
        this.mail + separator + this.adresse).getBytes();
    }

}