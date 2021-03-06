package ipsis.woot.command;

import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Localization;
import ipsis.woot.tileentity.TileEntityMobFactoryController;
import ipsis.woot.util.WootMob;
import ipsis.woot.util.WootMobBuilder;
import ipsis.woot.util.WootMobName;
import ipsis.woot.util.WootMobNameBuilder;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandGive extends CommandTreeBase {

    @Override
    public String getName() {

        return "give";
    }

    @Override
    public String getUsage(ICommandSender sender) {

        return Localization.TAG_COMMAND + "give.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length < 3)
            throw new WrongUsageException(getUsage(sender));

        EntityPlayer entityplayer = getPlayer(server, sender, args[0]);
        WootMob wootMob = WootMobBuilder.create(args[1], args[2]);
        if (wootMob.isValid()) {
            ItemStack itemStack = TileEntityMobFactoryController.getProgrammedStack(wootMob);

            /* straight from the CommandGive code */
            boolean flag = entityplayer.inventory.addItemStackToInventory(itemStack);

            if (flag)
            {
            /* added to the players inventory */
                entityplayer.world.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
                        SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((entityplayer.getRNG().nextFloat() - entityplayer.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                entityplayer.inventoryContainer.detectAndSendChanges();
            }

            if (flag && itemStack.isEmpty())
            {
                itemStack.setCount(1);
                sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 1);
                EntityItem entityitem1 = entityplayer.dropItem(itemStack, false);

                if (entityitem1 != null)
                {
                    entityitem1.makeFakeItem();
                }
            }
            else
            {
                sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 1 - itemStack.getCount());
                EntityItem entityitem = entityplayer.dropItem(itemStack, false);

                if (entityitem != null)
                {
                    entityitem.setNoPickupDelay();
                    entityitem.setOwner(entityplayer.getName());
                }
            }


        } else {
            LogHelper.info(Localization.INVALID_MOB_NAME);
        }
    }
}
