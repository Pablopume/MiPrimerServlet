package org.example;



import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;


public class Test {

    public String getMensaje() {
        return "Hola injectado tomcat mundo";
    }
}
