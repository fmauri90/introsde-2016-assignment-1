# Assignment 01: Reading/Writing objects to and from XML and JSON

## [Introduction to Service Design and Engineering](https://github.com/IntroSDE/introsde) | [University of Trento](http://www.unitn.it/) 


This repository is the solution to the [first assignment](https://sites.google.com/a/unitn.it/introsde_2016-17/lab-sessions/assignments/assignment-1) of the course IntroSDE of the University of Trento. This assignment cover the following topics:

* ANT
* XML, XPATH & XML Schemas
* Mapping XML (and JSON) to (and from) Objects

### Task of the code

The class *HealthProfileReader* reads the information about the people stored in the file *people.xml*. The file has this structure:


```xml
<people>
  <person id="0001">
    <firstname>Marlon</firstname>
    <lastname>Jakubowski</lastname>
    <birthdate>1943-03-21T00:00:00+00:00</birthdate>
    <healthprofile>
      <lastupdate>2015-10-08T15:51:28+02:00</lastupdate>
      <weight>83.52</weight>
      <height>1.85</height>
      <bmi>24.40</bmi>
    </healthprofile>
  </person>
  
  <!-- more people -->
</people>
  
```
The requirements for this assignment are:

**Based on [Lab 3](https://github.com/IntroSDE/introsde/lab03):**

1.3 Use xpath to implement methods like getWeight and getHeight (getWeight(personID) returns weight of a given person, the same for getHeight);  
2.3 a function that print all the details for each person stored in the file people.xml;  
3.3 a function that given the id, return the details of a person;  
4.3 a function that print all people fulfilling a condition on the weight (>90);

**Based on [Lab 4](https://github.com/IntroSDE/introsde/lab04):**
    
1.4 Create the XML schema XSD file for the example XML document of people;  
2.4 convert the list of Java object Person into XML (marshalling to XML) using classes generated with JAXB XJC;  
    convert XML into a list of Java object Person (un-marshalling from XML) using classes generated with JAXB XJC;  
3.4 convert the list of Java object Person into JSON (marshalling to JSON).

### Code

##### Folders

*[src/](src/)*: contains source code;

*[src/model](src/model)*: contains the definition of *Person* and *HealthProfile*;

*[src/peoplestore](src/peoplestore)*: contains the code for marshalling and un-marshalling from/to XML and marshalling to JSON;

##### File
*[src/HealthProfileAssignment1.java](src/HealthProfileAssignment1.java)*: contains the code to execute requirements 1.3, 2.3, 3.3, 4.3 of the previous list. The list of people are stored in people.xml;

*[people.xsd](people.xsd)* XML schema file for the document people.xml (requirement 1.4);

*[src/peoplestore/JAXBMarshaller.java](src/peoplestore/JAXBMarshaller.java)*: class to execute requirement 2.4. Three persons are created using Java and marshalled in XML. The Java objects are stored in the generated file *peopleMarshall.xml*;

*[src/peoplestore/JAXBUnMarshaller.java](src/peoplestore/JAXBUnMarshaller.java)*: class to execute requirement 2.4. The data are retrieved from *people.xml*;

*[src/peoplestore/JavatoJson.java](src/JavatoJson.java)*: class to execute requirement 3.4. Three persons are created using Java and marshalled in JSON. The data are stored in *people.json*; 

*[src/dao/PeopleStore.java](src/dao/PeopleStore.java)* contains the data access object.

### Installation

In order to execute this project you need the following technologies (in the brackets you see the version used to develop):

* Java (jdk1.8.0)
* ANT (version 1.9.4)

Then, clone the repository. Run in your terminal:

```
git clone https://github.com/fmauri90/introsde-2015-assignment-1.git && cd introsde-2015-assignment-1
```

and run the following command:
```
ant execute.evaluation
```

### Usage
This project use an [ant build script](build.xml) to automate the compilation and the execution of specific part of the Java application.
```
ant execute.evaluation
