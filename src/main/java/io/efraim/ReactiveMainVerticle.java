package io.efraim;

import io.efraim.verticles.AuthVerticle;
import io.efraim.verticles.RESTVerticle;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.reactivex.core.AbstractVerticle;


/**
 * Main Verticle Using RxJava 2
 * <br>
 * Retrieves Config for Verticles from JSON File and deploy them with its specific options
 *
 * @author <a href="https://github.com/EfraimLA" target="_blank"></a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class ReactiveMainVerticle extends AbstractVerticle {

    /**
     * Start method, configure verticles and deploy them
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

        // Get Config as a Single
        retriever.rxGetConfig().subscribe(res -> {
            System.out.println(":::CONFIG RETRIEVED:::");
            // Gets Config Of Each
            JsonObject RESTVerticleConfig = res.getJsonObject("RESTVerticle");
            JsonObject AuthVerticleConfig = res.getJsonObject("AuthVerticle");

            // Deploy Verticles with a Single
            vertx.rxDeployVerticle(RESTVerticle.class.getName(), new DeploymentOptions().setConfig(RESTVerticleConfig))
                    .subscribe();
            vertx.rxDeployVerticle(AuthVerticle.class.getName(), new DeploymentOptions().setConfig(AuthVerticleConfig))
                    .subscribe();
        }, Throwable::printStackTrace);
    }
}
