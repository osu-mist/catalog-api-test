#Catalog API Test

##Overview
A test implementation of a RESTful API with courses scraped from the online catalog.

##Description
Catalog API test is a web app API allowing users to access information on various computer science courses at Oregon State University.  There are two main components to this API, the scraper and the actual API.

###Web Scraper
Course information will be scraped from the online catalog and stored for use (in an Oracle database).  The data will include course information such as the cid, CRN, course name, description, instructor, CRN, day, time, and location.

###RESTful API
The API will grab the data from the database and provide an outlet for users to request and interact with the data.  The API as a whole as well as the methods of interaction will be highlighted in the next section.

##Features
Users will be able to access each course’s information through a RESTful interface.  With this data, they will be able to build any course related service.

With requests, users will be able to access each courses information as listed above.  They will be able to GET to retrieve data from a resource, PUT to update a resource, POST to create a resource, and DELETE to remove a resource.  If invalid requests are submitted, ie. attempting to retrieve a non-existing resource or submitting a PUT request with invalid data, an error message will be outputted to the user - examples are as shown in the mockup section.

**Please note that if scraper is run again after any submitted PUT/POST/DELETE requests, those changes will be overwritten by the scraped data.

Courses will be accessible by search through the course’s CRN.  For example CRN #50220 may reference one of the first computer science courses, ie. CS 101. And CRN #11933 may reference another course that follows, ie. CS 160.

### Setup Instructions

Open terminal, navigate to your cloned local directory, and enter the following line

```
gradle build
```

This builds the project into a single deployable jar file.

Next you'll want to run the jar file along with your credentials in your config file.  To do so, enter the followwing line:

```

java -classpath bin/ojdbc6_g.jar:build/libs/catalog-api-test-all.jar edu.oregonstate.mist.catalogapitest.CatalogAPITestApplication server configuration.yaml

```

You can also create and use a bash script to do this for you.  Mine is included and is called ```javaCall.sh```

To run it, type: ```./javaCall.sh``` or ```sh javaCall.sh```

*Note that my bash script may not work on your machine depending on your setup.

##Mockup

###Connecting
The following HTTP requests will be done over telnet for the purposes of example:

```
$ telnet localhost 8008
…
…
Connected to localhost.
```

###GET
Request data from resource.

If the course actually exists:

```
GET /api/v1/courses/11933  HTTP/1.1
Host: localhost:8008

HTTP/1.1 200 OK
…
…
…

{
"CRN": 11933,
"courseName": "CS 160. COMPUTER SCIENCE ORIENTATION",
"instructor": "Parham Mocello, J.",
        "day": "MW",
       "time": "1400-1450",
        "location": "GLFN AUD"
}
```

If the course doesn’t exist:

```
GET /api/v1/courses/11111  HTTP/1.1
Host: localhost:8008

HTTP/1.1 404 Not Found
Date: ...
Content-Length: 0
```

###PUT
Create/update course with specified course id.

If data is valid:

```
PUT /api/v1/courses     HTTP/1.1
Host: localhost: 8008
Content-Type: application/json
Content-Length: …

{
"CRN": 11111,
"courseName": "CS 111. COMPUTER SCIENCE: THE CLASS",
"instructor": "Brandon Lee",
        "day": "MW",
       "time": "1400-1450",
        "location": "KEC 1005"
}

HTTP/1.1 200 OK
Date: ...
Content-Type: application/json
Transfer-Encoding: chunked

{
"CRN": 11111,
"courseName": "CS 111. COMPUTER SCIENCE: THE CLASS",
"instructor": "Brandon Lee",
        "day": "MW",
       "time": "1400-1450",
        "location": "KEC 1005"
}
```

If the data is invalid:

```
PUT /api/v1/courses HTTP/1.1
Host: localhost:8008
Content-Type: application/json
Content-Length: ...

{
"CRN": 11111,
"courseName": "",
"instructor": "",
        "day": "",
       "time": "",
        "location": ""
}

HTTP/1.1 422
Date: ...
Content-Type: application/json
Transfer-Encoding: chunked

{
"errors": ["courseName may not be empty (was )",
        "instructor may not be empty (was )",
        "day may not be empty (was )",
           "time may not be empty (was )",
        "location may not be empty (was )"
   ]
}

0
```

###Instructor Resource and Mockup

```
PUT /api/v1/instructor     HTTP/1.1
Host: localhost: 8008
Content-Type: application/json
Content-Length: …

{
"instructor": "Brandon Lee",
        "rateMyProfessorLink": "http://www.ratemyprofessors.com/",
       "Courses taught": "CS 160 … CS 161"
}
```

####*More Examples To Come!

###Google Docs Version:
* Currently not as updated
https://docs.google.com/document/d/1y_Pyub3YOFrFQ0CYiEhIQdvlrPXRHliDVb-jiH84xM0/edit?usp=sharing
