package Arisza.antix.combat.ai;



import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.util.IntervalUtil;
import org.lwjgl.util.vector.Vector2f;


public class ally_target implements ShipSystemAIScript {
	private final IntervalUtil thinkInterval = new IntervalUtil(0.25f, 0.33f);

	private ShipAPI ship;
	private ShipSystemAPI system;
	private ShipwideAIFlags flags;
	private CombatEngineAPI engine;
	@Override
	public void init(ShipAPI ship, ShipSystemAPI system, ShipwideAIFlags flags, CombatEngineAPI engine) {
		this.ship = ship;
		this.system = system;
		this.flags = flags;
		this.engine = engine;



	}

	@Override
	public void advance(float amount, Vector2f missileDangerDir, Vector2f collisionDangerDir, ShipAPI target) {
		if (ship == null || system == null || !ship.isAlive() || !canUseSystem())
			return;
		if (target == null || !target.isAlive())
			return;
		var friendly = ship.getOwner() == target.getOwner();
		if (!friendly)
			return;

		float currFlux = target.getCurrFlux();
		if (currFlux > 1000f) { // use your  threshold
			ship.useSystem();
		}
	}



	private boolean canUseSystem() {
		if (system == null) {
			return false;
		}
		if (system.isOutOfAmmo() || system.isActive() || system.isOn() || system.isChargeup() || system.isChargedown() || system.isCoolingDown()) {
			return false;
		}
		if (ship.getFluxTracker() != null) {
			if (ship.getFluxTracker().isOverloadedOrVenting()) {
				return false;
			}
			if (ship.getFluxTracker().getFluxLevel() > 1000) {
				return false;
			}
		}
		return true;
	}
}