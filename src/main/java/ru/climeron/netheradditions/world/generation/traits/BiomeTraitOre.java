package ru.climeron.netheradditions.world.generation.traits;

import java.util.Random;
import java.util.function.Consumer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BiomeTraitOre extends BiomeTrait
{
    protected IBlockState blockToSpawn;
    protected IBlockState blockToReplace1;
    protected IBlockState blockToReplace2;
    protected IBlockState blockToReplace3;
    protected int veinSize;

    protected BiomeTraitOre(Builder builder)
    {
        super(builder);
        this.blockToSpawn = builder.blockToSpawn;
        this.blockToReplace1 = builder.blockToReplace1;
        this.blockToReplace2 = builder.blockToReplace2;
        this.blockToReplace3 = builder.blockToReplace3;
        this.veinSize = builder.veinSize;
    }

    public static BiomeTraitOre create(Consumer<Builder> consumer)
    {
        Builder builder = new Builder();
        consumer.accept(builder);
        return builder.create();
    }
    
    @Override
    public boolean generate(World world, BlockPos pos, Random random)
    {
        if(this.blockToSpawn == null || this.blockToReplace1 == null || this.blockToReplace2 == null || this.blockToReplace3 == null)
        {
            return false;
        }

        float f = random.nextFloat() * (float) Math.PI;
        double d0 = (double) ((float) pos.getX() + MathHelper.sin(f) * (float) this.veinSize / 8.0F);
        double d1 = (double) ((float) pos.getX() - MathHelper.sin(f) * (float) this.veinSize / 8.0F);
        double d2 = (double) ((float) pos.getZ() + MathHelper.cos(f) * (float) this.veinSize / 8.0F);
        double d3 = (double) ((float) pos.getZ() - MathHelper.cos(f) * (float) this.veinSize / 8.0F);
        double d4 = (double) (pos.getY() + random.nextInt(3) - 2);
        double d5 = (double) (pos.getY() + random.nextInt(3) - 2);

        for(int i = 0; i < this.veinSize; ++i)
        {
            float f1 = (float) i / (float) this.veinSize;
            double d6 = d0 + (d1 - d0) * (double) f1;
            double d7 = d4 + (d5 - d4) * (double) f1;
            double d8 = d2 + (d3 - d2) * (double) f1;
            double d9 = random.nextDouble() * (double) this.veinSize / 16.0D;
            double d10 = (double) (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
            double d11 = (double) (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
            int j = MathHelper.floor(d6 - d10 / 2.0D);
            int k = MathHelper.floor(d7 - d11 / 2.0D);
            int l = MathHelper.floor(d8 - d10 / 2.0D);
            int i1 = MathHelper.floor(d6 + d10 / 2.0D);
            int j1 = MathHelper.floor(d7 + d11 / 2.0D);
            int k1 = MathHelper.floor(d8 + d10 / 2.0D);

            for(int l1 = j; l1 <= i1; ++l1)
            {
                double d12 = ((double) l1 + 0.5D - d6) / (d10 / 2.0D);

                if(d12 * d12 < 1.0D)
                {
                    for(int i2 = k; i2 <= j1; ++i2)
                    {
                        double d13 = ((double) i2 + 0.5D - d7) / (d11 / 2.0D);

                        if(d12 * d12 + d13 * d13 < 1.0D)
                        {
                            for(int j2 = l; j2 <= k1; ++j2)
                            {
                                double d14 = ((double) j2 + 0.5D - d8) / (d10 / 2.0D);

                                if(d12 * d12 + d13 * d13 + d14 * d14 < 1.0D)
                                {
                                    BlockPos newPos = new BlockPos(l1, i2, j2);
                                    IBlockState state = world.getBlockState(newPos);

                                    if(state.getBlock().isReplaceableOreGen(state, world, newPos, BlockMatcher.forBlock(this.blockToReplace1.getBlock())) && state == this.blockToReplace1 ||
                                       state.getBlock().isReplaceableOreGen(state, world, newPos, BlockMatcher.forBlock(this.blockToReplace2.getBlock())) && state == this.blockToReplace2 ||
                                       state.getBlock().isReplaceableOreGen(state, world, newPos, BlockMatcher.forBlock(this.blockToReplace3.getBlock())) && state == this.blockToReplace3)
                                    {
                                        world.setBlockState(newPos, this.blockToSpawn, 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public static class Builder extends BiomeTrait.Builder
    {
        private IBlockState blockToSpawn;
        private IBlockState blockToReplace1;
        private IBlockState blockToReplace2;
        private IBlockState blockToReplace3;
        private int veinSize;

        public Builder()
        {
            this.blockToSpawn = Blocks.COAL_ORE.getDefaultState();
            this.blockToReplace1 = Blocks.STONE.getDefaultState();
            this.blockToReplace2 = Blocks.STONE.getDefaultState();
            this.blockToReplace3 = Blocks.STONE.getDefaultState();
            this.veinSize = 7;
        }

        public Builder blockToSpawn(IBlockState blockToSpawn)
        {
            this.blockToSpawn = blockToSpawn;
            return this;
        }

        public Builder blockToReplace1(IBlockState blockToReplace)
        {
            this.blockToReplace1 = blockToReplace;
            return this;
        }

        public Builder blockToReplace2(IBlockState blockToReplace)
        {
            this.blockToReplace2 = blockToReplace;
            return this;
        }

        public Builder blockToReplace3(IBlockState blockToReplace)
        {
            this.blockToReplace3 = blockToReplace;
            return this;
        }

        public Builder veinSize(int veinSize)
        {
            this.veinSize = veinSize;
            return this;
        }

        @Override
        public BiomeTraitOre create()
        {
            return new BiomeTraitOre(this);
        }
    }
}
