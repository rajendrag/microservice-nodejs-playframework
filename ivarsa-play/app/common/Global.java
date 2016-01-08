package common;

import play.GlobalSettings;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by RP on 1/7/16.
 */
public class Global extends GlobalSettings {

    @Override
    public Action onRequest(Http.Request request, Method actionMethod) {
        return new Action.Simple() {
            @Override
            public F.Promise<Result> call(Http.Context ctx) throws Throwable {
                Map<String, Object> args = ctx.args;
                args.put("tenantId", "uchealth");
                return delegate.call(ctx);
            }
        };
    }
}
