import java.io.File;

import model.Person;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import dao.PeopleStore;

public class HealthProfileJsonReader {
	public static PeopleStore people = new PeopleStore();
	
	public static void main(String[] args) throws Exception{
		
		//Jackson Object Mapper
		ObjectMapper mapper = new ObjectMapper();
		
		//Adding the Jackson Module to process JAXB annotation
		JaxbAnnotationModule module = new JaxbAnnotationModule();
		
		//configure as necessary
		mapper.registerModule(module);
		
		people = mapper.readValue(new File("people.json"), PeopleStore.class);
		
		//print data obtained from json
		for(Person person : people.getData()){
			System.out.println("Person: " + person.getFirtname() + " " + person.getLastname() + " born " + person.getBirthdate());
		}
	}
}
