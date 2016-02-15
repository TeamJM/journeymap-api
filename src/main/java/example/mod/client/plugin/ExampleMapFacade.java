/*
 * JourneyMap API (http://journeymap.info)
 * http://bitbucket.org/TeamJM/journeymap-api
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

package example.mod.client.plugin;

import example.mod.ExampleMod;
import example.mod.client.facade.IExampleMapFacade;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.display.DisplayType;
import journeymap.client.api.display.MarkerOverlay;
import journeymap.client.api.display.ModWaypoint;
import journeymap.client.api.display.PolygonOverlay;
import journeymap.client.api.model.MapImage;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;

import java.util.HashMap;

/**
 * An example mod's implementation of how it uses the JourneyMap API. This class is only directly
 * referenced by the example mod's {@link ExampleJourneymapPlugin}, so it will never be classloaded if
 * JourneyMap isn't loaded at runtime.
 */
class ExampleMapFacade implements IExampleMapFacade
{
    private final IClientAPI jmClientAPI;
    private final ModWaypoint bedWaypoint;
    private final HashMap<ChunkCoordIntPair, PolygonOverlay> slimeChunkOverlays;
    private int lastDimension = Integer.MIN_VALUE;

    ExampleMapFacade(IClientAPI api)
    {
        this.jmClientAPI = api;

        // Init map for pairing coords to slime chunk overlays
        slimeChunkOverlays = new HashMap<ChunkCoordIntPair, PolygonOverlay>();

        // Create the basic bed waypoint now, but it won't be shown until the player sleeps
        // and it can be updated with the correct dimension and world coordinates
        MapImage bedIcon = new MapImage(new ResourceLocation("examplemod:images/bed.png"), 32, 32);
        bedWaypoint = new ModWaypoint(ExampleMod.MODID, "bed_0", "Handy Locations", "Bed", 0, 0, 0, bedIcon, 0xffffff, true, Integer.MIN_VALUE);
    }

    /**
     * Refresh ExampleMod's displayables for the given dimension if needed.
     *
     * @param dimension
     */
    @Override
    public void refreshMap(int dimension)
    {
        if (lastDimension == dimension)
        {
            return;
        }
        else
        {
            lastDimension = dimension;
        }


        try
        {
            // Create a circle of Marker Overlays just to show how it's done.
            if (jmClientAPI.playerAccepts(ExampleMod.MODID, DisplayType.Marker))
            {
                BlockPos center = Minecraft.getMinecraft().thePlayer.getBedLocation();
                int points = 32;
                double radius = 64;
                double slice = 2 * Math.PI / points;
                for (int i = 1; i <= points; i++)
                {
                    // Create a position in a circle around the center position
                    double angle = slice * i;
                    int newX = (int) (center.getX() + radius * Math.cos(angle));
                    int newZ = (int) (center.getZ() + radius * Math.sin(angle));
                    BlockPos p = new BlockPos(newX, 70, newZ);

                    // Tint the cube icon using one Minecraft's map colors, give it a rotation just to prove it works.
                    MapImage cube = new MapImage(new ResourceLocation("examplemod:images/cube.png"), 64, 64)
                            .setColor(MapColor.mapColorArray[i].colorValue)
                            .setRotation((int) slice);

                    MarkerOverlay markerOverlay = new MarkerOverlay("journeymap", "marker" + i, p, cube);
                    markerOverlay.setDimension(0)
                            .setDisplayOrder(100 + i)
                            .setTitle(String.format("x:%s,z:%s", p.getX(), p.getZ()))
                            .setLabel("" + i);

                    jmClientAPI.show(markerOverlay);
                }
            }

            // Refresh pre-existing displayables from this dimension
            if (canShowSlimeChunks())
            {
                for (PolygonOverlay slimeChunkOverlay : slimeChunkOverlays.values())
                {
                    if (slimeChunkOverlay.getDimension() == dimension && !jmClientAPI.exists(slimeChunkOverlay))
                    {
                        jmClientAPI.show(slimeChunkOverlay);
                    }
                }
            }

            if (canShowBedWaypoint())
            {
                if (!bedWaypoint.isPersistent() && bedWaypoint.isInDimension(dimension))
                {
                    jmClientAPI.show(bedWaypoint);
                }
            }
        }
        catch (Throwable t)
        {
            ExampleMod.LOGGER.error(t.getMessage(), t);
        }
    }

    /**
     * Whether or not ExampleMod can provide slime chunk overlays.
     *
     * @return true if polygon overlays accepted
     */
    @Override
    public boolean canShowSlimeChunks()
    {
        return jmClientAPI.playerAccepts(ExampleMod.MODID, DisplayType.Polygon);
    }

    /**
     * The ExampleMod will show an overlay for the chunk to indicate it is a slime chunk.
     *
     * @param chunkCoords
     * @param dimension
     */
    @Override
    public void showSlimeChunk(ChunkCoordIntPair chunkCoords, int dimension)
    {
        try
        {
            if (!slimeChunkOverlays.containsKey(chunkCoords))
            {
                PolygonOverlay overlay = SlimeChunkOverlayFactory.create(chunkCoords, dimension);
                slimeChunkOverlays.put(chunkCoords, overlay);
                jmClientAPI.show(overlay);
            }
        }
        catch (Throwable t)
        {
            ExampleMod.LOGGER.error(t.getMessage(), t);
        }
    }

    /**
     * The ExampleMod will remove the slime chunk overlay for the coords.
     *
     * @param chunkCoords
     * @param dimension
     */
    @Override
    public void removeSlimeChunk(ChunkCoordIntPair chunkCoords, int dimension)
    {
        try
        {
            PolygonOverlay overlay = slimeChunkOverlays.get(chunkCoords);
            if (overlay != null)
            {
                jmClientAPI.remove(overlay);
            }
        }
        catch (Throwable t)
        {
            ExampleMod.LOGGER.error(t.getMessage(), t);
        }
    }

    /**
     * Whether or not ExampleMod can provide a bed waypoint.
     *
     * @return true if waypoints accepted
     */
    @Override
    public boolean canShowBedWaypoint()
    {
        return jmClientAPI.playerAccepts(ExampleMod.MODID, DisplayType.Waypoint);
    }

    /**
     * ExampleMod will create a waypoint for the bed slept in at the provided coordinates.
     *
     * @param bedLocation
     * @param dimension
     */
    @Override
    public void showBedWaypoint(BlockPos bedLocation, int dimension)
    {
        try
        {
            if (bedWaypoint.isInDimension(dimension) || !bedWaypoint.getPoint().equals(bedLocation))
            {
                bedWaypoint.setPoint(bedLocation).setDimensions(dimension).setPersistent(true);

                // Add or update existing waypoint
                jmClientAPI.show(bedWaypoint);
            }
        }
        catch (Throwable t)
        {
            ExampleMod.LOGGER.error(t.getMessage(), t);
        }
    }

}
