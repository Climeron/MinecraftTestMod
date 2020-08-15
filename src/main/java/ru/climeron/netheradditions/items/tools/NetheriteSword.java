package ru.climeron.netheradditions.items.tools;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import ru.climeron.netheradditions.init.InitCreativeTabs;
import ru.climeron.netheradditions.init.InitItems;
import ru.climeron.netheradditions.main.Main;
import ru.climeron.netheradditions.utils.interfaces.IHasModel;

public class NetheriteSword extends ItemSword implements IHasModel
{
	private double attackDamage;
	public NetheriteSword(String name, ToolMaterial material) {
		super(material);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(InitCreativeTabs.TAB_NETHER_PLUS);
		
		InitItems.ITEMS.add(this);
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