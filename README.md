# Student-RestAPI

API to Save and Get Student data along with their subject marks

## Technologies used :

Sprint boot
Java 8
Maven
MySQL

To run this code,
we have to change application.properties file with proper db username and password.

## Junit Test cases are written in below Path :

/studentdata/src/test/java/com/studentdata/studentdata/StudentdataApplicationTests.java

## API1 : Save Student/Subject Data :

URL : http://localhost:8080/StudentDetails/saveStudentData
Type : POST
Body :{
"studentEntity": {
"name": "hardik",
"age": 23,
"address": "Mumbai, India",
"subjects": [
{
"maths": 98
},
{
"physics": 66
}
]
}
}

## API2 : Get Student/Subject Data :

URL : http://localhost:8080/StudentDetails/getData?studentName=hardik
Type : GET
