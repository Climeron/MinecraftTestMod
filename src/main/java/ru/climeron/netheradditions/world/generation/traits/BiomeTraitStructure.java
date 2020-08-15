package ru.climeron.netheradditions.world.generation.traits;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import ru.climeron.netheradditions.utils.helpers.RandomHelper;
import ru.climeron.netheradditions.utils.helpers.StructureHelper;

public class BiomeTraitStructure extends BiomeTrait
{
    protected List<ResourceLocation> structures;
    protected StructureType structureType;
    protected Mirror mirror;
    protected Rotation rotation;
    protected Block ignoredBlock;
    protected double clearancePercentage;
    protected boolean orientRandomly;

    protected BiomeTraitStructure(Builder builder)
    {
        super(builder);
        this.structures = builder.structures;
        this.structureType = builder.structureType;
        this.mirror = builder.mirror;
        this.rotation = builder.rotation;
        this.ignoredBlock = builder.ignoredBlock;
        this.clearancePercentage = builder.clearancePercentage;
        this.orientRandomly = builder.orientRandomly;
    }

    public static BiomeTraitStructure create(Consumer<Builder> consumer)
    {
        Builder builder = new Builder();
        consumer.accept(builder);
        return builder.create();
    }
    
    @Override
    public boolean generate(World world, BlockPos pos, Random random)
    {
        if(this.structures == null || this.structureType == null || this.ignoredBlock == null)
        {
            return false;
        }

        if(this.orientRandomly)
        {
            this.mirror = RandomHelper.getEnum(Mirror.class, random);
            this.rotation = RandomHelper.getEnum(Rotation.class, random);
        }

        MinecraftServer server = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        Template template = templateManager.get(server, this.structures.get(random.nextInt(this.structures.size())));

        if(template != null)
        {
            PlacementSettings placementSettings = new PlacementSettings().setMirror(this.mirror).setRotation(this.rotation).setReplacedBlock(this.ignoredBlock).setRandom(random);
            BlockPos structureSize = template.transformedSize(placementSettings.getRotation());
            BlockPos spawnPos = null;

            if(this.structureType == StructureType.GROUND)
            {
                spawnPos = StructureHelper.getGroundPos(world, pos, structureSize, this.clearancePercentage);
            }
            else if(this.structureType == StructureType.AIR)
            {
                spawnPos = StructureHelper.getAirPos(world, pos, structureSize, this.clearancePercentage);
            }
            else if(this.structureType == StructureType.BURIED)
            {
                spawnPos = StructureHelper.getBuriedPos(world, pos, structureSize, this.clearancePercentage);
            }
            else if(this.structureType == StructureType.CEILING)
            {
                spawnPos = StructureHelper.getCeilingPos(world, pos, structureSize, this.clearancePercentage);
            }

            if(spawnPos != null && spawnPos.getY() >= this.minimumGenerationHeight && spawnPos.getY() <= this.maximumGenerationHeight)
            {
                template.addBlocksToWorld(world, spawnPos, placementSettings.copy());
                this.handleDataBlocks(world, spawnPos, template, placementSettings.copy(), random);
                return true;
            }
        }

        if(this.orientRandomly)
        {
            this.mirror = null;
            this.rotation = null;
        }

        return false;
    }

    private void handleDataBlocks(World world, BlockPos pos, Template template, PlacementSettings placementSettings, Random random)
    {
        Map<BlockPos, String> map = template.getDataBlocks(pos, placementSettings);

        for(Map.Entry<BlockPos, String> entry : map.entrySet())
        {
            BlockPos dataPos = entry.getKey();
            String[] data = entry.getValue().split("\\s+");

            if(data[0].equals("chest") && data.length == 2)
            {
                world.setBlockState(dataPos, Blocks.CHEST.correctFacing(world, dataPos, Blocks.CHEST.getDefaultState()));
                TileEntityChest chest = (TileEntityChest) world.getTileEntity(dataPos);

                if(chest != null)
                {
                    chest.setLootTable(new ResourceLocation(data[1]), random.nextLong());
                }
            }
            else if(data[0].equals("spawner") && data.length == 2)
            {
                world.setBlockState(dataPos, Blocks.MOB_SPAWNER.getDefaultState());
                TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getTileEntity(dataPos);

                if(spawner != null)
                {
                    MobSpawnerBaseLogic logic = spawner.getSpawnerBaseLogic();
                    NBTTagCompound compound = new NBTTagCompound();
                    logic.writeToNBT(compound);
                    compound.removeTag("SpawnPotentials");
                    logic.readFromNBT(compound);
                    logic.setEntityId(new ResourceLocation(data[1]));
                    spawner.markDirty();
                    IBlockState state = world.getBlockState(dataPos);
                    world.notifyBlockUpdate(pos, state, state, 3);
                }
            }
            else if(data[0].equals("entity") && data.length == 2)
            {
                Entity entity = EntityList.newEntity(EntityList.getClass(new ResourceLocation(data[1])), world);

                if(entity != null)
                {
                    entity.setPosition(dataPos.getX() + 0.5F, dataPos.getY(), dataPos.getZ() + 0.5F);

                    if(world.spawnEntity(entity) && entity instanceof EntityLiving)
                    {
                        ((EntityLiving) entity).onInitialSpawn(world.getDifficultyForLocation(dataPos), null);
                    }
                }
            }
            else
            {
                world.setBlockToAir(dataPos);
            }
        }
    }

    public static class Builder extends BiomeTrait.Builder
    {
        private List<ResourceLocation> structures;
        private StructureType structureType;
        private Mirror mirror;
        private Rotation rotation;
        private Block ignoredBlock;
        private double clearancePercentage;
        private boolean orientRandomly;

        public Builder()
        {
            this.structures = Collections.singletonList(new ResourceLocation("minecraft:missing_no"));
            this.structureType = StructureType.GROUND;
            this.mirror = Mirror.NONE;
            this.rotation = Rotation.NONE;
            this.ignoredBlock = Blocks.STRUCTURE_VOID;
            this.clearancePercentage = 0.75D;
            this.orientRandomly = true;
        }

        public Builder structures(List<ResourceLocation> structures)
        {
            this.structures = structures;
            return this;
        }

        public Builder structureType(StructureType structureType)
        {
            this.structureType = structureType;
            return this;
        }

        public Builder mirror(Mirror mirror)
        {
            this.mirror = mirror;
            return this;
        }

        public Builder rotation(Rotation rotation)
        {
            this.rotation = rotation;
            return this;
        }

        public Builder ignoredBlock(Block ignoredBlock)
        {
            this.ignoredBlock = ignoredBlock;
            return this;
        }

        public Builder clearancePercentage(double clearancePercentage)
        {
            this.clearancePercentage = clearancePercentage;
            return this;
        }

        public Builder orientRandomly(boolean orientRandomly)
        {
            this.orientRandomly = orientRandomly;
            return this;
        }

        @Override
        public BiomeTraitStructure create()
        {
            return new BiomeTraitStructure(this);
        }
    }

    public enum StructureType
    {
        GROUND,
        AIR,
        CEILING,
        BURIED
    }
}
