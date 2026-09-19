/**
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * The following limited rights are granted to you:
 *
 * You MAY:
 *  + Write your own code that uses the API source code in journeymap.* packages as a dependency.
 *  + Write and distribute your own code that uses, modifies, or extends the example source code in example.* packages
 *  + Fork and modify any source code for the purpose of submitting Pull Requests to the TeamJM/journeymap-api repository.
 *    Submitting new or modified code to the repository means that you are granting Techbrew all rights to the submitted code.
 *
 * You MAY NOT:
 *  - Distribute source code or classes (whether modified or not) from journeymap.* packages.
 *  - Submit any code to the TeamJM/journeymap-api repository with a different license than this one.
 *  - Use code or artifacts from the repository in any way not explicitly granted by this license.
 *
 */

package journeymap.api.v2.client.map;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * The settings of one mapping pass, resolved once from the player's cartography options and the server's
 * feature policy, as JourneyMap's own layers read them. Read-only; a layer must not cache it across passes.
 */
public interface IScanContext
{
    /**
     * @return the dimension being mapped
     */
    ResourceKey<Level> dimension();

    /**
     * @return how water is treated this pass
     */
    WaterType waterMode();

    /**
     * @return whether transparent blocks are blended into the surface color
     */
    boolean mapTransparency();

    /**
     * @return whether cave layers use block light (else caves are lit uniformly)
     */
    boolean mapCaveLighting();

    /**
     * @return whether cave layers show the dimmed surface where a slice has no cave content
     */
    boolean mapSurfaceAboveCaves();

    /**
     * @return whether cave layers look through glass
     */
    boolean caveIgnoreGlass();

    /**
     * @return whether unlit cave pixels are left transparent instead of black
     */
    boolean caveBlackAsClear();

    /**
     * @return whether the Nether surface layer applies lighting
     */
    boolean netherSurfaceLighting();

    /**
     * @return whether slope shading is applied
     */
    boolean mapShadows();

    /**
     * @return whether plants cast slope shading
     */
    boolean mapPlantShadows();

    /**
     * @return whether plants are painted
     */
    boolean mapPlants();

    /**
     * @return whether crops are painted
     */
    boolean mapCrops();

    /**
     * @return whether grass colors are blended across biomes
     */
    boolean mapBlendGrass();

    /**
     * @return whether foliage colors are blended across biomes
     */
    boolean mapBlendFoliage();

    /**
     * @return whether water colors are blended across biomes
     */
    boolean mapBlendWater();

    /**
     * @return whether the surface layers apply antialiasing
     */
    boolean mapAntialiasing();

    /**
     * @return whether snow layers are ignored when finding the surface
     */
    boolean ignoreSnow();

    /**
     * @return whether the world's heightmaps are ignored (the scan descends from the build height)
     */
    boolean ignoreHeightmaps();

    /**
     * @return whether water takes its biome color
     */
    boolean mapWaterBiomeColors();

    /**
     * @return the cave-mode threshold setting
     */
    int caveModeThreshold();

    /**
     * @return the height of one cave slice in blocks
     */
    int sliceHeight();

    /**
     * @return the biome color blend radius in blocks (0 when blending is off)
     */
    int biomeBlendRadius();

    /**
     * @return the darkest slope shade factor
     */
    float shadingSlopeMin();

    /**
     * @return the brightest slope shade factor
     */
    float shadingSlopeMax();

    /**
     * @return the multiplier applied to a primary downhill slope
     */
    float shadingPrimaryDownslopeMultiplier();

    /**
     * @return the multiplier applied to a primary uphill slope
     */
    float shadingPrimaryUpslopeMultiplier();

    /**
     * @return the multiplier applied to a secondary downhill slope
     */
    float shadingSecondaryDownslopeMultiplier();

    /**
     * @return the multiplier applied to a secondary uphill slope
     */
    float shadingSecondaryUpslopeMultiplier();

    /**
     * @return the moonlight level of the night layer (0..15)
     */
    float tweakMoonlightLevel();

    /**
     * @return the daylight brightening factor
     */
    float tweakBrightenDaylightDiff();

    /**
     * @return the brightening applied to light-source blocks
     */
    float tweakBrightenLightsourceBlock();

    /**
     * @return the water color blend factor
     */
    float tweakWaterColorBlend();

    /**
     * @return the minimum darkening of night water
     */
    float tweakMinimumDarkenNightWater();

    /**
     * @return whether the biome layer shades by height
     */
    boolean biomeHeightShading();

    /**
     * @return whether the biome layer's height shading skips plants
     */
    boolean biomeShadeSkipPlants();

    /**
     * @return whether the biome layer draws biome borders
     */
    boolean biomeBorders();

    /**
     * @return whether the biome layer paints the seafloor through water
     */
    boolean biomeWaterBathymetry();

    /**
     * @return the alpha scale of ice painted over water
     */
    float iceOverWaterAlphaScale();

    /**
     * @return the darkening of ice painted over water
     */
    float iceOverWaterDarken();

    /**
     * @return the surface ambient light color as {@code [r, g, b]} in 0..1; do not modify
     */
    float[] surfaceAmbientColor();

    /**
     * @return the cave ambient light color as {@code [r, g, b]} in 0..1; do not modify
     */
    float[] caveAmbientColor();

    /**
     * @return whether the dimension has no sky light (the surface layers use the no-sky formula)
     */
    boolean surfaceHasNoSky();

    /**
     * @return the dimension's ambient light level for the surface layers (0..1)
     */
    float surfaceWorldAmbientLight();

    /**
     * @return whether the surface layers treat every column as fully lit
     */
    boolean forceFullSurfaceLight();
}
