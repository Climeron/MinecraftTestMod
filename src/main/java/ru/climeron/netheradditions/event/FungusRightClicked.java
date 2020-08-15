package ru.climeron.netheradditions.event;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.climeron.netheradditions.init.InitBlocks;

public class FungusRightClicked
{
	public static int metaData;
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void RightClicked(PlayerInteractEvent.RightClickBlock event)
	{
		if(event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND) != null &&
		   event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() == Item.getItemFromBlock(InitBlocks.FUNGUS))
		{
			metaData = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getMetadata();
		}
	}
}
