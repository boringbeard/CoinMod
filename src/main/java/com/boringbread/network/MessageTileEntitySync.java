package com.boringbread.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageTileEntitySync implements IMessage
{
    public NBTTagCompound data;

    public MessageTileEntitySync(){}

    public MessageTileEntitySync(NBTTagCompound data)
    {
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeTag(buf, data);
    }

    public static class Handler implements IMessageHandler<MessageTileEntitySync, IMessage>
    {
        @Override
        public IMessage onMessage(MessageTileEntitySync message, MessageContext ctx)
        {
            NBTTagCompound tag = message.data;
            if(tag.hasKey("x") && tag.hasKey("y") && tag.hasKey("z"))
            {
                BlockPos pos = new BlockPos(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));
                World world = ctx.side.isClient() ? Minecraft.getMinecraft().world : ctx.getServerHandler().player.world;

                if (world.isBlockLoaded(pos))
                {
                    if (world.getTileEntity(pos) != null)
                    {
                        TileEntity te = world.getTileEntity(pos);
                        te.readFromNBT(message.data);
                    }
                }
            }
            return null;
        }
    }
}
