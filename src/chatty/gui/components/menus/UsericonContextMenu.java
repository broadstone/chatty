
package chatty.gui.components.menus;

import chatty.Chatty;
import static chatty.gui.components.menus.ContextMenuHelper.ICON_WEB;
import chatty.util.StringUtil;
import chatty.util.api.usericons.Usericon;
import java.awt.event.ActionEvent;

/**
 *
 * @author tduva
 */
public class UsericonContextMenu extends ContextMenu {

    private final ContextMenuListener listener;
    private final Usericon usericon;
    
    public UsericonContextMenu(Usericon usericon, ContextMenuListener listener) {
        this.listener = listener;
        this.usericon = usericon;
        
        //--------------------
        // General Description
        //--------------------
        if (usericon.metaTitle.isEmpty()) {
            addItem("badgeImage", "Badge: "+usericon.type.label, ContextMenuHelper.ICON_IMAGE);
        } else {
            addItem("badgeImage", "Badge: "+usericon.metaTitle, ContextMenuHelper.ICON_IMAGE);
            if (!usericon.metaTitle.equals(usericon.metaDescription) && !usericon.metaDescription.isEmpty()) {
                addItem("", StringUtil.shortenTo(usericon.metaDescription, 30));
            }
        }
        
        //--------
        // Submenu
        //--------
        String infoMenu = "More..";
        if (usericon.source == Usericon.SOURCE_CUSTOM) {
            infoMenu = "Custom Usericon";
        }
        if (!usericon.badgeType.isEmpty()) {
            addSubItem("copyBadgeType", "ID/Version: "+usericon.badgeType.toString(), infoMenu, ContextMenuHelper.ICON_COPY);
            if (usericon.source == Usericon.SOURCE_TWITCH2) {
                // Only show add options if original Twitch emote (custom emote
                // would already be added)
                addSeparator(infoMenu);
                addSubItem("addUsericonOfBadgeType", "Change/Hide ("+usericon.badgeType+")", infoMenu);
                addSubItem("addUsericonOfBadgeTypeId", "Change/Hide (all "+usericon.badgeType.id+" variants)", infoMenu);
            }
        }
        if (usericon.restriction != null) {
            if (usericon.restriction.isEmpty()) {
                addSubItem("", "No Restriction", infoMenu);
            } else {
                addSubItem("", "Restriction: "+usericon.restriction, infoMenu);
            }
        }
        if (!usericon.channelRestriction.isEmpty()) {
            addSubItem("", "Channel: "+usericon.channelRestriction, infoMenu);
        }
        
        //---------
        // Meta URL
        //---------
        if (!usericon.metaUrl.isEmpty()) {
            addSeparator();
            addItem("usericonUrl", "Click for info", ICON_WEB);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (listener != null) {
            listener.usericonMenuItemClicked(e, usericon);
        }
    }
    
}
