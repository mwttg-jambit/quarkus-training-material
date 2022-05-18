package com.jambit.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "at.java")
public interface AtJavaPropertyHolder {
    String name();
    String email();
    String address();
}
