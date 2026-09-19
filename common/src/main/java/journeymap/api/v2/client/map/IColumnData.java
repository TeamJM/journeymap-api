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

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

/**
 * One column's surface scan: the painted block, its heights, light, slopes, water and biome, as JourneyMap's
 * own day, night, topographic and biome layers consume them. Read-only.
 *
 * <p>The {@code slope*} values are the neighboring columns' surface heights (the slope descent's height,
 * which only follows water to the seafloor under bathymetry), from which the slope shading is derived.</p>
 */
public interface IColumnData
{
    /**
     * @return the Y of the painted surface block (the seafloor under bathymetric water)
     */
    int surfaceHeight();

    /**
     * @return the world's minimum build height
     */
    int worldMinY();

    /**
     * @return the world's maximum build height (exclusive)
     */
    int worldMaxY();

    /**
     * @return the painted block's base color (RGB, biome tint applied)
     */
    int blockColor();

    /**
     * @return the water depth over the surface block, 0 when dry
     */
    int waterDepth();

    /**
     * @return the seafloor Y when the column has water, else the surface height
     */
    int seafloorHeight();

    /**
     * @return the water color (RGB) when the column has water, else 0
     */
    int waterColor();

    /**
     * @return the block light above the surface block (0..15)
     */
    int lightLevel();

    /**
     * @return the summed light opacity of the transparent blocks above the surface block
     */
    int lightAttenuation();

    /**
     * @return the north neighbor column's surface height
     */
    float slopeNorth();

    /**
     * @return the south neighbor column's surface height
     */
    float slopeSouth();

    /**
     * @return the east neighbor column's surface height
     */
    float slopeEast();

    /**
     * @return the west neighbor column's surface height
     */
    float slopeWest();

    /**
     * @return the north-west neighbor column's surface height
     */
    float slopeNorthWest();

    /**
     * @return the topographic layer's color for this column (RGB), 0 when the topo layer is off
     */
    int topoColor();

    /**
     * @return the biome layer's color for this column (RGB), 0 when unknown
     */
    int biomeColor();

    /**
     * @return the biome id at the surface, or {@code null} when unknown
     */
    @Nullable
    Identifier biomeId();

    /**
     * @return the painted block's state
     */
    BlockState topBlockState();

    /**
     * @return the transparent strata above the surface block, topmost first (empty when none)
     */
    List<? extends IStrataEntry> transparency();

    /**
     * @return whether the painted block casts a slope shade (false for blocks that render flat)
     */
    boolean showSlope();

    /**
     * @return the secondary (two-block) slope factor
     */
    float secondarySlope();

    /**
     * @return the slope factor of the biome layer's height shading, {@code 1.0} when off
     */
    float biomeSlope();

    /**
     * @return whether a neighboring column has a different biome (the biome layer's border line)
     */
    boolean biomeBorder();

    /**
     * The column as the vanilla map item samples it: the topmost block whose map color is not
     * {@code NONE}, descending from the heightmap through plants, snow layers and leaves (glass and air
     * have none). For a fluid column this is the fluid's own block state.
     *
     * @return the sampled block state
     */
    BlockState mapColorState();

    /**
     * @return the Y of {@link #mapColorState()}
     */
    int mapColorHeight();

    /**
     * @return the fluid blocks from {@link #mapColorState()} down when it is a fluid (the map item's water
     *         depth), else 0
     */
    int mapColorFluidDepth();

    /**
     * @return the north neighbor column's {@link #mapColorHeight()} (the map item shades by the difference)
     */
    int mapColorNorthHeight();

    /**
     * @return whether this column has water above the seafloor
     */
    boolean hasWater();

    /**
     * @return whether the column is a hole in the world (no surface block above the minimum height)
     */
    boolean isVoid();
}
