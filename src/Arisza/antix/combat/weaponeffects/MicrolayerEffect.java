//package Arisza.antix.combat.weaponeffects;
////from DEX
//import com.fs.starfarer.api.Global;
//import com.fs.starfarer.api.combat.*;
//import com.fs.starfarer.api.fleet.FleetMemberAPI;
//import com.fs.starfarer.api.fleet.FleetMemberType;
//import com.fs.starfarer.api.impl.campaign.ids.Factions;
//import com.fs.starfarer.api.impl.campaign.ids.Personalities;
//import com.fs.starfarer.api.impl.combat.dweller.DwellerShroud;
//import com.fs.starfarer.api.mission.FleetSide;
//import org.lwjgl.util.vector.Vector2f;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class MicrolayerEffect implements OnFireEffectPlugin, EveryFrameWeaponEffectPlugin {
//
//
//	protected List<ShipAPI> localSpawnedShips = new ArrayList<>();
//
//	public String getVariantToSpawn(){
//		return "udf_microlayer_base";
//	}
//
//
//		public void initInCombat(ShipAPI ship) {
//
//
//			DwellerShroud shroud = createShroud(ship);
//
//		}
//
//
//		protected DwellerShroud createShroud(ShipAPI ship) {
//			DwellerShroud shroud = DwellerShroud.getShroudFor(ship);
//			if (shroud == null) {
//				shroud = new DwellerShroud(ship, createShroudParams(ship));
//
//			}
//			return shroud;
//		}
//
//		protected DwellerShroud.DwellerShroudParams createShroudParams(ShipAPI ship) {
//			DwellerShroud.DwellerShroudParams params = DwellerShroud.createBaselineParams(ship);
//			modifyBaselineShroudParams(ship, params);
//			return params;
//		}
//
//		protected void modifyBaselineShroudParams(ShipAPI ship, DwellerShroud.DwellerShroudParams params) {
//		}
//
//	@Override
//	public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {
//		if (engine.isPaused() || weapon.getShip() == null) return;
//
//
//		localSpawnedShips.removeIf(spawned -> !engine.isEntityInPlay(spawned) || !spawned.isAlive() || spawned.isHulk());
//		weapon.setForceNoFireOneFrame(localSpawnedShips.size() >= 8);
//	}
//
//	@Override
//	public void onFire(DamagingProjectileAPI projectile, WeaponAPI weapon, CombatEngineAPI engine) {
//		ShipAPI ship = weapon.getShip();
//		if (ship == null || ship.getOwner() == 100) return;
//
//		CombatFleetManagerAPI FleetManager = engine.getFleetManager(ship.getOwner());
//		FleetManager.setSuppressDeploymentMessages(true);
//		FleetSide fleetSide = FleetSide.values()[ship.getOwner()];
//
//
//		ShipAPI spawnedShip = spawnShipOrWingDirectly(getVariantToSpawn(), fleetSide, 0.55f, projectile.getLocation(), projectile.getFacing());
//
//		if (spawnedShip != null) {
//			spawnedShip.getVelocity().set(projectile.getVelocity());
//			spawnedShip.setCollisionClass(CollisionClass.FIGHTER);
//			spawnedShip.setName(Global.getSector().getFaction(Factions.NEUTRAL).pickRandomShipName());
//			spawnedShip.setSpawnDebris(false);
//			localSpawnedShips.add(spawnedShip);
//		}
//
//		engine.removeEntity(projectile);
//		FleetManager.setSuppressDeploymentMessages(false);
//	}
//
//	public ShipAPI spawnShipOrWingDirectly(String variantId, FleetSide side, float combatReadiness, Vector2f location, float facing) {
//		FleetMemberAPI member = Global.getFactory().createFleetMember(FleetMemberType.SHIP, variantId);
//		member.setOwner(side.ordinal());
//		member.getCrewComposition().addCrew(member.getNeededCrew());
//
//		ShipAPI ship = Global.getCombatEngine().getFleetManager(side).spawnFleetMember(member, location, facing, 0f);
//		ship.setCRAtDeployment(combatReadiness);
//		ship.setCurrentCR(combatReadiness);
//		ship.setOwner(side.ordinal());
//
//
//		ShipAIConfig config = new ShipAIConfig();
//		config.personalityOverride = Personalities.RECKLESS;
//		config.backingOffWhileNotVentingAllowed = false;
//
//
//		ship.getShipAI().forceCircumstanceEvaluation();
//		return ship;
//	}
//}