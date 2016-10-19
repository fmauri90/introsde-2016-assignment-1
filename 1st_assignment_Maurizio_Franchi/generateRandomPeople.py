# Created by Maurizio Franchi 03/05/2016
# ###########################################
# Generates an XML with random information 
# about a given (in input) number of people
# (!) Maximum 400 people (20 names x 20 surnames)

from random import randint
import datetime
import sys

# get current datetime correctly formatted
def getNow():
	ora = datetime.datetime.now()
	time = ora.strftime("%Y-%m-%dT%H:%M:%S")
	ms = ora.strftime("%f")[:3]

	mytime = time+"."+ms+"+01:00"
	return mytime
	
# get random date (used for birthdate)
def getRandomDate():
	#"2014-09-20T18:00:00.000+02:00"
	# random date between 1920 and 2000
	start_date = datetime.date.today().replace(year=1920, day=1, month=1).toordinal()
	end_date = datetime.date.today().replace(year=2000, day=31, month=12).toordinal()
	random_day = datetime.date.fromordinal(randint(start_date, end_date))
	# random hour 
	hour = randint(0,23)
	min = randint(0,59)
	sec = randint(0,59)
	milsec = randint(0,999)
	
	myhour = "T%02d:%02d:%02d.%03d" % (hour,min,sec,milsec)
	mydatetime = str(random_day) + myhour + "+01:00"
	
	return mydatetime

# write a string on a file
def writeOnFile(filename, string):
	myFile = open(filename,"w")
	myFile.write(string)
	myFile.close()
	print "File " + filename + " correctly written"
	
def createStringXML(size):
	# list of pair (name, surname)
	people = []	
	# stringa XML
	xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
	xmlString += "\n<people>"
	
	for i in range(0,size):
		# repeat until the generated pair (name, surname) already exist
		while True:
			name = names[randint(0, len(names)-1)]
			surname = surnames[randint(0, len(names)-1)]
			if((name,surname) not in people):
				break
		# append the generated pair to the people list
		people.append((name, surname))
		birthdate = getRandomDate()
		lastupdate = getNow()
		weight = randint(40, 100)
		height = randint(140, 200) * 0.01	# 175 * 0.01 = 1.75
		bmi = round(weight / (height*height), 2)
		
		xmlString += "\n\t<person id=\"%04d\">" % (i+1)
		xmlString += "\n\t\t<firstname>" + people[i][0] + "</firstname>"
		xmlString += "\n\t\t<lastname>" + people[i][1] + "</lastname>"
		xmlString += "\n\t\t<birthdate>" + birthdate + "</birthdate>"
		xmlString += "\n\t\t<healthprofile>"
		xmlString += "\n\t\t\t<lastupdate>" + lastupdate + "</lastupdate>"
		xmlString += "\n\t\t\t<weight>" + str(weight) + "</weight>" 
		xmlString += "\n\t\t\t<height>" + str(height) + "</height>" 
		xmlString += "\n\t\t\t<bmi>" + str(bmi) + "</bmi>" 
		xmlString += "\n\t\t</healthprofile>"
		xmlString += "\n\t</person>"

	xmlString += "\n</people>"
	return xmlString
	
names = ["Jack", "Thomas", "Joshua", "William", "Daniel", 
	"Matthew", "James", "Joseph", "Harry", "Samuel", "Emily", 
	"Chloe", "Megan", "Jessica", "Emma", "Sarah", "Elizabeth", 
	"Sophie", "Olivia", "Lauren"]
surnames = ["Smith","Jones","Williams","Taylor","Brown","Davies",
	"Evans","Wilson","Thomas","Johnson","Roberts","Robinson","Thompson",
	"Wright","Walker","White","Edwards","Hughes","Green","Hall"]

xmlString = createStringXML(int(sys.argv[1]))	# parameters = number of people to be generated (!max 400)
writeOnFile("people.xml", xmlString)			# parameters = file name, string to be written
