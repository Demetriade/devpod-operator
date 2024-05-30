package com.cncf;


import com.cncf.crds.DevPod;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;

import java.util.HashMap;
import java.util.Map;

import static io.javaoperatorsdk.operator.ReconcilerUtils.loadYaml;

@KubernetesDependent(labelSelector = "app.kubernetes.io/managed-by=remote-dev-operator")
public class PVCDependentResource extends CRUDKubernetesDependentResource<PersistentVolumeClaim, DevPod> {

    public PVCDependentResource() {
        super(PersistentVolumeClaim.class);
    }

    @Override
    protected PersistentVolumeClaim desired(DevPod devPod, Context<DevPod> context) {
        Map<String, String> labels = new HashMap<>();
        labels.put("app.kubernetes.io/managed-by", "remote-dev-operator");
        String pvcName = devPod.getSpec().getOwner() + "-" + devPod.getSpec().getFlavor() + "-pvc";
        PersistentVolumeClaim pvc = loadYaml(PersistentVolumeClaim.class, getClass(), "pvc.yaml");
        pvc.getMetadata().setName(pvcName);
        pvc.getMetadata().setLabels(labels);

        return pvc;
    }
}
