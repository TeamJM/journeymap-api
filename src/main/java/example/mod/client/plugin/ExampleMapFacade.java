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
import journeymap.client.api.display.ModWaypoint;
import journeymap.client.api.display.PolygonOverlay;
import journeymap.client.api.model.MapImage;
import journeymap.client.api.model.MapPolygon;
import journeymap.client.api.model.ShapeProperties;
import journeymap.client.api.util.PolygonHelper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;

import java.util.HashMap;

/**
 * Implementation of IExampleMapFacade that uses the JourneyMap API. This class should only be referenced by the
 * {@link ExampleJourneymapPlugin} so that the classloader doesn't try to find API classes if JourneyMap isn't loaded.
 */
class ExampleMapFacade implements IExampleMapFacade
{
    private final IClientAPI jmClientAPI;
    private final ModWaypoint bedWaypoint;
    private final HashMap<ChunkCoordIntPair, PolygonOverlay> slimeChunkOverlays;

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

    @Override
    public void refreshMap(int dimension)
    {
        try
        {
            if (canShowSlimeChunks())
            {
                for (PolygonOverlay slimeChunkOverlay : slimeChunkOverlays.values())
                {
                    if (slimeChunkOverlay.getDimension() == dimension)
                    {
                        jmClientAPI.show(slimeChunkOverlay);
                    }
                }
            }

            if (canShowBedWaypoint())
            {
                if (!bedWaypoint.isPersistent() && !bedWaypoint.isInDimension(dimension))
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

    @Override
    public boolean canShowSlimeChunks()
    {
        return jmClientAPI.playerAccepts(ExampleMod.MODID, DisplayType.Polygon);
    }

    @Override
    public void showSlimeChunk(ChunkCoordIntPair chunkCoords, int dimension)
    {
        try
        {
            String displayId = "slime_" + chunkCoords.toString();

            ShapeProperties shapeProps = new ShapeProperties()
                    .setStrokeWidth(2)
                    .setStrokeColor(0x00ff00).setStrokeOpacity(.7f)
                    .setFillColor(0x00ff00).setFillOpacity(.4f);

            MapPolygon polygon = PolygonHelper.createChunkPolygon(chunkCoords.chunkXPos, 0, chunkCoords.chunkZPos);

            PolygonOverlay chunkOverlay = new PolygonOverlay(ExampleMod.MODID, displayId, dimension, shapeProps, polygon);
            chunkOverlay.setOverlayGroupName("Slime Chunks").setTitle("Slime Chunk");

            slimeChunkOverlays.put(chunkCoords, chunkOverlay);
            jmClientAPI.show(chunkOverlay);
        }
        catch (Throwable t)
        {
            ExampleMod.LOGGER.error(t.getMessage(), t);
        }
    }

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

    @Override
    public boolean canShowBedWaypoint()
    {
        return jmClientAPI.playerAccepts(ExampleMod.MODID, DisplayType.Waypoint);
    }

    @Override
    public void showBedWaypoint(BlockPos bedLocation, int dimension)
    {
        try
        {
            if (bedWaypoint.isInDimension(dimension) || !bedWaypoint.getPoint().equals(bedLocation))
            {
                bedWaypoint.setPoint(bedLocation).setDimensions(dimension);

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
