package ru.climeron.netheradditions.misc;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import ru.climeron.netheradditions.init.InitItems;

public class TabNetherPlus extends CreativeTabs
{
	public TabNetherPlus(String label) {
		super(label);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(InitItems.NETHERITE_INGOT);
	}
}
