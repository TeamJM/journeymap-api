/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.server.plugin;

import journeymap.api.v2.common.Context;
import journeymap.api.v2.client.util.UIState;
import journeymap.api.v2.server.overlay.OverlayPoints;
import journeymap.api.v2.server.overlay.OverlayPolygon;
import journeymap.api.v2.server.overlay.OverlayShapeProps;
import journeymap.api.v2.server.overlay.ServerPolygon;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * Sample factory that builds a {@link ServerPolygon} for the server overlay API.
 * Mirrors {@code SamplePolygonOverlayFactory} on the client side, but the
 * output is data: it lives on the wire as a {@code ServerPolygon}, gets
 * shipped to a single player via {@link journeymap.api.v2.server.overlay.IServerOverlayAPI#show},
 * and the client receiver turns it back into a {@code PolygonOverlay}.
 *
 * <p>The factory itself owns no state. The consumer addon decides the
 * {@code overlayId} (stable, addon-chosen), the recipient, and when to push.
 * JourneyMap is the transport, not the database.</p>
 */
@SuppressWarnings("deprecation")
public final class SampleServerPolygonOverlayFactory
{
    private SampleServerPolygonOverlayFactory()
    {
    }

    /**
     * Builds a square {@link ServerPolygon} centred on the given block. Useful
     * for demonstrating the server overlay API with a recognisable shape.
     *
     * @param overlayId addon-stable id for this overlay; re-{@code show()} with the same id replaces the prior version
     * @param dimension the dimension the polygon belongs to
     * @param centre    the centre of the square
     * @param halfSize  half the edge length, in blocks
     * @return a {@code ServerPolygon} ready to push via {@code IServerOverlayAPI#show}
     */
    public static ServerPolygon createSampleSquare(String overlayId, int dimension,
                                                   BlockPos centre, int halfSize)
    {
        int y = centre.getY();
        OverlayPoints outer = new OverlayPoints(Arrays.asList(
                new BlockPos(centre.getX() - halfSize, y, centre.getZ() - halfSize).toLong(),
                new BlockPos(centre.getX() + halfSize, y, centre.getZ() - halfSize).toLong(),
                new BlockPos(centre.getX() + halfSize, y, centre.getZ() + halfSize).toLong(),
                new BlockPos(centre.getX() - halfSize, y, centre.getZ() + halfSize).toLong()));

        OverlayPolygon polygon = new OverlayPolygon(outer, null);

        OverlayShapeProps props = new OverlayShapeProps(
                0x3399FF,                                  // fillColor
                0.25f,                                     // fillOpacity
                0x3399FF,                                  // strokeColor
                1.5f,                                      // strokeWidth
                0.9f,                                      // strokeOpacity
                1000,                                      // displayOrder
                UIState.FULLSCREEN_ZOOM_MIN,               // minZoom - visible at the most zoomed-out level
                UIState.ZOOM_IN_MAX,                       // maxZoom - still visible fully zoomed in
                EnumSet.allOf(Context.UI.class),           // activeUIs
                EnumSet.allOf(Context.MapType.class),      // activeMapTypes
                "Sample Claim",                            // label
                "Pushed from the example server plugin"); // title

        return new ServerPolygon(overlayId, dimension, Arrays.asList(polygon), props);
    }
}
