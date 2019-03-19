package Bibi_ElGareh_Team.actions;
import EDU.gatech.cc.is.util.Vec2;
import static EDU.gatech.cc.is.abstractrobot.ControlSystemS.CSSTAT_OK;
import Bibi_ElGareh_Team.*;

/**
 * Action du joueur sur le terrain, il peut tirer ou non.
 */
public abstract class Action {
    /**
     * @return valeur de CSSTAT_OK
     */
    public abstract int action();
}
