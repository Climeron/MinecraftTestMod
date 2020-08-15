package ru.climeron.netheradditions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.climeron.netheradditions.init.InitBlocks;
import ru.climeron.netheradditions.init.InitCreativeTabs;
import ru.climeron.netheradditions.init.InitItems;
import ru.climeron.netheradditions.main.Main;
import ru.climeron.netheradditions.utils.interfaces.IHasModel;

public class SoulSoil extends Block implements IHasModel
{
	public SoulSoil(String name, Material material, String tool, int harvest_level, float hardness, float resistance, CreativeTabs tab, SoundType sound) {
		super(material);

		setRegistryName(name);
		setUnlocalizedName(name);
		setHarvestLevel(tool, harvest_level);
		setHardness((float)hardness);
		setResistance((float)resistance);
		setCreativeTab(tab);
		setSoundType(sound);
		
		InitBlocks.BLOCKS.add(this);
		InitItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        if (side != EnumFacing.UP)
            return false;
        if (this == InitBlocks.SOUL_SOIL)
            return true;
        return false;
    }

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
		
	}
}
