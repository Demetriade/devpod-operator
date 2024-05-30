# Devpod Operator
https://javaoperatorsdk.io/docs/features/

## Build Docker image

```bash
mvn clean compile
mvn jib:dockerBuild
```

## Deploy in K8s

```bash
# Create a new namespace
kubectl create namespace "cncf"
# Install the Operator
kubectl apply -f ./k8s/operator.yaml
# Install the CRDs
kubectl apply -f ./target/classes/META-INF/fabric8/devpods.cncf-v1.yml

# Start using the CRDs
kubectl apply -f ./k8s/devpod-charles.yaml
```