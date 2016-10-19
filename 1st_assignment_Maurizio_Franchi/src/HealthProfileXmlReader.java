import java.io.FileReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import dao.PeopleStore;
import model.Person;

public class HealthProfileXmlReader {
	public static PeopleStore people = new PeopleStore();
	
	public static void main(String[] args) throws Exception{
		JAXBContext jc = JAXBContext.newInstance(PeopleStore.class);
		
		System.out.println("Output from our XML File: ");
		
		Unmarshaller um = jc.createUnmarshaller();
		people = (PeopleStore) um.unmarshal(new FileReader("people.xml"));
		List<Person> list = people.getData();
		
		//print data obtained from xml
		for (Person person : list){
			System.out.println("Person: " + person.getFirtname() + " " + person.getLastname() + " born " + person.getBirthdate());
		}
	}
}
