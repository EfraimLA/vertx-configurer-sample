package io.efraim.verticles;

import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;

/**
 * AuthVerticle
 * <p>
 * Reactive Verticle
 *
 * @author <a href="https://github.com/EfraimLA" target="_blank"></a>
 * @version 1.0.0
 */
public class AuthVerticle extends AbstractVerticle {

    /**
     * start method get config and start http server
     */
    @Override
    public void start() {
        // Gets context then config
        JsonObject config = vertx.getOrCreateContext().config();

        // Starts Http Server with Single
        vertx.createHttpServer()
                .requestHandler(rc -> rc.response().end("Ready"))
                .rxListen(config.getInteger("PORT"))
                .subscribe(res -> System.out.println("REST Verticle Started\nRunning on PORT : " + config.getInteger("PORT") + "\nCONFIG :::" + config),
                        Throwable::printStackTrace);
    }
}
