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
kubectl config set-context --namespace=cncf --current
# Install the Operator
kubectl apply -f ./k8s/operator.yaml
# Install the CRDs
kubectl apply -f ./target/classes/META-INF/fabric8/devpods.com.cncf-v1.yml

# Start using the CRDs
kubectl apply -f ./k8s/devpod-charles.yaml
```

## Kind cluster

```bash
# Create kind cluster
kind create cluster --config="./k8s/kind/config.yaml" --name=cncf-cluster

# Delete kind cluster
kind delete cluster -n cncf-cluster

# Load local image in KinD cluster
kind -n cncf-cluster load docker-image devpod-operator
```

