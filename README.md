# Play Scala Demo

This project serves as a `seed project` to build a REST API backend service using Play 2.6 / Scala.

## About the project

This project uses :

* Programming languages - [Scala 2.12 or above](https://www.scala-lang.org/)

* Play framework - [Play 2.6.x or above](https://www.playframework.com/)

* Persistence - [MongoDB 3.4 or above](https://www.mongodb.com/mongodb-3.6)

* Build tool - [Scala-SBT 1.1.1 or above](https://www.scala-sbt.org/)

* CI and CD - [Drone.io](https://drone.io/)

* Distribution - [Docker](https://www.docker.com/)

## Static Code Analysis Setup

Support added for static analysis of code using [Scalastyle](http://www.scalastyle.org/)
To perform scala style check , use the below task : 
```bash
./sbt scalastyle
```

## Setting up the environment and running the application

Requirements on Local Machine:

 - IntelliJ IDEA Community Edition ([Download](https://www.jetbrains.com/idea/download/#section=linux))
 - Java 8+ ([Download](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)) 
 - Docker CE & Docker Compose ([Download](https://www.docker.com/community-edition#/download))


A docker-compose.yml file is created with mongodb setup. The following docker images are used

 - Mongo ([Docker Image](https://hub.docker.com/_/mongo/)
 

Setting up the and running the databases just involves running
```
docker-compose up
```

Shutting down the containers
```
docker-compose down
```
> This should start the mongodb server on your localhost:27017

Now run the application on localhost , using the below command
```bash
./sbt run
```

## Building the application

To build the application , use the below command
```bash
./sbt -Dsbt.ivy.home=.ivy2 clean universal:packageZipTarball
```

## Available SBT tasks
```
bgRun            Start an application's default main class as a background job
bgRunMain        Start a provided main class as a background job
clean            Deletes files produced by the build, such as generated sources, compiled classes, and task caches.
compile          Compiles sources.
console          Starts the Scala interpreter with the project classes on the classpath.
consoleProject   Starts the Scala interpreter with the sbt and the build definition on the classpath and useful imports.
consoleQuick     Starts the Scala interpreter with the project dependencies on the classpath.
copyResources    Copies resources to the output directory.
doc              Generates API documentation.
package          Produces the main artifact, such as a binary jar.  This is typically an alias for the task that actually does the packaging.
packageBin       Produces a main artifact, such as a binary jar.
packageDoc       Produces a documentation artifact, such as a jar containing API documentation.
packageSrc       Produces a source artifact, such as a jar containing sources and resources.
publish          Publishes artifacts to a repository.
publishLocal     Publishes artifacts to the local Ivy repository.
publishM2        Publishes artifacts to the local Maven repository.
run              Runs a main class, passing along arguments provided on the command line.
runMain          Runs the main class selected by the first argument, passing the remaining arguments to the main method.
scalastyle       Run scalastyle on your code
test             Executes all tests.
testOnly         Executes the tests provided as arguments or all tests if no arguments are provided.
testQuick        Executes the tests that either failed before, were not run or whose transitive dependencies changed, among those provided as arguments.
update           Resolves and optionally retrieves dependencies, producing a report.
```

## Sample CRUD

This project has an inbuilt **Sample application** , that performs the basic `CRUD` (create , read , update , delete) operations over an `User` model.
```scala
import org.joda.time.DateTime

case class BioData(firstName: Option[String],
                   middleName: Option[String],
                   lastName: Option[String],
                   ageInYears : Option[Int],
                   gender : Option[String])

case class TotalDuration(years : Int,months : Int , days : Int)

case class CareerProfile(currentDesignation : Option[String],
                         currentOrganization : Option[String],
                         totalExperience : Option[TotalDuration],
                         careerBreak : Option[TotalDuration])
                   
case class User(id: Option[String],
                biodata: Option[BioData],
                careerProfile: Option[CareerProfile],
                creationDateTime: Option[DateTime],
                modifiedDateTime: Option[DateTime])
```
The crud operations can be accessed at the following end points

**GET /v1/api/users** - Lists all users added

**GET /v1/api/users/:id** - Gets an user identified by id.

**POST /v1/api/users** - Adds an user to the database

Sample Payload
```json
{
    "biodata" : {
        "firstName" : "Peter",
        "middleName" : "Alex",
        "lastName" : "Samuel",
        "ageInYears" : 29,
        "gender" : "MALE"
    },
    "careerProfile" : {
        "currentDesignation" : "Senior Software Engineer",
        "currentOrganization" : "ABC Solutions Inc.",
        "totalExperience" : {
            "years" : 6,
            "months" : 2,
            "days" : 10
        },
        "careerBreak" : {
            "years" : 1,
            "months" : 4,
            "days" : 0
        }
    }
}
```
**PUT /v1/api/users/:id** - Updates a user identified by id.

Sample Payload
```json
{
    "biodata" : {
        "firstName" : "George"
    }
}
```

**DELETE /v1/api/users/:id** - Deletes a user identified by id.

> Checkout the latest [postman collections](https://www.getpostman.com/collections/1c2ab2e01e36736dc452) for the sample CRUD application
