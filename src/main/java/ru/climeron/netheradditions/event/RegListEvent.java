package ru.climeron.netheradditions.event;

import net.minecraftforge.common.MinecraftForge;

public class RegListEvent
{
	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(new FungusRightClicked());
	}
}
