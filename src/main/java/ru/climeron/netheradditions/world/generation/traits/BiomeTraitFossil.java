package ru.climeron.netheradditions.world.generation.traits;

import java.util.Random;
import java.util.function.Consumer;

import net.minecraft.block.BlockBone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.climeron.netheradditions.init.InitBlocks;

public class BiomeTraitFossil extends BiomeTrait
{
    protected IBlockState blockToSpawn;
    protected IBlockState blockToTarget1;
    protected IBlockState blockToTarget2;
    protected int fossil;
    protected int variant;
    public static String structureName;

    protected BiomeTraitFossil(Builder builder)
    {
        super(builder);
        this.blockToSpawn = builder.blockToSpawn;
        this.blockToTarget1 = builder.blockToTarget1;
        this.blockToTarget2 = builder.blockToTarget2;
    }

    public static BiomeTraitFossil create(Consumer<Builder> consumer)
    {
        Builder builder = new Builder();
        consumer.accept(builder);
        return builder.create();
    }
    
    @Override
    public boolean generate(World world, BlockPos pos, Random random)
    {
        /*if(this.blockToSpawn == null || this.blockToTarget1 == null || this.blockToTarget2 == null)
        {
            return false;
        }
        
        fossil = random.nextInt(13) + 1;
        variant = random.nextInt(3) + 1;
        
        fossil = 1;
        variant = 1;

        BlockPos newPos = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
        
        for(int i = 0; i < 64; i++)
        {
        	if((world.getBlockState(newPos.down()) == this.blockToTarget1 || world.getBlockState(newPos.down()) == this.blockToTarget2) && world.getBlockState(newPos) == Blocks.AIR.getDefaultState())
        	{
        		if(fossil == 1)
        			Fossil_1(world, newPos, variant);
        	}
        }*/

        return false;
    }

    public void Fossil_1(World world, BlockPos pos, int variant)
    {
    	if(variant == 1)
    	{
    		for(int i = 0; i < 5; i++)
    		{
    			world.setBlockState(new BlockPos(pos.getX() + 2 - i, pos.getY() + 2, pos.getZ()), blockToSpawn.withProperty(BlockBone.AXIS, EnumFacing.Axis.X));
    		}
    		for(int i = 0; i < 4; i++)
    		{
    			world.setBlockState(new BlockPos(pos.getX() + 1 - i, pos.getY() + 4, pos.getZ()), blockToSpawn.withProperty(BlockBone.AXIS, EnumFacing.Axis.X));
    		}
    		for(int i = 0; i < 5; i++)
    		{
    			world.setBlockState(pos.up(i), blockToSpawn.withProperty(BlockBone.AXIS, EnumFacing.Axis.Y));
    		}
    	}
    }

    public static class Builder extends BiomeTrait.Builder
    {
        private IBlockState blockToSpawn;
        private IBlockState blockToTarget1;
        private IBlockState blockToTarget2;

        public Builder()
        {
            this.blockToSpawn = Blocks.BONE_BLOCK.getDefaultState();
            this.blockToTarget1 = InitBlocks.SOUL_SOIL.getDefaultState();
            this.blockToTarget2 = Blocks.SOUL_SAND.getDefaultState();
        }

        public Builder blockToSpawn(IBlockState blockToSpawn)
        {
            this.blockToSpawn = blockToSpawn;
            return this;
        }

        public Builder blockToTarget1(IBlockState blockToTarget1)
        {
            this.blockToTarget1 = blockToTarget1;
            return this;
        }

        public Builder blockToTarget2(IBlockState blockToTarget2)
        {
            this.blockToTarget2 = blockToTarget2;
            return this;
        }

        @Override
        public BiomeTraitFossil create()
        {
            return new BiomeTraitFossil(this);
        }
    }
}
