package ru.climeron.netheradditions.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.climeron.netheradditions.blocks.item.ItemBlockVariants;
import ru.climeron.netheradditions.event.FungusRightClicked;
import ru.climeron.netheradditions.init.InitBlocks;
import ru.climeron.netheradditions.init.InitItems;
import ru.climeron.netheradditions.main.Main;
import ru.climeron.netheradditions.utils.handlers.EnumHandler;
import ru.climeron.netheradditions.utils.interfaces.IHasModel;
import ru.climeron.netheradditions.utils.interfaces.IMetaName;
import ru.climeron.netheradditions.world.generation.feature.WorldGenCrimsonTree;
import ru.climeron.netheradditions.world.generation.feature.WorldGenWarpedTree;

public class Fungus extends BlockBush implements IHasModel, IMetaName, IGrowable
{
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

	public static final PropertyEnum<EnumHandler.EnumType> VARIANT = PropertyEnum.<EnumHandler.EnumType>create("variant", EnumHandler.EnumType.class, new Predicate<EnumHandler.EnumType>()
    {
        public boolean apply(@Nullable EnumHandler.EnumType apply)
        {
            return apply.getMeta() < 2;
        }
    });	
	private String name;
	
	public Fungus(String name, int resistance, CreativeTabs tab, SoundType sound) {

		setRegistryName(name);
		setUnlocalizedName(name);
		setResistance(resistance);
		setCreativeTab(tab);
		setSoundType(sound);
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumHandler.EnumType.WARPED).withProperty(STAGE, Integer.valueOf(0)));
		
		this.name = name;
		
		InitBlocks.BLOCKS.add(this);
		InitItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
	}
	
	//Sapling Shape
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return SAPLING_AABB;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}
	
	//Variants
	@Override
	public int damageDropped(IBlockState state)
    {
        return ((EnumHandler.EnumType)state.getValue(VARIANT)).getMeta();
    }
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		i = i | ((EnumHandler.EnumType)state.getValue(VARIANT)).getMeta();
		i = i | ((Integer)state.getValue(STAGE)).intValue() << 3;
		return i;
	}
	
	//ÓÊÀÇÛÂÀÅÌ ÍÀÈÁÎËÜØÓÞ ÌÅÒÓ Â (meta & _)
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, EnumHandler.EnumType.byMetadata(meta & 1)).withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
		for(EnumHandler.EnumType variant : EnumHandler.EnumType.values())
		{
			items.add(new ItemStack(this, 1, variant.getMeta()));
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {VARIANT, STAGE});
	}

	@Override
	public String getSpecialName(ItemStack stack)
	{
		return EnumHandler.EnumType.values()[stack.getItemDamage()].getName();
	}
	
	@Override
	public void registerModels() {
		for(int i = 0; i < EnumHandler.EnumType.values().length; i++)
		{
			Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, EnumHandler.EnumType.values()[i].getName() + "_" + name, "inventory");
		}
	}

	//Tree
	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state)
	{
		return (double)world.rand.nextFloat() < 0.45D;
	}
	
	@Override
	protected boolean canSustainBush(IBlockState state)
	{
		return state.getBlock() == InitBlocks.NYLIUM;
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
		boolean flag = false;
		if(world.getBlockState(pos.down()) == InitBlocks.NYLIUM.getDefaultState().withProperty(Nylium.VARIANT, EnumHandler.EnumType.WARPED) && FungusRightClicked.metaData == 0 ||
		   world.getBlockState(pos.down()) == InitBlocks.NYLIUM.getDefaultState().withProperty(Nylium.VARIANT, EnumHandler.EnumType.CRIMSON) && FungusRightClicked.metaData == 1)
        flag = true;
        return flag;
    }
	
	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state)
	{
		if(((Integer)state.getValue(STAGE)).intValue() == 0)
		{
			world.setBlockState(pos, state.cycleProperty(STAGE), 4);
		}
		else
		{
			this.generateTree(world, rand, pos, state);
		}
	}
	
	public void generateTree(World world, Random rand, BlockPos pos, IBlockState state)
	{
		if (!TerrainGen.saplingGrowTree(world, rand, pos)) return;
	    WorldGenerator gen = (WorldGenerator)(rand.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true));
	    int i = 0;
	    int j = 0;
	    boolean flag = false;

	    switch((EnumHandler.EnumType)state.getValue(VARIANT))
	    {
	    case WARPED:
	    	gen = new WorldGenWarpedTree();
	    	break;
	    case CRIMSON:
	    	gen = new WorldGenCrimsonTree();
	    	break;
	    }

	    IBlockState iblockstate = Blocks.AIR.getDefaultState();

	    if (flag)
	    {
	        world.setBlockState(pos.add(i, 0, j), iblockstate, 4);
	        world.setBlockState(pos.add(i + 1, 0, j), iblockstate, 4);
	        world.setBlockState(pos.add(i, 0, j + 1), iblockstate, 4);
	        world.setBlockState(pos.add(i + 1, 0, j + 1), iblockstate, 4);
	    }
	    else
	    {
	    	world.setBlockState(pos, iblockstate, 4);
	    }
	    
	    if(!gen.generate(world, rand, pos.add(i, 0, j)))
	    {
	    	if (flag)
		    {
		        world.setBlockState(pos.add(i, 0, j), iblockstate, 4);
		        world.setBlockState(pos.add(i + 1, 0, j), iblockstate, 4);
		        world.setBlockState(pos.add(i, 0, j + 1), iblockstate, 4);
		        world.setBlockState(pos.add(i + 1, 0, j + 1), iblockstate, 4);
		    }
		    else
		    {
		    	world.setBlockState(pos, iblockstate, 4);
		    }
	    }
	}
}