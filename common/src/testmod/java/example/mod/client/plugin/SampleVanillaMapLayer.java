/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.client.plugin;

import example.mod.ExampleMod;
import journeymap.api.v2.client.map.IChunkScan;
import journeymap.api.v2.client.map.IColumnData;
import journeymap.api.v2.client.map.IMapLayer;
import journeymap.api.v2.client.map.IScanContext;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

/**
 * Sample map layer: a map type painted like the vanilla map item at its base scale. Registered through
 * {@link journeymap.api.v2.client.IClientAPI#registerMapLayer(IMapLayer)} during plugin initialization,
 * it gets its own button on the fullscreen map (with {@link #getIcon()}), an entry in the map-type hotkey
 * cycle and the minimap's locked map type list, and its own region images on disk.
 *
 * <p>Each column takes the {@link MapColor} of the block the map item samples (the topmost block with a
 * map color, plants and snow layers included), shaded by the vanilla brightness rules: land compares the
 * column's height with the column to its north, with the vanilla checkerboard dither, so a rise of one
 * block lightens every other pixel and a drop darkens it; a fluid (water, lava) takes its own color shaded
 * by depth the same way. Blocks without a map color leave the pixel unpainted.</p>
 *
 * <p>{@link #render} runs on JourneyMap's render worker threads, concurrently and once per chunk per pass:
 * it must stay pure and thread-safe, reading only the scan and the context handed in.</p>
 */
public final class SampleVanillaMapLayer implements IMapLayer
{
    private static final Identifier ICON = Identifier.withDefaultNamespace("textures/item/filled_map.png");

    @Override
    public String getModId()
    {
        return ExampleMod.MODID;
    }

    @Override
    public String getLayerId()
    {
        return "vanilla";
    }

    @Override
    public String getDisplayName()
    {
        return "Vanilla (Example)";
    }

    @Override
    public Identifier getIcon()
    {
        return ICON;
    }

    @Override
    public boolean requiresCaveSlices()
    {
        return false;
    }

    @Override
    public ResourceKey<Level> getDimension()
    {
        // Every dimension.
        return null;
    }

    @Override
    public void render(IChunkScan scan, IScanContext context, int sliceIndex, int[] pixels)
    {
        for (int z = 0; z < 16; z++)
        {
            for (int x = 0; x < 16; x++)
            {
                IColumnData column = scan.surfaceColumn(x, z);
                pixels[z * 16 + x] = column == null ? 0 : columnColor(column, x, z);
            }
        }
    }

    /**
     * One column's vanilla map pixel.
     *
     * @param column the column's surface data
     * @param x      chunk-local x (the dither uses the world parity, which chunk-local parity equals)
     * @param z      chunk-local z
     * @return the pixel (ARGB), 0 when the sampled block has no map color
     */
    private static int columnColor(IColumnData column, int x, int z)
    {
        BlockState sampled = column.mapColorState();
        MapColor color = sampled == null ? MapColor.NONE : sampled.getMapColor(EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
        if (color == MapColor.NONE)
        {
            return 0;
        }
        int dither = (x + z) & 1;
        MapColor.Brightness brightness;
        if (column.mapColorFluidDepth() > 0)
        {
            // MapItem: a fluid in its own color, lighter when shallow and darker when deep.
            double diff = column.mapColorFluidDepth() * 0.1 + dither * 0.2;
            brightness = diff < 0.5 ? MapColor.Brightness.HIGH
                    : diff > 0.9 ? MapColor.Brightness.LOW : MapColor.Brightness.NORMAL;
        }
        else
        {
            // MapItem at scale 0: (height - northHeight) * 4 / (0 + 4) + ((x + z & 1) - 0.5) * 0.4.
            double diff = (column.mapColorHeight() - column.mapColorNorthHeight()) + (dither - 0.5) * 0.4;
            brightness = diff > 0.6 ? MapColor.Brightness.HIGH
                    : diff < -0.6 ? MapColor.Brightness.LOW : MapColor.Brightness.NORMAL;
        }
        return color.calculateARGBColor(brightness);
    }
}
