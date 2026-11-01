package Arisza.antix.world;

import Arisza.antix.world.systems.azur_alaska;

import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;

public class ALGen {
    public static void initFactionRelationships(SectorAPI sector) {
        FactionAPI player = sector.getFaction(Factions.PLAYER);
        FactionAPI hegemony = sector.getFaction(Factions.HEGEMONY);
        FactionAPI tritachyon = sector.getFaction(Factions.TRITACHYON);
        FactionAPI pirates = sector.getFaction(Factions.PIRATES);
        FactionAPI independent = sector.getFaction(Factions.INDEPENDENT);
        FactionAPI church = sector.getFaction(Factions.LUDDIC_CHURCH);
        FactionAPI path = sector.getFaction(Factions.LUDDIC_PATH);
        FactionAPI diktat = sector.getFaction(Factions.DIKTAT);
        FactionAPI kol = sector.getFaction(Factions.KOL);
        FactionAPI persean = sector.getFaction(Factions.PERSEAN);
        FactionAPI guard = sector.getFaction(Factions.LIONS_GUARD);
        FactionAPI remnant = sector.getFaction(Factions.REMNANTS);
        FactionAPI derelict = sector.getFaction(Factions.DERELICT);
        FactionAPI udf = sector.getFaction("AZUR_AL_UDF");


        udf.setRelationship(path.getId(), RepLevel.HOSTILE);
        udf.setRelationship(hegemony.getId(), RepLevel.FAVORABLE);
        udf.setRelationship(pirates.getId(), RepLevel.HOSTILE);
        udf.setRelationship(tritachyon.getId(), RepLevel.SUSPICIOUS);
        udf.setRelationship(church.getId(), RepLevel.HOSTILE);
        udf.setRelationship(kol.getId(), RepLevel.HOSTILE);
        udf.setRelationship(persean.getId(), RepLevel.SUSPICIOUS);
        udf.setRelationship(independent.getId(), RepLevel.COOPERATIVE);



    }

    public void generate(SectorAPI sector) {

        initFactionRelationships(sector);
        new azur_alaska().generate(sector);


    }
}
