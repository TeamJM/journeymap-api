package journeymap.api.v2.client.fullscreen;

import journeymap.api.v2.common.util.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import javax.annotation.Nullable;

public interface IBlockInfo
{
    BlockPos getBlockPos();

    @Nullable
    Block getBlock();

    /**
     * The block's metadata value. 1.7.10 has no block-state concept; combine this with
     * {@link #getBlock()} to fully describe the block (Block, meta) pair.
     *
     * @return the metadata value (0-15)
     */
    int getBlockMeta();

    @Nullable
    BiomeGenBase getBiome();

    @Nullable
    Chunk getChunk();

    @Nullable
    ChunkCoordIntPair getChunkPos();

    @Nullable
    Integer getRegionX();

    @Nullable
    Integer getRegionZ();
}
