package ru.climeron.netheradditions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import ru.climeron.netheradditions.init.InitBlocks;
import ru.climeron.netheradditions.init.InitCreativeTabs;
import ru.climeron.netheradditions.init.InitItems;
import ru.climeron.netheradditions.main.Main;
import ru.climeron.netheradditions.utils.interfaces.IHasModel;

public class BaseBlock extends Block implements IHasModel
{
	public BaseBlock(String name, Material material, String tool, int harvest_level, int hardness, int resistance, CreativeTabs tab, SoundType sound) {
		super(material);

		setRegistryName(name);
		setUnlocalizedName(name);
		setHarvestLevel(tool, harvest_level);
		setHardness(hardness);
		setResistance(resistance);
		setCreativeTab(tab);
		setSoundType(sound);
		
		InitBlocks.BLOCKS.add(this);
		InitItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
		
	}
}
