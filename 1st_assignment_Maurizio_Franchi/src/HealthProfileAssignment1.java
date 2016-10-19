import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HealthProfileAssignment1 {
	
	Document doc;
	XPath xpath;
	
	//load the XML in the constructor
	//so its methods can be also used in other classes
	public HealthProfileAssignment1(){
		try{
			this.loadXML();
		}
		catch(ParserConfigurationException | SAXException | IOException e){
			e.printStackTrace();
		}
	}
	
	public void loadXML() throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		doc = builder.parse("people.xml");
		
		//creating XPATH object
		getXPathObj();
	}
	
	public XPath getXPathObj(){
		XPathFactory factory = XPathFactory.newInstance();
		xpath = factory.newXPath();
		return xpath;
	}
	
	//Use XPATH to implement methods like getWeight and getHeight
	//(assumption: given the person ID)
	public Double getLastUpdate(String ID) throws XPathExpressionException {
		XPathExpression expr = xpath.compile("/people/person[@id = '" + ID + "']/healthprofile/weight");
		Double val = (Double) expr.evaluate(doc, XPathConstants.NUMBER);
		return val;
	}
	
	public Double getWeight(String ID) throws XPathExpressionException{
		XPathExpression expr = xpath.compile("/people/person[@id = '" + ID + "']/healthprofile/weight");
		Double val = (Double) expr.evaluate(doc, XPathConstants.NUMBER);
		return val;
	}
	
	public Double getHeight(String ID) throws XPathExpressionException {
		XPathExpression expr = xpath.compile("/people/person[@id = '" + ID + "']/healthprofile/height");
		Double val = (Double) expr.evaluate(doc, XPathConstants.NUMBER);
		return val;
	}
	
	public Double getBmi(String ID) throws XPathExpressionException {
		XPathExpression expr = xpath.compile("/people/person[@id = '" + ID + "']/healthprofile/bmi");
		Double val = (Double) expr.evaluate(doc, XPathConstants.NUMBER);
	    return val;
	}
	
	//Read the XML file and return a list with all the information
	//(The information is printed in the function above)
	public List<String> readAllInfo() throws XPathExpressionException{
		List<String> peopleInfo = new ArrayList<String>();
		
		XPathExpression expr = xpath.compile("//person/@id | //person/*/text() | //person/*/*/text()");
		
		NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		
		//insert into a list the information
		for(int i = 0; i<nodes.getLength(); i++){
			//check if node text is empty
			if(nodes.item(i).getTextContent().replace("\n", "").replace("\t", "").isEmpty())
				continue;
			peopleInfo.add(nodes.item(i).getTextContent());
		}
		return peopleInfo;
	}
	
	//Make a function that prints all people in the list with detail (if >20, paginated)
	public void getAllInfo() throws XPathExpressionException{
		List<String> peopleInfo = this.readAllInfo();
		
		//nodes names are fixed, from the XML file structure
		String[] nodeNames = {"ID", "FIRSTNAME", "LASTNAME", "BIRTHDATE", "LAST UPDATE", "WEIGHT", "HEIGHT", "BMI"};
		int index = 0;
		
		//count the number of people already written
		int peopleCount = 0;
		boolean page = false;
		
		//print the information
		//paginated
		for (int i=0; i<peopleInfo.size(); i++){
			//check if there are no more information about person
			if(index%8 == 0){
				//carriage return before each person for better formatting of the output
				System.out.println("\n");
				//node names index come back to the start
				index = 0;
				//increment number of written people
				peopleCount++;
				//not yet paginated
				page = false;
			}
			
			//pagination (20 people per page)
			if(peopleCount%20 == 0 && !page){
				System.out.println("Press enter to continue...");
				//wait for the user to input enter
				new Scanner(System.in).nextLine();
				//flag to indicate we already paginated
				page = true;
			}
			
			System.out.println(nodeNames[index++] + ": " +peopleInfo.get(i));
		}
		
	}
	
	//A function that accepts id as parameter and prints the HealthProfile of the person with that id
	public void printHealthProfile(String id) throws XPathExpressionException {
		XPathExpression expr = xpath.compile(
				  "/people/person[@id='" + id +"']/firstname | "		// XPATH expressions to get name and surname, used for nicer presentation
	        		+ "/people/person[@id='" + id +"']/lastname | "
	        		+ "/people/person[@id='" + id +"']/healthprofile/*"		// actual XPATH expression to get the HealthProfile given an ID
				);
		NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		
		//check if the XPATH expression returned empty results
		//which means that the given ID is not present
		if(nodes.getLength() == 0)
			System.out.println("ID not found");
		
		//print the Health Profile
		System.out.println("Health Profile of " + nodes.item(0).getTextContent().toUpperCase() + " " + nodes.item(1).getTextContent().toUpperCase());
		
		//i starts from 2 because the first 2 value (name, surname) were already written above
		for(int i = 2;i<nodes.getLength(); i++){
			System.out.println("   " + nodes.item(i).getNodeName() + ": " + nodes.item(i).getTextContent());
			
		}
			
	}
	
	//A function which accepts a weight and an operator (=, >, <) as parameters and prints people that fulfill that condition (i.e., >80 Kg, = 75 Kg, etc.).
	public NodeList getPeopleByWeight(char condition, Double weight) throws XPathExpressionException{
		XPathExpression expr = xpath.compile("/people/person[healthprofile[weight " + condition + " " + weight + "]]/*");
		NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		return nodes;
	}
	
	public static void main(String[] args) throws XPathExpressionException{
		//in the constructor there is the "loadXML" method
		HealthProfileAssignment1 test = new HealthProfileAssignment1();
		
		//get the number of command line argument
		int argCount = args.length;
		if(argCount == 0){
			System.out.println("Insert at least the name of the method!");
			return;
		}
		
		//argument ID
		String id;
		//value returned by the methods
		Double val;
		
		//get the name of the method, given as 1st argument
		String method = args[0];
		switch(method){
				//Use xpath to implement methods like getWeight and getHeight
				case "getWeight":
					if(argCount != 2){
						System.out.println("Usage: getWeight [ID]");
						break;
					}
					id = args[1];
					val = test.getWeight(id);
					if(val.isNaN()){
						System.out.println("ID not found");
						break;
					}
					System.out.println("Weight = " + val);
					break;
				case "getHeight":
					if(argCount != 2){
						System.out.println("Usage: getHeight [ID]");
						break;
					}
					id = args[1];
					val = test.getHeight(id);
					if(val.isNaN()){
						System.out.println("ID not found");
						break;
					}
					System.out.println("Height = " + val);
					break;
				case "getBmi":
					if(argCount != 2){
						System.out.println("Usage: getBmi [ID]");
						break;
					}
					id = args[1];
					val = test.getBmi(id);
					if(val.isNaN()){
						System.out.println("ID not found");
						break;
					}
					System.out.println("BMI = " + val);
					break;
					
				//Make a function that prints all people in the list with details (if >20, paginated)
				case "getAllPeople":
					if(argCount != 1){
						System.out.println("Usage: getAllPeople");
						break;
					}
					
					test.getAllInfo();
					break;
					
				//A function that accepts id as parameter and prints the HealthProfile of the person with that id
				case "getHealthProfile":
					if(argCount != 2){
						System.out.println("Usage: getHealthProfile [ID]");
						break;
					}
					id = args[1];
					test.printHealthProfile(id);
					break;
				
				//A function which accepts a weight and an operator (=, >, <) as parameters and prints people that fulfill that condition (i.e., >80 Kg, =75 Kg, etc.)
				case "getPeopleByWeight":
					if(argCount != 3){
						System.out.println("Usage: getPeopleByWeight [operator] [weight]");
						break;
					}
				//check if the given weight can be interpreted as a number
					try{
						val = Double.parseDouble(args[2]);	
					}
					catch(NumberFormatException e){
						System.out.println("Weight not valid");
							break;
					}
				
					char op = args[1].charAt(0);
					//check id given condition is correct
					if(op != '=' && op != '>' && op != '<'){
						System.out.println("Condition not valid");
						break;
					}
				
					NodeList nodes = test.getPeopleByWeight(op, val);
					
					if(nodes == null){
						System.out.println("Error retrieving information");
						break;
					}
					if(nodes.getLength() == 0){
						System.out.println("No entry found");
						break;
					}
					
					//print results
					for(int i = 0; i<nodes.getLength(); i++){
						if(nodes.item(i).getNodeName().equals("healthprofile")){
							System.out.println("HealthProfile [Last update, Weight, Height, BMI]");
						}
						else{
							System.out.print(nodes.item(i).getNodeName() + ": ");
							System.out.println(nodes.item(i).getTextContent());
						}
					}
					break;
				
				default:
					System.out.println("The system did not find the method'" + method + "'");
					break;
		
		}
	}
}