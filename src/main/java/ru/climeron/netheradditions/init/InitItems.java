package ru.climeron.netheradditions.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import ru.climeron.netheradditions.items.BaseItem;
import ru.climeron.netheradditions.items.armor.NetheriteArmor;
import ru.climeron.netheradditions.items.tools.NetheriteAxe;
import ru.climeron.netheradditions.items.tools.NetheriteHoe;
import ru.climeron.netheradditions.items.tools.NetheritePickaxe;
import ru.climeron.netheradditions.items.tools.NetheriteShovel;
import ru.climeron.netheradditions.items.tools.NetheriteSword;
import ru.climeron.netheradditions.reference.Reference;

public class InitItems
{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//Items
	//public static final Item ITEM = new BaseItem(String name, CreativeTabs tab, int max_stack)
	public static final Item NETHERITE_SCRAP = new BaseItem("netherite_scrap", InitCreativeTabs.TAB_NETHER_PLUS, 64);
	public static final Item NETHERITE_INGOT = new BaseItem("netherite_ingot", InitCreativeTabs.TAB_NETHER_PLUS, 64);
	
	//Materials
	//public static final ToolMaterial TOOL_MATERIAL = EnumHelper.addToolMaterial(name, harvestLevel, maxUses, efficiency, damage, enchantability)
	public static final ToolMaterial NETHERITE_TOOL_MATERIAL = EnumHelper.addToolMaterial("netherite_tool_material", 4, 2031, 9.0F, 4.0F, 10).setRepairItem(new ItemStack(InitItems.NETHERITE_INGOT));
	//public static final ArmorMaterial ARMOR_MATERIAL = EnumHelper.addArmorMaterial(name, textureName, durability, reductionAmounts, enchantability, soundOnEquip, toughness)
	public static final ArmorMaterial NETHERITE_ARMOR_MATERIAL = EnumHelper.addArmorMaterial("netherite_armor_material", Reference.MODID + ":netherite",  37, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.0F).setRepairItem(new ItemStack(InitItems.NETHERITE_INGOT));
	
	//Tools
	public static final Item NETHERITE_SHOVEL = new NetheriteShovel("netherite_shovel", NETHERITE_TOOL_MATERIAL);
	public static final Item NETHERITE_PICKAXE = new NetheritePickaxe("netherite_pickaxe", NETHERITE_TOOL_MATERIAL);
	public static final Item NETHERITE_AXE = new NetheriteAxe("netherite_axe", NETHERITE_TOOL_MATERIAL);
	public static final Item NETHERITE_SWORD = new NetheriteSword("netherite_sword", NETHERITE_TOOL_MATERIAL);
	public static final Item NETHERITE_HOE = new NetheriteHoe("netherite_hoe", NETHERITE_TOOL_MATERIAL);
	
	//Armor
	public static final Item NETHERITE_HELMET = new NetheriteArmor("netherite_helmet", NETHERITE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD);
	public static final Item NETHERITE_CHESTPLATE = new NetheriteArmor("netherite_chestplate", NETHERITE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST);
	public static final Item NETHERITE_LEGGINGS = new NetheriteArmor("netherite_leggings", NETHERITE_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS);
	public static final Item NETHERITE_BOOTS = new NetheriteArmor("netherite_boots", NETHERITE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET);
}