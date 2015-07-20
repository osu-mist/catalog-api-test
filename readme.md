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

First off, you'll need to enter in your credentials into the ```configuration.yaml``` file.

Next you will need to download the ```ojdbc6_g.jar``` file from [Oracle](http://www.oracle.com/technetwork/apps-tech/jdbc-112010-090769.html) and move it into your bin directory.

Once that has been done, Open terminal, navigate to your cloned local directory, and enter the following line

```
gradle build
```

This builds the project into a single deployable jar file.

Next you'll want to run the jar file along with your credentials in your config file.  To do so, enter the following line:

```

java -classpath bin/ojdbc6_g.jar:build/libs/catalog-api-test-all.jar edu.oregonstate.mist.catalogapitest.CatalogAPITestApplication server configuration.yaml

```

You can also create and use a bash script to do this for you.  Mine is included and is called ```javaCall.sh```

To run it, type: ```./javaCall.sh```

*Note that my bash script may not work on your machine depending on your setup/project structure.

##Mockup

###Connecting
The following HTTP requests will be done over netcat for the purposes of example:

```
$ nc localhost 8008 << HERE
…
…
HERE
```

###GET
Request data from resource.

####If the course actually exists:

#####Get All
```
$ nc localhost 8008 << HERE
>
> GET /course/all HTTP/1.0
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

#####Get by CRN:
```
$ nc localhost 8008 << HERE
>
> GET /course/11111 HTTP/1.0
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

#####Get by Name
```
$ nc localhost 8008 << HERE
>
> GET /course/name/CS%20121 HTTP/1.0
> 
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

#####If the data is invalid:

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

#####If data is valid:

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

#####If data is invalid:

```
$ nc localhost 8008 << HERE
>
> GET /course/1117 HTTP/1.0
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

#####If data is valid:

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

#####If data is invalid

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

####*More Examples To Come!

###[Google Docs Version](https://docs.google.com/document/d/1y_Pyub3YOFrFQ0CYiEhIQdvlrPXRHliDVb-jiH84xM0/edit?usp=sharing)

(Currently not as updated)
