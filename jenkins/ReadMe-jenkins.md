# jenkins with maven

docker build -t jenkins-maven-docker .

docker run -d \
  --name jen-mvn \
  --user root \
  -p 8081:8080 \
  -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  jenkins-maven-docker
 
#url
http://0.0.0.0:8081/


#debug host vs container
git ls-remote https://github.com/chandranitu/payment.git

