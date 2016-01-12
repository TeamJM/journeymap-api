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

package journeymap.client.api;

import journeymap.client.api.display.DisplayType;
import journeymap.client.api.display.Displayable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * Definition for the JourneyMap Client API.
 */
@ParametersAreNonnullByDefault
public interface IClientAPI
{
    String API_OWNER = "journeymap";
    String API_VERSION = "@API_VERSION@";

    /**
     * Whether JourneyMap is active. It's good to check this before making other calls to the API.
     * There are small delays between a player entering the world (or changing dimensions) and when JourneyMap is
     * activated. It can also be the case that a player has disabled all functionality in JourneyMap for some reason.
     * In either case, this method will return false and you should check again later.
     *
     * @return true if active
     */
    boolean isActive();

    /**
     * Add (or update) a displayable object to the player's maps. If you modify a Displayable after it
     * has been added, call this method again to ensure the maps reflect your changes.
     * <p/>
     * If an object of the same Displayable.Type
     * from your mod with the same displayId has already been added, it will be replaced.
     * <p/>
     * Has no effect on display types not accepted by the player.
     *
     * @param displayable The object to display.
     * @see #playerAccepts(String, DisplayType)
     */
    void show(Displayable displayable);

    /**
     * Remove a displayable from the player's maps.
     * Has no effect on display types not accepted by the player.
     *
     * @param displayable The object to display.
     * @see #playerAccepts(String, DisplayType)
     */
    void remove(Displayable displayable);

    /**
     * Remove a displayable from the player's maps (if it exists).
     * Has no effect on display types not accepted by the player.
     *
     * @param modId       Mod id
     * @param displayType Display type to check
     * @param displayId   The display id
     * @see #playerAccepts(String, DisplayType)
     */
    void remove(String modId, DisplayType displayType, String displayId);

    /**
     * Remove all displayables by DisplayType from the player's maps.
     * Has no effect on display types not accepted by the player.
     *
     * @param modId       Mod id
     * @param displayType Display type
     * @see #playerAccepts(String, DisplayType)
     */
    void removeAll(String modId, DisplayType displayType);

    /**
     * Remove all displayables.
     * Has no effect on display types not accepted by the player.
     *
     * @param modId Mod id
     * @see #playerAccepts(String, DisplayType)
     */
    void removeAll(String modId);

    /**
     * Check whether a displayable exists in the Client API.  A return value of true means the Client API has the
     * indicated displayable, but not necessarily that the player has made it visible.
     *
     * Always returns false if the display type is not accepted by the player.
     *
     * @param modId       Mod id
     * @param displayType Display type to check
     * @param displayId   The display id
     * @see #playerAccepts(String, DisplayType)
     */
    boolean exists(String modId, DisplayType displayType, String displayId);

    /**
     * Check whether a displayable exists in the Client API and is rendered in at least one map display. A
     * return value of true doesn't necessarily mean the disable is actively on-screen, however.
     *
     * Always returns false if the display type is not accepted by the player.
     *
     * @param modId       Mod id
     * @param displayType Display type to check
     * @param displayId   The display id
     * @see #playerAccepts(String, DisplayType)
     */
    boolean isVisible(String modId, DisplayType displayType, String displayId);

    /**
     * Gets a list of player displayable ids associated with your mod.
     * Always returns an empty list if the display type is not accepted by the player.
     *
     * @param modId Mod id
     * @return A list, possibly empty.
     * @see #playerAccepts(String, DisplayType)
     */
    List<String> getShownIds(String modId, DisplayType displayType);

    /**
     * Check whether player will accept a type of Displayable from your mod. (Like Displayables or Overlays).
     *
     * @param modId       Mod id
     * @param displayType Display type to check
     * @return true if player accepts addition/removal of displayables
     * @see DisplayType
     */
    boolean playerAccepts(String modId, DisplayType displayType);
}
