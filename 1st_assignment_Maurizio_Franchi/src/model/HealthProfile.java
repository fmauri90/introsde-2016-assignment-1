package model;

import java.util.Random;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "healthprofile")
@XmlType(propOrder = {"lastupdate", "weight", "height", "bmi"})
@XmlAccessorType(XmlAccessType.FIELD)

public class HealthProfile {
	private String lastupdate;
	private double weight; // in kg
	private double height; // in m
	private double bmi;
	
	public HealthProfile(String lu, double weight, double height){
		this.lastupdate = lu;
		this.weight = weight;
		this.height = height;
		//calculate BMI (with 2 decimals)
		this.bmi = (double)Math.round(this.weight/(Math.pow(this.height, 2)) *100)/100;
	}
	
	public HealthProfile(){
		Random rand = new Random();
		//nextInt is normally exclusive of the top value,
		//so add 1 to make it inclusive
		//weight between 40 and 100 kg
		this.weight = rand.nextInt((100 - 40) +1) +40;
		//height between 1.40 and 2.00 m
		this.height = rand.nextInt((200 - 140) +1) +140;
		this.height /= 100;
		this.lastupdate = "2016-05-07T00:00:00.000+01:00";
	}
	
	
	public double getWeight() {return weight;}
	public void setWeight(double weight) {this.weight = weight;}
	
	public double getHeight() {return height;}
	public void setHeight(double height) {this.height = height;}
	
	public double getBmi() {return bmi;}
	public void setBmi(double bmi) {this.bmi = bmi;}
	
	public String getLastupdate() {return lastupdate;}
	public void setLastupdate(String lastupdate) {this.lastupdate = lastupdate;}
	
	public String toString() {return "Height = "+height+ ", Weight = "+weight+ ", BMI"+bmi;}
}
