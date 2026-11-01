package Arisza.antix.world.systems;


import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.terrain.MagneticFieldTerrainPlugin;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;



public class azur_alaska {
    public void generate(SectorAPI sector) {


        float planet1Dist= 4000f; //this is the distance the planet is from the star

                StarSystemAPI system = sector.createStarSystem("Alaska");
            system.getLocation().set(-29000, -25500); //position of the system on the map

        system.setBackgroundTextureFilename("graphics/backgrounds/background2.jpg");

        // create the star and generate the hyperspace anchor for this system
            PlanetAPI alaska_star = system.initStar("alaska", // unique id for this star
                 "azur_moon_shadow", // id in planets.json
                    200f,		// radius (in pixels at default zoom)
                        1050);
        system.setLightColor(new Color(170,255,255,255));



        SectorEntityToken relay = system.addCustomEntity("udf_relay", // unique id
                "United Council Relay", // name - if null, defaultName from custom_entities.json will be used
                "comm_relay", // type of object, defined in custom_entities.json
                "AZUR_AL_UDF"); // faction
        relay.setCircularOrbitPointingDown( system.getEntityById("alaska"), 160, 2750, 100);

        PlanetAPI anchorage = system.addPlanet("anchorage",
                alaska_star, //what it's orbiting
                "New Anchorage", //name
                "tundra", //the planet type (look in planets.json for more)
                350, //-
                130f, //radius
                17000, //distance from star
                700f); //how many days to orbit
        anchorage.setFaction("AZUR_AL_UDF");
        anchorage.setCustomDescriptionId("planet_anchorage"); //for custom descriptions

        PlanetAPI Rainbowend = system.addPlanet("rainbow_end",
                alaska_star, //what it's orbiting
                "Rainbow's End", //name
                "terran-eccentric", //the planet type (look in planets.json for more)
                90, //-
                130f, //radius
                13000, //distance from star
                700f); //how many days to orbit
        Rainbowend.setFaction("AZUR_AL_UDF");
        Rainbowend.setCustomDescriptionId("planet_rainbow"); //for custom descriptions


        PlanetAPI amahara = system.addPlanet("amahara",
                alaska_star, //what it's orbiting
                "Amahara", //name
                "terran", //the planet type (look in planets.json for more)
                260, //-
                220f, //radius
                13000, //distance from star
                400f); //how many days to orbit
        amahara.setFaction("AZUR_AL_UDF");
        amahara.setCustomDescriptionId("planet_amahara"); //for custom descriptions

        PlanetAPI antiochus = system.addPlanet("antioch",
                alaska_star, //what it's orbiting
                "Antioch", //name
                "gas_giant", //the planet type (look in planets.json for more)
                170, //-
                720f, //radius
                6500, //distance from star
                500f); //how many days to orbit
        antiochus.setFaction("AZUR_AL_UDF");
        antiochus.setCustomDescriptionId("planet_azuren"); //for custom descriptions

        PlanetAPI delta = system.addPlanet("site_delta",
                antiochus, //what it's orbiting
                "Test Site Delta", //name
                "rocky_ice", //the planet type (look in planets.json for more)
                10, //-
                150f, //radius
                1300, //distance from star
                100f); //how many days to orbit
        delta.setFaction("AZUR_AL_UDF");
        delta.setCustomDescriptionId("planet_delta"); //for custom descriptions

        SectorEntityToken field = system.addTerrain(Terrain.MAGNETIC_FIELD,
                new MagneticFieldTerrainPlugin.MagneticFieldParams(150f, // terrain effect band width
                        100, // terrain effect middle radius
                        delta, // entity that it's around
                        150f, // visual band start
                        650f, // visual band end
                        new Color(30, 114, 253, 255), // base color
                        1f, // probability to spawn aurora sequence, checked once/day when no aurora in progress
                        new Color(108, 248, 215, 255),
                        new Color(150, 30, 120, 208),
                        new Color(60, 255, 232, 218),
                        new Color(250, 70, 150, 240),
                        new Color(200, 80, 130, 255),
                        new Color(30, 114, 253, 255),
                        new Color(21, 178, 242, 255)
                ));
        field.setCircularOrbit(delta, 0, 0, 75);



        system.addRingBand(alaska_star, "misc", "rings_ice0", 256f, 4, new Color(255, 162, 86,255), 230f, 3350, 300, Terrain.RING, "Stellar Energy Band");
        system.addRingBand(alaska_star, "misc", "rings_ice0", 256f, 3, new Color(255, 162, 86,255), 230f, 3250, 100, Terrain.RING, "Stellar Energy Band2");
        system.addRingBand(alaska_star, "misc", "rings_ice0", 256f, 2, new Color(255, 162, 86,255), 330f, 2200, 100, Terrain.RING, "Stellar Energy Band2");
        system.addRingBand(alaska_star, "misc", "rings_ice0", 256f, 3, new Color(255, 162, 86,255), 230f, 3250, 100, Terrain.RING, "Stellar Energy Band2");
        system.addRingBand(alaska_star, "misc", "rings_ice0", 256f, 2, new Color(255, 162, 86,255), 230f, 3290, 100, Terrain.RING, "Stellar Energy Band2");
        system.addRingBand(alaska_star, "misc", "rings_ice0", 256f, 1, new Color(255, 162, 86,255), 230f, 3590, 100, Terrain.RING, "Stellar Energy Band2");
        system.addRingBand(alaska_star, "misc", "rings_dust0", 256f, 3, new Color(170, 255, 255,255), 230f, 13350, 100, Terrain.RING, "Stellar Energy Band2");
        system.addRingBand(alaska_star, "misc", "rings_dust0", 256f, 2, new Color(208,249,253,255), 230f, 16250, 100, Terrain.RING, "Stellar Energy Band2");
        system.addRingBand(alaska_star, "misc", "rings_dust0", 256f, 1, new Color(206,250,252,255), 230f, 9900, 100, Terrain.RING, "Stellar Energy Band2");


        system.autogenerateHyperspaceJumpPoints(true, true, true); //generates jump points

        HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin(); //these lines clear the hyperspace clouds around the system
        NebulaEditor editor = new NebulaEditor(plugin);
        StarSystemGenerator.addSystemwideNebula(system, StarAge.AVERAGE);
        float minRadius = plugin.getTileSize() * 2f;

        float radius = system.getMaxRadiusInHyperspace();
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f);
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);


    }
}

