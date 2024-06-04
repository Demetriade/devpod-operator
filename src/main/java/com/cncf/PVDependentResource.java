package com.cncf;


import com.cncf.crds.DevPod;
import io.fabric8.kubernetes.api.model.PersistentVolume;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;

import java.util.HashMap;
import java.util.Map;

import static io.javaoperatorsdk.operator.ReconcilerUtils.loadYaml;

@KubernetesDependent(labelSelector = "app.kubernetes.io/managed-by=remote-dev-operator")
public class PVDependentResource extends CRUDKubernetesDependentResource<PersistentVolume, DevPod> {

    public PVDependentResource() {
        super(PersistentVolume.class);
    }

    @Override
    protected PersistentVolume desired(DevPod devPod, Context<DevPod> context) {
        Map<String, String> labels = new HashMap<>();
        labels.put("app.kubernetes.io/managed-by", "remote-dev-operator");
        String namePrefix = devPod.getSpec().getOwner() + "-" + devPod.getSpec().getFlavor();
        String pvName = namePrefix + "-pv";
        PersistentVolume pvc = loadYaml(PersistentVolume.class, getClass(), "pvc.yaml");
        pvc.getMetadata().setName(pvName);
        pvc.getMetadata().setLabels(labels);

        return pvc;
    }
}
