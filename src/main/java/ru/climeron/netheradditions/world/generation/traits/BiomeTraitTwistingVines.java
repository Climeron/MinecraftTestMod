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
import ru.climeron.netheradditions.blocks.TwistingVines;
import ru.climeron.netheradditions.init.InitBlocks;
import ru.climeron.netheradditions.utils.handlers.EnumHandler;
import ru.climeron.netheradditions.utils.handlers.EnumHandlerVines;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitCluster.Builder;

public class BiomeTraitTwistingVines extends BiomeTrait
{
    protected BiomeTraitTwistingVines(Builder builder)
    {
        super(builder);
    }

    public static BiomeTraitTwistingVines create(Consumer<Builder> consumer)
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
            	for(int j = 0; j <= random.nextInt(10); j++)
            	{
            		newPos = new BlockPos(newPos.getX(), newPos.getY() + 1, newPos.getZ());
            		if(world.getBlockState(new BlockPos(newPos.getX(), newPos.getY(), newPos.getZ())) == Blocks.AIR.getDefaultState())
            		{
            			world.setBlockState(new BlockPos(newPos.getX(), newPos.getY() - 1, newPos.getZ()), InitBlocks.TWISTING_VINES.getDefaultState().withProperty(TwistingVines.VARIANT, EnumHandlerVines.EnumType.PLANT));
            		}
            		else
            		{
            			break;
            		}
            	}
            	world.setBlockState(new BlockPos(newPos.getX(), newPos.getY() - 1, newPos.getZ()), InitBlocks.TWISTING_VINES.getDefaultState().withProperty(TwistingVines.VARIANT, EnumHandlerVines.EnumType.FINAL_PART));
            }
        }
        return true;
    }

    public static class Builder extends BiomeTrait.Builder<BiomeTraitTwistingVines>
    {
        @Override
        public BiomeTraitTwistingVines create()
        {
            return new BiomeTraitTwistingVines(this);
        }
    }
}
