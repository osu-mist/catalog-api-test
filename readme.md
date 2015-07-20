#Catalog API Test

##Overview
A test implementation of RESTful API with courses scraped from the online catalog.

##Description
Catalog API test is a web app API allowing users to access information on various computer science courses at Oregon State University.  There are two main components to this API, the scraper and the actual RESTful API.

###Web Scraper
Course information will be scraped from the online catalog and stored for use (most likely with a database).  The data will include course information such as the course name, description, instructor, CRN, day, time, and location.  In order to make the scraping process not too aggressive on the host server, only one disciplinary course (computer science) will be scraped, versus other scrapers which hit every single course offered.  The scraping process will be made runnable via an API call.

###RESTful API
The API will grab the data from the database and provide an outlet for users to request and interact with the data.  The API as a whole as well as the methods of interaction will be highlighted in the next section.

##Features
Users will be able to access each course’s information through a RESTful interface.  With this data, they will be able to build any course related service.

With requests, users will be able to access each courses information as listed above.  They will be able to use GET to retrieve data from a resource and PUT to update a resource.  If invalid requests are submitted, ie. attempting to retrieve a non-existing resource or submitting a PUT request with invalid data, an error message will be outputted to the user - examples are as shown in the mockup section.

**Please note that if scraper is run again after any submitted PUT requests, those changes will be overwritten by the scraped data.

Courses will be accessible by search through the course’s CRN.  For example CRN #50220 may reference one of the first computer science courses, ie. CS 101. And CRN #11933 may reference another course that follows, ie. CS 160.

### Setup Instructions

Open terminal, navigate to your cloned local directory, and enter the following line

```

java -classpath bin/ojdbc6_g.jar:build/libs/catalog-api-test-all.jar edu.oregonstate.mist.catalogapitest.CatalogAPITestApplication server configuration.yaml

```

You can also create and use a bash script to do this for you.  Mine is included and is called ```javaCall.sh```

To run it, type: ```./javaCall.sh``` or ```sh javaCall.sh```

*Note that my bash script may not work on your machine depending on your setup/project structure.

##Mockup

###Connecting
The following HTTP requests will be done over telnet for the purposes of example:

```
$ telnet localhost 8080
…
…
Connected to localhost.
```

###GET
Request data from resource.

If the course actually exists:

```
GET /api/v1/courses/11933  HTTP/1.1
Host: localhost:8080

HTTP/1.1 200 OK
…
…
…

{
“CRN”: 11933,
“courseName”: “CS 160. COMPUTER SCIENCE ORIENTATION”,
“instructor”: “Parham Mocello, J.”,
        “day”: “MW”,
       “time”: “1400-1450”,
        “location”: “GLFN AUD”
}
```

If the course doesn’t exist:

```
GET /api/v1/courses/11111  HTTP/1.1
Host: localhost:8080

HTTP/1.1 404 Not Found
Date: ...
Content-Length: 0
```

###PUT
Create/update course with specified course id.

If data is valid:

```
$ nc localhost 8008 << HERE
> PUT /course/11111 HTTP/1.0
> [
>         {
>         "cid": 5,
>         "crn": 11111,
>         "courseName": "CS 121",
>         "instructor": "Mr. BOB",
>         "day":"MWF",
>         "time":"12-1",
>         "location":"KEC"
>         }
> ]
>
> HERE

HTTP/1.1 200 OK
Date: Mon, 20 Jul 2015 17:30:41 GMT
Content-Type: application/json
Content-Length: 112

[
        {
        "cid": 5,
        "crn": 11111,
        "courseName": "CS 121",
        "instructor": "Mr. TEST",
        "day":"MWF",
        "time":"12-1",
        "location":"KEC"
        }               
]  
```

If the data is invalid:

```
$ nc localhost 8008 << HERE
> PUT /course/33333 HTTP/1.0
> [
>         {
>         "cid": 5,
>         "crn": 11111,
>         "courseName": "CS 121",
>         "instructor": "Mr. BOB",
>         "day":"MWF",
>         "time":"12-1",
>         "location":"KEC"
>         }
> ]
>
> HERE

HTTP/1.1 404 Not Found
Date: Mon, 20 Jul 2015 17:36:56 GMT
Content-Type: text/html; charset=ISO-8859-1
Cache-Control: must-revalidate,no-cache,no-store
Content-Length: 295
```

###POST
Create course

If data is valid:

```
$ nc localhost 8008 << HERE
> POST /course HTTP/1.0
> [
>         {
>         "cid": 66,
>         "crn": 12546,
>         "courseName": "CS 111",
>         "instructor": "Mr. 123",
>         "day":"MWF",
>         "time":"12-1",
>         "location":"KEC"
>         }
> ]
>
> HERE

HTTP/1.1 200 OK
Date: Mon, 20 Jul 2015 17:30:41 GMT
Content-Type: application/json
Content-Length: 112
```

If the data is invalid:

```
$ nc localhost 8008 << HERE
> POST /course HTTP/1.0
> [
>         {
>         "cid": 66,
>         "crn": gfdsfds,
>         "courseName": "CS 111",
>         "instructor": "Mr. 123",
>         "day":"MWF",
>         "time":"12-1",
>         "location":"KEC"
>         }
> ]
>
> HERE

HTTP/1.1 500 Internal Server Error
Date: Mon, 20 Jul 2015 17:36:56 GMT
Content-Type: text/html; charset=ISO-8859-1
Cache-Control: must-revalidate,no-cache,no-store
Content-Length: 295
```

###DELETE
Remove course

If data is valid:

```
$ nc localhost 8008 << HERE
> DELETE /course/11111 HTTP/1.0
>
> HERE

HTTP/1.1 200 OK
Date: Mon, 20 Jul 2015 17:30:41 GMT
Content-Type: application/json
Content-Length: 112
```

If data is invalid

```
$ nc localhost 8008 << HERE
> DELETE /course/88888 HTTP/1.0
>
> HERE

HTTP/1.1 404 Not Found
Date: Mon, 20 Jul 2015 17:36:56 GMT
Content-Type: text/html; charset=ISO-8859-1
Cache-Control: must-revalidate,no-cache,no-store
Content-Length: 295
```

###Instructor Resource and Mockup

```
PUT /api/v1/instructor     HTTP/1.1
Host: localhost: 8080
Content-Type: application/json
Content-Length: …

{
“instructor”: “Brandon Lee”,
        “rateMyProfessorLink”: “http://www.ratemyprofessors.com/”,
       “Courses taught”: “CS 160 … CS 161”
}
```

###Dependencies
Scraper:
jsoup 1.8, tagsoup 1.2

###Google Docs Version:
https://docs.google.com/document/d/1y_Pyub3YOFrFQ0CYiEhIQdvlrPXRHliDVb-jiH84xM0/edit?usp=sharing
