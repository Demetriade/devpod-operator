package com.cncf;

import com.cncf.crds.DevPod;
import com.cncf.crds.DevPodStatus;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentStatus;
import io.javaoperatorsdk.operator.api.reconciler.*;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@ControllerConfiguration(dependents = {
        @Dependent(type = PVDependentResource.class),
        @Dependent(type = PVCDependentResource.class),
        @Dependent(type = DeploymentDependentResource.class)
})
public class DevPodReconciler implements Reconciler<DevPod> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public UpdateControl<DevPod> reconcile(DevPod devPod,
                                           Context<DevPod> context) {
        return context.getSecondaryResource(Deployment.class).map(deployment -> {
            DevPod updatedDevPod = updateDevPodStatus(devPod, deployment);
            log.info(
                    "Updating status of DevPod {} in namespace {} to {} ready replicas",
                    devPod.getMetadata().getName(),
                    devPod.getMetadata().getNamespace(),
                    devPod.getStatus().getReadyReplicas());
            return UpdateControl.patchStatus(updatedDevPod);
        }).orElseGet(UpdateControl::noUpdate);
    }

    private DevPod updateDevPodStatus(DevPod devPod, Deployment deployment) {
        DeploymentStatus deploymentStatus =
                Objects.requireNonNullElse(deployment.getStatus(), new DeploymentStatus());
        int readyReplicas = Objects.requireNonNullElse(deploymentStatus.getReadyReplicas(), 0);
        DevPodStatus status = new DevPodStatus();
        status.setReadyReplicas(readyReplicas);
        devPod.setStatus(status);
        return devPod;
    }

//    @Override
//    public ErrorStatusUpdateControl<DevPod> updateErrorStatus(DevPod resource,
//                                                               Context<DevPod> context, Exception e) {
//        resource.getStatus().setErrorMessage("Error: " + e.getMessage());
//        return ErrorStatusUpdateControl.updateStatus(resource);
//    }
//
//    @Override
//    public DeleteControl cleanup(DevPod resource, Context<DevPod> context) {
//        return DeleteControl.defaultDelete();
//    }
}
