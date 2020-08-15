package ru.climeron.netheradditions.world.generation.traits;

import java.util.Random;
import java.util.function.Consumer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.climeron.netheradditions.blocks.Nylium;
import ru.climeron.netheradditions.init.InitBlocks;
import ru.climeron.netheradditions.utils.handlers.EnumHandler;

public class BiomeTraitNetherSprouts extends BiomeTrait
{
    protected BiomeTraitNetherSprouts(Builder builder)
    {
        super(builder);
    }

    public static BiomeTraitNetherSprouts create(Consumer<Builder> consumer)
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
            IBlockState blockDown = world.getBlockState(newPos.down());

            if(blockDown == InitBlocks.NYLIUM.getDefaultState().withProperty(Nylium.VARIANT, EnumHandler.EnumType.WARPED) &&
               world.getBlockState(newPos) == Blocks.AIR.getDefaultState())
            {
            	world.setBlockState(newPos, InitBlocks.NETHER_SPROUTS.getDefaultState());
            }
        }

        return true;
    }

    public static class Builder extends BiomeTrait.Builder<BiomeTraitNetherSprouts>
    {
        @Override
        public BiomeTraitNetherSprouts create()
        {
            return new BiomeTraitNetherSprouts(this);
        }
    }
}
