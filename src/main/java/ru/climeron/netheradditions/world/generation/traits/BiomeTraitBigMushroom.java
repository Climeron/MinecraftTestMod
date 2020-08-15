package ru.climeron.netheradditions.world.generation.traits;

import java.util.Random;
import java.util.function.Consumer;

import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BiomeTraitBigMushroom extends BiomeTrait
{
    protected IBlockState mushroomCap;
    protected IBlockState mushroomStem;
    protected IBlockState blockToPlaceOn;
    protected Shape shape;

    protected BiomeTraitBigMushroom(Builder builder)
    {
        super(builder);
        this.mushroomCap = builder.mushroomCap;
        this.mushroomStem = builder.mushroomStem;
        this.blockToPlaceOn = builder.blockToPlaceOn;
        this.shape = builder.shape;
    }

    public static BiomeTraitBigMushroom create(Consumer<Builder> consumer)
    {
        Builder builder = new Builder();
        consumer.accept(builder);
        return builder.create();
    }

    @Override
    public boolean generate(World world, BlockPos pos, Random random)
    {
        if(this.mushroomCap == null || this.mushroomStem == null || this.blockToPlaceOn == null || this.shape == null)
        {
            return false;
        }

        int stemHeight = random.nextInt(3) + 4;

        if(random.nextInt(12) == 0)
        {
            stemHeight *= 2;
        }

        boolean flag = true;

        if(pos.getY() >= 1 && pos.getY() + stemHeight + 1 < 256)
        {
            for(int y = pos.getY(); y <= pos.getY() + 1 + stemHeight; ++y)
            {
                int k = 3;

                if(y <= pos.getY() + 3)
                {
                    k = 0;
                }

                BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

                for(int x = pos.getX() - k; x <= pos.getX() + k && flag; ++x)
                {
                    for(int z = pos.getZ() - k; z <= pos.getZ() + k && flag; ++z)
                    {
                        if(y >= 0 && y < 256)
                        {
                            IBlockState state = world.getBlockState(mutablePos.setPos(x, y, z));

                            if(!state.getBlock().isAir(state, world, mutablePos) && !state.getBlock().isLeaves(state, world, mutablePos))
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
                if(world.getBlockState(pos.down()) != this.blockToPlaceOn)
                {
                    return false;
                }
                else
                {
                    int k2 = pos.getY() + stemHeight;

                    if(this.shape == Shape.BULB)
                    {
                        k2 = pos.getY() + stemHeight - 3;
                    }

                    for(int l2 = k2; l2 <= pos.getY() + stemHeight; ++l2)
                    {
                        int j3 = 1;

                        if(l2 < pos.getY() + stemHeight)
                        {
                            ++j3;
                        }

                        if(this.shape == Shape.FLAT)
                        {
                            j3 = 3;
                        }

                        int k3 = pos.getX() - j3;
                        int l3 = pos.getX() + j3;
                        int j1 = pos.getZ() - j3;
                        int k1 = pos.getZ() + j3;

                        for(int l1 = k3; l1 <= l3; ++l1)
                        {
                            for(int i2 = j1; i2 <= k1; ++i2)
                            {
                                int j2 = 5;

                                if(l1 == k3)
                                {
                                    --j2;
                                }
                                else if(l1 == l3)
                                {
                                    ++j2;
                                }

                                if(i2 == j1)
                                {
                                    j2 -= 3;
                                }
                                else if(i2 == k1)
                                {
                                    j2 += 3;
                                }

                                BlockHugeMushroom.EnumType mushroomType = BlockHugeMushroom.EnumType.byMetadata(j2);

                                if(this.shape == Shape.FLAT || l2 < pos.getY() + stemHeight)
                                {
                                    if((l1 == k3 || l1 == l3) && (i2 == j1 || i2 == k1))
                                    {
                                        continue;
                                    }

                                    if(l1 == pos.getX() - (j3 - 1) && i2 == j1)
                                    {
                                        mushroomType = BlockHugeMushroom.EnumType.NORTH_WEST;
                                    }

                                    if(l1 == k3 && i2 == pos.getZ() - (j3 - 1))
                                    {
                                        mushroomType = BlockHugeMushroom.EnumType.NORTH_WEST;
                                    }

                                    if(l1 == pos.getX() + (j3 - 1) && i2 == j1)
                                    {
                                        mushroomType = BlockHugeMushroom.EnumType.NORTH_EAST;
                                    }

                                    if(l1 == l3 && i2 == pos.getZ() - (j3 - 1))
                                    {
                                        mushroomType = BlockHugeMushroom.EnumType.NORTH_EAST;
                                    }

                                    if(l1 == pos.getX() - (j3 - 1) && i2 == k1)
                                    {
                                        mushroomType = BlockHugeMushroom.EnumType.SOUTH_WEST;
                                    }

                                    if(l1 == k3 && i2 == pos.getZ() + (j3 - 1))
                                    {
                                        mushroomType = BlockHugeMushroom.EnumType.SOUTH_WEST;
                                    }

                                    if(l1 == pos.getX() + (j3 - 1) && i2 == k1)
                                    {
                                        mushroomType = BlockHugeMushroom.EnumType.SOUTH_EAST;
                                    }

                                    if(l1 == l3 && i2 == pos.getZ() + (j3 - 1))
                                    {
                                        mushroomType = BlockHugeMushroom.EnumType.SOUTH_EAST;
                                    }
                                }

                                if(mushroomType == BlockHugeMushroom.EnumType.CENTER && l2 < pos.getY() + stemHeight)
                                {
                                    mushroomType = BlockHugeMushroom.EnumType.ALL_INSIDE;
                                }

                                if(pos.getY() >= pos.getY() + stemHeight - 1 || mushroomType != BlockHugeMushroom.EnumType.ALL_INSIDE)
                                {
                                    BlockPos blockpos = new BlockPos(l1, l2, i2);
                                    IBlockState state = world.getBlockState(blockpos);

                                    if(state.getBlock().canBeReplacedByLeaves(state, world, blockpos))
                                    {
                                        world.setBlockState(blockpos, this.mushroomCap);
                                    }
                                }
                            }
                        }
                    }

                    for(int i3 = 0; i3 < stemHeight; ++i3)
                    {
                        IBlockState iblockstate = world.getBlockState(pos.up(i3));

                        if(iblockstate.getBlock().canBeReplacedByLeaves(iblockstate, world, pos.up(i3)))
                        {
                            world.setBlockState(pos.up(i3), this.mushroomStem);
                        }
                    }

                    return true;
                }
            }
        }
        else
        {
            return false;
        }
    }

    public static class Builder extends BiomeTrait.Builder
    {
        private IBlockState mushroomCap;
        private IBlockState mushroomStem;
        private IBlockState blockToPlaceOn;
        private Shape shape;

        public Builder()
        {
            this.mushroomCap = Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState().withProperty(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.ALL_OUTSIDE);
            this.mushroomStem = Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState().withProperty(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.STEM);
            this.blockToPlaceOn = Blocks.GRASS.getDefaultState();
            this.shape = Shape.FLAT;
        }

        public Builder mushroomCap(IBlockState mushroomCap)
        {
            this.mushroomCap = mushroomCap;
            return this;
        }

        public Builder mushroomStem(IBlockState mushroomStem)
        {
            this.mushroomStem = mushroomStem;
            return this;
        }

        public Builder blockToPlaceOn(IBlockState blockToPlaceOn)
        {
            this.blockToPlaceOn = blockToPlaceOn;
            return this;
        }

        public Builder shape(Shape shape)
        {
            this.shape = shape;
            return this;
        }

        @Override
        public BiomeTraitBigMushroom create()
        {
            return new BiomeTraitBigMushroom(this);
        }
    }

    public enum Shape
    {
        FLAT,
        BULB
    }
}
