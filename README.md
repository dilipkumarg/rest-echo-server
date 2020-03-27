#REST Echo Server
Simple HTTP Server for acting as ECHO for the request received. It also had ability to save last few requests for
debugging purposes.
 
This tool will help us in integrating with Web Hooks, and integrating with other client where we don't know the request
format. 

##API
###Echoing Requests
`URL: <server-url>/echo`

It accepts all the method types (i.e GET, PUT, POST), any request parameters, any header parameters.

It responds with the Data which it got.

###Example
####Request:

    URL: ${"SCHEME"}://${"HOST"}/echo/path1?param1=param1Value
    Method: POST
    Body: {
            "key1": "key1Value"
          }
    Headers: 
        Content-Type: application/json

####Response

    {
    	"url": "path1",
    	"method": "PUT",
    	"requestParams": {
    		"param1": ["param1Value"]
    	},
    	"headers": {
    		"sec-fetch-mode": ["cors"],
    		"content-length": ["25"],
    		"sec-fetch-site": ["none"],
    		"accept-language": ["en-GB,en-US;q=0.9,en;q=0.8"],
    		"origin": ["chrome-extension://aejoelaoggembcahagimdiliamlcdmfm"],
    		"host": ["localhost:8080"],
    		"content-type": ["application/json"],
    		"connection": ["keep-alive"],
    		"accept-encoding": ["gzip, deflate, br"],
    		"accept": ["*/*"],
    		"user-agent": ["Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36"],
    		"sec-fetch-dest": ["empty"]
    	},
    	"body": {
    		"key1": "key1Value"
    	},
    	"instant": "2020-03-27T12:51:50.323Z"
    }



If the `content-type` is `application/json` Serialization & Deserialization Handled.

###Getting History
It will memorizes last 10 requests.

`URL: <server-url>/history`

It returns List of last 10 requests. Response format same as Echo Request.

###Requirements
* Java 1.8
* Gradle (Wrapper included)

###Build
It is a Spring Boot application. You can do build using below command.

`   ./gradlew build
`  

It generates `rest-echo.jar` in target directory.

###Deploy

`java -jar rest-echo.jar`

It starts the server in 8080 port.

For more info [Help.md](HELP.md)