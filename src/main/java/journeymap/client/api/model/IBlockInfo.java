package journeymap.client.api.model;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

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
    LevelChunk getChunk();

    @Nullable
    ChunkPos getChunkPos();

    @Nullable
    Integer getRegionX();

    @Nullable
    Integer getRegionZ();
}
