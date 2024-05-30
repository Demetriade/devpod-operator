IMAGE_TAG=1.0.0

mvn jib:dockerBuild

# docker tag localhost/devpod-operator:latest

kubectl apply -f ./k8s/operator.yaml

mvn clean compile
kubectl apply -f ./target/classes/META-INF/fabric8/devpods.cncf-v1.yml