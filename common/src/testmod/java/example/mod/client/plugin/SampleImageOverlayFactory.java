/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.client.plugin;

import com.mojang.blaze3d.platform.NativeImage;
import example.mod.ExampleMod;
import journeymap.api.v2.client.IClientAPI;
import journeymap.api.v2.client.display.IOverlayListener;
import journeymap.api.v2.client.display.ImageOverlay;
import journeymap.api.v2.client.fullscreen.ModPopupMenu;
import journeymap.api.v2.client.model.MapImage;
import journeymap.api.v2.client.util.UIState;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Sample factory that generates random ImageOverlays.
 */
public final class SampleImageOverlayFactory
{
    private SampleImageOverlayFactory()
    {
    }

    public static List<ImageOverlay> create(IClientAPI jmAPI, BlockPos center, int quantity, int maxDistance, int maxSize)
    {
        List<ImageOverlay> list = new ArrayList<>();
        try
        {
            BlockPos start = center.offset(-maxDistance / 2, 0, -maxDistance / 2);

            Random random = new Random();
            for (int i = 0; i < quantity; i++)
            {
                BlockPos pos = start.offset(random.nextInt(maxDistance), 0, random.nextInt(maxDistance));
                int width = Math.max(32, random.nextInt(maxSize));
                int height = Math.max(32, random.nextInt(maxSize));
                ImageOverlay overlay = createOverlay(jmAPI, pos, width, height);
                jmAPI.show(overlay);
                list.add(overlay);
            }
        }
        catch (Throwable t)
        {
            ExampleMod.LOGGER.error(t.getMessage(), t);
        }

        return list;
    }

    static ImageOverlay createOverlay(IClientAPI jmAPI, BlockPos upperLeft, int blocksWide, int blocksTall)
    {
        BlockPos lowerRight = upperLeft.offset(blocksWide, 0, blocksTall);

        MapImage image = new MapImage(createImage(blocksWide, blocksTall));
        image.centerAnchors();

        String displayId = String.format("image%s,%s,%s,%s", upperLeft.getX(), upperLeft.getZ(), blocksWide, blocksTall);
        ImageOverlay imageOverlay = new ImageOverlay(ExampleMod.MODID, upperLeft, lowerRight, image);
        imageOverlay.getImage().setOpacity(.8f);
        imageOverlay.setDimension(Minecraft.getInstance().player.level().dimension());
        imageOverlay.setLabel("Image Overlay")
                .setTitle(displayId)
                .setOverlayListener(new ImageListener(jmAPI, imageOverlay));

        return imageOverlay;
    }

    static NativeImage createImage(int width, int height)
    {
        NativeImage image = new NativeImage(NativeImage.Format.LUMINANCE, width, height, false);

        //TODO: fix
////        Graphics2D g = bufferedImage.createGraphics();
//
//        // Garish background
//        g.setPaint(new GradientPaint(0, 0, new Color(255, 255, 255, 100), width, height, new Color(0, 0, 200, 200)));
//        g.fillRect(0, 0, width, height);
//
//        // Draw some text, for grins
//        Font font = new Font(Font.MONOSPACED, Font.BOLD, 8);
//        String text = String.format("%sx%s", width, height);
//        Rectangle2D fontBounds = font.getStringBounds(text, g.getFontRenderContext());
//        final float x = (float) (image.getWidth() - fontBounds.getWidth()) / 2f;
//        final float y = (float) (fontBounds.getHeight() + (image.getHeight() - fontBounds.getHeight()) / 2f);
//
//        // Text will be transparent
//        g.setComposite(AlphaComposite.Src);
//        g.setColor(new Color(0, 0, 0, 0));
//        g.setFont(font);
//        g.drawString(text, x, y);
//
//        // Stroke the perimeter
//        float strokeWidth = 1f;
//        g.setColor(new Color(0, 0, 0, 0));
//        final BasicStroke dashed = new BasicStroke(strokeWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{2f}, 0.0f);
//        g.setStroke(dashed);
//        g.draw(new Rectangle2D.Double(0, 0, width - strokeWidth, height - strokeWidth));
//
//        // Done
//        g.dispose();

        return image;
    }

    static class ImageListener implements IOverlayListener
    {
        final IClientAPI jmAPI;
        final ImageOverlay overlay;

        ImageListener(IClientAPI jmAPI, final ImageOverlay overlay)
        {
            this.jmAPI = jmAPI;
            this.overlay = overlay;
        }

        @Override
        public void onActivate(UIState uiState)
        {
            overlay.getImage().setOpacity(.5f);
        }

        @Override
        public void onDeactivate(UIState uiState)
        {
        }

        @Override
        public void onMouseMove(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition)
        {
            overlay.getImage().setOpacity(1f);
        }

        @Override
        public void onMouseOut(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition)
        {
            overlay.getImage().setOpacity(.5f);
        }

        @Override
        public boolean onMouseClick(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition, int button, boolean doubleClick)
        {
            jmAPI.remove(overlay);
            return true;
        }

        @Override
        public void onOverlayMenuPopup(UIState mapState, Point2D.Double mousePosition, BlockPos blockPosition, ModPopupMenu modPopupMenu)
        {
            modPopupMenu.addMenuItem("Delete", b -> jmAPI.remove(overlay));
        }
    }
}
