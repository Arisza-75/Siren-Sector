package Arisza.antix.combat.weaponeffects;

import java.util.Random;

import org.lazywizard.lazylib.VectorUtils;
import org.lwjgl.util.vector.Vector2f;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BeamAPI;
import com.fs.starfarer.api.combat.BeamEffectPlugin;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.EmpArcEntityAPI;
import com.fs.starfarer.api.util.IntervalUtil;
import com.fs.starfarer.api.util.Misc;
//borrowed from Halo dynamics
public class BeamArcEffect implements BeamEffectPlugin {
    private IntervalUtil fireInterval = new IntervalUtil(0.2f, 0.25f);


    public void advance(float amount, CombatEngineAPI engine, BeamAPI beam) {

        if (beam.getBrightness() >= 0.5) {
            fireInterval.advance(amount);
            if (fireInterval.intervalElapsed())
                createVisualArc(beam, beam.getWidth());
        }
    }


    public void createVisualArc(BeamAPI beam, float r) {
        float dist = Misc.getDistance(beam.getFrom(), beam.getRayEndPrevFrame());
        Vector2f direction = VectorUtils.getDirectionalVector(beam.getFrom(), beam.getRayEndPrevFrame());
        float gap = 100f;
        float gap_random = 10f;
        float next = gap + (1 - 0.5f) * (float) gap_random;
        float currentDist = 0;
        Vector2f last = new Vector2f(beam.getFrom());
        while ((dist - currentDist) > next) {
            currentDist += next;
            Vector2f n = new Vector2f(direction);
            n.scale(currentDist);
            n = Vector2f.add(beam.getFrom(), n, n);
            n = Misc.getPointWithinRadiusUniform(n, r, r * 1.5f, new Random());
            EmpArcEntityAPI arc = Global.getCombatEngine().spawnEmpArcVisual(last, beam.getSource(), n,
                    beam.getSource(), beam.getWidth() / 3f + beam.getWidth() / 8f * (float) Math.random(),
                    beam.getFringeColor(), beam.getCoreColor());
            arc.setSingleFlickerMode();
            last = new Vector2f(n);
            next = gap + (1 - 0.5f) * (float) gap_random;
        }
        EmpArcEntityAPI arc = Global.getCombatEngine().spawnEmpArcVisual(last, beam.getSource(),
                beam.getRayEndPrevFrame(), beam.getSource(),
                beam.getWidth() / 3f + beam.getWidth() / 8f * (float) Math.random(), beam.getFringeColor(),
                beam.getCoreColor());
        arc.setSingleFlickerMode();
    }
}
