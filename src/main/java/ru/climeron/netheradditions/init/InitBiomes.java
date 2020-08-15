package ru.climeron.netheradditions.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.climeron.netheradditions.main.Main;
import ru.climeron.netheradditions.reference.Reference;
import ru.climeron.netheradditions.utils.helpers.InjectionHelper;
import ru.climeron.netheradditions.world.biomes.BiomeCrimsonForest;
import ru.climeron.netheradditions.world.biomes.BiomeSoulSandValley;
import ru.climeron.netheradditions.world.biomes.BiomeWarpedForest;
import ru.climeron.netheradditions.world.biomes.data.BiomeDataBOP;
import ru.climeron.netheradditions.world.biomes.data.BiomeDataHell;

@GameRegistry.ObjectHolder(Reference.MODID)
public class InitBiomes
{
    public static final BiomeWarpedForest WARPED = InjectionHelper.nullValue();
    public static final BiomeCrimsonForest CRIMSON = InjectionHelper.nullValue();
    public static final BiomeSoulSandValley SOUL_SAND_VALLEY = InjectionHelper.nullValue();
    
    public static void registerBiomes()
    {
        BiomeDictionary.addTypes(WARPED, Type.NETHER, Type.HOT, Type.WET, Type.MUSHROOM);
        BiomeDictionary.addTypes(CRIMSON, Type.NETHER, Type.HOT, Type.WET, Type.MUSHROOM);
        BiomeDictionary.addTypes(SOUL_SAND_VALLEY, Type.NETHER, Type.HOT, Type.DRY, Type.SANDY);


        Main.BIOME_DATA_MANAGER.registerBiomeData(BiomeDataHell.INSTANCE);
        Main.BIOME_DATA_MANAGER.registerBiomeData(WARPED.getBiomeData());
        Main.BIOME_DATA_MANAGER.registerBiomeData(CRIMSON.getBiomeData());
        Main.BIOME_DATA_MANAGER.registerBiomeData(SOUL_SAND_VALLEY.getBiomeData());

        /*if(Main.BIOMES_O_PLENTY_LOADED)
        {
        	Main.BIOME_DATA_MANAGER.registerBiomeData(new BiomeDataBOP(new ResourceLocation("biomesoplenty:corrupted_sands"), 8, true, false));
            Main.BIOME_DATA_MANAGER.registerBiomeData(new BiomeDataBOP(new ResourceLocation("biomesoplenty:fungi_forest"), 4, true, false));
            Main.BIOME_DATA_MANAGER.registerBiomeData(new BiomeDataBOP(new ResourceLocation("biomesoplenty:phantasmagoric_inferno"), 6, true, false));
            Main.BIOME_DATA_MANAGER.registerBiomeData(new BiomeDataBOP(new ResourceLocation("biomesoplenty:undergarden"), 4, true, false));
            Main.BIOME_DATA_MANAGER.registerBiomeData(new BiomeDataBOP(new ResourceLocation("biomesoplenty:visceral_heap"), 4, true, false));
        }*/
    }
}