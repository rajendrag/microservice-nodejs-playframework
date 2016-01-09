package controllers;

import models.bo.UnitEntity;
import play.db.jpa.Transactional;
import play.libs.F;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.UnitSetupService;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * Created by RP on 12/15/15.
 */
public class UnitSetup extends Controller {

    @Inject
    private UnitSetupService unitSetupService;


    public F.Promise<Result> index() {
        return list();
    }

    /**
     * Returns list of units.
     */
    public F.Promise<Result> list() {
        F.Promise<Set<UnitEntity>> entities = unitSetupService.findAll();
        return entities.map((Set<UnitEntity> e) -> {
            return ok(Json.toJson(e));
        });
    }

    @Transactional
    public Result load(String unitId) {
    	return ok(Json.toJson(UnitEntity.find(UnitEntity.class, unitId)));
    }
}
