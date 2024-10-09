package com.upbusab.bureau.domain;

import java.net.URL;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.ReflectionUtils;

public class ResourceService {

    public static void main(String[] args) {
        System.out.println("ResourceService");
        Reflections reflections = new Reflections("com.upbusab.bureau.domain");
        Set<URL> resources = reflections.get(ReflectionUtils.Resources.of("*"));
        for(URL r:resources) {
            System.out.println(r);
        }
    }

}
