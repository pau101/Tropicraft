package net.tropicraft.core.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.tropicraft.core.common.Util;
import net.tropicraft.core.common.block.tileentity.TileEntityFactory;
import net.tropicraft.core.common.block.tileentity.TileEntitySifter;
import net.tropicraft.core.registry.BlockRegistry;

public class BlockSifter extends BlockTropicraft implements ITileEntityProvider {

	public BlockSifter() {
		super(Material.WOOD);
		this.blockHardness = 1.0F;
		this.blockResistance = 4.0F;
	}

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Nullable
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BlockRegistry.sifter);
    }

	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
	
	@Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

	@Override
	public boolean isFullyOpaque(IBlockState state) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entityPlayer, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}

		TileEntitySifter tileentitysifta = (TileEntitySifter) world.getTileEntity(pos);

        if (tileentitysifta != null && !Util.isEmpty(heldItem) && !tileentitysifta.isSifting()) {
            Item item = heldItem.getItem();
            if (item == Item.getItemFromBlock(Blocks.SAND) || (item == Item.getItemFromBlock(BlockRegistry.sands))) {
                tileentitysifta.addItemToSifter(heldItem);
                tileentitysifta.startSifting();
                --heldItem.stackSize;
            }
        }
		return true;
	} // /o/ \o\ /o\ \o\ /o\ \o/ /o/ /o/ \o\ \o\ /o/ /o/ \o/ /o\ \o/ \o/ /o\ /o\ \o/ \o/ /o/ \o\o\o\o\o\o\o\o\o\ :D

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return TileEntityFactory.getSifterTE();
	}

}
