package common;

import models.bo.UnitEntity;
import play.db.jpa.JPA;
import play.libs.F;
import play.libs.F.Promise;

import static play.libs.F.Promise.promise;


/**
 * Created by RP on 1/8/16.
 */
public class AsyncJPA extends JPA {

    public static <T> Promise<T> doInAsync(F.Function0<T> block) {
        return promise(() -> {
            return JPA.withTransaction(() -> {
                return block.apply();
            });
        });
    }
}
