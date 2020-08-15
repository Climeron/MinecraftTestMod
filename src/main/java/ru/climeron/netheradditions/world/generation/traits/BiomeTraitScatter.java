package ru.climeron.netheradditions.world.generation.traits;

import java.util.Random;
import java.util.function.Consumer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BiomeTraitScatter extends BiomeTrait
{
    protected IBlockState blockToSpawn;
    protected IBlockState blockToTarget;
    protected Placement placement;
    protected int spawnRadius;

    protected BiomeTraitScatter(Builder builder)
    {
        super(builder);
        this.blockToSpawn = builder.blockToSpawn;
        this.blockToTarget = builder.blockToTarget;
        this.placement = builder.placement;
        this.spawnRadius = builder.spawnRadius;
    }

    public static BiomeTraitScatter create(Consumer<Builder> consumer)
    {
        Builder builder = new Builder();
        consumer.accept(builder);
        return builder.create();
    }
    
    @Override
    public boolean generate(World world, BlockPos pos, Random random)
    {
        if(this.blockToSpawn == null || this.blockToTarget == null || this.placement == null)
        {
            return false;
        }

        for(int i = 0; i < 64; i++)
        {
            BlockPos newPos = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));

            if(this.placement == Placement.IN_GROUND && world.getBlockState(newPos) == this.blockToTarget && world.getBlockState(newPos.up()) == Blocks.AIR.getDefaultState() ||
               this.placement == Placement.IN_ROOF && world.getBlockState(newPos) == this.blockToTarget && world.getBlockState(newPos.down()) == Blocks.AIR.getDefaultState())
            {
            	for(int deltaX = -spawnRadius; deltaX <= spawnRadius; deltaX++)
                 	for(int deltaY = -spawnRadius; deltaY <= spawnRadius; deltaY++)
                     	for(int deltaZ = -spawnRadius; deltaZ <= spawnRadius; deltaZ++)
                     	{
                     		BlockPos offsetPos = this.placement.offsetPos(newPos);
                     		if(world.getBlockState(new BlockPos(newPos.getX() + deltaX, newPos.getY() + deltaY, newPos.getZ() + deltaZ)) == this.blockToTarget &&
                     		   world.getBlockState(new BlockPos(offsetPos.getX() + deltaX, offsetPos.getY() + deltaY, offsetPos.getZ() + deltaZ)) == Blocks.AIR.getDefaultState())
                     		{
                     			world.setBlockState(new BlockPos(newPos.getX() + deltaX, newPos.getY() + deltaY, newPos.getZ() + deltaZ), this.blockToSpawn);
                     		}
                     	}
            }
            else
            if(this.placement == Placement.ON_GROUND && world.getBlockState(newPos) == Blocks.AIR.getDefaultState() && world.getBlockState(newPos.down()) == this.blockToTarget ||
               this.placement == Placement.ON_ROOF && world.getBlockState(newPos) == Blocks.AIR.getDefaultState() && world.getBlockState(newPos.up()) == this.blockToTarget)
            {
                for(int deltaX = -spawnRadius; deltaX <= spawnRadius; deltaX++)
                    for(int deltaY = -spawnRadius; deltaY <= spawnRadius; deltaY++)
                        for(int deltaZ = -spawnRadius; deltaZ <= spawnRadius; deltaZ++)
                        {
                        	BlockPos offsetPos = this.placement.offsetPos(newPos);
                          	if(world.getBlockState(new BlockPos(newPos.getX() + deltaX, newPos.getY() + deltaY, newPos.getZ() + deltaZ)) == Blocks.AIR.getDefaultState() &&
                          	   world.getBlockState(new BlockPos(offsetPos.getX() + deltaX, offsetPos.getY() + deltaY, offsetPos.getZ() + deltaZ)) == this.blockToTarget)
                          	{
                          		world.setBlockState(new BlockPos(newPos.getX() + deltaX, newPos.getY() + deltaY, newPos.getZ() + deltaZ), this.blockToSpawn);
                          	}
                        }
            }
        }

        return true;
    }

    public enum Placement
    {
        ON_GROUND(EnumFacing.DOWN),
        IN_GROUND(EnumFacing.UP),
        ON_ROOF(EnumFacing.UP),
        IN_ROOF(EnumFacing.DOWN);

        EnumFacing offset;

        Placement(EnumFacing offsetIn)
        {
            this.offset = offsetIn;
        }

        public BlockPos offsetPos(BlockPos pos)
        {
            if(this.offset != null)
            {
                return pos.offset(this.offset);
            }
            else
            {
                return pos;
            }
        }
    }

    public static class Builder extends BiomeTrait.Builder
    {
        private IBlockState blockToSpawn;
        private IBlockState blockToTarget;
        private Placement placement;
        private int spawnRadius;

        public Builder()
        {
            this.blockToSpawn = Blocks.TALLGRASS.getDefaultState();
            this.blockToTarget = Blocks.GRASS.getDefaultState();
            this.placement = Placement.ON_GROUND;
            this.spawnRadius = 0;
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

        public Builder placement(Placement placement)
        {
            this.placement = placement;
            return this;
        }

        public Builder spawnRadius(int spawnRadius)
        {
            this.spawnRadius = spawnRadius;
            return this;
        }

        @Override
        public BiomeTraitScatter create()
        {
            return new BiomeTraitScatter(this);
        }
    }
}
