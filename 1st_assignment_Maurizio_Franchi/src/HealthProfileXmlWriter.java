import java.io.File;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.xpath.XPathExpressionException;

import model.HealthProfile;
import model.Person;
import dao.PeopleStore;

public class HealthProfileXmlWriter {
	public static PeopleStore people = new PeopleStore();
	
	//read data from the starting XML file (people.xml)
	//and put them into a PeopleStore instance
	public static void initializeDB(){
		HealthProfileAssignment1 HP = new HealthProfileAssignment1();
		List<String> peopleInfo = null;
		try{
			peopleInfo = HP.readAllInfo();
		}catch(XPathExpressionException e){
			e.printStackTrace();
		}
		
		//"saves" all the retrieved information about people
		for(int i = 0; i<peopleInfo.size();i+=8){
			Person pallino = new Person(peopleInfo.get(i+0), //id
										peopleInfo.get(i+1), //firstname
										peopleInfo.get(i+2), //lastname
										peopleInfo.get(i+3)); //birthdate
			
			pallino.setHProfile(new HealthProfile(peopleInfo.get(i+4), 	// last update
    											  Double.parseDouble(peopleInfo.get(i+5)), //weight
    											  Double.parseDouble(peopleInfo.get(i+6)))); //height
			
			//bmi will be calculated in HealthProfile
			
			people.getData().add(pallino);
		}
	}
	
	public static void main(String[] args) throws Exception{
		initializeDB();
		
		JAXBContext jc = JAXBContext.newInstance(PeopleStore.class);
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		m.marshal(people, new File("people_marshaller.xml")); //marshalling into a file
		
		m.marshal(people, System.out); //marshalling into the system default output
	}
	
}