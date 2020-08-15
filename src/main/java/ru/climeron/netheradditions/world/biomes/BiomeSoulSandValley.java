package ru.climeron.netheradditions.world.biomes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;
import ru.climeron.netheradditions.init.InitBiomes;
import ru.climeron.netheradditions.init.InitBlocks;
import ru.climeron.netheradditions.world.biomes.data.BiomeData;
import ru.climeron.netheradditions.world.generation.GenerationStage;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitCluster;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitFluid;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitFossil;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitOre;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitScatter;

public class BiomeSoulSandValley extends BiomeNetherAdditions
{
    private static final IBlockState TOP_BLOCK = InitBlocks.SOUL_SOIL.getDefaultState();
    private static final IBlockState FILLER_BLOCK = Blocks.NETHERRACK.getDefaultState();

    public BiomeSoulSandValley()
    {
        super(new BiomeProperties("Soul Sand Valley").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "soul_sand_valley");
        topBlock = TOP_BLOCK;
        fillerBlock = FILLER_BLOCK;
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityGhast.class, 100, 4, 6));
    }

    @Override
    public BiomeData getBiomeData()
    {
        BiomeData biomeData = new BiomeData(InitBiomes.SOUL_SAND_VALLEY, 10, true, false);
        biomeData.addBiomeBlock(BiomeData.BlockType.SURFACE_BLOCK, TOP_BLOCK);
        biomeData.addBiomeBlock(BiomeData.BlockType.SUBSURFACE_BLOCK, FILLER_BLOCK);
        biomeData.addBiomeBlock(BiomeData.BlockType.LIQUID_BLOCK, LAVA);
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitScatter.create(trait ->
        {
            trait.generationAttempts(3000);
            trait.minimumGenerationHeight(4);
            trait.maximumGenerationHeight(124);
            trait.blockToSpawn(TOP_BLOCK);
            trait.blockToTarget(Blocks.NETHERRACK.getDefaultState());
            trait.placement(BiomeTraitScatter.Placement.IN_ROOF);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitOre.create(trait ->
        {
            trait.generationAttempts(8);
            trait.minimumGenerationHeight(4);
            trait.maximumGenerationHeight(124);
            trait.blockToSpawn(Blocks.SOUL_SAND.getDefaultState());
            trait.blockToReplace1(TOP_BLOCK);
            trait.veinSize(64);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitFluid.create(trait ->
        {
            trait.generationAttempts(8);
            trait.minimumGenerationHeight(10);
            trait.maximumGenerationHeight(118);
            trait.blockToSpawn(Blocks.FLOWING_LAVA.getDefaultState());
            trait.blockToTarget(TOP_BLOCK);
            trait.generateFalling(true);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitFluid.create(trait ->
        {
            trait.generationAttempts(8);
            trait.minimumGenerationHeight(4);
            trait.maximumGenerationHeight(124);
            trait.blockToSpawn(Blocks.FLOWING_LAVA.getDefaultState());
            trait.blockToTarget(TOP_BLOCK);
            trait.generateFalling(false);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitScatter.create(trait ->
        {
            trait.generationAttempts(10);
            trait.randomizeGenerationAttempts(true);
            trait.minimumGenerationHeight(4);
            trait.maximumGenerationHeight(124);
            trait.blockToSpawn(Blocks.FIRE.getDefaultState());
            trait.blockToTarget(InitBlocks.SOUL_SOIL.getDefaultState());
            trait.placement(BiomeTraitScatter.Placement.ON_GROUND);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitCluster.create(trait ->
        {
            trait.generationAttempts(10);
            trait.randomizeGenerationAttempts(true);
            trait.minimumGenerationHeight(4);
            trait.maximumGenerationHeight(124);
            trait.blockToAttachTo(Blocks.NETHERRACK.getDefaultState());
            trait.direction(EnumFacing.DOWN);
        }));	//Place Glowstone
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitCluster.create(trait ->
        {
            trait.generationAttempts(10);
            trait.minimumGenerationHeight(1);
            trait.maximumGenerationHeight(128);
            trait.blockToAttachTo(Blocks.NETHERRACK.getDefaultState());
            trait.direction(EnumFacing.DOWN);
        }));	//Place Glowstone
        biomeData.addBiomeTrait(GenerationStage.ORE, BiomeTraitOre.create(trait ->
        {
            trait.generationAttempts(16);
            trait.minimumGenerationHeight(10);
            trait.maximumGenerationHeight(108);
            trait.blockToSpawn(Blocks.QUARTZ_ORE.getDefaultState());
            trait.blockToReplace1(Blocks.NETHERRACK.getDefaultState());
            trait.veinSize(14);
        }));
        biomeData.addBiomeTrait(GenerationStage.ORE, BiomeTraitOre.create(trait ->
        {
            trait.generationAttempts(16);
            trait.minimumGenerationHeight(2);
            trait.maximumGenerationHeight(120);
            trait.blockToSpawn(InitBlocks.NETHER_GOLD_ORE.getDefaultState());
            trait.blockToReplace1(Blocks.NETHERRACK.getDefaultState());
            trait.veinSize(8);
        }));
        biomeData.addBiomeTrait(GenerationStage.ORE, BiomeTraitOre.create(trait ->
        {
            trait.generationAttempts(4);
            trait.minimumGenerationHeight(28);
            trait.maximumGenerationHeight(38);
            trait.blockToSpawn(Blocks.MAGMA.getDefaultState());
            trait.blockToReplace1(Blocks.NETHERRACK.getDefaultState());
            trait.veinSize(32);
        }));
        /*biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitFossil.create(trait ->
        {
            trait.generationAttempts(8);
            trait.randomizeGenerationAttempts(true);
            trait.minimumGenerationHeight(32);
            trait.maximumGenerationHeight(100);
        }));*/
        return biomeData;
    }
}
