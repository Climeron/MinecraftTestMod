package ru.climeron.netheradditions.world.generation.traits;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.climeron.netheradditions.utils.helpers.RandomHelper;

public abstract class BiomeTrait
{
    protected int generationAttempts;
    protected boolean randomizeGenerationAttempts;
    protected double generationProbability;
    protected int minimumGenerationHeight;
    protected int maximumGenerationHeight;

    protected BiomeTrait(Builder builder)
    {
        this.generationAttempts = builder.generationAttempts;
        this.randomizeGenerationAttempts = builder.randomizeGenerationAttempts;
        this.generationProbability = builder.generationProbability;
        this.minimumGenerationHeight = builder.minimumGenerationHeight;
        this.maximumGenerationHeight = builder.maximumGenerationHeight;
    }

    public abstract boolean generate(World world, BlockPos pos, Random random);


    public boolean useRandomizedGenerationAttempts()
    {
        return this.randomizeGenerationAttempts;
    }

    public int getGenerationAttempts(World world, BlockPos pos, Random random)
    {
        int attempts = 0;

        if(this.generationProbability >= random.nextDouble())
        {
            attempts = this.generationAttempts;

            if(this.randomizeGenerationAttempts)
            {
                attempts = RandomHelper.getNumberInRange(1, attempts, random);
            }
        }

        return attempts;
    }

    public double getGenerationProbability(World world, BlockPos pos, Random random)
    {
        return this.generationProbability;
    }

    public int getMinimumGenerationHeight(World world, BlockPos pos, Random random)
    {
        return this.minimumGenerationHeight;
    }

    public int getMaximumGenerationHeight(World world, BlockPos pos, Random random)
    {
        return this.maximumGenerationHeight;
    }

    public static abstract class Builder<T extends BiomeTrait>
    {
        protected int generationAttempts;
        protected boolean randomizeGenerationAttempts;
        protected double generationProbability;
        protected int minimumGenerationHeight;
        protected int maximumGenerationHeight;

        public Builder()
        {
            this.generationAttempts = 4;
            this.randomizeGenerationAttempts = false;
            this.generationProbability = 1.0D;
            this.minimumGenerationHeight = 2;
            this.maximumGenerationHeight = 60;
        }

        public Builder<?> generationAttempts(int generationAttempts)
        {
            this.generationAttempts = generationAttempts;
            return this;
        }

        public Builder<?> randomizeGenerationAttempts(boolean randomizeGenerationAttempts)
        {
            this.randomizeGenerationAttempts = randomizeGenerationAttempts;
            return this;
        }

        public Builder<?> generationProbability(double generationProbability)
        {
            this.generationProbability = generationProbability;
            return this;
        }

        public Builder<?> minimumGenerationHeight(int minimumGenerationHeight)
        {
            this.minimumGenerationHeight = minimumGenerationHeight;
            return this;
        }

        public Builder<?> maximumGenerationHeight(int maximumGenerationHeight)
        {
            this.maximumGenerationHeight = maximumGenerationHeight;
            return this;
        }

        public abstract T create();
    }
}
