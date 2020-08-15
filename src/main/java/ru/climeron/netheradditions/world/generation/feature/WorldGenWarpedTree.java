package ru.climeron.netheradditions.world.generation.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import ru.climeron.netheradditions.blocks.Fungus;
import ru.climeron.netheradditions.blocks.Planks;
import ru.climeron.netheradditions.blocks.Stem;
import ru.climeron.netheradditions.blocks.TwistingVines;
import ru.climeron.netheradditions.blocks.WartBlock;
import ru.climeron.netheradditions.init.InitBlocks;
import ru.climeron.netheradditions.utils.handlers.EnumHandler;
import ru.climeron.netheradditions.utils.handlers.EnumHandlerVines;

public class WorldGenWarpedTree extends WorldGenAbstractTree
{
	public static final IBlockState LOG = InitBlocks.STEM.getDefaultState().withProperty(Stem.VARIANT, EnumHandler.EnumType.WARPED).withProperty(Stem.AXIS, EnumFacing.Axis.Y);

	private int minHeight;

	public WorldGenWarpedTree()
	{
		super(false);
		this.minHeight = 6;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		int height = this.minHeight + rand.nextInt(9);
		boolean flag = true;

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		for(int yPos = y; yPos <= y + 1 + height; yPos++)
		{
			if(!this.isReplaceable(world, new BlockPos(x, yPos, z)) &&
			   world.getBlockState(new BlockPos(x, yPos, z)) != Blocks.NETHER_WART_BLOCK.getDefaultState() &&
			   world.getBlockState(new BlockPos(x, yPos, z)) != InitBlocks.WARPED_WART_BLOCK.getDefaultState())
			{
				if(yPos - y < minHeight)
				{
					flag = false;
					break;
				}
				else
				{
					height = yPos - y;
					break;
				}
			}

			if(yPos < 0 || yPos > world.getHeight())
			{
				flag = false;
				break;
			}
		}
		if(!flag)
		{
			world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(InitBlocks.FUNGUS, 1, 0)));
			return false;
		}
		else
		{
			BlockPos down = pos.down();
			IBlockState state = world.getBlockState(down);
			boolean isSoil = state.getBlock().canSustainPlant(state, world, down, EnumFacing.UP,(Fungus) InitBlocks.FUNGUS);

			if(isSoil && y < world.getHeight() - height - 1)
			{
				state.getBlock().onPlantGrow(state, world, down, pos);

				for(int yPos = y - 3 + height; yPos <= y + height; yPos++)
				{
					int b1 = yPos - (y + height);
					int b2 = 1 - b1 / 2;

					for(int xPos = x - b2; xPos <= x + b2 && flag; xPos++)
					{
						int b3 = xPos - x;
						for(int zPos = z - b2; zPos <= z + b2 && flag; zPos++)
						{
							int b4 = zPos - z;
							if(Math.abs(b3) != b2 || Math.abs(b4) != b2 || rand.nextInt(2) != 0 && b1 != 0)
							{
								BlockPos treePos = new BlockPos(xPos, yPos, zPos);
								IBlockState treeState = world.getBlockState(treePos);
								if(treeState.getBlock().isAir(treeState, world, treePos) || treeState.getBlock().isLeaves(treeState, world, treePos))
								{
									this.setBlockAndNotifyAdequately(world, treePos, InitBlocks.WARPED_WART_BLOCK.getDefaultState());
								}
							}
						}
					}
				}
				for (int logHeight = 0; logHeight < height; logHeight++)
				{
					BlockPos up = pos.up(logHeight);
					IBlockState logState = world.getBlockState(up);

					if (logState.getBlock().isAir(logState, world, up) ||
						logState.getBlock().isLeaves(logState, world, up) ||
						world.getBlockState(up) == Blocks.NETHER_WART_BLOCK.getDefaultState() ||
						world.getBlockState(up) == InitBlocks.WARPED_WART_BLOCK.getDefaultState())
					{
						this.setBlockAndNotifyAdequately(world, pos.up(logHeight), LOG);
					}
				}
				for (int i = 0; i < 3; i++)
				{
					int deltaX;
					int deltaZ;
					BlockPos up = pos.up(height - 1);

					if (rand.nextBoolean() == true)
					{
						do{
							deltaX = rand.nextInt(4) - 2;
							deltaZ = rand.nextInt(4) - 2;
						}while (deltaX == deltaZ && deltaX == 0 || Math.abs(deltaX) == Math.abs(deltaZ) && Math.abs(deltaX) == 2);
						this.setBlockAndNotifyAdequately(world, new BlockPos(up.getX() + deltaX, up.getY() - i, up.getZ() + deltaZ), InitBlocks.SHROOMLIGHT.getDefaultState());
					}
				}
				return true;
			}
		}
		return true;
	}
}