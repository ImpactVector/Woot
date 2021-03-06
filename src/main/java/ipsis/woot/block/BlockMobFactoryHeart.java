package ipsis.woot.block;

import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.oss.ItemHelper;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.plugins.top.ITOPInfoProvider;
import ipsis.woot.plugins.top.TOPUIInfoConvertors;
import ipsis.woot.tileentity.IMobFarm;
import ipsis.woot.tileentity.TileEntityMobFactoryHeart;
import ipsis.woot.tileentity.ui.FarmUIInfo;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockMobFactoryHeart extends BlockWoot implements ITooltipInfo, ITileEntityProvider, ITOPInfoProvider {

    public static final String BASENAME = "factory";
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockMobFactoryHeart() {

        super(Material.ROCK, BASENAME);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityMobFactoryHeart();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        EnumFacing f = placer.getHorizontalFacing().getOpposite();
        worldIn.setBlockState(pos, state.withProperty(FACING, f), 2);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerBlock(ModBlocks.blockFactoryHeart, BASENAME);
    }

    @Override
    public void getTooltip(List<String> toolTip, boolean showAdvanced, int meta, boolean detail) {

    }

    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
            enumfacing = EnumFacing.NORTH;

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    public int getMetaFromState(IBlockState state)
    {

        return state.getValue(FACING).getIndex();
    }

    protected BlockStateContainer createBlockState()
    {

        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {

        TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof IMobFarm) {
            FarmUIInfo info = new FarmUIInfo();
            ((IMobFarm) te).getUIInfo(info);
            if (info.isValid)
                TOPUIInfoConvertors.farmConvertor(info, mode, probeInfo, player, world, blockState, data);
        }
    }

    public static class PluginTooltipInfo {

        private PluginTooltipInfo() { }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote)
            return true;

        ItemStack heldItem = playerIn.getHeldItemMainhand();
        if (heldItem.isEmpty()) {
            LogHelper.info("onBlockActivated: dump status to conosle");
            return true;
        }

        if (ItemHelper.areItemsEqual(heldItem.getItem(), Item.getItemFromBlock(Blocks.TORCH))) {

            EnumMobFactoryTier tier;
            if (heldItem.getCount() == 1)
                tier = EnumMobFactoryTier.TIER_ONE;
            else if (heldItem.getCount() == 2)
                tier = EnumMobFactoryTier.TIER_TWO;
            else if (heldItem.getCount() == 3)
                tier = EnumMobFactoryTier.TIER_THREE;
            else
                tier = EnumMobFactoryTier.TIER_FOUR;

            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TileEntityMobFactoryHeart)
                ((TileEntityMobFactoryHeart) te).manualFarmScan(playerIn, tier);

            return true;
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
