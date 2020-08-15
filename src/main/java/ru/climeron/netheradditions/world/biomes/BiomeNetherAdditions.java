package ru.climeron.netheradditions.world.biomes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import ru.climeron.netheradditions.world.biomes.data.BiomeData;
import ru.climeron.netheradditions.world.biomes.design.BiomeDecoratorNetherAdditions;

public abstract class BiomeNetherAdditions extends BiomeMod<BiomeData>
{
    protected static final IBlockState NETHERRACK = Blocks.NETHERRACK.getDefaultState();
    protected static final IBlockState SOUL_SAND = Blocks.SOUL_SAND.getDefaultState();
    protected static final IBlockState GLOWSTONE = Blocks.GLOWSTONE.getDefaultState();
    protected static final IBlockState FIRE = Blocks.FIRE.getDefaultState();
    protected static final IBlockState LAVA = Blocks.LAVA.getDefaultState();
    protected static final IBlockState FLOWING_LAVA = Blocks.FLOWING_LAVA.getDefaultState();
    protected static final IBlockState MAGMA = Blocks.MAGMA.getDefaultState();

    public BiomeNetherAdditions(BiomeProperties properties, String name)
    {
        super(properties, name);
    }

    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return this.getModdedBiomeDecorator(new BiomeDecoratorNetherAdditions());
    }

    @Override
    public abstract BiomeData getBiomeData();
}
