package ru.climeron.netheradditions.utils.handlers;

import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.climeron.netheradditions.main.Main;
import ru.climeron.netheradditions.reference.Reference;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class WorldHandler
{
    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event)
    {
        World world = event.getWorld();
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        World world = event.world;
    }

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event)
    {
        World world = event.getWorld();

        if(!world.isRemote)
        {
            if(world.provider.getDimension() == DimensionType.OVERWORLD.getId())
            {
                Main.BIOME_DATA_MANAGER.cleanup(event);
            }
        }
    }
}
