package com.bell.cts.hackathon2023.crds;

import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

@Group("com.bell.cts.hackathon2023")
@Version("v1")
public class DevPod extends CustomResource<DevPodSpec, DevPodStatus> implements Namespaced {
}
