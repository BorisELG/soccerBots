package Bibi_ElGareh_Team.actions;
import EDU.gatech.cc.is.util.Vec2;
import static EDU.gatech.cc.is.abstractrobot.ControlSystemS.CSSTAT_OK;
import Bibi_ElGareh_Team.*;


public class ShootAction extends Action {
    private GameContext gameContext;
    private Vec2 axis;
    private boolean shoot;

    public ShootAction(GameContext gameContext, Vec2 axis, boolean shoot){

        this.gameContext = gameContext;
        this.axis = axis;
        this.shoot = shoot;

        this.action();

    }

    public int action(){

        this.gameContext.getAbstractRobot().setSteerHeading(this.gameContext.getCurrTime(), this.axis.t);

        this.gameContext.getAbstractRobot().setSpeed(this.gameContext.getCurrTime(), 1);

        if (this.shoot) {
            if (this.gameContext.getAbstractRobot().canKick(this.gameContext.getCurrTime())) {
                this.gameContext.getAbstractRobot().kick(this.gameContext.getCurrTime());
            }
        }

        return(CSSTAT_OK);
    }
}