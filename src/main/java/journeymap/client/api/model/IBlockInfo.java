package journeymap.client.api.model;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import javax.annotation.Nullable;

public interface IBlockInfo
{
    BlockPos getBlockPos();

    @Nullable
    Block getBlock();

    @Nullable
    BlockState getBlockState();

    @Nullable
    Biome getBiome();

    @Nullable
    Chunk getChunk();

    @Nullable
    ChunkPos getChunkPos();

    @Nullable
    Integer getRegionX();

    @Nullable
    Integer getRegionZ();
}
