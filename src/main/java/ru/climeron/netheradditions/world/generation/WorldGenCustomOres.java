package ru.climeron.netheradditions.world.generation;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import ru.climeron.netheradditions.init.InitBlocks;

public class WorldGenCustomOres implements IWorldGenerator
{
	private WorldGenerator ancient_debris;
	
	public WorldGenCustomOres()
	{
		this.ancient_debris = new WorldGenMinable(InitBlocks.ANCIENT_DEBRIS.getDefaultState(), 3, BlockMatcher.forBlock(Blocks.NETHERRACK));
	}
	
	private void runGenerator(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight)
	{
		if(minHeight > maxHeight || minHeight < 0 || maxHeight > 256) throw new IllegalArgumentException("Ore generated out of bounds");
		
		int heightDiff = maxHeight - minHeight + 1;
		for(int i = 0; i< chance; i++)
		{
			int x = chunkX * 16 + rand.nextInt(16);
			int y = minHeight + rand.nextInt(heightDiff);
			int z = chunkZ * 16 + rand.nextInt(16);
			
			gen.generate(world,  rand,  new BlockPos(x,y,z));
		}
			
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		switch(world.provider.getDimension())
		{
		case -1:
			runGenerator(ancient_debris, world, random, chunkX, chunkZ, 2, 8, 22);
		}
	}
}
