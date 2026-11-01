package Arisza.antix.combat.weaponeffects;

import java.awt.Color;

import org.lwjgl.util.vector.Vector2f;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.DamageType;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.OnHitEffectPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;

public class SpikeEffect implements OnHitEffectPlugin {


	private ShipAPI ship;
	private String automated;

	public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target,
	                  Vector2f point, boolean shieldHit, ApplyDamageResultAPI damageResult, CombatEngineAPI engine) {
//		if (!shieldHit && target instanceof ShipAPI) {
//			ship = (ShipAPI) target;
//			ship.setCurrentCR(ship.getCurrentCR() - 0.05f);
//			if (ship.getCaptain().isAICore() && ship.getCaptain() != null){
//
//				ship.setCaptain(null);
//		}
			float emp = 2000f;
			float dam = projectile.getDamageAmount();

			engine.spawnEmpArc(projectile.getSource(), point, target, target,
					DamageType.ENERGY,
					dam,
					emp, // emp
					100000f, // max range
					"tachyon_lance_emp_impact",
					20f, // thickness

					new Color(25,100,155,255),
					new Color(60,255, 232, 218)
			);

			//engine.spawnProjectile(null, null, "plasma", point, 0, new Vector2f(0, 0));
		}
	}

