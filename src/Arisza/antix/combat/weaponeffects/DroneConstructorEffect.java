package Arisza.antix.combat.weaponeffects;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.impl.combat.dweller.DwellerShroud;
import com.fs.starfarer.api.impl.combat.dweller.VortexLauncherEffect;
import com.fs.starfarer.api.util.Misc;
import org.lwjgl.util.vector.Vector2f;
import java.util.ArrayList;
import java.util.List;



public class DroneConstructorEffect implements OnFireEffectPlugin, EveryFrameWeaponEffectPlugin {

	public static String VORTEX_WING = "mms_xvi_wpn_drone_wing";

	public static float LAUNCH_ARC = 60f;


	protected List<ShipAPI> localSpawnedShips = new ArrayList<>();

	public String getVariantToSpawn(){
		return "mms_xvi_wpn_drone_wing";
	}

	@Override
	public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {
		if (engine.isPaused() || weapon.getShip() == null) return;


		localSpawnedShips.removeIf(spawned -> !engine.isEntityInPlay(spawned) || !spawned.isAlive() || spawned.isHulk());
		weapon.setForceNoFireOneFrame(localSpawnedShips.size() >= 8);
	}

	@Override




	public void onFire(DamagingProjectileAPI projectile, WeaponAPI weapon, CombatEngineAPI engine) {

		//FighterWingSpecAPI spec = Global.getSettings().getFighterWingSpec(ATTACK_SWARM_WING);

		CombatFleetManagerAPI manager = engine.getFleetManager(projectile.getOwner());
		manager.setSuppressDeploymentMessages(true);
		// wing has size 1, so no other members besides the "leader"
		// point is to spawn a ship but without it showing up in the deployment dialog etc
		ShipAPI vortex = manager.spawnShipOrWing(VORTEX_WING,
				projectile.getLocation(), projectile.getFacing(), 0f, null);
		manager.setSuppressDeploymentMessages(false);

		Vector2f takeoffVel = Misc.getUnitVectorAtDegreeAngle(
				projectile.getFacing() + LAUNCH_ARC/2f - (float) Math.random() * LAUNCH_ARC);
		float velMult = 1f;
		if (Misc.getAngleDiff(projectile.getFacing(), weapon.getShip().getFacing()) > 150f) {
			velMult = 0.5f;
		}
		takeoffVel.scale(vortex.getMaxSpeed() * velMult);

		Vector2f.add(vortex.getVelocity(), takeoffVel, vortex.getVelocity());

		DwellerShroud shroud = DwellerShroud.getShroudFor(vortex);
		if (shroud != null) {
			shroud.custom1 = weapon.getShip();
		}

		DwellerShroud sourceShroud = DwellerShroud.getShroudFor(weapon.getShip());
		if (sourceShroud != null) {

			Vector2f dir = Misc.getUnitVectorAtDegreeAngle(projectile.getFacing());
			Vector2f offset = Vector2f.sub(projectile.getLocation(), sourceShroud.getAttachedTo().getLocation(), new Vector2f());
			Global.getCombatEngine().addPlugin(
					new VortexLauncherEffect.DisturbShroudPlugin(1f, dir, vortex, offset, sourceShroud,
							(int) (sourceShroud.getNumMembersToMaintain() * 0.1f)));
			//new DisturbShroudPlugin(1f, dir, offset, sourceShroud, 10));

		}

		engine.removeEntity(projectile);
	}


}