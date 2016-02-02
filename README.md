# SBoot-Cities-Service
This is a very simple Spring Boot project which demonstrates, that with only small a footprint of code its possible to a create a complex webservice which exposes CRUD operations as restful endpoints. This microservice runs on a local machine or on Cloud Foundry. 

![Cities](/docs/Arch.png)

Note: This is a FORK of https://github.com/cf-platform-eng/spring-boot-cities! Thanks to help and tips from my team, as well as Dave Syer and Scott Frederick in this and other branches :) The SCS branch includes updates to work with Spring Cloud Services.

###Running the app locally
Assuming you have access to a database server (e.g. MySQL, PostGres) or even have one running on your local machine, this microservice will run immediately on your desktop (within eclipse, standalone etc). Just create an empty database and amend the application.yml file with url, username etc settings for your database. 

To run outside of Eclipse just run 
```./gradlew bootRun ```
on your command line. You don't need to have gradle installed.

###Running the app on Cloud Foundry
To run this on Cloud Foundry, simply run the script:
```./scripts/first_time_push.sh ```

This script creates the required Cloud Foundry services, tidies up previous installations, pushes the app and binds the app to the service. Once the env is setup correctly, feel free to use the other script which will both build and push the app to cloud foundry:

```./scripts/push.sh ```

Alternately to build the application yourself, simply run:

``` ./gradlew clean assemble ```

Because Spring Boot is opinionated, it automatically connects this app to the correct datasources within your Cloud Foundry space using Spring Cloud Connectors - no code is needed in the application itself to read the credentials supplied by Cloud Foundry. The app will auto-populate data in the table of the db schema provisioned by Cloud Foundry in the SI - see below. Please note, when you first deploy this app it will take a long time to start because several SQL inserts are executing.

If you've never heard of Cloud Foundry - use it! This app is very simple to construct, as soon as you deploy it to Cloud Foundry your entire support infrastructure, app server, libraries etc are configured loaded and deployed within 2 minutes - push this application to our trial instance of cloud foundry at run.pivotal.io. This si classic DevOps separation of concerns yet both in harmony together.

###Usage!
When you run this app locally or on CF you can access its features using several RESTful endpoints. Note - this is only a SMALL sample of the endpoints available, this app exposes HATEOS endpoints. e.g. when running locally:
* <a href="http://localhost:8080/cities" target="_blank">http://localhost:8080/cities</a> - returns a single page JSON listing cities (20 cities in a page)
* <a href="http://localhost:8080/cities?page=2&size=5" target="_blank">http://localhost:8080/cities?page=2&size=5</a> - returns only FIVE results from the SECOND page
* <a href="http://localhost:8080/cities/search/name?q=London" target="_blank">http://localhost:8080/cities/search/name?q=London</a> - returns a list of cities with London in their name.
* <a href="http://localhost:8080/cities/search/nameContains?q=Lon&size=3" target="_blank">http://localhost:8080/cities/search/nameContains?q=Lon&size=3</a> - returns the first three results of the search to find any cities with a name containing the word "Lon" (case insensitive search)
* <a href="http://localhost:8080/health" target="_blank">http://localhost:8080/health</a> - This returns the current health of the app, it is provided by Spring Boot Actuator. This and all other actuator endpoints that actuator provides are available immediately.

###Wait, I want a GUI!
There is a separate application which can be used as a GUI to consume the data delivered by this Microservice here: https://github.com/skazi-pivotal/spring-boot-cities-ui or feel free to write your own, using that as a guide.

![Cities](/docs/Cities-ui.png)

###What about Netflix OSS and Spring Cloud Services?
Netflix OSS is a great way of managing distributed Microservices. There is another branch of this project which takes advantgaes of Spring Cloud Services in Pivotal Cloud Foundry, therfore automatically including several Netflix OSS features. To see this switch to the SCS branch.

###Can I get some metrics?
Spring Boot Actuator automatically exposes endpoints which allow you to consume useful information such as health, configprops, for more info check this out: http://docs.spring.io/autorepo/docs/spring-boot/1.2.0.M2/reference/htmlsingle/#production-ready

###This app is too simple
Yes it is, but ok then if you want a more advanced Microservice based application you should really check out this Repo: https://github.com/dpinto-pivotal/cf-SpringBootTrader. This is several microservices tied together using some great Netflix OSS features delivered via Spring and Cloud Foundry.

###How is data loaded?
With Spring and Spring Boot there are several ways to get an applicaton to initialise and load data automatically into a database on startup. This application uses flyway, but can also use Hibernate instead. For more info check out this page: https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html

This application will use Flyway by default to load data into the database. To do this I simply added the flyway maven repo dependency in my build.gradle and Spring Boot takes care of the rest by automatically binding the right datasource and loading it on class initialisation. Using flyway (or hibernate) eliminates any ugly initialisation java code which needs to be maintained and hidden away in your application.

