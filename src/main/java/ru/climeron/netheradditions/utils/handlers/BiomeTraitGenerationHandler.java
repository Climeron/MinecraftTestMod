package ru.climeron.netheradditions.utils.handlers;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.climeron.netheradditions.main.Main;
import ru.climeron.netheradditions.reference.Reference;
import ru.climeron.netheradditions.utils.helpers.RandomHelper;
import ru.climeron.netheradditions.world.biomes.data.BiomeData;
import ru.climeron.netheradditions.world.generation.GenerationStage;
import ru.climeron.netheradditions.world.generation.traits.BiomeTrait;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class BiomeTraitGenerationHandler
{
    @SubscribeEvent
    public static void onPreBiomeDecorate(DecorateBiomeEvent.Pre event)
    {
        World world = event.getWorld();

        if(world.provider.getDimension() == DimensionType.NETHER.getId())
        {
            generateBiomeTraits(event.getWorld(), event.getChunkPos().getBlock(0, 0, 0), event.getRand(), GenerationStage.TERRAIN_ALTERATION);
        }
    }

    @SubscribeEvent
    public static void onBiomeDecorate(DecorateBiomeEvent.Decorate event)
    {
        World world = event.getWorld();

        if(world.provider.getDimension() == DimensionType.NETHER.getId() && event.getType() == DecorateBiomeEvent.Decorate.EventType.CUSTOM)
        {
            generateBiomeTraits(event.getWorld(), event.getChunkPos().getBlock(0, 0, 0), event.getRand(), GenerationStage.DECORATION);
            generateBiomeTraits(event.getWorld(), event.getChunkPos().getBlock(0, 0, 0), event.getRand(), GenerationStage.PLANT_DECORATION);
        }
    }

    @SubscribeEvent
    public static void onPostBiomeDecorate(DecorateBiomeEvent.Post event)
    {
        World world = event.getWorld();

        if(world.provider.getDimension() == DimensionType.NETHER.getId())
        {
            generateBiomeTraits(event.getWorld(), event.getChunkPos().getBlock(0, 0, 0), event.getRand(), GenerationStage.STRUCTURE);
        }
    }

    @SubscribeEvent
    public static void onGenerateOres(OreGenEvent.GenerateMinable event)
    {
        World world = event.getWorld();

        if(world.provider.getDimension() == DimensionType.NETHER.getId() && event.getType() == OreGenEvent.GenerateMinable.EventType.CUSTOM)
        {
            generateBiomeTraits(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.ORE);
        }
    }

    private static void generateBiomeTraits(World world, BlockPos pos, Random random, GenerationStage generationStage)
    {
        BiomeData biomeData = Main.BIOME_DATA_MANAGER.getBiomeData(world.getBiome(pos.add(16, 0, 16)));

        if(biomeData != null)
        {
            for(BiomeTrait trait : biomeData.getBiomeTraits(generationStage))
            {
                for(int generationAttempts = 0; generationAttempts < trait.getGenerationAttempts(world, pos, random); generationAttempts++)
                {
                    trait.generate(world, pos.add(random.nextInt(16) + 8, RandomHelper.getNumberInRange(trait.getMinimumGenerationHeight(world, pos, random), trait.getMaximumGenerationHeight(world, pos, random), random), random.nextInt(16) + 8), random);
                }
            }
        }
    }
}
