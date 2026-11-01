package Arisza.antix.hullmods;

import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class ashenMight extends BaseHullMod {
	
	public static float SMOD_MANEUVER_PENALTY = 25f;
	
	
	private static Map mag = new HashMap();
	static {
//		mag.put(HullSize.FRIGATE, 100f);
//		mag.put(HullSize.DESTROYER, 200f);
//		mag.put(HullSize.CRUISER, 300f);
//		mag.put(HullSize.CAPITAL_SHIP, 400f);
		mag.put(HullSize.FRIGATE, 150f);
		mag.put(HullSize.DESTROYER, 300f);
		mag.put(HullSize.CRUISER, 400f);
		mag.put(HullSize.CAPITAL_SHIP, 500f);
	}
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		
		stats.getArmorBonus().modifyFlat(id, (Float) 500f);
		stats.getEffectiveArmorBonus().modifyFlat(id, (Float) mag.get(hullSize));


		

	}

	public void advanceInCombat(ShipAPI ship, float amount, MutableShipStatsAPI stats, String id) {
	if (ship.getHitpoints() < ship.getMaxHitpoints() * 0.25f) {
		stats.getBallisticWeaponDamageMult().modifyPercent(id, 150f);
	}
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + ((Float) mag.get(HullSize.FRIGATE)).intValue();
		if (index == 1) return "" + ((Float) mag.get(HullSize.DESTROYER)).intValue();
		if (index == 2) return "" + ((Float) mag.get(HullSize.CRUISER)).intValue();
		if (index == 3) return "" + ((Float) mag.get(HullSize.CAPITAL_SHIP)).intValue();

		return null;
		//if (index == 0) return "" + ((Float) mag.get(hullSize)).intValue();
		//return null;
	}
	
	
	@Override
	public String getSModDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
		if (index == 0) return "" + (int) SMOD_MANEUVER_PENALTY + "%";
		return null;
	}
	
	@Override
	public boolean isSModEffectAPenalty() {
		return true;
	}
	
	

	public boolean isApplicableToShip(ShipAPI ship) {
		//if (ship.getMutableStats().getCargoMod().computeEffective(ship.getHullSpec().getCargo()) < 70) return false;

		return true;
	}


}
