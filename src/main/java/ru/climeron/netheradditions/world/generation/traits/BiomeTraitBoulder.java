package ru.climeron.netheradditions.world.generation.traits;

import java.util.Random;
import java.util.function.Consumer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BiomeTraitBoulder extends BiomeTrait
{
    protected IBlockState blockToSpawn;
    protected IBlockState blockToTarget;
    protected int boulderRadius;

    protected BiomeTraitBoulder(Builder builder)
    {
        super(builder);
        this.blockToSpawn = builder.blockToSpawn;
        this.blockToTarget = builder.blockToTarget;
        this.boulderRadius = builder.boulderRadius;
    }

    public static BiomeTraitBoulder create(Consumer<Builder> consumer)
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

        while(true)
        {
            airCheckLabel:
            {
                if(pos.getY() > 3)
                {
                    if(world.isAirBlock(pos.down()))
                    {
                        break airCheckLabel;
                    }

                    IBlockState state = world.getBlockState(pos.down());

                    if(this.blockToTarget != state)
                    {
                        break airCheckLabel;
                    }
                }

                if(pos.getY() <= 3)
                {
                    return false;
                }

                for(int i = 0; this.boulderRadius >= 0 && i < 3; i++)
                {
                    int posX = this.boulderRadius + random.nextInt(2);
                    int posY = this.boulderRadius + random.nextInt(2);
                    int posZ = this.boulderRadius + random.nextInt(2);
                    float distance = (float) (posX + posY + posZ) * 0.333F + 0.5F;

                    for(BlockPos posLocal : BlockPos.getAllInBox(pos.add(-posX, -posY, -posZ), pos.add(posX, posY, posZ)))
                    {
                        if(posLocal.distanceSq(pos) <= (double) (distance * distance))
                        {
                            world.setBlockState(posLocal, this.blockToSpawn, 4);
                        }
                    }

                    pos = pos.add(-(this.boulderRadius + 1) + random.nextInt(2 + this.boulderRadius * 2), 0 - random.nextInt(2), -(this.boulderRadius + 1) + random.nextInt(2 + this.boulderRadius * 2));
                }

                return true;
            }

            pos = pos.down();
        }
    }

    public static class Builder extends BiomeTrait.Builder
    {
        private IBlockState blockToSpawn;
        private IBlockState blockToTarget;
        private int boulderRadius;

        public Builder()
        {
            this.blockToSpawn = Blocks.MOSSY_COBBLESTONE.getDefaultState();
            this.blockToTarget = Blocks.GRASS.getDefaultState();
            this.boulderRadius = 4;
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

        public Builder boulderRadius(int boulderRadius)
        {
            this.boulderRadius = boulderRadius;
            return this;
        }

        @Override
        public BiomeTraitBoulder create()
        {
            return new BiomeTraitBoulder(this);
        }
    }
}
