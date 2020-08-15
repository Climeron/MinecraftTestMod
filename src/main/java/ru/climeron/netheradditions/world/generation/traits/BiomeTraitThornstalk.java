package ru.climeron.netheradditions.world.generation.traits;

import java.util.Random;
import java.util.function.Consumer;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import ru.climeron.netheradditions.init.InitBlocks;

public class BiomeTraitThornstalk extends BiomeTrait
{
    protected BiomeTraitThornstalk(Builder builder)
    {
        super(builder);
    }

    public static BiomeTraitThornstalk create(Consumer<Builder> consumer)
    {
        Builder builder = new Builder();
        consumer.accept(builder);
        return builder.create();
    }

    @Override
    public boolean generate(World world, BlockPos pos, Random random)
    {
        for(int i = 0; i < 64; i++)
        {
            BlockPos newPos = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
            Block blockDown = world.getBlockState(newPos.down()).getBlock();

            if(blockDown == Blocks.SOUL_SAND && InitBlocks.SHROOMLIGHT.canPlaceBlockAt(world, newPos))
            {
            	//InitBlocks.SHROOMLIGHT.generate(world, random, newPos);
            	world.setBlockState(pos, InitBlocks.SHROOMLIGHT.getDefaultState());
            }
        }

        return true;
    }

    public static class Builder extends BiomeTrait.Builder<BiomeTraitThornstalk>
    {
        @Override
        public BiomeTraitThornstalk create()
        {
            return new BiomeTraitThornstalk(this);
        }
    }
}
