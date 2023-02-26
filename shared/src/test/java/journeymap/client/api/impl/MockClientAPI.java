/*
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

package journeymap.client.api.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.LinkedHashMultimap;
import com.mojang.blaze3d.platform.NativeImage;
import journeymap.client.api.display.Context;
import journeymap.client.api.display.DisplayType;
import journeymap.client.api.display.Displayable;
import journeymap.client.api.event.ClientEvent;
import journeymap.client.api.util.UIState;
import journeymap.common.api.waypoint.Waypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Stub implementation of the IClientAPI. Doesn't actually do anything, other than track displayIds.
 */
//@Optional.Interface(iface = "journeymap.client.api.IClientAPI", modid = "journeymap")
@ParametersAreNonnullByDefault
enum MockClientAPI implements journeymap.client.api.IClientAPI
{
    INSTANCE;

    private final static Logger LOGGER = LogManager.getLogger("journeymap-stub");

    // This stub only needs to store displayIds by DisplayType by modId, so a cache of multimaps is convenient
    // and avoids all the boilerplate check-and-create-if-missing mess.
    private final LoadingCache<String, LinkedHashMultimap<DisplayType, String>> modDisplayables =
            CacheBuilder.newBuilder().build(
                    new CacheLoader<String, LinkedHashMultimap<DisplayType, String>>()
                    {
                        public LinkedHashMultimap<DisplayType, String> load(String key)
                        {
                            LinkedHashMultimap<DisplayType, String> multimap = LinkedHashMultimap.create();
                            return multimap;
                        }
                    });


    @Override
    public UIState getUIState(Context.UI ui)
    {
        if (ui == Context.UI.Any)
        {
            return null;
        }

        return new UIState(ui, true, Level.OVERWORLD, 1,
                Context.MapType.Day,
                new BlockPos(128, 0, 128), null,
                new AABB(new net.minecraft.core.BlockPos(0, 0, 0), new net.minecraft.core.BlockPos(256, 256, 256)),
                new Rectangle2D.Double(0, 0, 1240, 960));
    }

    @Override
    public void subscribe(String modId, EnumSet<ClientEvent.Type> eventTypes)
    {
        // Not implemented
    }

    @Override
    public void show(Displayable displayable)
    {
        showDisplayable(displayable.getModId(), displayable.getDisplayType(), displayable.getId());
    }

    private void showDisplayable(String modId, DisplayType displayType, String displayId)
    {
        modDisplayables.getUnchecked(modId).put(displayType, displayId);
        log(String.format("Showed %s:%s:%s", modId, displayType, displayId));
    }

    @Override
    public void remove(Displayable displayable)
    {
        modDisplayables.getUnchecked(displayable.getModId()).remove(displayable.getDisplayType(), displayable.getId());
    }

    @Override
    public void removeAll(String modId, DisplayType displayType)
    {
        modDisplayables.getUnchecked(modId).removeAll(displayType);
        log(String.format("Removed all %s:%s", modId, displayType));
    }

    @Override
    public void removeAll(String modId)
    {
        modDisplayables.invalidateAll();
        log(String.format("Removed all %s", modId));
    }

    @Override
    public boolean exists(Displayable displayable)
    {
        return modDisplayables.getUnchecked(displayable.getModId()).containsEntry(displayable.getDisplayType(), displayable.getId());
    }

    @Override
    public boolean playerAccepts(String modId, DisplayType displayType)
    {
        return true;
    }

    @Override
    public void requestMapTile(String modId, ResourceKey<Level> dimension, Context.MapType mapType, net.minecraft.world.level.ChunkPos startChunk, ChunkPos endChunk,
                               @Nullable Integer chunkY, int zoom, boolean showGrid, final Consumer<NativeImage> callback)
    {
        // Determine chunks for coordinates at zoom level
        final int scale = (int) Math.pow(2, zoom);
        final int chunkSize = 32 / scale;
        final int pixels = chunkSize * 16;
        final int width = Math.min(512, (endChunk.x - startChunk.x) * pixels);
        final int height = Math.min(512, (endChunk.z - startChunk.z) * pixels);

        Minecraft.getInstance().submit(() -> callback.accept(createFakeImage(width, height)));
    }

    @Override
    public void toggleDisplay(@Nullable ResourceKey<Level> dimension, Context.MapType mapType, Context.UI mapUI, boolean enable)
    {
        log(String.format("Toggled display in %s:%s:%s:%s", dimension, mapType, mapUI, enable));
    }

    @Override
    public void toggleWaypoints(@Nullable ResourceKey<Level> dimension, Context.MapType mapType, Context.UI mapUI, boolean enable)
    {
        log(String.format("Toggled waypoints in %s:%s:%s:%s", dimension, mapType, mapUI, enable));
    }

    @Override
    public boolean isDisplayEnabled(@Nullable ResourceKey<Level> dimension, Context.MapType mapType, Context.UI mapUI)
    {
        return false;
    }

    @Override
    public boolean isWaypointsEnabled(@Nullable ResourceKey<Level> dimension, Context.MapType mapType, Context.UI mapUI)
    {
        return false;
    }

    @Override
    public File getDataPath(String modId)
    {
        return new File("");
    }

    @Override
    public List<Waypoint> getAllWaypoints()
    {
        return null;
    }

    @Override
    public List<Waypoint> getAllWaypoints(ResourceKey<Level> dim)
    {
        return null;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public Waypoint getWaypoint(String modId, String displayId)
    {
        return null;
    }

    @Override
    public List<Waypoint> getWaypoints(String modId)
    {
        return null;
    }

    @Override
    public void removeWaypoint(String modId, Waypoint waypoint)
    {

    }

    @Override
    public void addWaypoint(String modId, Waypoint waypoint)
    {

    }

    @Override
    public void removeAllWaypoints(String modId)
    {

    }

    @Override
    public void setWorldId(String identifier)
    {

    }

    @Override
    public String getWorldId()
    {
        return null;
    }

    /**
     * Create a randomly-colored image
     *
     * @return
     */
    private NativeImage createFakeImage(int width, int height)
    {
        NativeImage image = new NativeImage(width, height, false);
        int color = new Random().nextInt(0xffffff);
        image.fillRect(0, 0, 512, 512, color);
        return image;
    }

    /**
     * Log what's happening in the stub, since there's nothing real to see.
     *
     * @param message
     */
    private void log(String message)
    {
        LOGGER.info(String.format("[%s] %s", getClass().getSimpleName(), message));
    }
}
