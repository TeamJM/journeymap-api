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

/**
 * One column of one cave slice (vertical chunk): what JourneyMap's cave layer paints from. Read-only.
 */
public interface ICaveSliceData
{
    /**
     * @return the slice's lowest Y
     */
    int sliceMinY();

    /**
     * @return the slice's highest Y
     */
    int sliceMaxY();

    /**
     * @return the cave floor found for the slice (the height the cave slope shading uses)
     */
    int ceilingHeight();

    /**
     * @return the lowest lit cave floor block Y, or the slice bottom when none
     */
    int floorHeight();

    /**
     * @return the blended color of the lit cave strata (ARGB), 0 when the column has no lit cave content
     */
    int caveColor();

    /**
     * @return whether the column has lit cave content in this slice
     */
    boolean hasCaveContent();

    /**
     * @return the dimmed surface color painted where the slice has no cave content (RGB), 0 when unavailable
     */
    int surfaceColor();

    /**
     * @return the column's surface height
     */
    int surfaceHeight();

    /**
     * @return whether the surface shows through for this column (open to the sky within the slice)
     */
    boolean surfaceVisible();

    /**
     * @return the strongest light level of the pushed cave strata (0..15)
     */
    int lightLevel();

    /**
     * @return the cave slope toward the north neighbor
     */
    float slopeNorth();

    /**
     * @return the cave slope toward the south neighbor
     */
    float slopeSouth();

    /**
     * @return the cave slope toward the east neighbor
     */
    float slopeEast();

    /**
     * @return the cave slope toward the west neighbor
     */
    float slopeWest();

    /**
     * @return the blocks between the cave floor and the surface
     */
    int distanceToSurface();
}
