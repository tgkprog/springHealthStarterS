rem --- Deleting Sel2inWeb all lib jar--------
cd \w4\Sel2inWeb\target\Sel2inWeb\WEB-INF\lib
rem del *.jar
rem --- copping Sel2inWeb static content to Tomcat Root--------
xcopy /c /e/y /d \w4\Sel2inWeb\target\Sel2inWeb C:\apache-tomcat-7.0.57\webapps\ROOT
