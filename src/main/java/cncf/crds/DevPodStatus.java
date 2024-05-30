package cncf.crds;

import io.javaoperatorsdk.operator.api.ObservedGenerationAwareStatus;

public class DevPodStatus extends ObservedGenerationAwareStatus {

    private Integer readyReplicas = 0;

    public Integer getReadyReplicas() {
        return readyReplicas;
    }

    public void setReadyReplicas(Integer readyReplicas) {
        this.readyReplicas = readyReplicas;
    }
}
