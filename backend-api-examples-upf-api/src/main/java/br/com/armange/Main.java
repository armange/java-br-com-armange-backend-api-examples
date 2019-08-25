package br.com.armange;

import org.apache.catalina.LifecycleException;

import br.com.armange.backend.api.upf.server.http.tomcat.TomcatServer;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        TomcatServer.main(args);
    }
}
