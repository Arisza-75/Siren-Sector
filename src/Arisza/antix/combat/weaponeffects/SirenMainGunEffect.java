package Arisza.antix.combat.weaponeffects;


import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.util.IntervalUtil;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

public class SirenMainGunEffect implements BeamEffectPlugin {
    private IntervalUtil fireInterval = new IntervalUtil(0.6f, 0.6f);
    private IntervalUtil fireInterval2 = new IntervalUtil(0.2f, 0.2f);



    public void advance(float amount, CombatEngineAPI engine, BeamAPI beam) {
        fireInterval.advance(amount);
        if (fireInterval.intervalElapsed()) {
            WeaponAPI weapon = beam.getWeapon();
            float angle = weapon.getCurrAngle();
            Vector2f point3 =  MathUtils.getPointOnCircumference(beam.getFrom(), 0, angle - 90f);
            engine.spawnProjectile(weapon.getShip(), weapon, "siren_MainGun1", point3, angle, weapon.getShip().getVelocity());
            Global.getSoundPlayer().playSound("cryoblaster_fire", 1.0f, 1.0f, point3, weapon.getShip().getVelocity());
        }
            fireInterval2.advance(amount);
            if (fireInterval2.intervalElapsed()) {
                WeaponAPI weapon = beam.getWeapon();
                float angle = weapon.getCurrAngle();
                Vector2f point1 =  MathUtils.getPointOnCircumference(beam.getFrom(), 10, angle + 90f);
                Vector2f point2 =  MathUtils.getPointOnCircumference(beam.getFrom(), 10, angle - 90f);
                engine.spawnProjectile(weapon.getShip(), weapon, "siren_MainGun2", point1, angle, weapon.getShip().getVelocity());
                engine.spawnProjectile(weapon.getShip(), weapon, "siren_MainGun2", point2, angle, weapon.getShip().getVelocity());
                Global.getSoundPlayer().playSound("antimatter_blaster_fire", 1.0f, 1.0f, point1, weapon.getShip().getVelocity());
                fireInterval2.nextInterval();
            }


    }

    }



