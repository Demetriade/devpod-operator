kubectl delete -f ./k8s/operator.yaml
kubectl delete -f ./target/classes/META-INF/fabric8/devpods.com.cncf-v1.yml

mvn clean compile
mvn jib:dockerBuild
docker build -t java-devpod ./devpod-flavors/java/

kind -n cncf-cluster load docker-image devpod-operator
kind -n cncf-cluster load docker-image java-devpod

kubectl apply -f ./k8s/operator.yaml
kubectl apply -f ./target/classes/META-INF/fabric8/devpods.com.cncf-v1.yml
