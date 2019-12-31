package tk.dczippl.lightestlamp.tile;

import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModTileEntities;

public class DeltaLampTileEntity extends TileEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    public DeltaLampTileEntity(TileEntityType<?> type)
    {
        super(type);
    }

    public DeltaLampTileEntity()
    {
        super(ModTileEntities.DELTA_TE);
    }

    @Override
    public void tick()
    {
        //TODO: Change this to DELTA
        if (world.isRemote) return;

        cooldown++;

        if (cooldown == 5)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 3).offset(Direction.NORTH, 3).offset(Direction.WEST, 3),
            pos.offset(Direction.DOWN, 3).offset(Direction.SOUTH, 3).offset(Direction.EAST, 3)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.getDefaultState());
                }
            });

            cooldown = 0;
        }
    }

    private boolean isAir(BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR || world.getBlockState(pos).getBlock() == ModBlocks.LIGHT_AIR;
    }
}