package ru.climeron.netheradditions.world.generation.traits;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BiomeTraitAbstractTree extends BiomeTrait
{
    protected IBlockState logBlock;
    protected IBlockState leafBlock;
    protected IBlockState blockToTarget;
    protected int minimumGrowthHeight;
    protected int maximumGrowthHeight;

    protected BiomeTraitAbstractTree(Builder builder)
    {
        super(builder);
        this.logBlock = builder.logBlock;
        this.leafBlock = builder.leafBlock;
        this.blockToTarget = builder.blockToTarget;
        this.minimumGrowthHeight = builder.minimumGrowthHeight;
        this.maximumGrowthHeight = builder.maximumGrowthHeight;
    }

    protected void placeLogAt(World world, BlockPos pos)
    {
        if(this.canGrowInto(world.getBlockState(pos).getBlock()))
        {
            world.setBlockState(pos, this.logBlock);
        }
    }

    protected boolean canGrowInto(Block block)
    {
        Material material = block.getDefaultState().getMaterial();
        return material == Material.AIR || material == Material.LEAVES;
    }

    protected boolean isReplaceable(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos) || state.getBlock().isWood(world, pos) || this.canGrowInto(state.getBlock());
    }

    public abstract static class Builder extends BiomeTrait.Builder
    {
        protected IBlockState logBlock;
        protected IBlockState leafBlock;
        protected IBlockState blockToTarget;
        protected int minimumGrowthHeight;
        protected int maximumGrowthHeight;

        public Builder()
        {
            this.logBlock = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
            this.leafBlock = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK);
            this.blockToTarget = Blocks.GRASS.getDefaultState();
            this.minimumGrowthHeight = 4;
            this.maximumGrowthHeight = 6;
        }

        public Builder logBlock(IBlockState logBlock)
        {
            this.logBlock = logBlock;
            return this;
        }

        public Builder leafBlock(IBlockState leafBlock)
        {
            this.leafBlock = leafBlock;
            return this;
        }

        public Builder blockToTarget(IBlockState blockToTarget)
        {
            this.blockToTarget = blockToTarget;
            return this;
        }

        public Builder minimumGrowthHeight(int minimumGrowthHeight)
        {
            this.minimumGrowthHeight = minimumGrowthHeight;
            return this;
        }

        public Builder maximumGrowthHeight(int maximumGrowthHeight)
        {
            this.maximumGrowthHeight = maximumGrowthHeight;
            return this;
        }

        @Override
        public abstract BiomeTrait create();
    }
}
