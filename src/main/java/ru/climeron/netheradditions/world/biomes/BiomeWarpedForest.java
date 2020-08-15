package ru.climeron.netheradditions.world.biomes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;
import ru.climeron.netheradditions.blocks.Nylium;
import ru.climeron.netheradditions.blocks.Roots;
import ru.climeron.netheradditions.init.InitBiomes;
import ru.climeron.netheradditions.init.InitBlocks;
import ru.climeron.netheradditions.utils.handlers.EnumHandler;
import ru.climeron.netheradditions.world.biomes.data.BiomeData;
import ru.climeron.netheradditions.world.generation.GenerationStage;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitCluster;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitFluid;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitFungi;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitNetherSprouts;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitOre;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitPlants;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitScatter;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitTwistingVines;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitWarpedTree;

public class BiomeWarpedForest extends BiomeNetherAdditions
{
    private static final IBlockState TOP_BLOCK = InitBlocks.NYLIUM.getDefaultState().withProperty(Nylium.VARIANT, EnumHandler.EnumType.WARPED);
    private static final IBlockState FILLER_BLOCK = Blocks.NETHERRACK.getDefaultState();

    public BiomeWarpedForest()
    {
        super(new BiomeProperties("Warped Forest").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "warped");
        topBlock = TOP_BLOCK;
        fillerBlock = FILLER_BLOCK;
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 100, 4, 6));
    }

    @Override
    public BiomeData getBiomeData()
    {
        BiomeData biomeData = new BiomeData(InitBiomes.WARPED, 10, true, false);
        biomeData.addBiomeBlock(BiomeData.BlockType.SURFACE_BLOCK, TOP_BLOCK);
        biomeData.addBiomeBlock(BiomeData.BlockType.SUBSURFACE_BLOCK, FILLER_BLOCK);
        biomeData.addBiomeBlock(BiomeData.BlockType.LIQUID_BLOCK, LAVA);
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitFluid.create(trait ->
        {
            trait.generationAttempts(16);
            trait.minimumGenerationHeight(10);
            trait.maximumGenerationHeight(118);
            trait.blockToSpawn(Blocks.FLOWING_LAVA.getDefaultState());
            trait.blockToTarget(Blocks.NETHERRACK.getDefaultState());
            trait.generateFalling(true);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitFluid.create(trait ->
        {
            trait.generationAttempts(8);
            trait.minimumGenerationHeight(4);
            trait.maximumGenerationHeight(124);
            trait.blockToSpawn(Blocks.FLOWING_LAVA.getDefaultState());
            trait.blockToTarget(Blocks.NETHERRACK.getDefaultState());
            trait.generateFalling(false);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitScatter.create(trait ->
        {
            trait.generationAttempts(10);
            trait.randomizeGenerationAttempts(true);
            trait.minimumGenerationHeight(4);
            trait.maximumGenerationHeight(124);
            trait.blockToSpawn(Blocks.FIRE.getDefaultState());
            trait.blockToTarget(Blocks.NETHERRACK.getDefaultState());
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
            trait.blockToReplace2(TOP_BLOCK);
            trait.veinSize(14);
        }));
        biomeData.addBiomeTrait(GenerationStage.ORE, BiomeTraitOre.create(trait ->
        {
            trait.generationAttempts(12);
            trait.minimumGenerationHeight(2);
            trait.maximumGenerationHeight(120);
            trait.blockToSpawn(InitBlocks.NETHER_GOLD_ORE.getDefaultState());
            trait.blockToReplace1(Blocks.NETHERRACK.getDefaultState());
            trait.blockToReplace2(TOP_BLOCK);
            trait.veinSize(8);
        }));
        biomeData.addBiomeTrait(GenerationStage.ORE, BiomeTraitOre.create(trait ->
        {
            trait.generationAttempts(4);
            trait.minimumGenerationHeight(28);
            trait.maximumGenerationHeight(38);
            trait.blockToSpawn(Blocks.MAGMA.getDefaultState());
            trait.blockToReplace1(Blocks.NETHERRACK.getDefaultState());
            trait.blockToReplace2(TOP_BLOCK);
            trait.veinSize(32);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitWarpedTree.create(trait ->
        {
            trait.generationAttempts(1024);
            trait.minimumGenerationHeight(32);
            trait.maximumGenerationHeight(108);
            trait.minimumGrowthHeight(6);
            trait.maximumGrowthHeight(15);
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitPlants.create(trait ->
        {
            trait.generationAttempts(5);
            trait.minimumGenerationHeight(32);
            trait.maximumGenerationHeight(108);
            trait.blockToSpawn(InitBlocks.FUNGUS.getDefaultState().withProperty(Roots.VARIANT, EnumHandler.EnumType.WARPED));
            trait.blockToAttachTo(InitBlocks.NYLIUM.getDefaultState().withProperty(Nylium.VARIANT, EnumHandler.EnumType.WARPED));
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitPlants.create(trait ->
        {
            trait.generationAttempts(16);
            trait.minimumGenerationHeight(32);
            trait.maximumGenerationHeight(108);
            trait.blockToSpawn(InitBlocks.ROOTS.getDefaultState().withProperty(Roots.VARIANT, EnumHandler.EnumType.WARPED));
            trait.blockToAttachTo(InitBlocks.NYLIUM.getDefaultState().withProperty(Nylium.VARIANT, EnumHandler.EnumType.WARPED));
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitPlants.create(trait ->
        {
            trait.generationAttempts(32);
            trait.minimumGenerationHeight(32);
            trait.maximumGenerationHeight(108);
            trait.blockToSpawn(InitBlocks.NETHER_SPROUTS.getDefaultState());
            trait.blockToAttachTo(InitBlocks.NYLIUM.getDefaultState().withProperty(Nylium.VARIANT, EnumHandler.EnumType.WARPED));
        }));
        biomeData.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitTwistingVines.create(trait ->
        {
            trait.generationAttempts(4);
            trait.minimumGenerationHeight(32);
            trait.maximumGenerationHeight(108);
        }));
        return biomeData;
    }
}
