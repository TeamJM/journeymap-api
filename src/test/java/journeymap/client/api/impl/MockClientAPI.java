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

package journeymap.client.api.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.LinkedHashMultimap;
import cpw.mods.fml.common.Optional;
import journeymap.client.api.display.DisplayType;
import journeymap.client.api.display.Displayable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

/**
 * Stub implementation of the IClientAPI. Doesn't actually do anything, other than track displayIds.
 */
@Optional.Interface(iface = "journeymap.client.api.IClientAPI", modid = "journeymap")
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
    public boolean isActive()
    {
        return true;
    }

    @Override
    public void show(Displayable displayable)
    {
        showDisplayable(displayable.getModId(), DisplayType.of(displayable.getClass()), displayable.getDisplayId());
    }

    private void showDisplayable(String modId, DisplayType displayType, String displayId)
    {
        modDisplayables.getUnchecked(modId).put(displayType, displayId);
        log(String.format("Showed %s:%s:%s", modId, displayType, displayId));
    }

    @Override
    public void remove(Displayable displayable)
    {
        remove(displayable.getModId(), DisplayType.of(displayable.getClass()), displayable.getDisplayId());
    }

    @Override
    public void remove(String modId, DisplayType displayType, String displayId)
    {
        modDisplayables.getUnchecked(modId).remove(displayType, displayId);
        log(String.format("Removed %s:%s:%s", modId, displayType, displayId));
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
    public boolean exists(String modId, DisplayType displayType, String displayId)
    {
        return modDisplayables.getUnchecked(modId).containsEntry(displayType, displayId);
    }

    @Override
    public boolean isVisible(String modId, DisplayType displayType, String displayId)
    {
        return exists(modId, displayType, displayId);
    }

    @Override
    public List<String> getShownIds(String modId, DisplayType displayType)
    {
        return new ArrayList<String>(modDisplayables.getUnchecked(modId).get(displayType));
    }

    @Override
    public boolean playerAccepts(String modId, DisplayType displayType)
    {
        return true;
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
