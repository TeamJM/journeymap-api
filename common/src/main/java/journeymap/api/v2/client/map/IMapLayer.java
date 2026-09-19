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
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/**
 * A map layer supplied by a mod: JourneyMap scans each chunk once and hands the scan to every registered
 * layer, which paints the chunk's 16x16 pixels for its own map type.
 *
 * <p>Register through {@link journeymap.api.v2.client.IClientAPI#registerMapLayer(IMapLayer)} during
 * {@link journeymap.api.v2.client.IClientPlugin#initialize} (before any world is joined); layers registered
 * later apply from the next mapping session.</p>
 *
 * <p>{@link #render} runs concurrently on JourneyMap's render worker threads, once per chunk per pass.
 * Implementations must be thread-safe and pure: no shared mutable state, no world or file access; read
 * only the scan and the context handed in.</p>
 */
public interface IMapLayer
{
    /**
     * @return the owning mod's id
     */
    String getModId();

    /**
     * Stable id of this layer within the mod ({@code [a-z0-9_]+}); JourneyMap keys the layer as
     * {@code modId:layerId}.
     *
     * @return the layer id
     */
    String getLayerId();

    /**
     * @return the name shown to players for this layer
     */
    String getDisplayName();

    /**
     * The icon of this layer's map-type button on the fullscreen map and minimap, a texture in the mod's
     * assets (for example {@code mymod:textures/gui/heat_layer.png}). Drawn at the theme's button icon
     * size, so a 24x24 image with a transparent background matches JourneyMap's own icons.
     *
     * @return the icon texture
     */
    Identifier getIcon();

    /**
     * Whether this layer renders per cave slice (vertical chunk) rather than once for the surface. A cave
     * layer's {@link #render} is called once per active slice with that slice's index; a surface layer is
     * called once with slice index {@code -1}.
     *
     * @return whether this is a cave-slice layer
     */
    boolean requiresCaveSlices();

    /**
     * @return the dimension this layer applies to, or {@code null} for every dimension
     */
    @Nullable
    ResourceKey<Level> getDimension();

    /**
     * Paints one chunk of this layer.
     *
     * @param scan       the chunk's scan (surface columns, and cave slices when {@link #requiresCaveSlices()})
     * @param context    the settings of this mapping pass
     * @param sliceIndex the cave slice index for a cave layer, {@code -1} for a surface layer
     * @param pixels     256 ARGB pixels to fill, row-major ({@code z * 16 + x}); 0 leaves a pixel unpainted
     */
    void render(IChunkScan scan, IScanContext context, int sliceIndex, int[] pixels);
}
