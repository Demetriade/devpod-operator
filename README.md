# DevPod Operator

Experiment with the [Java Operator SDK](https://javaoperatorsdk.io/docs/features/).

Goal: Reproduce [Uber's DevPod](https://uber.com/en-CA/blog/devpod-improving-developer-productivity-at-uber/), but simpler!

[![CNCF Operator Framework (French)](https://i.ytimg.com/vi/-9CF8pVlUXA/hqdefault.jpg)](https://youtu.be/-9CF8pVlUXA?si=82pRXCAUje-02zcJ&t=2542 "Everything Is AWESOME")

## Dev set up

### 🐳 Build Docker image

```bash
mvn clean compile
mvn jib:dockerBuild
```

### ☸️ Kind cluster

If you don't have any cluster, start a KinD!

```bash
# Create kind cluster
kind create cluster --config="./k8s/kind/config.yaml" --name=cncf-cluster

# Delete kind cluster
kind delete cluster -n cncf-cluster

# Load local image in KinD cluster
kind -n cncf-cluster load docker-image devpod-operator
```

### 💻 Installation

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

### 🚀 Usage

```bash
# Deploy your CRDs
kubectl apply -f ./k8s/devpod-charles.yaml
```