package Arisza.antix.hullmods;

import java.util.LinkedHashMap;
import java.util.Map;

import Arisza.antix.combat.util.MicrolayerShipShroud;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.combat.dweller.DwellerCombatStrategyForBothSidesPlugin;
import com.fs.starfarer.api.impl.combat.dweller.DwellerShipCreator;


/**
 * Required for Shrouded Dweller "ships" - spawns combat entities for custom rendering etc.
 *
 * @author Alex
 *
 */
public class microlayerShroudHullmod extends BaseHullMod {

    public static Map<String, DwellerShipCreator> SHIP_CREATORS = new LinkedHashMap<>();

    static {
        //SHIP_CREATORS.put("shrouded_tendril", new TestDwellerShipCreator());
        SHIP_CREATORS.put("", new MicrolayerShipShroud());

    }


    public static String INITED_DWELLER_STUFF = "inited_dweller_stuff";

    protected DwellerShipCreator getShipCreator(String hullId) {
        return SHIP_CREATORS.get(hullId);
    }
    protected boolean addStrategyAI() {
        return true;
    }

    @Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        if (stats == null || stats.getVariant() == null) return;
        String hullId = stats.getVariant().getHullSpec().getBaseHullId();
        DwellerShipCreator creator = getShipCreator(hullId);
        if (creator != null) {
            creator.initBeforeShipCreation(hullSize, stats, id);
        }
    }

    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        if (ship == null || ship.getHullSpec() == null) return;
        String hullId = ship.getHullSpec().getBaseHullId();
        DwellerShipCreator creator = getShipCreator(hullId);
        if (creator != null) {
            creator.initAfterShipCreation(ship, id);
        }
    }



    @Override
    public void applyEffectsAfterShipAddedToCombatEngine(ShipAPI ship, String id) {
        if (ship == null || ship.getHullSpec() == null) return;
        String hullId = ship.getHullSpec().getBaseHullId();
        DwellerShipCreator creator = getShipCreator(hullId);
        if (creator != null) {
            creator.initAfterShipAddedToCombatEngine(ship, id);
        }

        if (addStrategyAI()) {
            CombatEngineAPI engine = Global.getCombatEngine();
            if (!engine.hasPluginOfClass(DwellerCombatStrategyForBothSidesPlugin.class)) {
                engine.addPlugin(new DwellerCombatStrategyForBothSidesPlugin());
            }
        }
    }

    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {
        if (amount <= 0f || ship == null) return;

        if (ship.hasTag(INITED_DWELLER_STUFF)) return;
        ship.addTag(INITED_DWELLER_STUFF);

        if (ship == null || ship.getHullSpec() == null) return;
        String hullId = ship.getHullSpec().getBaseHullId();
        DwellerShipCreator creator = getShipCreator(hullId);
        if (creator != null) {
            creator.initInCombat(ship);
        }
    }
}














