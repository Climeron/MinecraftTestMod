package ru.climeron.netheradditions.world.generation.traits;

import java.util.Random;
import java.util.function.Consumer;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.climeron.netheradditions.blocks.Nylium;
import ru.climeron.netheradditions.blocks.TwistingVines;
import ru.climeron.netheradditions.blocks.WeepingVines;
import ru.climeron.netheradditions.init.InitBlocks;
import ru.climeron.netheradditions.utils.handlers.EnumHandler;
import ru.climeron.netheradditions.utils.handlers.EnumHandlerVines;
import ru.climeron.netheradditions.utils.helpers.RandomHelper;

public class BiomeTraitWarpedTree extends BiomeTraitAbstractTree
{

    protected IBlockState logBlock;
    protected IBlockState leafBlock;
    protected IBlockState blockToTarget;
    
    protected BiomeTraitWarpedTree(Builder builder)
    {
        super(builder);
        this.logBlock = builder.logBlock;
        this.leafBlock = builder.leafBlock;
        this.blockToTarget = builder.blockToTarget;
        this.minimumGrowthHeight = builder.minimumGrowthHeight;
        this.maximumGrowthHeight = builder.maximumGrowthHeight;
    }

    public static BiomeTraitWarpedTree create(Consumer<Builder> consumer)
    {
        Builder builder = new Builder();
        consumer.accept(builder);
        return builder.create();
    }

    @Override
    public boolean generate(World world, BlockPos pos, Random random)
    {
        int height = RandomHelper.getNumberInRange(this.minimumGrowthHeight, this.maximumGrowthHeight, random);
        boolean flag = true;

        if(pos.getY() > 0 && pos.getY() + height + 1 <= world.getHeight())
        {
            for(int y = pos.getY(); y <= pos.getY() + height + 1; y++)	//Check free place for tree
            {
                int adjustedHeight = 1;

                if(y == pos.getY())
                {
                    adjustedHeight = 0;
                }

                if(y >= pos.getY() + height + 1 - 5)
                {
                    adjustedHeight = 2;
                }

                BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

                for(int x = pos.getX() - adjustedHeight; x <= pos.getX() + adjustedHeight && flag; x++)
                {
                    for(int z = pos.getZ() - adjustedHeight; z <= pos.getZ() + adjustedHeight && flag; z++)
                    {
                        if(y >= 0 && y < world.getHeight())
                        {
                            if(!this.isReplaceable(world, mutablePos.setPos(x, y, z)))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if(!flag)
            {
                return false;
            }
            else
            {
                IBlockState checkState = world.getBlockState(pos.down());

                if(this.blockToTarget == checkState && pos.getY() < world.getHeight() - height - 1)
                {
                    checkState.getBlock().onPlantGrow(checkState, world, pos.down(), pos);

                    for(int y = pos.getY() - 3 + height; y <= pos.getY() + height; y++)
                    {
                        int b1 = y - (pos.getY() + height);
                        int b2 = 1 - b1 / 2;

                        for(int x = pos.getX() - b2; x <= pos.getX() + b2; x++)
                        {
                            int b3 = x - pos.getX();
                            for(int z = pos.getZ() - b2; z <= pos.getZ() + b2; z++)
                            {
                                int b4 = z - pos.getZ();
                                if(Math.abs(b3) != b2 || Math.abs(b4) != b2 || random.nextInt(2) != 0 && b1 != 0)
                                {
                                    BlockPos treePos = new BlockPos(x, y, z);
                                    checkState = world.getBlockState(treePos);
                                    if(checkState.getBlock().isAir(checkState, world, treePos) || checkState.getBlock().isLeaves(checkState, world, treePos) || checkState.getMaterial() == Material.VINE)
                                    {
                                        world.setBlockState(treePos, this.leafBlock);
                                    }
                                }
                            }
                        }
                    }
                    for(int heightOffset = 0; heightOffset < height; heightOffset++)	//Place stems
                    {
                        BlockPos offsetPos = pos.up(heightOffset);
                        checkState = world.getBlockState(offsetPos);

                        if(checkState.getBlock().isAir(checkState, world, offsetPos) ||
                           checkState.getBlock().isLeaves(checkState, world, offsetPos) ||
                           checkState.getMaterial() == Material.VINE ||
        				   world.getBlockState(offsetPos) == Blocks.NETHER_WART_BLOCK.getDefaultState() ||
        				   world.getBlockState(offsetPos) == InitBlocks.WARPED_WART_BLOCK.getDefaultState())
                        {
                            world.setBlockState(pos.up(heightOffset), this.logBlock);
                        }
                    }
                    for (int i = 0; i < 3; i++)	//Place shroomlights
    				{
    					int deltaX;
    					int deltaZ;
    					BlockPos up = pos.up(height - 1);

    					if (random.nextBoolean() == true)
    					{
    						do{
    							deltaX = random.nextInt(4) - 2;
    							deltaZ = random.nextInt(4) - 2;
    						}while (deltaX == deltaZ && deltaX == 0 || Math.abs(deltaX) == Math.abs(deltaZ) && Math.abs(deltaX) == 2);
    						world.setBlockState(new BlockPos(up.getX() + deltaX, up.getY() - i, up.getZ() + deltaZ), InitBlocks.SHROOMLIGHT.getDefaultState());
    					}
    				}

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }

    public static class Builder extends BiomeTraitAbstractTree.Builder
    {
        private IBlockState logBlock;
        private IBlockState leafBlock;
        private IBlockState blockToTarget;
        
        public Builder()
        {
            this.logBlock = InitBlocks.STEM.getDefaultState().withProperty(Nylium.VARIANT, EnumHandler.EnumType.WARPED);
            this.leafBlock = InitBlocks.WARPED_WART_BLOCK.getDefaultState();
            this.blockToTarget = InitBlocks.NYLIUM.getDefaultState().withProperty(Nylium.VARIANT, EnumHandler.EnumType.WARPED);
        }
        
        @Override
        public BiomeTraitWarpedTree create()
        {
            return new BiomeTraitWarpedTree(this);
        }
    }
}
