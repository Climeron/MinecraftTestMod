package ru.climeron.netheradditions.utils.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.climeron.netheradditions.init.FurnaceRecipes;
import ru.climeron.netheradditions.init.InitBlocks;
import ru.climeron.netheradditions.init.InitItems;
import ru.climeron.netheradditions.utils.interfaces.IHasModel;
import ru.climeron.netheradditions.world.biomes.BiomeCrimsonForest;
import ru.climeron.netheradditions.world.biomes.BiomeSoulSandValley;
import ru.climeron.netheradditions.world.biomes.BiomeWarpedForest;
import ru.climeron.netheradditions.world.generation.WorldGenCustomOres;
import ru.climeron.netheradditions.world.generation.WorldGenCustomStructures;

@EventBusSubscriber
public class RegisterHandler
{
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(InitItems.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(InitBlocks.BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
    public static void onRegisterBiomes(RegistryEvent.Register<Biome> event)
    {
        event.getRegistry().registerAll(
                new BiomeWarpedForest(),
                new BiomeCrimsonForest(),
                new BiomeSoulSandValley()
        );
    }
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : InitItems.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}
		for(Block block : InitBlocks.BLOCKS)
		{
			if(block instanceof IHasModel)
			{
				((IHasModel)block).registerModels();
			}
		}
	}
	
	public static void otherRegister()
	{
		FurnaceRecipes.registerRecipes();
		GameRegistry.registerWorldGenerator(new WorldGenCustomOres(), 0);
		GameRegistry.registerWorldGenerator(new WorldGenCustomStructures(), 0);
	}
}
