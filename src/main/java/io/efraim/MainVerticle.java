package io.efraim;

import io.efraim.verticles.AuthVerticle;
import io.efraim.verticles.RESTVerticle;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;

/**
 * Main Verticle
 * <br>
 * Retrieves Config from Filepath and deploy verticles with its specific options
 *
 * @author <a href="https://github.com/EfraimLA" target="_blank"></a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class MainVerticle extends AbstractVerticle {

    /**
     * Start method get verticle config and deploy them
     */
    @Override
    public void start() {
        // Set Config Filepath
        ConfigStoreOptions storeOptions = new ConfigStoreOptions().setType("file")
                .setConfig(new JsonObject().put("path", "src/main/conf/verticles.json"));

        // Add Filepath Config to Config Retriever
        ConfigRetrieverOptions options = new ConfigRetrieverOptions().addStore(storeOptions);

        // Get Config Retriever
        ConfigRetriever retriever = ConfigRetriever.create(vertx, options);

        // Get Config with a Callback
        retriever.getConfig(res -> {
            if (res.failed()) {
                System.out.println("::::::::::::::::::::::::: Failed retrieving config :::::::::::::::::::::::::::");
            } else {
                System.out.println(":::CONFIG RETRIEVED:::");
                // Gets Config Of Each
                JsonObject RESTVerticleConfig = res.result().getJsonObject("RESTVerticle");
                JsonObject AuthVerticleConfig = res.result().getJsonObject("AuthVerticle");

                // Deploy Verticles
                vertx.deployVerticle(RESTVerticle.class.getName(), new DeploymentOptions().setConfig(RESTVerticleConfig));
                vertx.deployVerticle(AuthVerticle.class.getName(), new DeploymentOptions().setConfig(AuthVerticleConfig));
            }
        });
    }

}
