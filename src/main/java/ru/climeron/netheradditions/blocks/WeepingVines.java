package ru.climeron.netheradditions.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;
import ru.climeron.netheradditions.blocks.item.ItemBlockVariants;
import ru.climeron.netheradditions.event.FungusRightClicked;
import ru.climeron.netheradditions.init.InitBlocks;
import ru.climeron.netheradditions.init.InitItems;
import ru.climeron.netheradditions.main.Main;
import ru.climeron.netheradditions.utils.handlers.EnumHandlerVines;
import ru.climeron.netheradditions.utils.interfaces.IHasModel;
import ru.climeron.netheradditions.utils.interfaces.IMetaName;
import ru.climeron.netheradditions.world.generation.feature.WorldGenCrimsonTree;
import ru.climeron.netheradditions.world.generation.feature.WorldGenWarpedTree;

public class WeepingVines extends BlockBush implements IHasModel, IMetaName
{
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
	protected static final AxisAlignedBB[] VINES_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.2D, 0.55D, 0.2D, 0.8D, 1.0D, 0.8D), new AxisAlignedBB(0D, 0D, 0D, 1.0D, 1.0D, 1.0D)};

	public static final PropertyEnum<EnumHandlerVines.EnumType> VARIANT = PropertyEnum.<EnumHandlerVines.EnumType>create("variant", EnumHandlerVines.EnumType.class, new Predicate<EnumHandlerVines.EnumType>()
    {
        public boolean apply(@Nullable EnumHandlerVines.EnumType apply)
        {
            return apply.getMeta() < 2;
        }
    });	
	private String name;
	
	public WeepingVines(String name, int resistance, CreativeTabs tab, SoundType sound)
	{

		setRegistryName(name);
		setUnlocalizedName(name);
		setResistance(resistance);
		setSoundType(sound);
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumHandlerVines.EnumType.FINAL_PART));
		setCreativeTab(tab);
        this.setTickRandomly(true);
		
		this.name = name;
		
		InitBlocks.BLOCKS.add(this);
		InitItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
	}
	
	//Sapling Shape
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return VINES_AABB[(this.getMetaFromState(state))];
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos)
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
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
		if (world.isAirBlock(pos.down()))
		{
			int vineCounter;
			for (vineCounter = 1; world.getBlockState(pos.up(vineCounter)).getBlock() == this; ++vineCounter)
            {
                ;
            }
			if (vineCounter < 25)
            {
				int age = ((Integer)state.getValue(AGE)).intValue();
				if (age == 6)
                {
					world.setBlockState(pos, InitBlocks.WEEPING_VINES.getDefaultState().withProperty(VARIANT, EnumHandlerVines.EnumType.PLANT));
					world.setBlockState(pos.down(), InitBlocks.WEEPING_VINES.getDefaultState().withProperty(VARIANT, EnumHandlerVines.EnumType.FINAL_PART));
                }
                else
                {
                    world.setBlockState(pos, InitBlocks.WEEPING_VINES.getDefaultState().withProperty(AGE, Integer.valueOf(age + 1)).withProperty(VARIANT, EnumHandlerVines.EnumType.FINAL_PART));
                }
            }
        }
    }
	
	//Variants
	@Override
	public int damageDropped(IBlockState state)
    {
        return 0;
    }
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		i = i | ((EnumHandlerVines.EnumType)state.getValue(VARIANT)).getMeta();
		return i;
	}
	
	//ÓÊÀÇÛÂÀÅÌ ÍÀÈÁÎËÜØÓÞ ÌÅÒÓ Â (meta & _)
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, EnumHandlerVines.EnumType.byMetadata(meta & 1));
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {VARIANT, AGE});
	}

	@Override
	public String getSpecialName(ItemStack stack)
	{
		return EnumHandlerVines.EnumType.values()[stack.getItemDamage()].getName();
	}
	
	@Override
	public void registerModels()
	{
		for(int i = 0; i < EnumHandlerVines.EnumType.values().length; i++)
		{
			Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, name + "_" + EnumHandlerVines.EnumType.values()[i].getName(), "inventory");
		}
	}
	
	@Override
	protected boolean canSustainBush(IBlockState state)
	{
		return true;
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
		for(int i = 0; i < 16; i++)
			if(world.getBlockState(pos.up()) == InitBlocks.WEEPING_VINES.getDefaultState().withProperty(AGE, Integer.valueOf(i)))
			{
				world.setBlockState(pos.up(), InitBlocks.WEEPING_VINES.getDefaultState().withProperty(VARIANT, EnumHandlerVines.EnumType.PLANT));
				return true;
			}
		if(!world.getBlockState(pos.up()).getMaterial().isOpaque() || !world.getBlockState(pos.up()).isFullCube())
		{
			return false;
		}
		else
		{
			return true;
		}
    }
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        this.checkAndDropBlock(world, pos, state);
    }
	
	@Override
	protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state)
    {
        if ((!world.getBlockState(pos.up()).getMaterial().isOpaque() || !world.getBlockState(pos.up()).isFullCube()) && 
        	world.getBlockState(pos.up()).getBlock() != InitBlocks.WEEPING_VINES)
        {
            this.dropBlockAsItem(world, pos, getStateFromMeta(0), 0);
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            if(world.getBlockState(pos.up(2)) == InitBlocks.WEEPING_VINES.getDefaultState().withProperty(VARIANT, EnumHandlerVines.EnumType.PLANT))
            {
            	world.setBlockState(pos.up(2), InitBlocks.WEEPING_VINES.getDefaultState().withProperty(VARIANT, EnumHandlerVines.EnumType.FINAL_PART));
            }
        }
        if(world.getBlockState(pos) == InitBlocks.WEEPING_VINES.getDefaultState().withProperty(VARIANT, EnumHandlerVines.EnumType.PLANT) &&
           world.getBlockState(pos.down()).getBlock() != InitBlocks.WEEPING_VINES)
        {
        	world.setBlockState(pos, InitBlocks.WEEPING_VINES.getDefaultState().withProperty(VARIANT, EnumHandlerVines.EnumType.FINAL_PART));
        }
    }
	
	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
    {
		for(int i = 0; i < 16; i++)
		{
			if(world.getBlockState(pos.up()).getMaterial().isOpaque() && world.getBlockState(pos.up()).isFullCube() ||
			   world.getBlockState(pos.up()) == InitBlocks.WEEPING_VINES.getDefaultState().withProperty(AGE, Integer.valueOf(i)))
			{
				return true;
			}
		}
        return false;
    }
	
	@Override public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) { return true; }
}