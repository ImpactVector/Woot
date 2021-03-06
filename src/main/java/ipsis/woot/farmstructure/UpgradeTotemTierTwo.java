package ipsis.woot.farmstructure;

import ipsis.woot.util.EnumFarmUpgrade;
import ipsis.woot.util.EnumSpawnerUpgrade;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UpgradeTotemTierTwo extends AbstractUpgradeTotem {

    @Override
    public void scan() {

        if (!world.isBlockLoaded(origin))
            return;

        EnumSpawnerUpgrade baseUpgrade = TotemHelper.getUpgrade(world, origin, 1);
        if (baseUpgrade == null)
            return;

        if (TotemHelper.getTier(world, origin, 1) != 1)
            return;

        spawnerUpgrade = baseUpgrade;
        spawnerUpgradeLevel = 1;
        blockPosList.add(new BlockPos(origin));

        // Tier 2
        EnumSpawnerUpgrade t2 = TotemHelper.getUpgrade(world, origin, 2);
        if (t2 == null ||
                (EnumFarmUpgrade.getFromEnumSpawnerUpgrade(t2) != EnumFarmUpgrade.getFromEnumSpawnerUpgrade(spawnerUpgrade)) ||
                (TotemHelper.getTier(world, origin, 2) != 2))
            return;

        spawnerUpgradeLevel = 2;
        blockPosList.add(new BlockPos(origin).up(1));
    }

    public UpgradeTotemTierTwo(World world, BlockPos pos) {
        super(world, pos);
    }
}
