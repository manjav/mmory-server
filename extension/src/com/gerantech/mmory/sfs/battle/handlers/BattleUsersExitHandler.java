package com.gerantech.mmory.sfs.battle.handlers;

import com.gerantech.mmory.core.battle.BattleField;
import com.gerantech.mmory.libs.BBGRoom;
import com.gerantech.mmory.libs.utils.BattleUtils;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

/**
 * Created by ManJav on 9/2/2017.
 */
public class BattleUsersExitHandler extends BaseServerEventHandler
{
    public void handleServerEvent(ISFSEvent arg) throws SFSException
    {
        User user = (User) arg.getParameter(SFSEventParam.USER);
        Object core = user.getSession().getProperty("core");
        if( core == null)
            return;

        BattleUtils bu = BattleUtils.getInstance();
        BBGRoom room = bu.find(bu.getGame(user).player.id, BattleField.STATE_0_WAITING, BattleField.STATE_4_ENDED);
        if( room == null )
            return;
        if( room.getPropertyAsInt("state") < BattleField.STATE_1_CREATED )
            BattleUtils.getInstance().remove(room);
        else
            BattleUtils.getInstance().leave(room, user);
    }
}