import java.io.File;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import model.HealthProfile;
import model.Person;
import dao.PeopleStore;

public class HealthProfileJsonWriter {
	public static PeopleStore people = new PeopleStore();
	
	//read data from the starting XML file (people.xml)
	//and put them into a PeopleStore instance
	public static void inizializeDB(){
		HealthProfileAssignment1 HP = new HealthProfileAssignment1();
		List<String> peopleInfo = null;
		try{
			peopleInfo = HP.readAllInfo();
		}
		catch(XPathExpressionException e){
			e.printStackTrace();
		}
		
		//"saves" all the retrieved information about people
		for(int i = 0; i<peopleInfo.size(); i += 8){
			Person pallino = new Person(peopleInfo.get(i+0), //id
							 peopleInfo.get(i+1), //firstname
							 peopleInfo.get(i+2), //lastname
							 peopleInfo.get(i+3)); //birthday
			pallino.setHProfile(new HealthProfile(peopleInfo.get(i+4), //lastupdate 
							    Double.parseDouble(peopleInfo.get(i+5)), //weight
							    Double.parseDouble(peopleInfo.get(i+6)))); //height
			
			//bmi will be calculated in HealthProfile
			people.getData().add(pallino);
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		inizializeDB();
		
		//Jackson Object Mapper
		ObjectMapper mapper = new ObjectMapper();
		
		//Adding the Jackson Module to process JAXB annotations
		JaxbAnnotationModule module = new JaxbAnnotationModule();
		
		//configure as necessary
		mapper.registerModule(module);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
		
		String result = mapper.writeValueAsString(people);
		mapper.writeValue(new File("people.json"), people);
		
		//print result
		System.out.println(result);
	}

}