package ru.climeron.netheradditions.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import ru.climeron.netheradditions.init.InitCreativeTabs;
import ru.climeron.netheradditions.init.InitItems;
import ru.climeron.netheradditions.main.Main;
import ru.climeron.netheradditions.utils.interfaces.IHasModel;

public class BaseItem extends Item implements IHasModel
{
	public BaseItem(String name, CreativeTabs tab, int max_stack)
	{
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(tab);
		setMaxStackSize(max_stack);
		
		InitItems.ITEMS.add(this);
	}

	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}