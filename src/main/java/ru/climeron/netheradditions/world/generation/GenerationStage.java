package ru.climeron.netheradditions.world.generation;

public enum GenerationStage
{
    TERRAIN_ALTERATION("terrain_alteration"),
    DECORATION("decoration"),
    PLANT_DECORATION("plant_decoration"),
    ORE("ore"),
    STRUCTURE("structure");

    private String identifier;

    GenerationStage(String identifier)
    {
        this.identifier = identifier;
    }

    public static GenerationStage getFromIdentifier(String identifier)
    {
        for(GenerationStage stage : values())
        {
            if(stage.toString().equals(identifier))
            {
                return stage;
            }
        }

        return DECORATION;
    }

    @Override
    public String toString()
    {
        return this.identifier;
    }
}
