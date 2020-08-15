package ru.climeron.netheradditions.world.generation.traits;

import java.util.Random;
import java.util.function.Consumer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BiomeTraitFluid extends BiomeTrait
{
    protected IBlockState blockToSpawn;
    protected IBlockState blockToTarget;
    protected boolean generateFalling;

    protected BiomeTraitFluid(Builder builder)
    {
        super(builder);
        this.blockToSpawn = builder.blockToSpawn;
        this.blockToTarget = builder.blockToTarget;
        this.generateFalling = builder.generateFalling;
    }

    public static BiomeTraitFluid create(Consumer<Builder> consumer)
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

        if(world.getBlockState(pos.up()) != this.blockToTarget)
        {
            return false;
        }
        else if(!world.isAirBlock(pos) && world.getBlockState(pos) != this.blockToTarget)
        {
            return false;
        }
        else
        {
            int i = 0;

            if(world.getBlockState(pos.west()) == this.blockToTarget)
            {
                i++;
            }

            if(world.getBlockState(pos.east()) == this.blockToTarget)
            {
                i++;
            }

            if(world.getBlockState(pos.north()) == this.blockToTarget)
            {
                i++;
            }

            if(world.getBlockState(pos.south()) == this.blockToTarget)
            {
                i++;
            }

            if(world.getBlockState(pos.down()) == this.blockToTarget)
            {
                i++;
            }

            int j = 0;

            if(world.isAirBlock(pos.west()))
            {
                j++;
            }

            if(world.isAirBlock(pos.east()))
            {
                j++;
            }

            if(world.isAirBlock(pos.north()))
            {
                j++;
            }

            if(world.isAirBlock(pos.south()))
            {
                j++;
            }

            if(world.isAirBlock(pos.down()))
            {
                j++;
            }

            if(!this.generateFalling && i == 4 && j == 1 || i == 5)
            {
                world.setBlockState(pos, this.blockToSpawn, 2);
                world.immediateBlockTick(pos, this.blockToSpawn, random);
            }

            return true;
        }
    }

    public static class Builder extends BiomeTrait.Builder
    {
        private IBlockState blockToSpawn;
        private IBlockState blockToTarget;
        private boolean generateFalling;

        public Builder()
        {
            this.blockToSpawn = Blocks.WATER.getDefaultState();
            this.blockToTarget = Blocks.STONE.getDefaultState();
            this.generateFalling = false;
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

        public Builder generateFalling(boolean generateFalling)
        {
            this.generateFalling = generateFalling;
            return this;
        }

        @Override
        public BiomeTraitFluid create()
        {
            return new BiomeTraitFluid(this);
        }
    }
}
