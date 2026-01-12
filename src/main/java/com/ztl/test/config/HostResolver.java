package com.ztl.test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "host-resolver")
public class HostResolver {
    private Map<String, Host> hosts;

    @Data
    public static class Host {
        private String hostName;
        private String hostUrl;
    }

    public Host getHost(String hostName) {
        return hosts.values().stream()
                .filter(h -> h.getHostName().equalsIgnoreCase(hostName))
                .findFirst()
                .orElse(null);
    }
}

