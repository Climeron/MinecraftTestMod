package ru.climeron.netheradditions.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import ru.climeron.netheradditions.blocks.Basalt;
import ru.climeron.netheradditions.blocks.BaseBlock;
import ru.climeron.netheradditions.blocks.Fungus;
import ru.climeron.netheradditions.blocks.NetherSprouts;
import ru.climeron.netheradditions.blocks.Nylium;
import ru.climeron.netheradditions.blocks.Planks;
import ru.climeron.netheradditions.blocks.Roots;
import ru.climeron.netheradditions.blocks.SoulSoil;
import ru.climeron.netheradditions.blocks.Stem;
import ru.climeron.netheradditions.blocks.TwistingVines;
import ru.climeron.netheradditions.blocks.WarpedWartBlock;
import ru.climeron.netheradditions.blocks.WeepingVines;

public class InitBlocks
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	//Materials
	public static final Material NONFLAMMABLE_WOOD = (new Material(MapColor.WOOD));
			
	//Blocks
	//public static final Block BLOCK = new BaseStoneBlock(String name, Material material, String tool, int harvest_level, int hardness, int resistance, CreativeTabs tab, SoundType sound);
	public static final Block ANCIENT_DEBRIS = new BaseBlock("ancient_debris", Material.ROCK, "pickaxe", 3, 30, 6000, InitCreativeTabs.TAB_NETHER_PLUS, SoundType.STONE);
	public static final Block NETHERITE_BLOCK = new BaseBlock("netherite_block", Material.ROCK, "pickaxe", 3, 50, 6000, InitCreativeTabs.TAB_NETHER_PLUS, SoundType.STONE);
	public static final Block NETHER_GOLD_ORE = new BaseBlock("nether_gold_ore", Material.ROCK, "pickaxe", 2, 3, 15, InitCreativeTabs.TAB_NETHER_PLUS, SoundType.STONE);
	public static final Block SOUL_SOIL = new SoulSoil("soul_soil", Material.SAND, "shovel", 0, 0.5F, 2.5F, InitCreativeTabs.TAB_NETHER_PLUS, SoundType.SAND);
	public static final Block BASALT = new Basalt("basalt", Material.ROCK, "pickaxe", 0, 1.25F, 30, InitCreativeTabs.TAB_NETHER_PLUS, SoundType.STONE);
	public static final Block NYLIUM = new Nylium("nylium", Material.ROCK, "pickaxe", 0, 1, 2, InitCreativeTabs.TAB_NETHER_PLUS, SoundType.STONE);
	public static final Block FUNGUS = new Fungus("fungus", 2, InitCreativeTabs.TAB_NETHER_PLUS, SoundType.STONE);
	public static final Block STEM = new Stem("stem", NONFLAMMABLE_WOOD, 2, 10, InitCreativeTabs.TAB_NETHER_PLUS, SoundType.WOOD);
	public static final Block PLANKS = new Planks("planks", NONFLAMMABLE_WOOD, "axe", 0, 2, 15, InitCreativeTabs.TAB_NETHER_PLUS, SoundType.WOOD);
	public static final Block WARPED_WART_BLOCK = new WarpedWartBlock("warped_wart_block", Material.CORAL, 1, 5, InitCreativeTabs.TAB_NETHER_PLUS, SoundType.WOOD);
	public static final Block SHROOMLIGHT = new BaseBlock("shroomlight", Material.WOOD, "axe", 0, 1, 10, InitCreativeTabs.TAB_NETHER_PLUS, SoundType.STONE).setLightLevel(1.0F);
	public static final Block ROOTS = new Roots("roots", Material.VINE, 0, InitCreativeTabs.TAB_NETHER_PLUS, SoundType.PLANT);
	public static final Block TWISTING_VINES = new TwistingVines("twisting_vines", 0, InitCreativeTabs.TAB_NETHER_PLUS, SoundType.PLANT);
	public static final Block WEEPING_VINES = new WeepingVines("weeping_vines", 0, InitCreativeTabs.TAB_NETHER_PLUS, SoundType.PLANT);
	public static final Block NETHER_SPROUTS = new NetherSprouts("nether_sprouts", Material.VINE, 0, InitCreativeTabs.TAB_NETHER_PLUS, SoundType.PLANT);
}
