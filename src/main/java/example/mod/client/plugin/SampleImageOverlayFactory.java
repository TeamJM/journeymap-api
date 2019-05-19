package example.mod.client.plugin;

import example.mod.ExampleMod;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.display.IOverlayListener;
import journeymap.client.api.display.ImageOverlay;
import journeymap.client.api.model.MapImage;
import journeymap.client.api.util.UIState;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Sample factory that generates random ImageOverlays.
 */
class SampleImageOverlayFactory
{
    /**
     * Generate a bunch of random image overlays, just to show how they're put together.
     *
     * @param jmAPI       api
     * @param center      center of area to show the overlays
     * @param quantity    how many overlays to show
     * @param maxDistance furthest distance of overlay (upper left corner)
     * @param maxSize     largest size in blocks an overlay should cover
     * @return list.
     */
    static List<ImageOverlay> create(IClientAPI jmAPI, BlockPos center, int quantity, int maxDistance, int maxSize)
    {
        List<ImageOverlay> list = new ArrayList<ImageOverlay>();
        try
        {
            BlockPos start = center.add(-maxDistance / 2, 0, -maxDistance / 2);

            Random random = new Random();
            for (int i = 0; i < quantity; i++)
            {
                BlockPos pos = start.add(random.nextInt(maxDistance), 0, random.nextInt(maxDistance));
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


    /**
     * Generate a sample image overlay with a generated image.
     *
     * @param jmAPI      api
     * @param upperLeft  upper left position where image will start
     * @param blocksWide how wide of an area to cover
     * @param blocksTall how tall of an area to cover
     * @return overlay
     */
    static ImageOverlay createOverlay(IClientAPI jmAPI, BlockPos upperLeft, int blocksWide, int blocksTall)
    {
        BlockPos lowerRight = upperLeft.add(blocksWide, 0, blocksTall);

        // For this example, we'll generate a BufferedImage, but using a pre-existing ResourceLocation works too.
        MapImage image = new MapImage(createImage(blocksWide, blocksTall));

        // Generate a deterministic displayId in case we need to refer to it again
        String displayId = String.format("image%s,%s,%s,%s", upperLeft.getX(), upperLeft.getZ(), blocksWide, blocksTall);

        ImageOverlay imageOverlay = new ImageOverlay(ExampleMod.MODID, displayId, upperLeft, lowerRight, image);

        imageOverlay.getImage().setOpacity(.8f);

        imageOverlay.setLabel("Image Overlay")
                .setTitle(displayId)
                .setOverlayListener(new ImageListener(jmAPI, imageOverlay));

        return imageOverlay;
    }

    /**
     * Create a Buffered Image on the fly for a given area.
     *
     * @param width  image width
     * @param height image height
     * @return image
     */
    static BufferedImage createImage(int width, int height)
    {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Garish background
        g.setPaint(new GradientPaint(0, 0, new Color(255, 255, 255, 100), width, height, new Color(0, 0, 200, 200)));
        g.fillRect(0, 0, width, height);

        // Draw some text, for grins
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 8);
        String text = String.format("%sx%s", width, height);
        Rectangle2D fontBounds = font.getStringBounds(text, g.getFontRenderContext());
        final float x = (float) (bufferedImage.getWidth() - fontBounds.getWidth()) / 2f;
        final float y = (float) (fontBounds.getHeight() + (bufferedImage.getHeight() - fontBounds.getHeight()) / 2f);

        // Text will be transparent
        g.setComposite(AlphaComposite.Src);
        g.setColor(new Color(0, 0, 0, 0));
        g.setFont(font);
        g.drawString(text, x, y);

        // Stroke the perimeter
        float strokeWidth = 1f;
        g.setColor(new Color(0, 0, 0, 0));
        final BasicStroke dashed = new BasicStroke(strokeWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{2f}, 0.0f);
        g.setStroke(dashed);
        g.draw(new Rectangle2D.Double(0, 0, width - strokeWidth, height - strokeWidth));

        // Done
        g.dispose();

        return bufferedImage;
    }

    /**
     * Listener for events on a MarkerOverlay instance.
     */
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
            // Make it transparent
            overlay.getImage().setOpacity(.5f);
        }

        @Override
        public void onDeactivate(UIState uiState)
        {
        }

        @Override
        public void onMouseMove(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition)
        {
            // Make it opaque
            overlay.getImage().setOpacity(1f);
        }

        @Override
        public void onMouseOut(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition)
        {
            // Make it transparent
            overlay.getImage().setOpacity(.5f);
        }

        @Override
        public boolean onMouseClick(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition, int button, boolean doubleClick)
        {
            // Remove it on click
            jmAPI.remove(overlay);

            // Returning true will allow the click event to be used by other overlays
            return true;
        }
    }
}
