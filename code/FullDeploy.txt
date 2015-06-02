rem --- Maven clean install----------
echo off
cd C:\Users\tripotha\Documents\w4
start mvn clean install

echo press the any key after compilation is Successs.
pause
cd  C:\Users\tripotha\Documents\w4\Sel2inWeb\target
IF EXIST Sel2inWeb.war (
 echo Sel2inWeb is there
) ELSE (
 echo Sel2inWeb  missing
)



rem --- copping Sel2inWeb lib to Tomcat lib--------
xcopy /c /e/y /d C:\Users\tripotha\Documents\w4\Sel2inWeb\target\Sel2inWeb\WEB-INF\lib C:\apache-tomcat-7.0.57\lib
rem --- Deleting Sel2inWeb all lib jar--------
cd C:\Users\tripotha\Documents\w4\Sel2inWeb\target\Sel2inWeb\WEB-INF\lib
del *.jar
rem --- copping Sel2inWeb static content to Tomcat Root--------
xcopy /c /e/y /d C:\Users\tripotha\Documents\w4\Sel2inWeb\target\Sel2inWeb C:\apache-tomcat-7.0.57\webapps\ROOT

rem --- copping Sel2inRptWeb lib to Tomcat lib--------
xcopy /c /e/y /d C:\Users\tripotha\Documents\w4Sel2inRptWeb\target\Sel2inRptWeb\WEB-INF\lib C:\apache-tomcat-7.0.57\lib
rem --- Deleting Sel2inRptWeb all lib jar--------
cd C:\Users\tripotha\Documents\w4\Sel2inRptWeb\target\Sel2inRptWeb\WEB-INF\lib
del *.jar

rem --- copping Sel2inWeb static content to Tomcat Root-------
cd C:\apache-tomcat-7.0.57\webapps
rmdir /s /q Sel2inRptWeb
mkdir Sel2inRptWeb 
xcopy /c /e/y /d C:\Users\tripotha\Documents\w4\Sel2inRptWeb\target\Sel2inRptWeb C:\apache-tomcat-7.0.57\webapps\Sel2inRptWeb

echo Deployment is Done.Script is existing now
timeout 5




