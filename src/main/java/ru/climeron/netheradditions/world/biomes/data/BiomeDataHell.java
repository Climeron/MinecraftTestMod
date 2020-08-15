package ru.climeron.netheradditions.world.biomes.data;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import ru.climeron.netheradditions.init.InitBlocks;
import ru.climeron.netheradditions.world.generation.GenerationStage;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitCluster;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitFluid;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitOre;
import ru.climeron.netheradditions.world.generation.traits.BiomeTraitScatter;

public final class BiomeDataHell extends BiomeData
{
    public static final BiomeData INSTANCE = new BiomeDataHell();

    private BiomeDataHell()
    {
        super(Biomes.HELL, 10, true, false);
        this.addBiomeBlock(BiomeData.BlockType.SURFACE_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.addBiomeBlock(BiomeData.BlockType.SUBSURFACE_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.addBiomeBlock(BlockType.LIQUID_BLOCK, Blocks.LAVA.getDefaultState());
        this.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitFluid.create(trait ->
        {
            trait.generationAttempts(8);
            trait.minimumGenerationHeight(4);
            trait.maximumGenerationHeight(124);
            trait.blockToSpawn(Blocks.FLOWING_LAVA.getDefaultState());
            trait.blockToTarget(Blocks.NETHERRACK.getDefaultState());
            trait.generateFalling(false);
        }));
        this.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitScatter.create(trait ->
        {
            trait.generationAttempts(10);
            trait.randomizeGenerationAttempts(true);
            trait.minimumGenerationHeight(4);
            trait.maximumGenerationHeight(124);
            trait.blockToSpawn(Blocks.FIRE.getDefaultState());
            trait.blockToTarget(Blocks.NETHERRACK.getDefaultState());
            trait.placement(BiomeTraitScatter.Placement.ON_GROUND);
        }));
        this.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitCluster.create(trait ->
        {
            trait.generationAttempts(10);
            trait.randomizeGenerationAttempts(true);
            trait.minimumGenerationHeight(4);
            trait.maximumGenerationHeight(124);
            trait.blockToAttachTo(Blocks.NETHERRACK.getDefaultState());
            trait.direction(EnumFacing.DOWN);
        }));
        this.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitCluster.create(trait ->
        {
            trait.generationAttempts(10);
            trait.minimumGenerationHeight(1);
            trait.maximumGenerationHeight(128);
            trait.blockToAttachTo(Blocks.NETHERRACK.getDefaultState());
            trait.direction(EnumFacing.DOWN);
        }));
        this.addBiomeTrait(GenerationStage.DECORATION, BiomeTraitFluid.create(trait ->
        {
            trait.generationAttempts(16);
            trait.minimumGenerationHeight(10);
            trait.maximumGenerationHeight(118);
            trait.blockToSpawn(Blocks.FLOWING_LAVA.getDefaultState());
            trait.blockToTarget(Blocks.NETHERRACK.getDefaultState());
            trait.generateFalling(true);
        }));
        this.addBiomeTrait(GenerationStage.PLANT_DECORATION, BiomeTraitScatter.create(trait ->
        {
            trait.generationAttempts(1);
            trait.generationProbability(0.25D);
            trait.minimumGenerationHeight(1);
            trait.maximumGenerationHeight(128);
            trait.blockToSpawn(Blocks.BROWN_MUSHROOM.getDefaultState());
            trait.blockToTarget(Blocks.NETHERRACK.getDefaultState());
            trait.placement(BiomeTraitScatter.Placement.ON_GROUND);
        }));
        this.addBiomeTrait(GenerationStage.PLANT_DECORATION, BiomeTraitScatter.create(trait ->
        {
            trait.generationAttempts(1);
            trait.generationProbability(0.25D);
            trait.minimumGenerationHeight(1);
            trait.maximumGenerationHeight(128);
            trait.blockToSpawn(Blocks.RED_MUSHROOM.getDefaultState());
            trait.blockToTarget(Blocks.NETHERRACK.getDefaultState());
            trait.placement(BiomeTraitScatter.Placement.ON_GROUND);
        }));
        this.addBiomeTrait(GenerationStage.ORE, BiomeTraitOre.create(trait ->
        {
            trait.generationAttempts(16);
            trait.minimumGenerationHeight(10);
            trait.maximumGenerationHeight(108);
            trait.blockToSpawn(Blocks.QUARTZ_ORE.getDefaultState());
            trait.blockToReplace1(Blocks.NETHERRACK.getDefaultState());
            trait.veinSize(14);
        }));
        this.addBiomeTrait(GenerationStage.ORE, BiomeTraitOre.create(trait ->
        {
            trait.generationAttempts(12);
            trait.minimumGenerationHeight(2);
            trait.maximumGenerationHeight(120);
            trait.blockToSpawn(InitBlocks.NETHER_GOLD_ORE.getDefaultState());
            trait.blockToReplace1(Blocks.NETHERRACK.getDefaultState());
            trait.veinSize(8);
        }));
        this.addBiomeTrait(GenerationStage.ORE, BiomeTraitOre.create(trait ->
        {
            trait.generationAttempts(4);
            trait.minimumGenerationHeight(28);
            trait.maximumGenerationHeight(38);
            trait.blockToSpawn(Blocks.MAGMA.getDefaultState());
            trait.blockToReplace1(Blocks.NETHERRACK.getDefaultState());
            trait.veinSize(32);
        }));
    }
}
