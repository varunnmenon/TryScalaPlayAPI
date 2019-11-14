# TryScalaPlayAPI
Created a scala API using the startup project.

You can download the example startup project from https://github.com/playframework/play-samples/tree/2.7.x/play-scala-rest-api-example


Get Started
------------------------------------------

The app was built using IntelliJ IDEA IDE by enabling the Scala plugin. Plugin can be downloded by Navigating to "Preferences -> Plugin -> Scala Plugin" in IntelliJ

To open the project, click on File -> Open , navigate to the folder where you have downloaded the project, click on build.sbt file and then click Open as Project and then click on Ok.

After the project is opened, you have to click on "Add Configuration". When the project is indexing, the Add Configuration is disabled by the IDE. If you face the same issue, please wait for the indexing to finish and then click on Add Configuration. 

Once the Add Configuration Screen is open, click on + sign on the top right and then select "sbt Task". Type run in "NAME:" and "TASK:" and then click on OK.

Now you are all set to run the project.



Test Case Documentation:
------------------------------------------

To run tests, open the test folder -> controllers ->  select the file "ComplianceRouterSpec"

Right click on the class name "ComplianceRouterSpec" and select "Run ComplianceRouterSpec". The IDE will build and run all the test cases specified in the given file. In this project, ScalaTest + Play is used for writing and running the test cases.

The test case scenarios are explained in the API Documentation.


API Documentation:
------------------------------------------

app -> compliance folder contains the scala files for ComplianceController, Compliance Routes, Compliance Repository, ComplianceResourceHandler, and ComplianceActionBuilder. 

ComplianceRoute route the API requests to appropriate function inside the ComplianceController. The basic routing is mentioned in conf -> routes.



API Use-Cases:

1. GET "http://localhost:9000/compliance/" -> returns a Compliance object with 3 fields, gdpr, unprotected-devices, and uninspectable-data. The values genereated for these fields are random numbers between 0 and 1, with uniform distribution . The response has the http code 200 and JSON payload looks like : 

                                    {
                                      "compliance": {
                                        "gdpr": 0.87,
                                        "unprotected-devices": 0.71,
                                        "uninspectable-data": 0.33
                                      }
                                    }
                                    
2. if any of the keywords gdpr, unprotected-devices, or uninspectable-data is mentioned after "compliance/" then we return only the specified keyword inside the Compliance Object. The URL will look like:

GET "http://localhost:9000/compliance/unprotected-devices" -> The response has the http code 200 and JSON payload looks like : 

                                    {
                                      "compliance": {
                                        "unprotected-devices": 0.71,
                                      }
                                    }
                                    

GET "http://localhost:9000/compliance/uninspectable-data" -> The response has the http code 200 and JSON payload looks like : 

                                    {
                                      "compliance": {
                                        "uninspectable-data": 0.33
                                      }
                                    }
                                    

GET "http://localhost:9000/compliance/gdpr" -> The response has the http code 200 and JSON payload looks like : 

                                    {
                                      "compliance": {
                                        "gdpr": 0.87,
                                      }
                                    }
                                    
3. If "/" is added or if we add url parameters at the end of the keyword, then we return with the http code 404(Not Found) and a pre-defined error message.

4. If keywords other than gdpr, unprotected-devices, or uninspectable-data is mentioned after "compliance/" then we return with the http code 404(Not Found) and a pre-defined error message.

5. If any keyword is mentioned after the approved keywords gdpr, unprotected-devices, or uninspectable-data for example, "compliance/gdpr/text" then we return with the http code 400(BAD REQUEST) and a pre-defined error message.

6. If any other HTTP Method is mentioned other than GET, then we return with the http code 400(BAD REQUEST) and a pre-defined error message The Request might look like:

        POST/PUT/DELETE "http://localhost:9000/compliance/gdpr"



--> All the Above mentioned scenarios are present in the test cases and can be checked in the "ComplianceRouterSpec" file in test folder
