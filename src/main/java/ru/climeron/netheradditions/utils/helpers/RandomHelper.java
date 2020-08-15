package ru.climeron.netheradditions.utils.helpers;

import java.util.Random;
import java.util.UUID;

public class RandomHelper
{
    private static final Random RANDOM = new Random();

    public static int getNumberInRange(int min, int max)
    {
        return getNumberInRange(min, max, RANDOM);
    }

    public static int getNumberInRange(int min, int max, Random random)
    {
        if(min > max)
        {
            int temp = min;
            min = max;
            max = temp;
        }

        return random.nextInt(max - min + 1) + min;
    }

    public static <E extends Enum> E getEnum(Class<? extends E> cls)
    {
        return getEnum(cls, RANDOM);
    }

    public static <E extends Enum> E getEnum(Class<? extends E> cls, Random random)
    {
        return cls.getEnumConstants()[random.nextInt(cls.getEnumConstants().length)];
    }

    public static UUID getUUID()
    {
        return getUUID(RANDOM);
    }

    public static UUID getUUID(Random random)
    {
        long mostSignificantBits = random.nextLong() & -61441L | 16384L;
        long leastSignificantBits = random.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
        return new UUID(mostSignificantBits, leastSignificantBits);
    }

    public static Random getRandom()
    {
        return RANDOM;
    }
}