#k8
kind create cluster --config kind-config.yml
kind get clusters

#------------------------------------------
-- Load Image in kind
kind load docker-image payment:latest


kubectl apply -f deployment.yml
kubectl get pods

kubectl apply -f service.yml
kubectl get svc

kubectl port-forward service/payment 8088:8088

http://localhost:8088

-- --------------------------------------
kubectl delete -f deployment.yml 
kubectl describe pod <podname>

-- test pod deployment
curl 

----------------------------------------------------

#debug host vs container
git ls-remote https://github.com/chandranitu/payment.git

#k8 verify
kubectl get pods
kubectl get deployments
kubectl get svc
kubectl get svc payment-service



