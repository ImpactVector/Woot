package ipsis.woot.util;

import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;

public class ConfigKeyHelper {

    private static EnumConfigKey getEntryFromLevel(int level, EnumSet<EnumConfigKey> keys) {

        for (EnumConfigKey key : keys) {
            if (key.getLevel() == level)
                return key;
        }

        return (EnumConfigKey)keys.toArray()[0];
    }

    /**
     * Power
     */
    public static EnumConfigKey getPowerPerTick(EnumMobFactoryTier tier) {

        int level = tier.ordinal();
        return getEntryFromLevel(level, EnumConfigKey.POWER_PER_TICK);
    }

    public static EnumConfigKey getRatePowerPerTick(int level) {

        level = MathHelper.clamp(level, 1, 3);
        return getEntryFromLevel(level, EnumConfigKey.RATE_POWER_PER_TICK);
    }

    public static EnumConfigKey getMassPowerPerTick(int level) {

        level = MathHelper.clamp(level, 1, 3);
        return getEntryFromLevel(level, EnumConfigKey.MASS_POWER_PER_TICK);
    }

    public static EnumConfigKey getLootingPowerPerTick(int level) {

        level = MathHelper.clamp(level, 1, 3);
        return getEntryFromLevel(level, EnumConfigKey.LOOTING_POWER_PER_TICK);
    }

    public static EnumConfigKey getDecapPowerPerTick(int level) {

        level = MathHelper.clamp(level, 1, 3);
        return getEntryFromLevel(level, EnumConfigKey.DECAP_POWER_PER_TICK);
    }

    public static EnumConfigKey getXpPowerPerTick(int level) {

        level = MathHelper.clamp(level, 1, 3);
        return getEntryFromLevel(level, EnumConfigKey.XP_POWER_PER_TICK);
    }

    public static EnumConfigKey getEffPowerPerTick(int level) {

        level = MathHelper.clamp(level, 1, 3);
        return getEntryFromLevel(level, EnumConfigKey.EFF_POWER_PER_TICK);
    }

    public static EnumConfigKey getBmPowerPerTick(int level) {

        level = MathHelper.clamp(level, 1, 3);
        return getEntryFromLevel(level, EnumConfigKey.BM_POWER_PER_TICK);
    }

    /**
     * Param
     */
    public static EnumConfigKey getRateParam(int level) {

        level = MathHelper.clamp(level, 1, 3);
        return getEntryFromLevel(level, EnumConfigKey.RATE_PARAM);
    }

    public static EnumConfigKey getMassParam(int level) {

        level = MathHelper.clamp(level, 1, 3);
        return getEntryFromLevel(level, EnumConfigKey.MASS_PARAM);
    }

    public static EnumConfigKey getLootingParam(int level) {

        level = MathHelper.clamp(level, 1, 3);
        return getEntryFromLevel(level, EnumConfigKey.LOOTING_PARAM);
    }

    public static EnumConfigKey getDecapParam(int level) {

        level = MathHelper.clamp(level, 1, 3);
        return getEntryFromLevel(level, EnumConfigKey.DECAP_PARAM);
    }

    public static EnumConfigKey getXpParam(int level) {

        level = MathHelper.clamp(level, 1, 3);
        return getEntryFromLevel(level, EnumConfigKey.XP_PARAM);
    }

    public static EnumConfigKey getEffParam(int level) {

        level = MathHelper.clamp(level, 1, 3);
        return getEntryFromLevel(level, EnumConfigKey.EFF_PARAM);
    }

    public static EnumConfigKey getBmParam1(int level) {

        level = MathHelper.clamp(level, 1, 3);
        return getEntryFromLevel(level, EnumConfigKey.BM_PARAM_1);
    }

    public static EnumConfigKey getBmParam2(int level) {

        level = MathHelper.clamp(level, 1, 3);
        return getEntryFromLevel(level, EnumConfigKey.BM_PARAM_2);
    }
}
