# JENKINS

### Build 🔥 Creating a Report
```
$ ./mvnw checkstyle:checkstyle
ls -l target/checkstyle-result.xml 
-rw-r--r--  1 m2  staff  300704 Apr 12 16:35 target/checkstyle-result.xml

$ ./mvnw package -DskipTests
$ ls -l target/BPS-0.0.12-SNAPSHOT.jar

$ ./mvnw spotbugs:spotbugs
$ ls -l target/spotbugsReport.xml 
-rw-r--r--  1 m2  staff  249452 Apr 12 16:34 target/spotbugsReport.xml

$ ./mvnw package -Dmaven.test.failure.ignore=true
```