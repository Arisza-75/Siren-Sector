package Arisza.antix.combat.weaponeffects;
//ripped off directly from the LTA talon launcher, give all credit to Yui I would have never figured this shit out on my own
//also copied from DEX
import java.util.List;
import java.util.ArrayList;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.util.IntervalUtil;
import org.lwjgl.util.vector.Vector2f;

public class shipspawnlimit extends BaseEveryFrameCombatPlugin {

    private CombatEngineAPI engine;
    private final IntervalUtil DARKSUMMONINGRITUALFROMBEYONDTHEGREYSUN2 = new IntervalUtil(0.1f, 0.1f);
    private final IntervalUtil interval = new IntervalUtil(30f, 1f);
    private float timer = 0f;

    private static final String ShipID = "udf_microlayer_base";
    private static final String ProjectileID = "ship_constructor_shot";
    private static final String TAG = "microlayer_tag";

    @Override
    public void advance(float amount, List<InputEventAPI> events) {
        if (engine == null || engine.isPaused()) return;

        List<DamagingProjectileAPI> projectiles = engine.getProjectiles();
        List<DamagingProjectileAPI> projectiles_copy = new ArrayList<>(projectiles);
        int picket_count = 0;

        for (DamagingProjectileAPI projectile : projectiles_copy) {
            if (projectile.getProjectileSpecId() == null) continue;

            if (DARKSUMMONINGRITUALFROMBEYONDTHEGREYSUN2.getElapsed() >= 0.1f && picket_count < 8) {
                if (projectile.getProjectileSpecId().equals(ProjectileID)) {
                    Vector2f location = new Vector2f(projectile.getLocation());
                    ShipAPI ship = projectile.getSource();
                    float angle = projectile.getFacing();
                    int owner = projectile.getOwner();

                    engine.removeEntity(projectile);
                    CombatFleetManagerAPI FleetManager = engine.getFleetManager(ship.getOwner());
                    FleetManager.setSuppressDeploymentMessages(true);

                    FleetMemberAPI missileMember = Global.getFactory().createFleetMember(FleetMemberType.FIGHTER_WING, ShipID);
                    missileMember.getRepairTracker().setCrashMothballed(false);
                    missileMember.getRepairTracker().setMothballed(false);
                    missileMember.getRepairTracker().setCR(1f);
                    missileMember.setOwner(owner);
                    missileMember.getVariant().addTag(TAG);
                    missileMember.setAlly(ship.isAlly());

                    ShipAPI missile = engine.getFleetManager(owner).spawnFleetMember(missileMember, location, angle, 2.5f);
                    missile.setCRAtDeployment(0.55f);
                    missile.setCollisionClass(CollisionClass.FIGHTER);
                    missile.getVelocity().set(ship.getVelocity());
                    missile.setAngularVelocity(ship.getAngularVelocity());

                    interval.advance(amount);
                    timer += amount;

                    picket_count++;
                    //Duration of the effect
                    float duration = 0.05f;
                    if (timer >= duration) {
                        timer = 0f;
                        FleetManager.setSuppressDeploymentMessages(false);
                    }
                }
            }
            DARKSUMMONINGRITUALFROMBEYONDTHEGREYSUN2.advance(amount);
        }
    }

    @Override
    public void init(CombatEngineAPI engine) {
        this.engine = engine;
    }
}