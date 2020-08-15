package ru.climeron.netheradditions.items.armor;

import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.GenericEvent;
import ru.climeron.netheradditions.init.InitCreativeTabs;
import ru.climeron.netheradditions.init.InitItems;
import ru.climeron.netheradditions.main.Main;
import ru.climeron.netheradditions.utils.interfaces.IHasModel;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class NetheriteArmor extends ItemArmor implements IHasModel
{
	private static final UUID[] ARMOR_MODIFIERS = new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
	public NetheriteArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(InitCreativeTabs.TAB_NETHER_PLUS);
		
		InitItems.ITEMS.add(this);
	}
	
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == this.armorType)
        {
            multimap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", 0.25F, 0));
        }

        return multimap;
    }
	
	@Override
	public boolean hasCustomEntity(ItemStack stack)
    {
        return true;
    }
	
	@Nullable
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack)
	{
		if(!world.isRemote)
		{
			EntityItem item = new EntityItem(world, location.posX, location.posY, location.posZ, itemstack)
	        {
	            @Override
	            public void setFire(int seconds)
	            {
	            }

	            @Override
	            protected void setOnFireFromLava()
	            {
	            }

	            @Override
	            public boolean attackEntityFrom(DamageSource source, float amount)
	            {
	                if (source.isFireDamage())
	                {
	                    return false;
	                }
	                return super.attackEntityFrom(source, amount);
	            } 
	            
	        };
	        
	        item.copyLocationAndAnglesFrom(location);
	        item.motionX = location.motionX;
	        item.motionY = location.motionY;
	        item.motionZ = location.motionZ;
	        item.setPickupDelay(30);
	        return item;
		}
        return null;
    }
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}