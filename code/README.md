READ ME

Code is under folder /code

Project Set Up
============

1. Database(MySql) and tomcat server setup
Install xammp software in your machine from https://www.apachefriends.org/download.html or other version 
Or get both to run on local seperately with my sql driver avaiable to tomcat
Tested on ubuntu, mac and ubuntu

2. Run following db scripts
Open phpmyadmin console in xammp, dump db using import option
import--> select 1_ddl_script.sql --> go
import--> select 2_dml_script.sql --> go

3. set up maven 3+ in your system from http://maven.apache.org/download.cgi

4. go to code/smsWebSend starter project top folder.
run maven command
>mvn clean install tomcat:run-war-only


mvn clean -- cleans all projects
mvn install -- compiles and runs test cases and generates jar files 
mvn tomcat:run-war-only  -- deploys the generated war file into tomcat

q.sh is for quick compile without tests 
> mvn install -Dmaven.test.skip=true

Copying directories from one to another.
------------------------------------------------------
C:\>xcopy /c /e/y /d source_path  destination_path

Example
C:\>xcopy /c /e/y /d E:\Trimurthulu\MOH\NeevMOHPRO\Sel2inWeb\target\Sel2inWeb C:\apache-tomcat-7.0.57\webapps\ROOT

Structure
--------------------
The project is divided in to sub projects for seperation of concerns.
We have 
1. Utils - general java, apach libraries and spring utilities that do not depend on opur code. Reusable across projects. generic or specific to our domain. S2nSmsWSUtils
2. Entities - just the Pojos and db mapping. S2nSmsWSEntities
3. Dao - db specific code for CRUD ops. Can have seperate Daos per entity or special classes say for search that are across entities. S2nSmsWSDao
4. Service layer. Each class talks to one Dao here. Can add caching and txn mgt code here. S2nSmsWSService
5. Facade here we talk to one or more services. can add caches for computer values (example appointment slots that are not in db due sparse strategy). Here we also change to POJO from entity to DTO, which can have a subset of one or more entities. use populators to convert one DTO to other. S2nSmsWSFacade
6. Web,  used to expose APIs. Also has exported angular code and a few admin jsps. S2nSmsWSWebDeps and S2nSmsWSWeb
                                                    
S2nSmsWSRptEntity, S2nSmsWSRptDao, 	S2nSmsWSRptService, S2nSmsWSRptFacade, S2nSmsWSRptWeb illustrate a way to make the project modular, to split a project into smaller sub projects that can be deployed together or separately as a loosely coupled micro service so different dev teams can work on them. Plus there can be separation of concerns and maintenance.						


Test Case for Transaction Roll Back Demo
--------------------------------------------------
AppointmentFacadeTest.testCreateUserAndBookAppointment() written to prove the concept of transaction rollback. With a clean database (similar to the database at step 2 of Mock_Startup  project SetUp) user_1 and appoinment_1 will be created. 

Since the same appointment slot is not available, appointment_2 will not be created for user_2, and will be rolled back. Any subsequent runs of this test will not create user or appointment because the appointment slot is not available.

1. go to smsWebSend starter\Sel2inFacade project top folder.
run maven command:
mvn -Dtest=TransactionalBehaviorTest#testCreateUserAndBookAppointment test


Local Ehcache Configuration
---------------------------------
1. go to smsWebSend starter\Sel2inService\src\main\resources folder.
2. Open and edit line 62(<beans:bean id='ehcache') of service-context.xml and change to p:configLocation='classpath:ehcache-service.xml'


Remote Ehcache Configuration
---------------------------------
1. go to smsWebSend starter\Sel2inService\src\main\resources folder.
2. Open and edit line 4(<terracottaConfig rejoin="true" url="localhost:9510,localhost:9610"/>) of ehcache-service-remote.xml with remote server ip and port
3. Open and edit line 62(<beans:bean id='ehcache') of service-context.xml and change to p:configLocation='classpath:ehcache-service-remote.xml'

Clear Cache
----------------------------------------------
URL: http://localhost:8080/Sel2inWeb/cacheManager.jsp

"Clear All Cache" button will clear entire cache. "Clear Cache" button will clear cache based on the cache name entered into the textbox. Valid cache names will be found in \Sel2inService\src\main\resources\ehcache.xml, these are: "addressCache", "appointmentCache", "departmentCache", "doctorCache", "genderCache", "medicineInventoryCache", "patientCache" and "userCache".


Logging
-------------------
For logging, factory method pattern has been used. The log factory Sel2inLogFactory
 returns the actual logger implementor class. As of now 2 custom implementors are 
 created Sel2inSlf4jLoggerImpl for slf4j and Sel2inLog4j2LoggerImpl for log4j2. 
 Currently the factory returns log4j2 implementor. 
 
 Various adapters are configured in pom for log4j 1, Apache commons, JUL and slf4j.
Logs of these frameworks will be captured by log4j2 implementor and placed into the
rolling file. Log4j2 specific configurations are done in log4j2.xml file.

To migrate from log4j2 to any new logging framework a new implementor class needs 
to be written and the log factory will return that class, if config points to it. For this migration 
no other change in the existing code base will be required.

Full Deployment
-------------------------
First configure path for tommyHome , java , mysql , mysqlUsername , mysqlUserpassword variable in below two script 
 1- fullBuildDeploy.sh for in Ubuntu
 2- bld.sh for Mac
 
 above two script does following tasks in order. 
 	svn update , stop tomcat , clean db , setup db with scripts(1,2,3 sql script), 
 	full maven build with testcase,start tomcat,
 	open tab in default browser

script will deploy Sel2inWeb as "/" (inside ROOT directory) and Sel2inRptWeb as "Sel2inRptWeb" inside Tomcat.

