package at.javatraining.configdemo;


//import io.quarkus.arc.config.ConfigProperties; -> deprecated


import io.smallrye.config.ConfigMapping;

// @ConfigProperties(prefix = "at.javatraining") -> deprecated, was annotated on a class
@ConfigMapping(prefix = "at.javatraining")
public interface MultipleProperties {
    String dbhost();
    String greeting();


    default public String allProps() {
        return "MultipleProperties{" +
                "dbhost='" + dbhost() + '\'' +
                ", greeting='" + greeting() + '\'' +
                '}';
    }
}