Flyway expects the database to be empty (but you can add a property to override this). Flyway will create an additional table in your database which it uses to keep track of which database "migrations" it has performed, so on start up it will see the empty db and "migrate" from scratch. In the future if you want to add more data, change db structure, you just encode these as db migration too and flyway will only aply these new commands. Flyway migrations are simply .sql files, you can write flavours of different sql for different vendors if you need to. Spring Boot will automatically look for these sql files in your apps the [src/main/resources/db/migration] (src/main/resources/) folder, and instruct flyway automatically. The names of the .sql files allows flyway to run these SQL commands in the correct order and also for it to keep track of what commands it has already run, e.g. file V1.sql will run before V1.1. If you later add V1.2, only this file will be executed.

By default, only cities from Hampshire, Surrey and the West Midlands are loaded (for performance reasons). To load all cities, renmae the db/migrations/....txt file and delete your local copy of the three sql files for  these counties.

If you don't want to use Flyway, simply comment it out from the dependencies section in the buld.gradle (and Spring Boot will not activate it). You can then simple uncomment the following lines in [src/main/resources/application.properties] (src/main/resources/application.properties) file:

```
spring.jpa.hibernate.ddl-auto = create
spring.jpa.hibernate.naming-strategy = org.hibernate.cfg.ImprovedNamingStrategy
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
```

###Achitecture
![Cities](/docs/Classes.png)

This app is very simple, it is ultimately driven by three classes and some properties and that is it.
* SBootCitiesAplication.java - simple class which alows you to run this class as a regular java app. Spring Boot will automaticaly configure and deploy tomcat even though you launch a regular java app. 
* City.java - This class uses JPA to bind to a database table called uktowns. The table is where city data is held, this class maps java fields to the column names and enables Spring Data to dynamically construct instances of the class when it fetches data from the database. (Data is loaded in automatically - see the section below)
* CityRepository.java - This "interface" declares both restful endpoints as well as defines SQL operations required. Spring Boot and Spring Web automatically register typical DB endpoints for CRUD operations without the need to edit a web.xml or any other configuration files. Spring also "automagically" builds the right SQL queries to search, update, insert and retirve data from the database by automatically interpreting method names into real logic. This class also returns results as pages (i.e. 20 results at a time, but this can be tweaked using paramters to RESTFUL calls.
* WebController.java (optional) - This class isn't necessary, however it exposes a new REST endpoint 'cities_all' which lists all cities with no paging or size control options
* DataSourceConfig.java (optional) - This class isn't necessary, however it allows you to run this application locally on your Mac, desktop etc - it will bound your app to a local MySQL Server. You can use hibernate very easily instead, see the original project this is forked from.

###Tell me more
Spring Boot is designed to get you up and running quickly and it is opinionated, so:

* I have not needed to define a long list of libraries, in my build.gradle I add a dependency on Spring Boot and then dependencies on specific spring-boot starter projects. Spring Boot does the rest, it makes opinions for you
* I have not needed to configure endpoints in my web.xml or configure more detail about which endpoints exists, my CityRepository class automatically exposes these as endpoints because of the @RestRepository endpoints
* I do not need to install Tomcat,  configure it or write a dpoyment script to put it in the correct location in Tomcat etc, Spring Boot decides I need Tomcat and installs and deploys my app to it for me. I could even tell Spring Boot to use Jetty instead is I wanted to, or to use a different port.
* I have not needed to define any SQL queries, the methods I list in the repository class are automatically interpreted into queires because of the way I define them -> findByNameIgnoreCase (findBy<field in my entityy><type of find>)
* I have not needed to build a mapping config file between java and the db - this is handled by a few simple annotations e.g. @Entity
* I have not needed to hard code db parameters. When running locally, these are "injected" at runtime using the DataSourceConfig class (it is labelled with a specific @Profile), or just injected by Boot immediatelty when running in Pivotal Cloud Foundry. This can be tweaked to add db pooling etc (https://spring.io/blog/2015/04/27/binding-to-data-services-with-spring-boot-in-cloud-foundry)
* I have not needed to write any code to locate or parse properties files, Spring Boot just knows where to read them and how. (https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html)
* I did not need to add the flyway plugin or flyway db params to my build.gradle. Spring Boot automaticaly coonfigures and triggers flyway for me when it finds flyway in my classpath.

Do Check out the following URLs:
* https://spring.io/guides
* https://spring.io/guides/gs/rest-service/
* https://spring.io/guides/gs/accessing-data-jpa/
* http://cloud.spring.io/spring-cloud-connectors/spring-cloud-spring-service-connector.html
* http://cloud.spring.io/spring-cloud-connectors/spring-cloud-connectors.html
* https://spring.io/blog/2015/04/27/binding-to-data-services-with-spring-boot-in-cloud-foundry
* http://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
* https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html
