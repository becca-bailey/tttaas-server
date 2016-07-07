package application;

import com.rnelson.server.routing.RouteInitializer;
import com.rnelson.server.routing.Router;
import com.rnelson.server.utilities.http.HttpMethods;

public class Routes implements RouteInitializer {

    @Override
    public void initializeRoutes(Router router) {
        router.addRoute(HttpMethods.get,"/");
    }
}
