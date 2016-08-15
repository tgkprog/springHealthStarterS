rem --- Deleting Sel2inWeb all lib jar--------
cd C:\Users\tripotha\Documents\w4\Sel2inWeb\target\Sel2inWeb\WEB-INF\lib
del *.jar
rem --- copping Sel2inWeb static content to Tomcat Root--------
xcopy /c /e/y /d C:\Users\tripotha\Documents\w4\Sel2inWeb\target\Sel2inWeb C:\apache-tomcat-7.0.57\webapps\ROOT