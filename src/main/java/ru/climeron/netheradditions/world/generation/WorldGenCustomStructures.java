package ru.climeron.netheradditions.world.generation;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import ru.climeron.netheradditions.init.InitBlocks;
import ru.climeron.netheradditions.world.biomes.BiomeSoulSandValley;
import ru.climeron.netheradditions.world.generation.feature.WorldGenStructure;
import scala.actors.threadpool.Arrays;

public class WorldGenCustomStructures implements IWorldGenerator
{
	public static final WorldGenStructure FOSSIL_1 = new WorldGenStructure("fossil_1");
	
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		switch(world.provider.getDimension())
		{
		case 0:
			generateStructure(FOSSIL_1, world, rand, chunkX, chunkZ, 10, Blocks.GRASS, BiomePlains.class);
			break;
		case -1:
			generateStructure(FOSSIL_1, world, rand, chunkX, chunkZ, 10, InitBlocks.SOUL_SOIL, BiomeSoulSandValley.class);
		}
	}
	
	private void generateStructure(WorldGenerator generator, World world, Random rand, int chunkX, int chunkZ, int chance, Block topBlock,  Class<?>...classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));
		
		int x = (chunkX * 16) + rand.nextInt(15);
		int z = (chunkZ * 16) + rand.nextInt(15);
		int y = calculateGenerationHeight(world, x, z, topBlock);
		BlockPos pos = new BlockPos(x, y, z);
		
		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
		
		if(world.getWorldType() != WorldType.FLAT)
			if(classesList.contains(biome))
				if(rand.nextInt(chance) == 0)
				{
					System.out.println("Генерация в " + world + " на координатах: " + pos);
					generator.generate(world, rand, pos);
				}
					
	}
	
	private static int calculateGenerationHeight(World world, int x, int z, Block topBlock)
	{
		int y = world.getHeight();
		boolean foundGround = false;
		
		while(!foundGround && y-- >= 0)
		{
			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			foundGround = block == topBlock;
		}
		
		return y;	
	}
}
