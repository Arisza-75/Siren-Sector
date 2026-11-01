package Arisza.antix.combat.weaponeffects;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import org.dark.shaders.distortion.DistortionShader;
import org.dark.shaders.distortion.RippleDistortion;
import org.lwjgl.util.vector.Vector2f;

public class Singularitypulse extends BaseEveryFrameCombatPlugin {

	private static final Vector2f ZERO = new Vector2f();
	public void advance(DamagingProjectileAPI projectile) {



	RippleDistortion ripple = new RippleDistortion(projectile.getLocation(), ZERO);
					ripple.setSize(1000f);
					ripple.setIntensity(500f);
					ripple.setFrameRate(30f);
					ripple.fadeInSize(0.2f);
					ripple.fadeOutIntensity(0.7f);

		DistortionShader.addDistortion(ripple);

	}
}

