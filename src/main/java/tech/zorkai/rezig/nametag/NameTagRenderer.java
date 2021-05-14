package tech.zorkai.rezig.nametag;

import net.labymod.api.events.RenderEntityEvent;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.labymod.user.group.EnumGroupDisplayType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.lwjgl.opengl.GL11;
import tech.zorkai.rezig.REZig;
import tech.zorkai.rezig.utils.TextUtils;

public class NameTagRenderer implements RenderEntityEvent {

    private final REZig main;
    private final JSONObject nametags = new JSONObject(
            new JSONTokener(
                    REZig.class.getResourceAsStream("/nametags.json")
            ));
    private final TextUtils textUtils = new TextUtils();
    private final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();

    public NameTagRenderer(REZig main) {
        this.main = main;
    }

    @Override
    public void onRender(Entity entity, double x, double y, double z, float partialTicks) {
        if (!(entity instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) entity;

        if (player.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer) || entity.riddenByEntity != null)
            return;
        if (player.isSneaking())
            return;

        double distance = player.getDistanceSqToEntity(renderManager.livingPlayer);

        if (distance > (double) (64.0F * 64.0F))
            return;

        this.processRendering(player, x, y, z, distance);
    }

    private String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
        return networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() :
                ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
    }

    private boolean isToggled(){
        //return in game color != rank color from api
        return false;
    }

    private void processRendering(EntityPlayer player, double x, double y, double z, double distance) {

        if (!main.playingRevive)
            return;

        String name = player.getName();

        /* waiting for api
        NetHandlerPlayClient nethandlerplayclient = Minecraft.getMinecraft().thePlayer.sendQueue;
        for (NetworkPlayerInfo networkplayerinfo : nethandlerplayclient.getPlayerInfoMap()) {
            String renderedName = getPlayerName(networkplayerinfo);
            if (textUtils.removeChars(renderedName).equals(name)){
                break;
            }
        }
        */

        if (nametags.has(name)){
            this.renderTag(player, x, this.modifyHeightWithUserdata(player, y, distance), z, nametags.getString(name));
        }
    }

    private double modifyHeightWithUserdata(EntityPlayer player, double currentY, double distance) {
        User user = LabyMod.getInstance().getUserManager().getUser(player.getUniqueID());

        double BELOW_NAME_SCOREBOARD_MODIFIER = 1.15F * 0.02666667F;
        if (distance < 100.0D && player.getWorldScoreboard().getObjectiveInDisplaySlot(2) != null)
            currentY += LabyMod.getInstance().getDrawUtils().fontRenderer.FONT_HEIGHT * BELOW_NAME_SCOREBOARD_MODIFIER;

        if (user == null)
            return currentY;

        if (LabyMod.getSettings().cosmetics)
            currentY += user.getMaxNameTagHeight();

        double ADDITIONAL_GROUP_HEIGHT = 0.15D;
        if (user.getGroup() != null && user.getGroup().getDisplayType() == EnumGroupDisplayType.ABOVE_HEAD)
            currentY += ADDITIONAL_GROUP_HEIGHT;

        double SUBTITLE_DIVIDE = 6.0D;
        if (user.getSubTitle() != null)
            currentY += user.getSubTitleSize() / SUBTITLE_DIVIDE;

        return currentY;
    }

    private void renderTag(EntityPlayer player, double x, double y, double z, String tag) {
        float fixedPlayerViewX = renderManager.playerViewX * (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1 : 1);
        FontRenderer fontRenderer = renderManager.getFontRenderer();
        GlStateManager.pushMatrix();

        double TAG_HEIGHT = 0.70D;
        GlStateManager.translate((float) x, (float) y + player.height + TAG_HEIGHT, (float) z);
        GL11.glNormal3f(0F, 1F, 0F);
        GlStateManager.rotate(-renderManager.playerViewY, 0F, 1F, 0F);
        GlStateManager.rotate(fixedPlayerViewX, 1F, 0F, 0F);
        float scale = 0.016666668F;
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate( 770, 771, 1, 0 );
        GlStateManager.enableTexture2D();
        fontRenderer.drawString(tag, -fontRenderer.getStringWidth(tag) / 2, 0, 553648127);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        fontRenderer.drawString(tag, -fontRenderer.getStringWidth(tag) / 2, 0, -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color( 1.0F, 1.0F, 1.0F, 1.0F );
        GlStateManager.popMatrix();
    }

}
