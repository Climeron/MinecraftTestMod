package ru.climeron.netheradditions.world.generation.traits;

import java.util.Random;
import java.util.function.Consumer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BiomeTraitCluster extends BiomeTrait
{
    protected IBlockState blockToSpawn;
    protected IBlockState blockToAttachTo;
    protected EnumFacing direction;

    protected BiomeTraitCluster(Builder builder)
    {
        super(builder);
        this.blockToSpawn = builder.blockToSpawn;
        this.blockToAttachTo = builder.blockToAttachTo;
        this.direction = builder.direction;
    }

    public static BiomeTraitCluster create(Consumer<Builder> consumer)
    {
        Builder builder = new Builder();
        consumer.accept(builder);
        return builder.create();
    }

    @Override
    public boolean generate(World world, BlockPos pos, Random random)
    {
        if(this.blockToSpawn == null || this.blockToAttachTo == null || this.direction == null)
        {
            return false;
        }

        if(!world.isAirBlock(pos))
        {
            return false;
        }
        else if(world.getBlockState(pos.offset(this.direction.getOpposite())) != this.blockToAttachTo)
        {
            return false;
        }
        else
        {
            world.setBlockState(pos, this.blockToSpawn, 3);

            for(int i = 0; i < 1500; i++)
            {
                BlockPos newPos;

                switch(this.direction)
                {
                    default:
                    case DOWN:
                        newPos = pos.add(random.nextInt(8) - random.nextInt(8), -random.nextInt(12), random.nextInt(8) - random.nextInt(8));
                        break;
                    case UP:
                        newPos = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(12), random.nextInt(8) - random.nextInt(8));
                        break;
                    case NORTH:
                        newPos = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(8) - random.nextInt(8), -random.nextInt(12));
                        break;
                    case SOUTH:
                        newPos = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(8) - random.nextInt(8), random.nextInt(12));
                        break;
                    case WEST:
                        newPos = pos.add(-random.nextInt(12), random.nextInt(8) - random.nextInt(8), random.nextInt(8) - random.nextInt(8));
                        break;
                    case EAST:
                        newPos = pos.add(random.nextInt(12), random.nextInt(8) - random.nextInt(8), random.nextInt(8) - random.nextInt(8));
                        break;
                }

                if(world.isAirBlock(newPos))
                {
                    int adjacentBlocks = 0;

                    for(EnumFacing facing : EnumFacing.values())
                    {
                        if(world.getBlockState(newPos.offset(facing)).getBlock() == this.blockToSpawn.getBlock())
                        {
                            adjacentBlocks++;
                        }

                        if(adjacentBlocks > 1)
                        {
                            break;
                        }
                    }

                    if(adjacentBlocks == 1)
                    {
                        world.setBlockState(newPos, this.blockToSpawn, 3);
                    }
                }
            }

            return true;
        }
    }

    public static class Builder extends BiomeTrait.Builder
    {
        private IBlockState blockToSpawn;
        private IBlockState blockToAttachTo;
        private EnumFacing direction;

        public Builder()
        {
            this.blockToSpawn = Blocks.GLOWSTONE.getDefaultState();
            this.blockToAttachTo = Blocks.NETHERRACK.getDefaultState();
            this.direction = EnumFacing.DOWN;
        }

        public Builder blockToSpawn(IBlockState blockToSpawn)
        {
            this.blockToSpawn = blockToSpawn;
            return this;
        }

        public Builder blockToAttachTo(IBlockState blockToAttachTo)
        {
            this.blockToAttachTo = blockToAttachTo;
            return this;
        }

        public Builder direction(EnumFacing direction)
        {
            this.direction = direction;
            return this;
        }

        @Override
        public BiomeTraitCluster create()
        {
            return new BiomeTraitCluster(this);
        }
    }
}
