package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

//XmlRootElement defines the root element of the XML tree to which this class will be serialized
// --> <person> ... </person>
@XmlRootElement(name = "person")

//XmlType can optionally define the order in which the fields of person are written
@XmlType(propOrder = {"firstname", "lastname", "birthdate", "healthprofile"})

//XmlAccessorType indicates what to use to create the mapping: either FIELDS, PROPERTIES (i.e., getters/setters), PUBLIC_MEMBER or NAME (which means, you should indicate manually)
@XmlAccessorType(XmlAccessType.FIELD)

public class Person {
	private String firstname;
	private String lastname;
	//XmlElement indicates the element to use for this field. Only used if the name in XML will be different than that in the class
	@XmlElement(name="healthprofile")
	private HealthProfile healthprofile;	
	private String birthdate;
	
	// XmlAttribute indicates that this field will be serialized as an attribute
	@XmlAttribute(name="id")
	private String personId;
	
	public Person(String personId, String fname, String lname, String birthdate, HealthProfile hp){
		this.setPersonId(personId);
		this.setFirstname(fname);
		this.setLastname(lname);
		this.setBirthdate(birthdate);
		this.healthprofile = hp;
	}
	
	public Person(String personId, String fname, String lname, String birthdate){
		this.setPersonId(personId);
		this.setFirstname(fname);
		this.setLastname(lname);
		this.setBirthdate(birthdate);
		this.healthprofile = new HealthProfile();
	}
	
	public Person(){
		this.firstname = "Pinco";
		this.lastname = "Pallino";
		this.healthprofile = new HealthProfile();
		
		//setting personId to a random "number" between 0001 and 9999
		this.personId = String.format("%04d", Math.round(Math.floor(Math.random()*9998) + 1));
		this.birthdate = "1980-01-01";
	}
	
	public String getFirtname() {return firstname;}
	public void setFirstname(String firstname) {this.firstname = firstname;}
	
	public String getLastname() {return lastname;}
	public void setLastname(String lastname) {this.lastname = lastname;}
	
	public HealthProfile getHProfile() {return healthprofile;}
	public void setHProfile(HealthProfile healthprofile) {this.healthprofile = healthprofile;}
	
	public String getBirthdate() {return birthdate;}
	public void setBirthdate(String birthdate) {this.birthdate = birthdate;}
	
	public String getPersonId() {return personId;}
	public void setPersonId(String personId2) {this.personId = personId2;}
}
