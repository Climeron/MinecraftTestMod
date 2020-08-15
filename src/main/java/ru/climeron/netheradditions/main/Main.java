package ru.climeron.netheradditions.main;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import ru.climeron.netheradditions.event.RegListEvent;
import ru.climeron.netheradditions.init.InitBiomes;
import ru.climeron.netheradditions.proxy.CommonProxy;
import ru.climeron.netheradditions.reference.Reference;
import ru.climeron.netheradditions.utils.handlers.RegisterHandler;
import ru.climeron.netheradditions.world.biomes.data.BiomeDataManager;
import ru.climeron.netheradditions.world.generation.NetherAdditionsOverrides;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
acceptedMinecraftVersions = Reference.ACCEPTED_MINECRAFT_VERSIONS)
public class Main
{
	public static final boolean BIOMES_O_PLENTY_LOADED = Loader.isModLoaded("biomesoplenty");
	
	@Instance
	public static Main instance;
	
	@SidedProxy(modId = Reference.MODID, clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;	
	
	public static final BiomeDataManager BIOME_DATA_MANAGER = new BiomeDataManager(Reference.MODID, Reference.NAME);
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
        NetherAdditionsOverrides.overrideObjects();
		RegisterHandler.otherRegister();
		RegListEvent.init();
		};
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		InitBiomes.registerBiomes();
	};
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		BIOME_DATA_MANAGER.setup();
	}

    @EventHandler
    public void onFMLServerStarting(FMLServerStartingEvent event)
    {
        NetherAdditionsOverrides.overrideNether();
    }
	
	public static ResourceLocation getResource(String name)
    {
        return new ResourceLocation(Reference.MODID + ":" + name);
    }

}
