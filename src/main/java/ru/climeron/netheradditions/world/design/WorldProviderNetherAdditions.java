package ru.climeron.netheradditions.world.design;

import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.gen.IChunkGenerator;
import ru.climeron.netheradditions.world.biomes.design.BiomeProviderNetherAdditions;
import ru.climeron.netheradditions.world.generation.ChunkGeneratorNetherAdditions;

public class WorldProviderNetherAdditions extends WorldProviderHell
{
    @Override
    public void init()
    {
        super.init();
        this.biomeProvider = new BiomeProviderNetherAdditions(this.world);
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorNetherAdditions(this.world, this.world.getWorldInfo().isMapFeaturesEnabled(), this.world.getSeed());
    }
}
