package ru.climeron.netheradditions.world.generation.traits;

import java.util.Random;
import java.util.function.Consumer;

import net.minecraft.block.BlockMushroom;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.climeron.netheradditions.init.InitBlocks;

public class BiomeTraitEnoki extends BiomeTrait
{
    private BiomeTraitEnoki(Builder builder)
    {
        super(builder);
    }

    public static BiomeTraitEnoki create(Consumer<Builder> consumer)
    {
        Builder builder = new Builder();
        consumer.accept(builder);
        return builder.create();
    }

    @Override
    public boolean generate(World world, BlockPos pos, Random random)
    {
        if(world.isAirBlock(pos.down()) && random.nextInt(8) == 7)
        {
            //BlockMushroom.generatePlant(world, pos, random, 8);
            world.setBlockState(pos, Blocks.RED_MUSHROOM_BLOCK.getDefaultState());
            return true;
        }

        return false;
    }

    public static class Builder extends BiomeTrait.Builder<BiomeTraitEnoki>
    {
        public Builder()
        {
            super();
        }

        @Override
        public BiomeTraitEnoki create()
        {
            return new BiomeTraitEnoki(this);
        }
    }
}