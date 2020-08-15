package ru.climeron.netheradditions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import ru.climeron.netheradditions.blocks.item.ItemBlockVariants;
import ru.climeron.netheradditions.init.InitBlocks;
import ru.climeron.netheradditions.init.InitItems;
import ru.climeron.netheradditions.main.Main;
import ru.climeron.netheradditions.utils.handlers.EnumHandler;
import ru.climeron.netheradditions.utils.interfaces.IHasModel;

public class FungusBlock extends Block implements IHasModel
{
	
	public FungusBlock(String name, Material material, String tool, int harvest_level, int hardness, int resistance,
			CreativeTabs tab, SoundType sound) {
		super(material);

		setRegistryName(name);
		setUnlocalizedName(name);
		setHarvestLevel(tool, harvest_level);
		setHardness(hardness);
		setResistance(resistance);
		setCreativeTab(tab);
		setSoundType(sound);

		InitBlocks.BLOCKS.add(this);
		InitItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");

	}
}
