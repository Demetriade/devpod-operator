package cncf;


import cncf.crds.DevPod;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;

import java.util.HashMap;
import java.util.Map;

import static io.javaoperatorsdk.operator.ReconcilerUtils.loadYaml;

@KubernetesDependent(labelSelector = "app.kubernetes.io/managed-by=remote-dev-operator")
public class DeploymentDependentResource extends CRUDKubernetesDependentResource<Deployment, DevPod> {
    public DeploymentDependentResource() {
        super(Deployment.class);
    }

    @Override
    protected Deployment desired(DevPod devPod, Context<DevPod> context) {
        String deploymentName = devPod.getSpec().getOwner() + "-" + devPod.getSpec().getFlavor() + "-devpod";
        String pvcName = devPod.getSpec().getOwner() + "-" + devPod.getSpec().getFlavor() + "-pvc";
        Map<String, String> labels = new HashMap<>();
        labels.put("app.kubernetes.io/managed-by", "remote-dev-operator");
        labels.put("app", deploymentName);

        Deployment deployment = loadYaml(Deployment.class, getClass(), "deployment.yaml");
        deployment.getMetadata().setLabels(labels);
        deployment.getMetadata().setName(deploymentName);
        deployment.getMetadata().setNamespace(devPod.getMetadata().getNamespace());
        deployment.getSpec().getSelector().getMatchLabels().put("app", deploymentName);

        deployment
                .getSpec()
                .getTemplate()
                .getMetadata()
                .getLabels()
                .put("app", deploymentName);
        deployment
                .getSpec()
                .getTemplate()
                .getSpec()
                .getContainers()
                .get(0)
                .setName(deploymentName);
//        deployment.getSpec()
//                .getTemplate()
//                .getSpec()
//                .getVolumes()
//                .get(0)
//                .getPersistentVolumeClaim()
//                .setClaimName(pvcName);
        return deployment;
    }
}
