rem --- Deleting Sel2inRptWeb all lib jar--------
cd C:\Users\tripotha\Documents\w4\Sel2inRptWeb\target\Sel2inRptWeb\WEB-INF\lib
del *.jar

rem --- copping Sel2inWeb static content to Tomcat Root-------
cd C:\apache-tomcat-7.0.57\webapps
rmdir /s /q Sel2inRptWeb
mkdir Sel2inRptWeb 
xcopy /c /e/y /d C:\Users\tripotha\Documents\w4\Sel2inRptWeb\target\Sel2inRptWeb C:\apache-tomcat-7.0.57\webapps\Sel2inRptWeb