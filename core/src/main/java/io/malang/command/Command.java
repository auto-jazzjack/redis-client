package io.malang.command;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Command {
    SET("set"),
    GET("get"),
    CLUSTER_INFO("cluster info");

    private final String cmd;
}