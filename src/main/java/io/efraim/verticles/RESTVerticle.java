package io.efraim.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

/**
 * RESTVerticle
 * <p>
 * Normal Verticle
 *
 * @author <a href="https://github.com/EfraimLA" target="_blank"></a>
 * @version 1.0.0
 */
public class RESTVerticle extends AbstractVerticle {

    /**
     * start method get config and start http server
     */
    @Override
    public void start() {
        // Gets context then config
        JsonObject config = vertx.getOrCreateContext().config();

        // Starts Http Server with Callback
        vertx.createHttpServer()
                .requestHandler(rc -> rc.response().end("Ready"))
                .listen(config.getInteger("PORT"), ar -> {
                    if (ar.succeeded()) {
                        System.out.println("REST Verticle Started\nRunning on PORT :" + config.getInteger("PORT") + "\nCONFIG :::" + config);
                    } else {
                        System.out.println("Failed Starting Verticle...");
                    }
                });
    }
}
