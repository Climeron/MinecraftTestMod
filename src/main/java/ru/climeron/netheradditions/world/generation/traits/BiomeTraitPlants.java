package ru.climeron.netheradditions.world.generation.traits;

import java.util.Random;
import java.util.function.Consumer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.climeron.netheradditions.blocks.Nylium;
import ru.climeron.netheradditions.blocks.Roots;
import ru.climeron.netheradditions.init.InitBlocks;
import ru.climeron.netheradditions.utils.handlers.EnumHandler;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitCluster.Builder;

public class BiomeTraitPlants extends BiomeTrait
{
    protected IBlockState blockToSpawn;
    protected IBlockState blockToAttachTo;
    
    protected BiomeTraitPlants(Builder builder)
    {
        super(builder);
        this.blockToSpawn = builder.blockToSpawn;
        this.blockToAttachTo = builder.blockToAttachTo;
    }

    public static BiomeTraitPlants create(Consumer<Builder> consumer)
    {
        Builder builder = new Builder();
        consumer.accept(builder);
        return builder.create();
    }

    @Override
    public boolean generate(World world, BlockPos pos, Random random)
    {
    	if(this.blockToSpawn == null || this.blockToAttachTo == null)
        {
            return false;
        }
        for(int i = 0; i < 64; i++)
        {
            BlockPos newPos = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
            IBlockState blockDown = world.getBlockState(newPos.down());

            if(blockDown == this.blockToAttachTo && world.getBlockState(newPos) == Blocks.AIR.getDefaultState())
            {
            	world.setBlockState(newPos, this.blockToSpawn);
            }
        }

        return true;
    }

    public static class Builder extends BiomeTrait.Builder<BiomeTraitPlants>
    {
        protected IBlockState blockToSpawn;
        protected IBlockState blockToAttachTo;

        public Builder()
        {
            this.blockToSpawn = Blocks.TALLGRASS.getDefaultState();
            this.blockToAttachTo = Blocks.GRASS.getDefaultState();
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
        
        @Override
        public BiomeTraitPlants create()
        {
            return new BiomeTraitPlants(this);
        }
    }
}
