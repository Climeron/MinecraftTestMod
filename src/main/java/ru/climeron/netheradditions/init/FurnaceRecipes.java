package ru.climeron.netheradditions.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FurnaceRecipes
{
	public static void registerRecipes()
	{
		GameRegistry.addSmelting(new ItemStack(InitBlocks.ANCIENT_DEBRIS), new ItemStack(InitItems.NETHERITE_SCRAP), 1.00F);
		GameRegistry.addSmelting(new ItemStack(InitBlocks.NETHER_GOLD_ORE), new ItemStack(Items.GOLD_INGOT), 1.00F);
	}

}
