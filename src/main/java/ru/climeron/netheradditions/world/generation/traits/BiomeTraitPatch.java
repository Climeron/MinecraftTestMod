package ru.climeron.netheradditions.world.generation.traits;

import java.util.Random;
import java.util.function.Consumer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BiomeTraitPatch extends BiomeTrait
{
    protected IBlockState blockToSpawn;
    protected IBlockState blockToTarget;
    protected int patchWidth;

    protected BiomeTraitPatch(Builder builder)
    {
        super(builder);
        this.blockToSpawn = builder.blockToSpawn;
        this.blockToTarget = builder.blockToTarget;
        this.patchWidth = builder.patchWidth;
    }

    public static BiomeTraitPatch create(Consumer<Builder> consumer)
    {
        Builder builder = new Builder();
        consumer.accept(builder);
        return builder.create();
    }

    @Override
    public boolean generate(World world, BlockPos pos, Random random)
    {
        if(this.blockToSpawn == null || this.blockToTarget == null)
        {
            return false;
        }

        while(world.isAirBlock(pos) && pos.getY() > 2)
        {
            pos = pos.down();
        }

        if(world.getBlockState(pos) != this.blockToTarget)
        {
            return false;
        }
        else
        {
            int width = random.nextInt(this.patchWidth - 2) + 2;

            for(int posX = pos.getX() - width; posX <= pos.getX() + width; posX++)
            {
                for(int posZ = pos.getZ() - width; posZ <= pos.getZ() + width; posZ++)
                {
                    int areaX = posX - pos.getX();
                    int areaZ = posZ - pos.getZ();

                    if(areaX * areaX + areaZ * areaZ <= width * width)
                    {
                        for(int posY = pos.getY() - 1; posY <= pos.getY() + 1; posY++)
                        {
                            BlockPos blockPos = new BlockPos(posX, posY, posZ);

                            if(world.getBlockState(blockPos) == this.blockToTarget)
                            {
                                world.setBlockState(blockPos, this.blockToSpawn, 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }

    public static class Builder extends BiomeTrait.Builder
    {
        private IBlockState blockToSpawn;
        private IBlockState blockToTarget;
        private int patchWidth;

        public Builder()
        {
            this.blockToSpawn = Blocks.PACKED_ICE.getDefaultState();
            this.blockToTarget = Blocks.ICE.getDefaultState();
            this.patchWidth = 4;
        }

        public Builder blockToSpawn(IBlockState blockToSpawn)
        {
            this.blockToSpawn = blockToSpawn;
            return this;
        }

        public Builder blockToTarget(IBlockState blockToTarget)
        {
            this.blockToTarget = blockToTarget;
            return this;
        }

        public Builder patchWidth(int patchWidth)
        {
            this.patchWidth = patchWidth;
            return this;
        }

        @Override
        public BiomeTraitPatch create()
        {
            return new BiomeTraitPatch(this);
        }
    }
}
