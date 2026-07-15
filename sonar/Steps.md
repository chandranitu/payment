## URLS and port

jenkins- http://localhost:8081

postgres- 5432 

sonar- http://localhost:9001   default usr/pass - admin/admin  changed M..n@1.3!

# Generate Sonar Token

Login->My Account->Security->Generate Token

name-jenkins-sonar
user token

squ_28ecba2ce6b216253fae2e43a91400833bb1574b


#Jenkins Configuration 

Git configuration
Manage Jenkins->Credentials->system      

sonar configuration in jenkins. 
Add -secret text
iD-


| Scope       | Global                                   
| Secret      | *Paste the SonarQube token you generated* 
| ID          | `sonar-token`                             
| Description | SonarQube Token (optional)               


#Install plugins:
Manage Jenkins->Plugins
-------------------
SonarQube Scanner
Quality Gates
GitHub Integration  
Pipeline github

restart jenkins

# Manage Jenkins -> System

Find- SonarQube Servers

Name:
SonarQube

Server URL:
http://sonarqube:9000

Authenticate- secret text

#update payment project pom.xml

<properties>
    <sonar.host.url>http://sonarqube:9000</sonar.host.url>
</properties>

<build>
    <plugins>

        <plugin>
            <groupId>org.sonarsource.scanner.maven</groupId>
            <artifactId>sonar-maven-plugin</artifactId>
            <version>3.11.0.3922</version>
        </plugin>

    </plugins>
</build>


# Verify Maven Scanner  inside jenkins container

cd /var/jenkins_home/workspace/sonarAnalysis

mvn sonar:sonar \
-Dsonar.host.url=http://sonarqube:9000 \
-Dsonar.login=squ_28ecba2ce6b216253fae2e43a91400833bb1574b

#debug inside jenkins container
mvn help:effective-pom | grep sonar.host.url

#try finally below url or in sonar gui
http://sonarqube:9000/dashboard?id=my-app


